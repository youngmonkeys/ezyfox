package com.tvd12.ezyfox.boot;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DatabaseContext {

    private final Set<String> packagesToScan;
}