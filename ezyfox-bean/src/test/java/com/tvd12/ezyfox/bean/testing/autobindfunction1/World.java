package com.tvd12.ezyfox.bean.testing.autobindfunction1;

import com.tvd12.ezyfox.bean.testing.autobindfunction2.Hello;

import lombok.Getter;

@Getter
public abstract class World implements HelloAware {

    private Hello hello;

    @Override
    public void setHello(Hello hello) {
        this.hello = hello;
    }

    protected abstract String getName();
}
