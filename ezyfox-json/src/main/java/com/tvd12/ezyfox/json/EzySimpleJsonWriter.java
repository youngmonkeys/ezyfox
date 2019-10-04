package com.tvd12.ezyfox.json;

import java.util.List;
import java.util.Map;

import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.util.EzyToMap;

@SuppressWarnings("rawtypes")
public class EzySimpleJsonWriter implements EzyJsonWriter {

	private static final String NO_INDENT = "";
	
	@Override
	public String writeAsString(Object value) {
		String answer = writeAsString(value, NO_INDENT);
		return answer;
	}
	
	public String writeAsString(Object value, String indent) {
		StringBuilder builder = new StringBuilder();
		writeAsString(value, indent, builder);
		return builder.toString();
	}
	
	public void writeAsString(Object value, String indent, StringBuilder output) {
		if(value == null)
			output.append(EzyStrings.NULL);
		else if(value instanceof EzyToMap)
			writeMapAsString(((EzyToMap)value).toMap(), indent, output);
		else if(value instanceof Boolean)
			output.append(value.toString());
		else if(value instanceof Number)
			output.append(value.toString());
		else if(value instanceof Map)
			writeMapAsString((Map)value, indent, output);
		else if(value instanceof List)
			writeListAsString((List)value, indent, output);
		else
			output.append(EzyStrings.quote(value));
	}
	
	private void writeMapAsString(Map map, String indent, StringBuilder output) {
		if(map == null) {
			output.append(EzyStrings.NULL);
		}
		else {
			output.append("{");
			int size = map.size();
			if(size > 0) {
				int index = 0;
				String nexIndent = indent + "    ";
				output.append("\n");
				for(Object key : map.keySet()) {
					Object value = map.get(key);
					output.append(nexIndent).append(EzyStrings.quote(key)).append(": ");
					String valueAsString = writeAsString(value, nexIndent);
					output.append(valueAsString);
					if((index ++) < size - 1) {
						output.append(",");
						output.append("\n");
					}
				}
				output.append("\n");
			}
			output.append(indent).append("}");
		}
	}
	
	private void writeListAsString(List list, String indent, StringBuilder output) {
		if(list == null) {
			output.append(EzyStrings.NULL);
		}
		else {
			output.append("[");
			int size = list.size();
			if(size > 0) {
				int index = 0;
				String nexIndent = indent + "    ";
				for(Object value : list) {
					String valueAsString = writeAsString(value, nexIndent);
					output.append(valueAsString);
					if((index ++) < size - 1)
						output.append(", ");
				}
			}
			output.append("]");
		}
	}
	
}
