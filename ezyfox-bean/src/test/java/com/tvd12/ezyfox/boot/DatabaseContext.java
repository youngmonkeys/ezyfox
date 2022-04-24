package com.tvd12.ezyfox.boot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class DatabaseContext {

    private final Set<String> packagesToScan;
}
