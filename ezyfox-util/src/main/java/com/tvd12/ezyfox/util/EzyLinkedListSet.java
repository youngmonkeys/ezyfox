package com.tvd12.ezyfox.util;

import java.util.*;
import java.util.function.Predicate;

public class EzyLinkedListSet<E> extends LinkedList<E> {
    private static final long serialVersionUID = -6698639347522121319L;

    protected Set<E> elementSet = new HashSet<>();

    public EzyLinkedListSet() {
        super();
    }

    @Override
    public boolean add(E e) {
        boolean success = elementSet.add(e);
        if (success) {
            return super.add(e);
        }
        return false;
    }

    @Override
    public void add(int index, E element) {
        boolean success = elementSet.add(element);
        if (success) {
            super.add(index, element);
        }
    }

    @Override
    public boolean remove(Object o) {
        super.remove(o);
        boolean contains = elementSet.remove(o);
        return contains;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        super.removeAll(c);
        boolean changed = elementSet.removeAll(c);
        return changed;
    }

    @Override
    public E remove() {
        E e = super.remove();
        elementSet.remove(e);
        return e;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean retainAll(Collection<?> c) {
        List toRetain = new ArrayList<>();
        Set passed = new HashSet<>();
        for (Object e : c) {
            if (elementSet.contains(e) && !passed.contains(e)) {
                toRetain.add(e);
                passed.add(e);
            }
        }
        super.retainAll(toRetain);
        boolean changed = elementSet.retainAll(toRetain);
        return changed;
    }

    @Override
    public E remove(int index) {
        E e = super.remove(index);
        elementSet.remove(e);
        return e;
    }

    @Override
    public E removeFirst() {
        E e = super.removeFirst();
        elementSet.remove(e);
        return e;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        boolean result = super.removeFirstOccurrence(o);
        elementSet.remove(o);
        return result;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        super.removeIf(filter);
        boolean removed = elementSet.removeIf(filter);
        return removed;
    }

    @Override
    public E removeLast() {
        E e = super.removeLast();
        elementSet.remove(e);
        return e;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        boolean answer = super.removeLastOccurrence(o);
        elementSet.remove(o);
        return answer;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int lastSize = elementSet.size();
        for (E e : c) {
            if (!elementSet.contains(e)) {
                add(e);
            }
        }
        int currentSize = elementSet.size();
        return lastSize != currentSize;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        List<E> toAdd = new ArrayList<>();
        for (E e : c) {
            if (!elementSet.contains(e)) {
                elementSet.add(e);
                toAdd.add(e);
            }
        }
        boolean changed = super.addAll(index, toAdd);
        return changed;
    }

    @Override
    public void addFirst(E e) {
        boolean success = elementSet.add(e);
        if (success) {
            super.addFirst(e);
        }
    }

    @Override
    public void addLast(E e) {
        boolean success = elementSet.add(e);
        if (success) {
            super.addLast(e);
        }
    }

    @Override
    public boolean offer(E e) {
        boolean success = elementSet.add(e);
        if (success) {
            return super.add(e);
        }
        return success;
    }

    @Override
    public boolean offerFirst(E e) {
        boolean success = elementSet.add(e);
        if (success) {
            super.addFirst(e);
        }
        return success;
    }

    @Override
    public boolean offerLast(E e) {
        boolean success = elementSet.add(e);
        if (success) {
            super.addLast(e);
        }
        return success;
    }

    public E poll() {
        E e = super.poll();
        if (e != null) {
            elementSet.remove(e);
        }
        return e;
    }

    @Override
    public E pollFirst() {
        E e = super.pollFirst();
        if (e != null) {
            elementSet.remove(e);
        }
        return e;
    }

    @Override
    public E pollLast() {
        E e = super.pollLast();
        if (e != null) {
            elementSet.remove(e);
        }
        return e;
    }

    @Override
    public boolean contains(Object o) {
        boolean contains = elementSet.contains(o);
        return contains;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean containsAll = elementSet.containsAll(c);
        return containsAll;
    }
}
