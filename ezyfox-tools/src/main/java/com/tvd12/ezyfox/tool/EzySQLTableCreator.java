package com.tvd12.ezyfox.tool;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyAnnotatedElement;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyGetterMethod;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzySetterMethod;
import com.tvd12.ezyfox.tool.data.EzyCaseType;

public class EzySQLTableCreator {

	protected final String entityName;
	protected final EzyClass entityClass;
	protected final EzyCaseType caseType;
	protected final Map<String, EzyAnnotatedElement> fieldElements;
	protected final static Map<Class<?>, String> TYPE_MAP = defaultTypeMap();
	
	public EzySQLTableCreator(Class<?> entityType, EzyCaseType caseType) {
		this.caseType = caseType;
		this.entityClass = new EzyClass(entityType);
		this.fieldElements = fetchFieldElements();
		this.entityName = entityType.getSimpleName();
	}
	
	protected Map<String, EzyAnnotatedElement> fetchFieldElements() {
		Map<String, EzyAnnotatedElement> map = new HashMap<>();
		List<EzyField> fields = entityClass.getFields(f -> isValidField(f));
		for(EzyField field : fields)
			map.put(field.getName(), field);
		List<EzyMethod> methods = entityClass.getMethods();
		for(EzyMethod method : methods) {
			if(method.isAnnotated(Column.class))
				map.put(method.getFieldName(), method);
		}
		return map;
	}
	
	protected boolean isValidField(EzyField field) {
		int modifier = field.getField().getModifiers();
		return !Modifier.isStatic(modifier) && !Modifier.isFinal(modifier);
	}
	
	public String createScript() {
		StringBuilder builder = new StringBuilder()
				.append("CREATE TABLE IF NOT EXISTS ")
				.append("`").append(standardizedName(entityName)).append("` (\n");
		List<String> columnsDeclare = new ArrayList<>();
		for(String fieldName : fieldElements.keySet()) {
			EzyAnnotatedElement element = fieldElements.get(fieldName);
			Column columnAnno = element.getAnnotation(Column.class);
			String columName = getColumnName(fieldName, element);
			StringBuilder columnBuilder = new StringBuilder()
					.append("\t").append("`" + columName + "`")
					.append(" ")
					.append(getTypeValue(element));
			if(columnAnno != null) {
				if(!columnAnno.nullable())
					columnBuilder.append(" NOT NULL");
				if(columnAnno.unique())
					columnBuilder.append(" UNIQUE");
			}
			columnsDeclare.add(columnBuilder.toString());
		}
		return builder
				.append(String.join(",\n", columnsDeclare))
				.append("\n) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;")
				.toString();
	}
	
	protected String getTypeValue(EzyAnnotatedElement element) {
		Column columnAnno = element.getAnnotation(Column.class);
		Class<?> elementType = null;
		if(element instanceof EzyField)
			elementType = ((EzyField)element).getType();
		else if(element instanceof EzyGetterMethod)
			elementType = ((EzyMethod)element).getReturnType();
		else
			elementType = ((EzySetterMethod)element).getType();
		String sqlType = TYPE_MAP.get(elementType);
		if(sqlType == null) {
			sqlType = "VARCHAR(45)";
		}
		else if(elementType == String.class) {
			int length = columnAnno.length();
			if(length <= 0)
				length = 45;
			sqlType = sqlType + "(" + length + ")";
		}
		return sqlType;
	}
	
	protected String getColumnName(String fieldName, EzyAnnotatedElement element) {
		String columName = null;
		Column columnAnno = element.getAnnotation(Column.class);
		if(columnAnno != null)
			columName = columnAnno.name();
		if(!EzyStrings.isNoContent(columName))
			return columName;
		return standardizedName(fieldName);
	}
	
	protected String standardizedName(String inputName) {
		if(caseType == EzyCaseType.UPPERCASE)
			return EzyStringTool.toUnderscore(inputName).toUpperCase();
		if(caseType == EzyCaseType.LOWERCASE)
			return inputName.toLowerCase();
		if(caseType == EzyCaseType.CAMEL)
			return EzyStringTool.lowerFistChar(inputName);
		if(caseType == EzyCaseType.DASH)
			return EzyStringTool.toUnderscore(inputName).replace('_', '-');
		if(caseType == EzyCaseType.UNDERSCORE)
			return EzyStringTool.toUnderscore(inputName);
		return inputName;
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
		return Collections.unmodifiableMap(map);
	}
	
}
