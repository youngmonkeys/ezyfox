package com.tvd12.ezyfox.testing.tool;

import java.util.concurrent.atomic.AtomicInteger;

import com.tvd12.ezyfox.tool.EzyFileContentFilter;
import com.tvd12.ezyfox.tool.csv.EzyCsvFileWriter;
import com.tvd12.ezyfox.tool.csv.EzyCsvWriter;
import com.tvd12.ezyfox.tool.io.EzyCsvOutputStream;
import com.tvd12.ezyfox.tool.io.EzyObjectOutputStream;

public class EzyLoggerScanner {

	public static void main(String[] args) throws Exception {
		AtomicInteger count = new AtomicInteger();
		EzyFileContentFilter filter = EzyFileContentFilter.builder()
				.rootDir("../../")
				.fileFilter(p -> p.toString().endsWith(".java"))
				.lineFilter(l ->
					l.toLowerCase().contains("logger.")
				)
				.lineTransformer(l -> {
					String violate = "NONE";
					if(l.getLine().contains(" + "))
						violate = "VIOLATE";
					return new Object[] {count.incrementAndGet(), l.getFileName(), violate, "\"" + l.getLine().trim() + "\"" };
				})
				.build();
		EzyCsvWriter writer = new EzyCsvFileWriter("target/ezyfox_logger_scan.csv");
		writer.append("#", "File Name", "Violate", "Logger");
		EzyObjectOutputStream<Object[]> outputStream = new EzyCsvOutputStream(writer);
		filter.filter(outputStream);
		outputStream.close();
	}
	
}
