package com.tvd12.ezyfox.util;

import lombok.Getter;

@Getter
public class Next {
    
    protected final long skip;
    protected final long limit;
    
    public Next(long limit) {
        this(0, limit);
    }
    
    public Next(long skip, long limit) {
        this.skip = skip;
        this.limit = limit;
    }
    
    
    public static Next limit(long limit) {
        return new Next(limit);
    }
    
    public static Next fromLimit(long limit) {
        return new Next(limit);
    }
    
    public static Next skipLimit(long skip, long limit) {
        return new Next(skip, limit);
    }
    
    public static Next fromSkipLimit(long skip, long limit) {
        return new Next(skip, limit);
    }
    
    /**
     * @param page the page start from 0
     * @param size the page size
     * @return the Next object
     */
    public static Next fromPageSize(long page, long size) {
        return new Next((page < 0L ? 0L : page) * size, size);
    }
}
