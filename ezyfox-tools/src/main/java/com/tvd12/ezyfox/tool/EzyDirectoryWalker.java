package com.tvd12.ezyfox.tool;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.function.EzyPredicates;

public class EzyDirectoryWalker {

    protected final int maxDepth;
    protected final Path directoryStart;
    protected final List<Predicate<Path>> fileFilters;

    protected EzyDirectoryWalker(Builder builder) {
        this.maxDepth = builder.maxDepth;
        this.fileFilters = builder.fileFilters;
        this.directoryStart = builder.directoryStart;
    }

    @SuppressWarnings("unchecked")
    public void forEach(Consumer<Path> action) {
        try {
            Files.walk(directoryStart, maxDepth)
                .filter(EzyPredicates.and(fileFilters))
                .forEachOrdered(action);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements EzyBuilder<EzyDirectoryWalker> {

        protected int maxDepth;
        protected Path directoryStart;
        protected List<Predicate<Path>> fileFilters;

        public Builder() {
            this.fileFilters = new ArrayList<>();
        }

        public Builder maxDepth(int maxDepth) {
            this.maxDepth = maxDepth;
            return this;
        }

        public Builder start(String directoryPath) {
            this.directoryStart = Paths.get(directoryPath);
            return this;
        }

        public Builder fileFilter(Predicate<Path> fileFilter) {
            this.fileFilters.add(fileFilter);
            return this;
        }

        public Builder fileFilters(Collection<Predicate<Path>> fileFilters) {
            this.fileFilters.addAll(fileFilters);
            return this;
        }

        @Override
        public EzyDirectoryWalker build() {
            return new EzyDirectoryWalker(this);
        }

    }
}
