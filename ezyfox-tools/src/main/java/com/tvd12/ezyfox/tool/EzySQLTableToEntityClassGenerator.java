package com.tvd12.ezyfox.tool;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.util.EzyMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EzySQLTableToEntityClassGenerator {

    private static final Map<String, String> JAVA_IMPORT_BY_TYPE =
        EzyMapBuilder
            .mapBuilder()
            .put("BigInteger", "import java.math.BigInteger")
            .put("LocalDate", "import java.time.LocalDate")
            .put("LocalDateTime", "import java.time.LocalDateTime")
            .put("BigDecimal", "import java.math.BigDecimal")
            .put("Set", "import java.util.Set")
            .put("LocalTime", "import java.time.LocalTime")
            .toMap();
    private static final Map<String, String> JAVA_TYPE_BY_SQL_TYPE =
        EzyMapBuilder
            .mapBuilder()
            .put("binary", "byte[]")
            .put("bigint", "BigInteger")
            .put("bit", "byte")
            .put("blob", "byte[]")
            .put("bool", "boolean")
            .put("boolean", "boolean")
            .put("char", "String")
            .put("date", "LocalDate")
            .put("datetime", "LocalDateTime")
            .put("dec", "BigDecimal")
            .put("decimal", "BigDecimal")
            .put("double", "double")
            .put("double precision", "double")
            .put("enum", "String")
            .put("float", "float")
            .put("int", "int")
            .put("integer", "int")
            .put("longblob", "byte[]")
            .put("longtext", "String")
            .put("mediumblob", "byte[]")
            .put("mediumint", "int")
            .put("mediumtext", "String")
            .put("nchar", "String")
            .put("ntext", "String")
            .put("nvarchar", "String")
            .put("set", "Set<String>")
            .put("smallint", "int")
            .put("text", "String")
            .put("time", "LocalTime")
            .put("timestamp", "long")
            .put("tinyblob", "byte[]")
            .put("tinyint", "boolean")
            .put("tinytext", "String")
            .put("varchar", "String")
            .put("year", "int")
            .toMap();

    private static final Pattern SQL_WRAP_COMPILE = Pattern.compile(
        "`",
        Pattern.LITERAL    );

    public String generate(
        String createTableScript
    ) {
        final String[] lines = createTableScript.split("\n");
        String tableName = "";
        String className = "";
        final List<String> outputLines = new ArrayList<>();
        final Set<String> importSet = Sets.newHashSet(
            "import lombok.*",
            "import javax.persistence.*"

        );
        for (String line : lines) {
            final String[] components = line.trim().split(" ");
            if (components.length < 2) {
                continue;
            }
            for (int i = 0; i < components.length; ++i) {
                components[i] = components[i].trim().toLowerCase();
            }
            if (line.toLowerCase().contains("create table")) {
                final String last = components[components.length - 1];
                tableName = SQL_WRAP_COMPILE                    .matcher(
                        "(".equals(last)
                            ? components[components.length - 2]
                            : last.substring(0, last.length() - 1)
                    )
                    .replaceAll(Matcher.quoteReplacement(""));
                className = EzyStrings.toDisplayName(
                    EzyStrings.underscoreToCamelCase(
                        EzyStrings.toUnderscoreCase(tableName)
                    )
                );
                continue;
            }
            boolean isFieldLine = false;
            final String fieldTypeRaw = components[1];
            String fieldJavaType = fieldTypeRaw;
            for (Entry<String, String> entry : JAVA_TYPE_BY_SQL_TYPE.entrySet()) {
                if (fieldTypeRaw.startsWith(entry.getKey())) {
                    isFieldLine = true;
                    fieldJavaType = entry.getValue();
                    break;
                }
            }
            if (isFieldLine) {
                final String fieldNameRaw = SQL_WRAP_COMPILE
                    .matcher(components[0])
                    .replaceAll(
                        Matcher.quoteReplacement("")
                    );
                final String fieldName = EzyStrings.underscoreToCamelCase(
                    EzyStrings.toUnderscoreCase(fieldNameRaw)
                );
                if ("id".equals(fieldName)) {
                    fieldJavaType = "long";
                    outputLines.add("@Id");
                    outputLines.add("@GeneratedValue(strategy = GenerationType.IDENTITY)");
                }
                final String importItem = JAVA_IMPORT_BY_TYPE.get(fieldJavaType);
                if (importItem != null) {
                    importSet.add(importItem);
                }
                if (!fieldName.equals(fieldNameRaw)) {
                    importSet.add("import javax.persistence.Column");
                    outputLines.add(
                        "@Column(name = \"" + fieldNameRaw + "\")"
                    );
                }
                outputLines.add(
                    "private " + fieldJavaType + ' ' + fieldName + ";"
                );
            }
        }
        final List<String> importList = importSet
            .stream()
            .sorted()
            .collect(Collectors.toList());
        importList.add("");
        final StringBuilder builder = new StringBuilder()
            .append(String.join(";\n", importList))
            .append("\n")
            .append("@Getter\n")
            .append("@Setter\n")
            .append("@ToString\n")
            .append("@Entity\n")
            .append("@Table(name = \"")
            .append(tableName)
            .append("\")\n")
            .append("@AllArgsConstructor\n")
            .append("@NoArgsConstructor\n")
            .append("public class ")
            .append(className)
            .append(" {\n");
        if (!outputLines.isEmpty() && !outputLines.get(0).startsWith("@")) {
            builder.append('\n');
        }
        for (int i = 0; i < outputLines.size(); ++i) {
            final String line = outputLines.get(i);
            builder
                .append("    ")
                .append(line)
                .append('\n');
            if (i < outputLines.size() - 1 && !line.startsWith("@")) {
                builder.append('\n');
            }
        }
        builder.append('}');
        return builder.toString();
    }
}
