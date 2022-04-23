package com.tvd12.ezyfox.tool.data;

import java.nio.file.Path;

import lombok.Getter;

public class EzyFileLine extends EzyPathProxy {

    @Getter
    protected final String line;

    public EzyFileLine(Path path, String line) {
        super(path);
        this.line = line;
    }

    @Override
    public String toString() {
        return line;
    }
}
