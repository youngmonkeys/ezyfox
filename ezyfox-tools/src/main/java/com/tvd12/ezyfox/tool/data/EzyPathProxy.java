package com.tvd12.ezyfox.tool.data;

import com.tvd12.ezyfox.util.EzyFileUtil;
import lombok.Getter;

import java.io.File;
import java.nio.file.Path;

public class EzyPathProxy {

    @Getter
    protected final Path path;
    @Getter
    protected final Path root;

    public EzyPathProxy(Path path) {
        this(null, path);
    }

    public EzyPathProxy(Path root, Path path) {
        this.root = root;
        this.path = path;
    }

    public File toFile() {
        return path.toFile();
    }

    public Path getRelativePath() {
        return path.relativize(root);
    }

    public String getFileName() {
        return path.getFileName().toString();
    }

    public String getFileExtension() {
        return EzyFileUtil.getFileExtension(getFileName());
    }

    @Override
    public String toString() {
        return path.toString();
    }
}
