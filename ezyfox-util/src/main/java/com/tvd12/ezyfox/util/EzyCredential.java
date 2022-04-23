package com.tvd12.ezyfox.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class EzyCredential implements Serializable {
    private static final long serialVersionUID = 4525085403412972161L;

    protected String username;
    protected String password;
}
