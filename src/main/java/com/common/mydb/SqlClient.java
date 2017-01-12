package com.common.mydb;

import java.util.List;
import java.util.Map;

public interface SqlClient {
	Number persist(Object entity);
	
	int update(Object entity);
	
	<T> T queryForObject(String sqlId, Map<String, Object> paramMap, Class<T> requiredType);
	
	<T> List<T> queryForList(String sqlId, Map<String, Object> paramMap, Class<T> requiredType);
	
	Map<String, Object> queryForMap(String sqlId, Map<String, Object> paramMap);
}