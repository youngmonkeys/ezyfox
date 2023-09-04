package com.tvd12.ezyfox.concurrent;

import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyRoundRobin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.tvd12.ezyfox.util.EzyProcessor.processSilently;
import static com.tvd12.ezyfox.util.EzyProcessor.processWithLogException;

public class EzyEventLoopGroup extends EzyLoggable {

    private final EzyRoundRobin<EventLoop> eventLoops;
    private final Map<EzyEventLoopEvent, EventLoop> eventLoopByEvent;

    public static final int DEFAULT_MAX_SLEEP_TIME = 3;

    public EzyEventLoopGroup(int numberOfThreads) {
        this(
            numberOfThreads,
            EzyThreadFactory.builder()
                .poolName("event-loop")
                .build()
        );
    }

    public EzyEventLoopGroup(
        int numberOfThreads,
        ThreadFactory threadFactory
    ) {
        this(
            DEFAULT_MAX_SLEEP_TIME,
            numberOfThreads,
            threadFactory
        );
    }

    public EzyEventLoopGroup(
        int maxSleepTime,
        int numberOfThreads,
        ThreadFactory threadFactory
    ) {
        eventLoopByEvent = new ConcurrentHashMap<>();
        final AtomicInteger index = new AtomicInteger();
        eventLoops = new EzyRoundRobin<>(
            () -> new EventLoop(
                index.getAndIncrement(),
                maxSleepTime,
                threadFactory
            ),
            numberOfThreads
        );
        for (int i = 0; i < numberOfThreads; ++i) {
            eventLoops.get().start();
        }
    }

    public void addEvent(EzyEventLoopEvent event) {
        final EventLoop eventLoop = eventLoops.get();
        eventLoopByEvent.put(
            event instanceof ScheduledEvent
                ? ((ScheduledEvent) event).event
                : event,
            eventLoop
        );
        eventLoop.addEvent(event);
    }

    public void addScheduleEvent(
        EzyEventLoopEvent event,
        long period
    ) {
        addScheduleEvent(event, 0, period);
    }

    public void addScheduleEvent(
        EzyEventLoopEvent event,
        long delayTime,
        long period
    ) {
        addEvent(new ScheduledEvent(event, delayTime, period));
    }

    public void addOneTimeEvent(
        Runnable task,
        long delayTime
    ) {
        final EzyEventLoopEvent wrapper = new EzyEventLoopEvent() {
            @Override
            public boolean call() {
                try {
                    task.run();
                } catch (Throwable e) {
                    logger.warn("call one time event error", e);
                }
                return false;
            }

            @Override
            public void onFinished() {
                eventLoopByEvent.remove(this);
            }
        };
        addEvent(
            new ScheduledEvent(
                wrapper,
                delayTime,
                delayTime
            )
        );
    }

    public void removeEvent(EzyEventLoopEvent event) {
        final EventLoop eventLoop = eventLoopByEvent.remove(event);
        if (eventLoop != null) {
            eventLoop.removeEvent(event);
        }
    }

    public void shutdown() {
        eventLoops.forEach(EventLoop::shutdownAndGet);
    }

    public List<EzyEventLoopEvent> shutdownAndGet() {
        final List<EzyEventLoopEvent> unfinishedEvents = new ArrayList<>();
        eventLoops.forEach(it ->
            unfinishedEvents.addAll(it.shutdownAndGet())
        );
        return unfinishedEvents;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
            .append("EzyEventLoopGroup{\n  numberOfEvents=")
            .append(eventLoopByEvent.size())
            .append(",\n  eventLoops=[");
        eventLoops.forEach(it ->
            builder.append("\n    ").append(it)
        );
        return builder.append("\n]}").toString();
    }

    private static final class EventLoop extends EzyLoggable {

        private final int index;
        private final int maxSleepTime;
        private final AtomicBoolean active;
        private final AtomicBoolean stopped;
        private final EzyFuture shutdownFuture;
        private final ThreadFactory threadFactory;
        private final List<EzyEventLoopEvent> removeEvents;
        private final Map<EzyEventLoopEvent, EzyEventLoopEvent> events;

