package com.tvd12.ezyfox.entity;

import java.io.Serializable;

import com.tvd12.ezyfox.entity.EzyData;

public interface EzyData extends Cloneable, Serializable {
	
	/**
     * @see java.lang.Object#clone()
     * 
     * @return a new object
     * @throws CloneNotSupportedException if can't clone
     */
    Object clone() throws CloneNotSupportedException;
    
    /**
     * duplicate new object
     * 
     * @return a duplicated object
     */
    EzyData duplicate();
	
}
