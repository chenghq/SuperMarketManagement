package com.common.mydb;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * 
 * @author 13072937
 * 
 */
public class SqlCommands {

	private String tableName;

	private String id;

	private String idName;

	private boolean isGenerator = true;

	private String sequenceName;

	// private String sequenceColumnName;

	private String catalog = "db2"; // default db2 database.

	// private int allcationSize = 1;

	private List<String> columnList = new ArrayList<String>();

	private List<String> columnNameList = new ArrayList<String>();

	private List<String> fieldList = new ArrayList<String>();

	private String insert;

	private String update;

	private String delete;

	private String select;

	private String dynamicUpdate;

	private String selectAll;

	private String querySequence;

	private boolean isMysql;

	private Map<String, Column> propertyMap = new HashMap<String, Column>();

	private Map<String, Column> fieldMap = new HashMap<String, Column>();

	public SqlCommands(Class<? extends Object> clazz) {
		setFieldList(clazz);
		setTable(clazz);
		setId(clazz);
		setColumnList(clazz);

		setInsertSql();
		setUpdateSql();
		setDeleteSql();
		setSelectSql();
		setSelectAllSql();
	}

	public String getId() {
		return id;
	}

	public String getInsert() {
		return insert;
	}

	public String getUpdate() {
		return update;
	}

	public String getDynamicUpdate(Map<String, ?> object) {
		setDynamicUpdateSql(object);
		return dynamicUpdate;
	}

	public String getDelete() {
		return delete;
	}

	public String getSelect() {
		return select;
	}

	public String querySequence() {
		buildQuerySequence();
		return querySequence;
	}

	private void buildQuerySequence() {
		if (catalog != null && StringUtils.isNotBlank(sequenceName)
				&& StringUtils.isBlank(querySequence)) {
			StringBuffer buffer = new StringBuffer();
			if ("db2".equalsIgnoreCase(catalog)) {
				buffer.append("SELECT NEXTVAL FOR ").append(sequenceName)
						.append(" FROM SYSIBM.SYSSEQUENCES WHERE SEQNAME='")
						.append(sequenceName).append("'");
				querySequence = buffer.toString();
			}
			if ("mysql".equalsIgnoreCase(catalog)) {
				// buffer.append("update ").append(sequenceName).append(" set ").append(sequenceColumnName)
				// .append(" = last_insert_id(").append(sequenceColumnName).append("+").append(allcationSize)
				// .append(")");
				// querySequence = buffer.toString();
				// isMysql = true;
			}
		}
	}

	public boolean isMysql() {
		return isMysql;
	}

	public String getSelectAll() {
		return selectAll;
	}

	private void setDynamicUpdateSql(Map<String, ?> object) {
		StringBuffer sb = new StringBuffer("UPDATE ");
		sb.append(tableName).append(" SET ");
		int size = columnNameList.size();
		for (int i = 0; i < size; i++) {

			if (object.get(columnList.get(i)) == null
					|| !propertyMap.get(columnNameList.get(i)).updatable()) {
				continue;
			}

			sb.append(columnNameList.get(i)).append(" = :")
					.append(columnList.get(i));
			sb.append(", ");
		}
		sb.deleteCharAt(sb.length() - 2);
		sb.append(" WHERE ");
		sb.append(idName).append(" = :").append(id);
		dynamicUpdate = sb.toString();
	}

	private void setInsertSql() {
		StringBuffer sb = new StringBuffer("INSERT INTO ");
		sb.append(tableName).append("(");

		if (!isGenerator) {
			if (idName != null && id != null) {
				sb.append(idName);
				sb.append(", ");
			}
		}
		int size = columnNameList.size();
		for (int i = 0; i < size; i++) {
			if (propertyMap.get(columnNameList.get(i)).insertable()) {
				sb.append(columnNameList.get(i));
				sb.append(", ");
			}
		}
		sb.deleteCharAt(sb.length() - 2);
		sb.append(") VALUES (");
		if (!isGenerator) {
			if (idName != null && id != null) {
				sb.append(":").append(id);
				sb.append(", ");
			}
		}
		size = columnList.size();
		for (int i = 0; i < size; i++) {
			if (fieldMap.get(columnList.get(i)).insertable()) {
				sb.append(":").append(columnList.get(i));
				sb.append(", ");
			}
		}
		sb.deleteCharAt(sb.length() - 2);
		sb.append(")");
		insert = sb.toString();
	}

