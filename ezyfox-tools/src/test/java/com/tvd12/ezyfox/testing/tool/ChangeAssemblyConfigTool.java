package com.tvd12.ezyfox.testing.tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ChangeAssemblyConfigTool {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("hello world");
        List<Path> paths = new ArrayList<>();
        Files.walk(path)
            .filter(p -> p.toString().endsWith("assembly.xml"))
            .forEach(p -> {
                paths.add(p);
            });
        for(Path p : paths) {
            byte[] bytes = Files.readAllBytes(p);
            String str = new String(bytes);
            String newStr = str.replace("<format>zip</format>", "<format>dir</format>");
            Files.write(p, newStr.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
