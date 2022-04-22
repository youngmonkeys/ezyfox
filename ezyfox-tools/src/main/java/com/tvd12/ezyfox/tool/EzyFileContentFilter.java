package com.tvd12.ezyfox.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.tool.data.EzyFileLine;
import com.tvd12.ezyfox.tool.io.EzyObjectOutputStream;
import com.tvd12.reflections.util.Predicates;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzyFileContentFilter {

    protected final String rootDir;
    protected final List<Predicate<Path>> fileFilters;
    protected final List<Predicate<String>> lineFilters;
    protected final Function<EzyFileLine, Object> lineTransformer;

    public EzyFileContentFilter(Builder builder) {
        this.rootDir = builder.rootDir;
        this.fileFilters = builder.fileFilters;
        this.lineFilters = builder.lineFilters;
        this.lineTransformer = builder.lineTransformer;
    }

    public <T> List<T> filter() throws IOException {
        List<T> output = new ArrayList<>();
        filter(t -> output.add((T) t));
        return output;
    }

    public <T> void filter(EzyObjectOutputStream<T> outputStream) throws IOException {
        Path start = Paths.get(rootDir);
        Predicate<Path> fileFilterPredicate = getFileFilterPredicate();
        Files.walk(start)
            .filter(p -> Files.isRegularFile(p))
            .filter(fileFilterPredicate)
            .forEach(path -> filterFile0(path, outputStream));
    }

    protected void filterFile0(Path path, EzyObjectOutputStream outputStream) {
        try {
            filterFile(path, outputStream);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected void filterFile(
            Path path, EzyObjectOutputStream outputStream) throws IOException {
        Predicate<String> lineFilterPredicate = getLineFilterPredicate();
        Files.lines(path)
            .filter(lineFilterPredicate)
            .forEach(line -> {
                EzyFileLine fileLine = new EzyFileLine(path, line);
                Object transformed = fileLine;
                if(lineTransformer != null)
                    transformed = lineTransformer.apply(fileLine);
                outputStream.write(transformed);
            });
    }

    private Predicate<Path> getFileFilterPredicate() {
        Predicate<Path>[] array =
                fileFilters.toArray(new Predicate[fileFilters.size()]);
        return Predicates.and(array);
    }

    private Predicate<String> getLineFilterPredicate() {
        Predicate<String>[] array =
                lineFilters.toArray(new Predicate[lineFilters.size()]);
        return Predicates.and(array);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements EzyBuilder<EzyFileContentFilter> {

        protected String rootDir;
        protected List<Predicate<Path>> fileFilters;
        protected List<Predicate<String>> lineFilters;
        protected Function<EzyFileLine, Object> lineTransformer;

        public Builder() {
            this.fileFilters = new ArrayList<>();
            this.lineFilters = new ArrayList<>();
        }

        public Builder rootDir(String rootDir) {
            this.rootDir = rootDir;
            return this;
        }

        public Builder fileFilter(Predicate<Path> fileFilter) {
            this.fileFilters.add(fileFilter);
            return this;
        }

        public Builder lineFilter(Predicate<String> lineFilter) {
            lineFilters.add(lineFilter);
            return this;
        }

        public Builder lineTransformer(Function<EzyFileLine, Object> lineTransformer) {
            this.lineTransformer = lineTransformer;
            return this;
        }

        @Override
        public EzyFileContentFilter build() {
            return new EzyFileContentFilter(this);
        }

    }
}