package com.tvd12.ezyfox.tool.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.tool.EzyFileTool;

public class EzyCsvFileWriter implements EzyCsvWriter {

	protected final List<Object[]> rows;
	protected final Path outputFilePath;
	
	public EzyCsvFileWriter(String outputFile) {
		this.rows = new ArrayList<>();
		this.outputFilePath = Paths.get(outputFile);
		EzyFileTool.createParentDir(outputFilePath);
	}
	
	@Override
	public void append(Object... values) {
		synchronized (rows) {
			rows.add(values);
		}
	}
	
	@Override
	public void flush() throws IOException {
		List<Object[]> toWrite = new ArrayList<>();
		synchronized (rows) {
			for(Object[] row : rows)
				toWrite.add(row);
			rows.clear();
		}
		List<String> lines = new ArrayList<>();
		for(Object[] row : toWrite)
			lines.add(rowToLine(row));
		Files.write(outputFilePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
	}
	
	protected String rowToLine(Object[] row) {
		String line = EzyStrings.join(row, ",");
		return line;
	}
	
}
