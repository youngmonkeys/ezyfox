package com.tvd12.ezyfox.io;

import static com.tvd12.ezyfox.naming.EzyNamingCase.DASH;
import static com.tvd12.ezyfox.naming.EzyNamingCase.DOT;
import static com.tvd12.ezyfox.naming.EzyNamingCase.UNDERSCORE;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EzyStrings {
	
	public static final String NULL = "null";
	public static final String UTF_8 = "UTF-8";

	private EzyStrings() {
	}
	
	public static String newUtf(byte[] bytes) {
		return newString(bytes, UTF_8);
	}
	
	public static String newUtf(ByteBuffer buffer, int size) {
		return newString(buffer, size, UTF_8);
	}
	
	public static byte[] getUtfBytes(String str) {
		return getBytes(str, UTF_8);
	}
	
	public static String newString(byte[] bytes, String charset) {
		try {
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static byte[] getBytes(String str, String charset) {
		try {
			return str.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public static String newString(ByteBuffer buffer, int size, String charset) {
		byte[] bytes = new byte[size];
		buffer.get(bytes);
		return newString(bytes, charset);
	}
	
	public static String getString(String[] array, int index, String def) {
		return array.length > index ? array[index] : def;
	}
	
	public static String quote(Object value) {
		return new StringBuilder()
				.append("\"")
				.append(value != null ? value.toString() : NULL)
				.append("\"")
				.toString();
	}
	
	public static String newString(char ch, int count) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0 ; i < count ; ++i)
			builder.append(ch);
		return builder.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String wrap(
			Collection collection,
			String open, 
			String close, 
			String separator, boolean noWrapIfNull) {
		if(collection == null) {
			if(noWrapIfNull)
				return NULL;
			return new StringBuilder()
					.append(open)
					.append(close)
					.toString();
		}
		int size = collection.size();
		StringBuilder builder = new StringBuilder(open);
		int index = 0;
		for(Object item : collection) {
			builder.append(item);
			if((++ index) < size)
				builder.append(separator);
		}
		return builder.append(close).toString();
	}
	
	public static boolean isEmpty(CharSequence cs) {
        boolean empty = (cs == null || cs.length() == 0);
        return empty;
    }
	
	public static boolean isNoContent(String cs) {
        boolean noContent = (cs == null || cs.isEmpty() || cs.trim().isEmpty());
        return noContent;
    }
	
	public static String join(double[] array, String separator) {
		return Arrays.stream(array)
				.mapToObj(t -> String.valueOf(t))
				.collect(Collectors.joining(separator));
	}
	
	public static String join(int[] array, String separator) {
		return Arrays.stream(array)
				.mapToObj(t -> String.valueOf(t))
				.collect(Collectors.joining(separator));
	}
	
	public static String join(long[] array, String separator) {
		return Arrays.stream(array)
				.mapToObj(t -> String.valueOf(t))
				.collect(Collectors.joining(separator));
	}
	
	public static <T> String join(T[] array, String separator) {
		return join(Arrays.stream(array), separator);
	}
	
	public static <T> String join(Collection<T> collection, String separator) {
		return join(collection.stream(), separator);
	}
	
	public static <T> String join(Stream<T> stream, String separator) {
		String answer = stream
	            .map(t -> t.toString())
	            .collect(Collectors.joining(separator));
		return answer;
	}
	
	public static String toUpperCase(String original) {
		return original.toUpperCase();
	}
	
	public static String toLowerCase(String original) {
		return original.toLowerCase();
	}
	
	public static String toCamelCase(String original) {
		if(original.length() < 2)
			return original.toLowerCase();
		return original.substring(0, 1).toLowerCase() + original.substring(1);
	}
	
	public static String underscoreToCamelCase(String original) {
	    if (original == null) {
	        return null;
	    }
	    String trim = original.trim();
	    if (trim.length() == 0) {
	        return trim;
	    }
	    if (trim.length() == 1) {
	        return trim.toLowerCase();
	    }
	    StringBuilder builder = new StringBuilder().append(
            Character.toLowerCase(trim.charAt(0))
        );
	    boolean needUpper = false;
	    
	    for (int i = 1 ; i < trim.length() ; ++i) {
	        char ch = original.charAt(i);
	        if (ch == '_') {
	            needUpper = true;
	        } else {
	            builder.append(needUpper ? Character.toUpperCase(ch) : ch);
	            needUpper = false;
	        }
	    }
        return builder.toString();
    }
	
	public static String toDotCase(String original) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0 ; i < original.length() ; ++i) {
			char ch = original.charAt(i);
			if(Character.isUpperCase(ch) && i > 0)
				builder.append(DOT.getSign());
			builder.append(Character.toLowerCase(ch));
		}
		return builder
				.toString()
				.replace(DASH.getSign(), DOT.getSign())
				.replace(UNDERSCORE.getSign(), DOT.getSign())
				.replace(DOT.getSign() + DOT.getSign(), DOT.getSign());
	}
	
	public static String toDashCase(String original) {
		return toDotCase(original).replace(DOT.getSign(), DASH.getSign());
	}
	
	public static String toUnderscoreCase(String original) {
		return toDotCase(original).replace(DOT.getSign(), UNDERSCORE.getSign());
	}
	
	public static String replace(String query, Object[] parameters) {
		return replace(query, parameters, null);
	}
	
	public static String replace(
			String query, 
			Object[] parameters, 
			Function<Object, Object> parameterConveter
	) {
        final int paramCount = parameters.length;
        final int length = query.length();
        final StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < length ; ) {
            char ch = query.charAt(i ++);
            if(i < length && ch == '?') {
                int numberCharCount = 0;
                final int startParamIndex = i;
                ch = query.charAt(i);
                while(ch >= '0' && ch <= '9') {
                	++ numberCharCount;
                    if (++ i >= length) {
                        break;
                    }
                    ch = query.charAt(i);
                }
                if(numberCharCount > 0) {
                    final char[] numberChars = new char[numberCharCount];
                    final int endParamIndex = startParamIndex + numberCharCount;
                    query.getChars(startParamIndex, endParamIndex, numberChars, 0);
                    final int paramIndex = Integer.parseInt(new String(numberChars));
                    if (paramIndex >= paramCount) {
                        throw new IllegalArgumentException("invalid query: " + query + ", not enough parameter values, required: " + paramIndex);
                    }
                    builder.append(getParameterValue(parameters[paramIndex], parameterConveter));
                    continue;
                }
                else {
                    builder.append('?');
                }
            }
            else {
                builder.append(ch);
            }
        }
        return builder.toString();
    }
	
	private static Object getParameterValue(Object parameter, Function<Object, Object> parameterConveter) {
		Object value = parameter;
		if(parameterConveter != null)
			value = parameterConveter.apply(parameter);
		return value;
	}
}
