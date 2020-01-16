package com.tvd12.ezyfox.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import com.tvd12.ezyfox.io.EzyCollectionConverter;
import com.tvd12.ezyfox.io.EzyInputTransformer;
import com.tvd12.ezyfox.io.EzyOutputTransformer;
import com.tvd12.ezyfox.util.EzyArrayToList;

@SuppressWarnings({"unchecked", "rawtypes"})
public class EzyArrayList extends EzyTransformable implements EzyArray {
	private static final long serialVersionUID = 5952111146742741007L;
	
	protected final ArrayList<Object> list = new ArrayList<>();
	
	protected final EzyCollectionConverter collectionConverter;
	
	public EzyArrayList(
			EzyInputTransformer inputTransformer,
			EzyOutputTransformer outputTransformer,
			EzyCollectionConverter collectionConverter) {
		super(inputTransformer, outputTransformer);
		this.collectionConverter = collectionConverter;
	}
	
	public EzyArrayList(
			Collection items,
			EzyInputTransformer inputTransformer,
			EzyOutputTransformer outputTransformer,
			EzyCollectionConverter collectionConverter) {
		this(inputTransformer, outputTransformer, collectionConverter);
		this.list.addAll(items);
		
	}

	@Override
	public <T> T get(int index) {
		T answer = (T)list.get(index);
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#get(int, java.lang.Class)
	 */
	@Override
	public <T> T get(int index, Class<T> type) {
		T answer = (T) getValue(index, type);
		return answer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#getValue(int, java.lang.Class)
	 */
	@Override
	public Object getValue(int index, Class type) {
		Object answer = transformOutput(list.get(index), type);
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#isNotNullIndex(int)
	 */
	@Override
	public boolean isNotNullValue(int index) {
		boolean answer = false;
		int size = size();
		if(index < size)
			answer = list.get(index) != null;
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object value) {
		boolean answer = list.contains(value);
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection values) {
		boolean answer = list.containsAll(values);
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#sub(int, int)
	 */
	@Override
	public EzyArray sub(int fromIndex, int toIndex) {
		List<Object> subList = list.subList(fromIndex, toIndex);
		return new EzyArrayList(
				subList,
				inputTransformer,
				outputTransformer,
				collectionConverter);
	}
	
	/**
	 * add an item to the list
	 * 
	 * @param item the item
	 */
	@Override
	public void add(Object item) {
		list.add(transformInput(item));
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyArray#add(java.lang.Object[])
	 */
	@Override
	public void add(Object... items) {
		for(Object item : items)
			this.add(item);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyArray#add(java.util.Collection)
	 */
	@Override
	public void add(Collection items) {
		for(Object item : items)
			this.add(item);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#size()
	 */
	@Override
	public int size() {
		int size = list.size();
		return size;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		boolean answer = list.isEmpty();
		return answer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyArray#set(int, java.lang.Object)
	 */
	@Override
	public <T> T set(int index, Object item) {
		T answer = (T) list.set(index, transformInput(item));
		return answer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyArray#remove(int)
	 */
	@Override
	public <T> T remove(int index) {
		T answer = (T) list.remove(index);
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyArray#clear()
	 */
	@Override
	public void clear() {
		list.clear();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyArray#forEach(java.util.function.Consumer)
	 */
	@Override
	public void forEach(Consumer<Object> action) {
		list.forEach(action);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyArray#iterator()
	 */
	@Override
	public Iterator<Object> iterator() {
		Iterator<Object> it = list.iterator();
		return it;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#toList()
	 */
	@Override
	public List toList() {
		EzyArrayToList arrayToList = EzyArrayToList.getInstance();
		List list = arrayToList.toList(this);
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#toList(java.lang.Class)
	 */
	@Override
	public <T> List<T> toList(Class<T> type) {
		List<T> list = toList();
		return list;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyRoArray#toArray(java.lang.Class)
	 */
	@Override
	public <T,A> A toArray(Class<T> type) {
		A array = collectionConverter.toArray(list, type);
		return array;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Collection listClone = (Collection) list.clone();
		EzyArrayList clone = new EzyArrayList(
				listClone,
				inputTransformer,
				outputTransformer,
				collectionConverter);
		return clone;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyArray#duplicate()
	 */
	@Override
	public EzyArray duplicate() {
		try {
			return (EzyArray) clone();
		} catch (CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tvd12.ezyfox.entity.EzyArray#compareTo(com.tvd12.ezyfox.entity.EzyArray)
	 */
	@Override
	public int compareTo(EzyArray o) {
		EzyArrayList other = (EzyArrayList)o;
		int result = this.list.size() - other.list.size();
		if(result != 0)
			return result;
		for(int i = 0 ; i < list.size() ; ++i) {
			Object value = list.get(i);
			Object otherValue = other.list.get(i);
			if(value == null) {
				if(otherValue != null)
					return -1;
			}
			else {
				if(otherValue == null)
					return 1;
				if(value instanceof Comparable && otherValue instanceof Comparable) {
					result = ((Comparable)value).compareTo((Comparable)otherValue);
					if(result != 0)
						return result;
				}
				else {
					if(!(value instanceof Comparable))
						throw new IllegalArgumentException("value: " + value.getClass().getName() + "(" + value + ") is not comparable");
					else
						throw new IllegalArgumentException("value: " + otherValue.getClass().getName() + "(" + otherValue + ") is not comparable");
				}
			}
		}
		return 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(other == this)
			return true;
		if(!other.getClass().equals(this.getClass()))
			return false;
		EzyArrayList t = (EzyArrayList)other;
		return t.list.equals(this.list);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return list.hashCode();
	}
	
	/**
	 * Transform input value
	 * 
	 * @param input the input value
	 * @return the transformed value
	 */
	protected Object transformInput(Object input) {
		Object answer = inputTransformer.transform(input);
		return answer;
	}

	/**
	 * Transform output value
	 * 
	 * @param output the output value
	 * @param type the output type
	 * @return the transformed value
	 */
	private Object transformOutput(Object output, Class type) {
		Object answer = outputTransformer.transform(output, type);
		return answer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return list.toString();
	}

}
