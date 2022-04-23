package com.tvd12.ezyfox.entity;

import java.io.Serializable;

public interface EzyData extends Cloneable, Serializable {

    /**
     * Clone object.
     *
     * @return a new object
     * @throws CloneNotSupportedException if the object can't be cloned
     * @see java.lang.Object#clone()
     */
    Object clone() throws CloneNotSupportedException;

    /**
     * duplicate new object.
     *
     * @return a duplicated object
     */
    EzyData duplicate();
}
