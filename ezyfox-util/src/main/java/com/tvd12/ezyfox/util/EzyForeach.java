package com.tvd12.ezyfox.util;

import java.util.function.Consumer;

public interface EzyForeach {

    <T> void forEach(Consumer<T> action);
}