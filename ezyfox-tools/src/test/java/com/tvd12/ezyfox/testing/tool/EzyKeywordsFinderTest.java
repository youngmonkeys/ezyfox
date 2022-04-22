package com.tvd12.ezyfox.testing.tool;

import com.tvd12.ezyfox.tool.EzyKeywordsFinder;

public class EzyKeywordsFinderTest {

    public static void main(String[] args) throws Exception {
        EzyKeywordsFinder finder = new EzyKeywordsFinder()
                .folderPath("")
                .addKeywords("public", "class");
        System.out.println(finder.findToCsv("test/find_keywords_test.csv"));
    }
}