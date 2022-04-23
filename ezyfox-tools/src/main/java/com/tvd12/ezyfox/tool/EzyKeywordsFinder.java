package com.tvd12.ezyfox.tool;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import com.tvd12.ezyfox.tool.data.EzyKeywordsLine;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyKeywordsFinder extends EzyLoggable {

    protected String folderPath;
    protected Set<String> keywords;
    protected Predicate<Path> fileFilters;
    protected Predicate<String> lineFilters;

    public EzyKeywordsFinder() {
        this.keywords = new HashSet<>();
        this.fileFilters = t -> true;
        this.lineFilters = t -> true;
    }

    public EzyKeywordsFinder folderPath(String folderPath) {
        this.folderPath = folderPath;
        return this;
    }

    public EzyKeywordsFinder addKeyword(String keyword) {
        keywords.add(keyword.toLowerCase());
        return this;
    }

    public EzyKeywordsFinder addKeywords(String... keywords) {
        return addKeywords(Arrays.asList(keywords));
    }

    public EzyKeywordsFinder addKeywords(Collection<String> keywords) {
        for(String keyword : keywords)
            addKeyword(keyword);
        return this;
    }

    public EzyKeywordsFinder addFileFilter(Predicate<Path> filter) {
        fileFilters.and(filter);
        return this;
    }

    public EzyKeywordsFinder addLineFilter(Predicate<String> filter) {
        lineFilters.and(filter);
        return this;
    }

    public List<EzyKeywordsLine> find() throws Exception {
        List<EzyKeywordsLine> lines = new ArrayList<>();
        AtomicInteger foundCount = new AtomicInteger();
        Files.walk(Paths.get(folderPath), Integer.MAX_VALUE)
            .filter(p -> Files.isRegularFile(p))
            .filter(fileFilters)
            .forEach(p -> {
                try {
                    lines.addAll(find(p, foundCount));
                }
                catch (Exception e) {
                    logger.error("read file: {} error: {}", p, e.getMessage());
                }
            });
        return lines;
    }

    public String findToCsv(String csvFilePath) throws Exception {
        List<String> lines = new ArrayList<>();
        lines.add("#,File Path,Line Number,Keywords,Line Content");
        List<EzyKeywordsLine> keywordsLines = find();
        for(EzyKeywordsLine keywordsLine : keywordsLines)
            lines.add(keywordsLine.toString());
        Path path = Paths.get(csvFilePath);
        if(path.getParent() != null && !Files.exists(path.getParent()))
            Files.createDirectories(path.getParent());
        Files.write(path, lines);
        return path.toAbsolutePath().toString();
    }

    protected List<EzyKeywordsLine> find(
            Path path, AtomicInteger foundCount) throws Exception {
        AtomicInteger lineNumber = new AtomicInteger();
        List<EzyKeywordsLine> lines = new ArrayList<>();
        Files.lines(path)
            .forEach(l -> {
                lineNumber.incrementAndGet();
                if(!lineFilters.test(l))
                    return;
                String lineLower = l.toLowerCase();
                Set<String> foundKeywords = new HashSet<>();
                for(String keyword : keywords) {
                    if(lineLower.contains(keyword))
                        foundKeywords.add(keyword);
                }
                if(foundKeywords.size() > 0) {
                    lines.add(EzyKeywordsLine.builder()
                                .index(foundCount.incrementAndGet())
                                .filePath(path.toString())
                                .lineNumber(lineNumber.get())
                                .keywords(foundKeywords)
                                .line(l)
                                .build());
                }
            });
        return lines;
    }
}
