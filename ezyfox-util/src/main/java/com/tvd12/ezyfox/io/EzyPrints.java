package com.tvd12.ezyfox.io;

import java.util.Arrays;

public final class EzyPrints {
	private final static char[] HEX_ARRAY = new char[] {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	};
	
	private EzyPrints() {
	}
	
	public static String print(Object object) {
		if(object == null)
			return "null";
		if(object instanceof boolean[])
			return Arrays.toString((boolean[])object);
		if(object instanceof byte[])
			return Arrays.toString((byte[])object);
		if(object instanceof char[])
			return Arrays.toString((char[])object);
		if(object instanceof double[])
			return Arrays.toString((double[])object);
		if(object instanceof float[])
			return Arrays.toString((float[])object);
		if(object instanceof int[])
			return Arrays.toString((int[])object);
		if(object instanceof long[])
			return Arrays.toString((long[])object);
		if(object instanceof short[])
			return Arrays.toString((short[])object);
		if(object instanceof Object[])
			return Arrays.toString((Object[])object);
		return object.toString();
	}
	
	public static String printBits(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0 ; i < bytes.length ; i++) 
			builder.append(printBits(bytes[i]));
		return builder.toString();
	}
	
	public static String printBits(byte value) {
		String str = insertBegin(Integer.toBinaryString(value & 0xff), "0", 8);
		return str.substring(str.length() - 8);
	}
	
	public static String insertBegin(String str, String ch, int maxlen) {
		StringBuilder builder = new StringBuilder(str);
		while(builder.length() < maxlen)
			builder.insert(0, ch);
		return builder.toString();
	}
	
	public static String printHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xFF;
			hexChars[i * 2] = HEX_ARRAY[v >>> 4];
			hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		String answer = new String(hexChars);
		return answer;
	}
	
	public static String print2d(int[][] table) {
		StringBuilder builder = new StringBuilder();
		Array2DPrinter printer = new Array2DPrinter(builder);
		printer.print(table);
		return builder.toString();
	}
	
	public static String print2d(String[][] table) {
		StringBuilder builder = new StringBuilder();
		Array2DPrinter printer = new Array2DPrinter(builder);
		printer.print(table);
		return builder.toString();
	}
	
	public static final class Array2DPrinter {

		private static final char BORDER_KNOT = '+';
		private static final char HORIZONTAL_BORDER = '-';
		private static final char VERTICAL_BORDER = '|';

		private static final String DEFAULT_AS_NULL = "(NULL)";

		private final StringBuilder out;
		private final String asNull;

		public Array2DPrinter(StringBuilder out) {
			this(out, DEFAULT_AS_NULL);
		}

		public Array2DPrinter(StringBuilder out, String asNull) {
			if (out == null) {
				throw new IllegalArgumentException("No print stream provided");
			}
			if (asNull == null) {
				throw new IllegalArgumentException("No NULL-value placeholder provided");
			}
			this.out = out;
			this.asNull = asNull;
		}

		public void print(int[][] table) {
			String[][] strss = new String[table.length][];
			for (int i = 0; i < table.length; i++) {
				strss[i] = new String[table[i].length];
				for (int k = 0; k < table[i].length; k++)
					strss[i][k] = String.valueOf(table[i][k]);
			}
			print(strss);
		}

		public void print(String[][] table) {
			if (table == null) {
				throw new IllegalArgumentException("No tabular data provided");
			}
			if (table.length == 0) {
				return;
			}
			final int[] widths = new int[getMaxColumns(table)];
			adjustColumnWidths(table, widths);
			printPreparedTable(table, widths, getHorizontalBorder(widths));
		}

		private void printPreparedTable(String[][] table, int widths[], String horizontalBorder) {
			final int lineLength = horizontalBorder.length();
			out.append(horizontalBorder).append("\n");
			int index = 0;
			for (final String[] row : table) {
				if (row != null) {
					out.append(getRow(row, widths, lineLength))
						.append("\n")
						.append(horizontalBorder);
					if((index ++) < table.length - 1)
						out.append("\n");
				}
			}
		}

		private String getRow(String[] row, int[] widths, int lineLength) {
			final StringBuilder builder = new StringBuilder(lineLength).append(VERTICAL_BORDER);
			final int maxWidths = widths.length;
			for (int i = 0; i < maxWidths; i++) {
				builder.append(padRight(getCellValue(safeGet(row, i, null)), widths[i])).append(VERTICAL_BORDER);
			}
			return builder.toString();
		}

		private String getHorizontalBorder(int[] widths) {
			final StringBuilder builder = new StringBuilder(256);
			builder.append(BORDER_KNOT);
			for (final int w : widths) {
				for (int i = 0; i < w; i++) {
					builder.append(HORIZONTAL_BORDER);
				}
				builder.append(BORDER_KNOT);
			}
			return builder.toString();
		}

		private int getMaxColumns(String[][] rows) {
			int max = 0;
			for (final String[] row : rows) {
				if (row != null && row.length > max) {
					max = row.length;
				}
			}
			return max;
		}

		private void adjustColumnWidths(String[][] rows, int[] widths) {
			for (final String[] row : rows) {
				if (row != null) {
					for (int c = 0; c < widths.length; c++) {
						final String cv = getCellValue(safeGet(row, c, asNull));
						final int l = cv.length();
						if (widths[c] < l) {
							widths[c] = l;
						}
					}
				}
			}
		}

		private static String padRight(String s, int n) {
			return String.format("%1$-" + n + "s", s);
		}

		private static String safeGet(String[] array, int index, String defaultValue) {
			return index < array.length ? array[index] : defaultValue;
		}

		private String getCellValue(Object value) {
			return value == null ? asNull : value.toString();
		}

	}
	
}