        private EventLoop(
            int index,
            int maxSleepTime,
            ThreadFactory threadFactory
        ) {
            this.index = index;
            this.maxSleepTime = maxSleepTime;
            this.threadFactory = threadFactory;
            this.active = new AtomicBoolean();
            this.stopped = new AtomicBoolean();
            this.events = new ConcurrentHashMap<>();
            this.removeEvents = new ArrayList<>();
            this.shutdownFuture = new EzyFutureTask();
        }

        public void addEvent(EzyEventLoopEvent event) {
            if (!active.get()) {
                throw new IllegalStateException("event loop has stopped");
            }
            events.put(
                event instanceof ScheduledEvent
                    ? ((ScheduledEvent) event).event
                    : event,
                event
            );
        }

        public void removeEvent(EzyEventLoopEvent event) {
            synchronized (removeEvents) {
                removeEvents.add(event);
            }
        }

        private void doRemoveEvent(EzyEventLoopEvent event) {
            events.remove(
                event instanceof ScheduledEvent
                    ? ((ScheduledEvent) event).event
                    : event
            );
            processWithLogException(event::onRemoved, true);
        }

        public void start() {
            threadFactory.newThread(this::doStart)
                .start();
        }

        private void doStart() {
            active.set(true);
            final List<EzyEventLoopEvent> eventBuffers = new ArrayList<>();
            while (active.get()) {
                final long startTime = System.currentTimeMillis();
                eventBuffers.addAll(events.values());
                for (EzyEventLoopEvent event : eventBuffers) {
                    try {
                        if (event instanceof ScheduledEvent) {
                            final ScheduledEvent scheduledEvent = (ScheduledEvent) event;
                            if (scheduledEvent.isNotFireTime()) {
                                continue;
                            }
                        }
                        if (!event.call()) {
                            synchronized (removeEvents) {
                                removeEvents.add(event);
                            }
                            event.onFinished();
                        }
                    } catch (Throwable e) {
                        logger.error("fatal error on event loop with event: {}", event, e);
                    }
                }
                eventBuffers.clear();
                synchronized (removeEvents) {
                    for (EzyEventLoopEvent event : removeEvents) {
                        doRemoveEvent(event);
                    }
                    removeEvents.clear();
                }
                final long elapsedTime = System.currentTimeMillis() - startTime;
                final long sleepTime = maxSleepTime - elapsedTime;
                if (sleepTime > 0) {
                    try {
                        //noinspection BusyWait
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
            synchronized (this) {
                stopped.set(true);
                shutdownFuture.setResult(true);
            }
        }

        public List<EzyEventLoopEvent> shutdownAndGet() {
            active.set(false);
            synchronized (this) {
                if (stopped.get()) {
                    shutdownFuture.setResult(true);
                }
            }
            processSilently(shutdownFuture::get);
            return new ArrayList<>(events.values());
        }

        @Override
        public String toString() {
            return "EventLoop-" + index + "{" +
                "numberOfEvents=" + events.size() + ", " +
                "numberOfRemoveEvents=" + removeEvents.size() +
                '}';
        }
    }

    private static final class ScheduledEvent implements EzyEventLoopEvent {
        private final long period;
        private final EzyEventLoopEvent event;
        private final AtomicLong nextFireTime = new AtomicLong();

        private ScheduledEvent(
            EzyEventLoopEvent event,
            long delayTime,
            long period
        ) {
            this.period = period;
            this.event = event;
            this.nextFireTime.set(
                System.currentTimeMillis() + (delayTime <= 0 ? 0 : period)
            );
        }

        public boolean isNotFireTime() {
            return System.currentTimeMillis() < nextFireTime.get();
        }

        @Override
        public boolean call() {
            this.nextFireTime.addAndGet(period);
            return event.call();
        }

        @Override
        public void onFinished() {
            event.onFinished();
        }

        @Override
        public void onRemoved() {
            event.onRemoved();
        }
    }
}
