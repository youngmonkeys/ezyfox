package com.tvd12.ezyfox.tool.data;

import lombok.Getter;

import java.nio.file.Path;

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
