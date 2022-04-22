package com.tvd12.ezyfox.tool;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.function.EzyPredicates;

public class EzyFileListWalker {

    protected final List<Path> fileList;
    protected final List<Predicate<Path>> fileFilters;
    protected final List<EzyDirectoryWalker> directoryWalkers;

    public EzyFileListWalker(Builder builder) {
        this.fileList = builder.fileList;
        this.fileFilters = builder.fileFilters;
        this.directoryWalkers = builder.newDirectoryWalkers();
    }

    @SuppressWarnings("unchecked")
    public void forEach(Consumer<Path> action) {
        Predicate<Path> andFileFilter = EzyPredicates.and(fileFilters);
        fileList.stream().filter(andFileFilter).forEach(action);
        directoryWalkers.forEach(walker -> walker.forEach(action));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements EzyBuilder<EzyFileListWalker> {

        protected List<Path> fileList;
        protected List<Predicate<Path>> fileFilters;
        protected List<EzyDirectoryWalker> directoryWalkers;
        protected List<EzyDirectoryWalker.Builder> directoryWalkerBuilders;

        public Builder() {
            this.fileList = new ArrayList<>();
            this.fileFilters = new ArrayList<>();
            this.directoryWalkerBuilders = new ArrayList<>();
        }

        public Builder addFile(String filePath) {
            this.fileList.add(Paths.get(filePath));
            return this;
        }

        public Builder fileFilter(Predicate<Path> fileFilter) {
            this.fileFilters.add(fileFilter);
            return this;
        }

        public Builder addDirectory(String directory, int maxWalkDepth) {
            this.directoryWalkerBuilders.add(EzyDirectoryWalker.builder()
                    .start(directory)
                    .maxDepth(maxWalkDepth));
            return this;
        }

        @Override
        public EzyFileListWalker build() {
            return new EzyFileListWalker(this);
        }

        protected List<EzyDirectoryWalker> newDirectoryWalkers() {
            List<EzyDirectoryWalker> answer = new ArrayList<>();
            for(EzyDirectoryWalker.Builder builder : directoryWalkerBuilders) {
                answer.add(builder
                        .fileFilters(fileFilters)
                        .build());
            }
            return answer;
        }

    }
}