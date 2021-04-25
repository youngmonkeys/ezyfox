package com.tvd12.ezyfox.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ByteArray implements Serializable {
	private static final long serialVersionUID = 7607209931402134720L;
	
	@Getter
	protected final byte[] bytes;
	
	public ByteArray(String bytes) {
		this(bytes.getBytes());
	}
	
	public ByteArray(byte[] bytes) {
		if(bytes == null)
			throw new NullPointerException("bytes can not be null");
		this.bytes = bytes;
	}

	public static ByteArray wrap(String bytes) {
		return new ByteArray(bytes);
	}
	
	public static ByteArray wrap(byte[] bytes) {
		return new ByteArray(bytes);
	}
	
	public static List<ByteArray> wrap(byte[][] bytess) {
		List<ByteArray> list = new ArrayList<>(bytess.length);
		for(byte[] bytes : bytess)
			list.add(wrap(bytes));
		return list;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(other == this)
			return true;
		if(!other.getClass().equals(this.getClass()))
			return false;
		ByteArray t = (ByteArray)other;
		boolean result = Arrays.equals(this.bytes, t.bytes);
		return result;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(bytes);
	}
	
	@Override
	public String toString() {
		return new String(bytes);
	}
}
