package com.tvd12.ezyfox.tool;

import java.io.File;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tvd12.ezyfox.file.EzySimpleFileWriter;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyAnnotatedElement;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.tool.data.EzyCaseType;

public class EzySQLTableCreator {

    protected final Class<?> idType;
    protected final EzyClass idClass;
    protected final String entityName;
    protected final EzyClass entityClass;
    protected final boolean customizedId;
    protected final boolean overrideExists;
    protected final EzyCaseType caseType;
    protected final List<EzyField> idFields;
    protected final EzyAnnotatedElement idElement;
    protected final Map<String, EzyAnnotatedElement> fieldElements;
    protected final static Map<Class<?>, String> TYPE_MAP = defaultTypeMap();

    public EzySQLTableCreator(
            Class<?> entityType, EzyCaseType caseType) {
        this(entityType, caseType, true, false);
    }

    public EzySQLTableCreator(
            Class<?> entityType,
            EzyCaseType caseType, boolean customizedId) {
        this(entityType, caseType, customizedId, false);
    }

    public EzySQLTableCreator(
            Class<?> entityType,
            EzyCaseType caseType,
            boolean customizedId, boolean overrideExists) {
        this.caseType = caseType;
        this.customizedId = customizedId;
        this.overrideExists = overrideExists;
        this.entityClass = new EzyClass(entityType);
        this.fieldElements = fetchFieldElements();
        this.idElement = fetchAndRemoveIdElement();
        this.idType = getElementType(idElement);
        this.idClass = idType != null ? new EzyClass(idType) : null;
        this.idFields = getIdFields();
        this.entityName = entityType.getSimpleName();
    }

    protected Map<String, EzyAnnotatedElement> fetchFieldElements() {
        Map<String, EzyAnnotatedElement> map = new HashMap<>();
        List<EzyField> fields = entityClass.getFields(f -> isValidField(f));
        for(EzyField field : fields)
            map.put(field.getName(), field);
        List<EzyMethod> methods = entityClass.getMethods();
        for(EzyMethod method : methods) {
            if(method.isAnnotated(Id.class) || method.isAnnotated(Column.class))
                map.put(method.getFieldName(), method);
        }
        return map;
    }

    protected EzyAnnotatedElement fetchAndRemoveIdElement() {
        String idFieldName = null;
        EzyAnnotatedElement idElement = null;
        for(String fieldName : fieldElements.keySet()) {
            EzyAnnotatedElement element = fieldElements.get(fieldName);
            if(element.isAnnotated(Id.class)) {
                idElement = element;
                idFieldName = fieldName;
                break;
            }
            if(fieldName.equals("id")) {
                idElement = element;
                idFieldName = fieldName;
            }
        }
        fieldElements.remove(idFieldName);
        return idElement;
    }

    protected List<EzyField> getIdFields() {
        if(idClass == null)
            return Collections.emptyList();
        return idClass.getFields(f -> isValidField(f));
    }

    protected boolean isValidField(EzyField field) {
        int modifier = field.getField().getModifiers();
        return !Modifier.isStatic(modifier) && !Modifier.isFinal(modifier);
    }

    public String createScriptToFolder(String folderPath) {
        File folder = new File(folderPath);
        folder.mkdirs();
        String fileName = "create-table-"
                + standardizedName(entityName, EzyCaseType.DASH)
                + ".sql";
        return createScriptToFile(Paths.get(folderPath, fileName).toString());
    }

    public String createScriptToFile(String filePath) {
        File file = new File(filePath);
        if(file.exists() && !overrideExists)
            return file.getAbsolutePath();
        EzyFileTool.createParentDir(file.toPath());
        String script = createScript();
        EzySimpleFileWriter.builder().build()
            .write(new File(filePath), script, StandardCharsets.UTF_8);
        return file.getAbsolutePath();
    }

    public String createScript() {
        StringBuilder builder = new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS ")
                .append("`").append(getTableName()).append("` (\n");
        List<String> columnsDeclare = new ArrayList<>();
        addIdColumDeclare(columnsDeclare);
        for(String fieldName : fieldElements.keySet()) {
            EzyAnnotatedElement element = fieldElements.get(fieldName);
            StringBuilder columnBuilder = getColumnDeclare(element);
            columnsDeclare.add(columnBuilder.toString());
        }
        addPrimaryKeyDeclare(columnsDeclare);
        return builder
                .append(String.join(",\n", columnsDeclare))
                .append("\n) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;")
                .toString();
    }

    protected String getTableName() {
        String tableName = null;
        Entity entityAnno = entityClass.getAnnotation(Entity.class);
        if(entityAnno != null)
            tableName = entityAnno.name();
        if(!EzyStrings.isNoContent(tableName))
            return tableName;
        Table tableAnno = entityClass.getAnnotation(Table.class);
        if(tableAnno != null)
            tableName = tableAnno.name();
        if(!EzyStrings.isEmpty(tableName))
            return tableName;
        return standardizedName(entityName);

    }

