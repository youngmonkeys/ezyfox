package com.tvd12.ezyfox.tool.data;

import java.util.Set;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.io.EzyStrings;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EzyKeywordsLine {

    protected final int index;
    protected final String filePath;
    protected final int lineNumber;
    protected final Set<String> keywords;
    protected final String line;

    protected EzyKeywordsLine(Builder builder) {
        this.index = builder.index;
        this.filePath = builder.filePath;
        this.lineNumber = builder.lineNumber;
        this.keywords = builder.keywords;
        this.line = builder.line;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(index).append(",")
                .append(filePath).append(",")
                .append(lineNumber).append(",")
                .append(EzyStrings.join(keywords, "|")).append(",")
                .append(line)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements EzyBuilder<EzyKeywordsLine> {

        protected int index;
        protected String filePath;
        protected int lineNumber;
        protected Set<String> keywords;
        protected String line;

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder lineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
            return this;
        }

        public Builder keywords(Set<String> keywords) {
            this.keywords = keywords;
            return this;
        }

        public Builder line(String line) {
            this.line = line;
            return this;
        }

        public EzyKeywordsLine build() {
            return new EzyKeywordsLine(this);
        }

    }
}
