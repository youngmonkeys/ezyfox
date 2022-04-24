package com.tvd12.ezyfox.tool;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.io.EzyStrings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class EzyFileMerger {

    protected final String comment;
    protected final EzyFileListWalker fileListWalker;
    protected boolean firstFile;

    public EzyFileMerger(Builder builder) {
        this.comment = builder.comment;
        this.fileListWalker = builder.fileListWalker;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Path merge(String outputFilePath) {
        Path target = Paths.get(outputFilePath);
        deleteExistedAndCreateTargetFile(target);
        firstFile = true;
        fileListWalker.forEach(p -> writeToFile(target, p));
        return target;
    }

    protected void deleteExistedAndCreateTargetFile(Path target) {
        try {
            Files.deleteIfExists(target);
            Files.createDirectories(target.getParent());
            Files.createFile(target);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected void writeToFile(Path target, Path from) {
        try {
            if (!EzyStrings.isNoContent(comment)) {
                writeToFile(target, EzyStrings.getUtfBytes(getFileComment(from)));
            }
            byte[] bytes = Files.readAllBytes(from);
            writeToFile(target, bytes);
            firstFile = false;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected void writeToFile(Path target, byte[] bytes) throws IOException {
        Files.write(target, bytes, StandardOpenOption.APPEND);
    }

    protected String getFileComment(Path from) {
        return (firstFile ? "" : "\n\n") +
            comment +
            "================ " +
            from.getFileName() +
            " ================" +
            "\n";
    }

    public static class Builder implements EzyBuilder<EzyFileMerger> {

        protected String comment;
        protected EzyFileListWalker fileListWalker;

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder fileListWalker(EzyFileListWalker fileListWalker) {
            this.fileListWalker = fileListWalker;
            return this;
        }

        @Override
        public EzyFileMerger build() {
            return new EzyFileMerger(this);
        }
    }
}