    protected void addIdColumDeclare(List<String> columnsDeclare) {
        if(idElement == null)
            return;
        if(customizedId && !EzyToolTypes.ALL_TYPES.contains(idType)) {
            for(EzyField field : idFields) {
                StringBuilder declare = getColumnDeclare(field, true);
                columnsDeclare.add(declare.toString());
            }
        }
        else {
            StringBuilder declare = getColumnDeclare(idElement, true);
            GeneratedValue anno = idElement.getAnnotation(GeneratedValue.class);
            if(anno != null && anno.strategy() == GenerationType.IDENTITY)
                declare.append(" AUTO_INCREMENT");
            columnsDeclare.add(declare.toString());
        }
    }

    protected void addPrimaryKeyDeclare(List<String> columnsDeclare) {
        if(idElement == null)
            return;
        List<String> idColumns = new ArrayList<>();
        if(customizedId && !EzyToolTypes.ALL_TYPES.contains(idType)) {
            for(EzyField field : idFields)
                idColumns.add("`" + getColumnName(field) + "`");
        }
        else {
            idColumns.add("`" + getColumnName(idElement) + "`");
        }
        columnsDeclare.add("\tPRIMARY KEY (" + String.join(", ", idColumns) + ")");
    }

    public StringBuilder getColumnDeclare(EzyAnnotatedElement element) {
        return getColumnDeclare(element, false);
    }

    public StringBuilder getColumnDeclare(EzyAnnotatedElement element, boolean notNull) {
        Column columnAnno = element.getAnnotation(Column.class);
        String columName = getColumnName(element);
        StringBuilder builder = new StringBuilder()
                .append("\t").append("`" + columName + "`")
                .append(" ")
                .append(getTypeValue(element));
        if(columnAnno != null) {
            if(!columnAnno.nullable())
                builder.append(" NOT NULL");
            if(columnAnno.unique())
                builder.append(" UNIQUE");
        }
        else {
            if(notNull)
                builder.append(" NOT NULL");
        }
        return builder;
    }

    protected String getTypeValue(EzyAnnotatedElement element) {
        Class<?> elementType = getElementType(element);
        String sqlType = TYPE_MAP.get(elementType);
        if(sqlType == null) {
            sqlType = "VARCHAR(45)";
        }
        else if(elementType == String.class) {
            Column columnAnno = element.getAnnotation(Column.class);
            int length = columnAnno != null ? columnAnno.length() : 0;
            if(length <= 0)
                length = 45;
            sqlType = sqlType + "(" + length + ")";
        }
        return sqlType;
    }

    protected Class<?> getElementType(EzyAnnotatedElement element) {
        if(element == null)
            return null;
        Class<?> elementType = null;
        if(element instanceof EzyField) {
            elementType = ((EzyField)element).getType();
        }
        else {
            EzyMethod m = (EzyMethod)element;
            if(m.isGetter())
                elementType = ((EzyMethod)element).getReturnType();
            else
                elementType = ((EzyMethod)element).getParameterTypes()[0];
        }
        return elementType;
    }

    protected String getColumnName(EzyAnnotatedElement element) {
        String columName = null;
        Column columnAnno = element.getAnnotation(Column.class);
        if(columnAnno != null)
            columName = columnAnno.name();
        if(!EzyStrings.isNoContent(columName))
            return columName;
        String fieldName = getElementFieldName(element);
        return standardizedName(fieldName);
    }

    protected String getElementFieldName(EzyAnnotatedElement element) {
        if(element instanceof EzyMethod)
            return ((EzyMethod)element).getFieldName();
        return ((EzyField)element).getName();
    }

    protected String standardizedName(String inputName) {
        return standardizedName(inputName, caseType);
    }

    protected String standardizedName(String inputName, EzyCaseType caseType) {
        return EzyStringTool.standardized(inputName, caseType);
    }

    private static Map<Class<?>, String> defaultTypeMap() {
        Map<Class<?>, String> map = new HashMap<>();
        map.put(boolean.class, "TINYINT");
        map.put(byte.class, "INT");
        map.put(char.class, "CHAR");
        map.put(double.class, "DOUBLE");
        map.put(float.class, "DOUBLE");
        map.put(int.class, "INT");
        map.put(long.class, "BIGINT");
        map.put(short.class, "INT");
        map.put(Boolean.class, "TINYINT");
        map.put(Byte.class, "INT");
        map.put(Character.class, "CHAR");
        map.put(Double.class, "DOUBLE");
        map.put(Float.class, "DOUBLE");
        map.put(Integer.class, "INT");
        map.put(Long.class, "BIGINT");
        map.put(Short.class, "INT");
        map.put(String.class, "VARCHAR");
        map.put(Date.class, "DATETIME");
        map.put(java.sql.Date.class, "DATETIME");
        map.put(LocalDate.class, "DATE");
        map.put(LocalDateTime.class, "DATETIME");
        map.put(BigInteger.class, "NUMERIC");
        map.put(BigDecimal.class, "DECIMAL(19,5)");
        return Collections.unmodifiableMap(map);
    }
}