	private void setUpdateSql() {
		StringBuffer sb = new StringBuffer("UPDATE ");
		sb.append(tableName).append(" SET ");
		int size = columnNameList.size();
		for (int i = 0; i < size; i++) {
			if (propertyMap.get(columnNameList.get(i)).updatable()) {
				sb.append(columnNameList.get(i)).append(" = :")
						.append(columnList.get(i));
				sb.append(", ");
			}
		}
		sb.deleteCharAt(sb.length() - 2);
		sb.append(" WHERE ");
		sb.append(idName).append(" = :").append(id);
		update = sb.toString();
	}

	private void setDeleteSql() {
		StringBuffer sb = new StringBuffer("DELETE FROM ");
		sb.append(tableName).append(" WHERE ");
		sb.append(idName).append(" = :").append(id);
		delete = sb.toString();
	}

	private void setSelectSql() {
		StringBuffer sb = new StringBuffer("SELECT ");
		List<String> tempList = new ArrayList<String>(columnNameList);
		tempList.add(idName);
		int size = tempList.size();
		for (int i = 0; i < size; i++) {
			sb.append(tempList.get(i));
			if (i < size - 1) {
				sb.append(", ");
			}
		}
		sb.append(" FROM ").append(tableName).append(" WHERE ");
		sb.append(idName).append(" = :").append(id);
		select = sb.toString();
	}

	private void setSelectAllSql() {
		StringBuffer sb = new StringBuffer("SELECT ");
		List<String> tempList = new ArrayList<String>(columnNameList);
		tempList.add(idName);
		int size = tempList.size();
		for (int i = 0; i < size; i++) {
			sb.append(tempList.get(i));
			if (i < size - 1) {
				sb.append(", ");
			}
		}
		sb.append(" FROM ").append(tableName);
		selectAll = sb.toString();
	}

	private void setTable(Class<? extends Object> clazz) {
		Entity entity = clazz.getAnnotation(Entity.class);
		tableName = entity.name().toUpperCase();
	}

	private void setFieldList(Class<? extends Object> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			fieldList.add(field.getName());
		}
	}

	private void setId(Class<? extends Object> clazz) {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Id.class)) {
				idName = method.getAnnotation(Column.class).name();
				id = BeanUtils.findPropertyForMethod(method).getName();
				
				// TODO
			}
		}
	}

	private void setColumnList(Class<? extends Object> clazz) {
		Method[] methods = clazz.getMethods();
		Field[] fields = clazz.getDeclaredFields();
		Field[] superFields = clazz.getSuperclass().getDeclaredFields();
		for (Method method : methods) {
			if (method.isAnnotationPresent(Column.class)
					&& !method.isAnnotationPresent(Id.class)) {
				PropertyDescriptor descriptor = BeanUtils
						.findPropertyForMethod(method);
				if (isTransient(fields, descriptor.getName())
						|| isTransient(superFields, descriptor.getName())) {
					continue;
				}
				Column columnAnnoation = method.getAnnotation(Column.class);
				columnNameList.add(columnAnnoation.name());
				columnList.add(BeanUtils.findPropertyForMethod(method)
						.getName());
				propertyMap.put(columnAnnoation.name(), columnAnnoation);
				fieldMap.put(BeanUtils.findPropertyForMethod(method).getName(),
						columnAnnoation);
			}
		}
	}

	private boolean isTransient(Field[] fields, String fileName) {
		for (Field field : fields) {
			if (field.getName().equals(fileName)
					&& Modifier.isTransient(field.getModifiers())) {
				return true;
			}
		}
		return false;
	}
}
