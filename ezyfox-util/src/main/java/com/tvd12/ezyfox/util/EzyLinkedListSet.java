package com.tvd12.ezyfox.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class EzyLinkedListSet<E> extends LinkedList<E> {
	private static final long serialVersionUID = -6698639347522121319L;

	protected Set<E> elementSet = new HashSet<>();
	
	public EzyLinkedListSet() {
		super();
	}
	
	@Override
	public boolean add(E e) {
		if(elementSet.add(e))
			return super.add(e);
		return false;
	}
	
	@Override
	public void add(int index, E element) {
		if(elementSet.add(element))
			super.add(index, element);
	}
	
	@Override
	public boolean remove(Object o) {
		super.remove(o);
		return elementSet.remove(o);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		super.removeAll(c);
		return elementSet.removeAll(c);
	}
	
	@Override
	public E remove() {
		E e = super.remove();
		elementSet.remove(e);
		return e;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean retainAll(Collection<?> c) {
		List toRetain = new ArrayList<>();
		Set passed = new HashSet<>();
		for(Object e : c) {
			if(elementSet.contains(e) && !passed.contains(e)) {
				toRetain.add(e);
				passed.add(e);
			}
		}
		super.retainAll(toRetain);
		return elementSet.retainAll(toRetain);
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
		return elementSet.removeIf(filter);
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
		elementSet.remove(0);
		return answer;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		int lastSize = elementSet.size();
		for(E e : c) {
			if(!elementSet.contains(e))
				add(e);
		}
		int currentSize = elementSet.size();
		return lastSize != currentSize;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		List<E> toAdd = new ArrayList<>();
		for(E e : c) {
			if(!elementSet.contains(e)) {
				elementSet.add(e);
				toAdd.add(e);
			}
		}
		return super.addAll(index, toAdd);
	}
	
	@Override
	public void addFirst(E e) {
		if(elementSet.add(e))
			super.addFirst(e);
	}
	
	@Override
	public void addLast(E e) {
		if(elementSet.add(e))
			super.addLast(e);
	}
	
	@Override
	public boolean offer(E e) {
		if(elementSet.add(e))
			return super.offer(e);
		return false;
	}
	
	@Override
	public boolean offerFirst(E e) {
		if(elementSet.add(e))
			return super.offerFirst(e);
		return false;
	}
	
	@Override
	public boolean offerLast(E e) {
		if(elementSet.add(e))
			return super.offerLast(e);
		return false;
	}
	
	public E poll() {
		E e = super.poll();
		if(e != null)
			elementSet.remove(e);
		return e;
	}
	
	@Override
	public E pollFirst() {
		E e = super.pollFirst();
		if(e != null)
			elementSet.remove(e);
		return e;
	}
	
	@Override
	public E pollLast() {
		E e = super.pollLast();
		if(e != null)
			elementSet.remove(e);
		return e;
	}
	
	@Override
	public void push(E e) {
		if(elementSet.add(e))
			super.push(e);
	}
	
	@Override
	public E pop() {
		E e = super.pop();
		if(e != null)
			elementSet.remove(e);
		return e;
	}
	
	@Override
	public boolean contains(Object o) {
		return elementSet.contains(o);
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return elementSet.containsAll(c);
	}
	
}
