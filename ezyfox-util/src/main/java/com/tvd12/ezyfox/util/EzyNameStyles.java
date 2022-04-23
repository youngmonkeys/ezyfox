package com.tvd12.ezyfox.util;

import static com.tvd12.ezyfox.io.EzyChars.*;

public final class EzyNameStyles {

    private EzyNameStyles() {
    }

    public static String toLowerHyphen(String name) {
        if(name == null)
            throw new NullPointerException("name is null");
        int length = name.length();
        int finish = length - 1;
        StringBuilder builder = new StringBuilder();
        for(int i = 0 ; i < length ; ++i) {
            char ch = name.charAt(i);
            if(isLowerCase(ch)) {
                builder.append(ch);
            }
            else {
                if(i > 0) {
                    char before = name.charAt(i - 1);
                    if(isLowerCase(before) || (i < finish && isLowerCase(name.charAt(i + 1))))
                        builder.append('-');
                }
                builder.append((char)(ch + 32));
            }
        }
        return builder.toString();
    }
}
