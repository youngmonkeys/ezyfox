package com.tvd12.ezyfox.naming;

import static com.tvd12.ezyfox.naming.EzyNamingCase.CAMEL;
import static com.tvd12.ezyfox.naming.EzyNamingCase.DASH;
import static com.tvd12.ezyfox.naming.EzyNamingCase.DOT;
import static com.tvd12.ezyfox.naming.EzyNamingCase.LOWER;
import static com.tvd12.ezyfox.naming.EzyNamingCase.UNDERSCORE;
import static com.tvd12.ezyfox.naming.EzyNamingCase.UPPER;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.io.EzyStrings;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class EzySimpleNameTranslator implements EzyNameTranslator {

    @Getter
    protected final EzyNamingCase namingCase;
    protected final String ignoredSuffix;
    
    @Override
    public String translate(String originalName) {
        String startName = originalName;
        if(originalName.endsWith(ignoredSuffix)) {
            if(originalName.length() > ignoredSuffix.length()) {
                startName = originalName
                    .substring(0, originalName.length() - ignoredSuffix.length());
            }
        }
        if(namingCase == UPPER)
            return EzyStrings.toUpperCase(startName);
        if(namingCase == LOWER)
            return EzyStrings.toLowerCase(startName);
        if(namingCase == CAMEL)
            return EzyStrings.toCamelCase(originalName);
        if(namingCase == DOT)
            return EzyStrings.toDotCase(startName);
        if(namingCase == DASH)
            return EzyStrings.toDashCase(startName);
        if(namingCase == UNDERSCORE)
            return EzyStrings.toUnderscoreCase(startName);
        return startName;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder implements EzyBuilder<EzySimpleNameTranslator> {
        protected EzyNamingCase namingCase = EzyNamingCase.NATURE;
        protected String ignoredSuffix = "";
        
        public Builder namingCase(EzyNamingCase namingCase) {
            if(namingCase != null)
                this.namingCase = namingCase;
            return this;
        }
        
        public Builder ignoredSuffix(String ignoredSuffix) {
            if(ignoredSuffix != null)
                this.ignoredSuffix = ignoredSuffix;
            return this;
        }
        
        @Override
        public EzySimpleNameTranslator build() {
            return new EzySimpleNameTranslator(namingCase, ignoredSuffix);
        }
    }
    
}
