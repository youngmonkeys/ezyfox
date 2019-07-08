package com.tvd12.ezyfox.tool.io;

import com.tvd12.ezyfox.tool.csv.EzyCsvFileWriter;
import com.tvd12.ezyfox.tool.csv.EzyCsvWriter;

public class EzyCsvOutputStream implements EzyObjectOutputStream<Object[]> {

	protected final EzyCsvWriter writer;
	
	public EzyCsvOutputStream(String outputFile) {
		this(new EzyCsvFileWriter(outputFile));
	}
	
	public EzyCsvOutputStream(EzyCsvWriter writer) {
		this.writer = writer;
	}
	
	@Override
	public void write(Object[] t) {
		writer.append(t);
	}
	
	@Override
	public void close() {
		try {
			writer.flush();
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}
