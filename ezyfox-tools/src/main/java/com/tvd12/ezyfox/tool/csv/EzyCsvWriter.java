package com.tvd12.ezyfox.tool.csv;

import java.io.IOException;

public interface EzyCsvWriter {

    void append(Object... values);

    void flush() throws IOException;
}