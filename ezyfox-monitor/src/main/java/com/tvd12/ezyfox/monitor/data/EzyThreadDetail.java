package com.tvd12.ezyfox.monitor.data;

import lombok.Getter;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

@Getter
public class EzyThreadDetail {

    protected final long id;
    protected final String name;
    protected final long cpuTime;

    public EzyThreadDetail(ThreadInfo info) {
        ThreadMXBean tmxBean = getThreadMXBean();
        this.id = info.getThreadId();
        this.name = info.getThreadName();
        this.cpuTime = tmxBean.getThreadCpuTime(id);
    }

    protected EzyThreadDetail(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.cpuTime = builder.cpuTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("AbbreviationAsWordInName")
    protected ThreadMXBean getThreadMXBean() {
        return ManagementFactory.getThreadMXBean();
    }

    public static class Builder {
        protected long id;
        protected String name;
        protected long cpuTime;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder cpuTime(long cpuTime) {
            this.cpuTime = cpuTime;
            return this;
        }

        public EzyThreadDetail build() {
            return new EzyThreadDetail(this);
        }
    }
}
