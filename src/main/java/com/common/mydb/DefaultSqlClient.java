package com.common.mydb;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.common.mydb.page.RowMapperFactory;
import com.common.mydb.resources.FreeMakerParser;
import com.common.mydb.resources.XmlBean;
import com.common.mydb.resources.XmlResources;
import com.common.mydb.utils.SqlUtils;


public class DefaultSqlClient extends XmlResources implements SqlClient,
		InitializingBean {

	private static Logger logger = LoggerFactory
			.getLogger(DefaultSqlClient.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		/**
		 */
		parseResource();
	}

	@Override
	public Number persist(Object entity) {
		return persist(entity, Number.class);
	}

	@SuppressWarnings({ "unchecked" })
	private <T> T persist(Object entity, Class<T> requiredType) {
		SqlCommands sqlCommands = SqlParserManager.getSqlParser(entity
				.getClass());
		String insertSQL = sqlCommands.getInsert();

		Map<String, Object> paramMap = ValueParser.parser(entity);
		logMessage("persist", insertSQL, entity);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getNamedParameterJdbcTemplate().update(insertSQL, new MapSqlParameterSource(paramMap), keyHolder);
		Object key = paramMap.get(sqlCommands.getId());
		if (null == key || (key instanceof Number && ((Number) key).doubleValue() == 0.0d)) {
			SqlUtils.setProperty(entity, sqlCommands.getId(), keyHolder.getKey());
            return (T) keyHolder.getKey();
		}
		return (T) key;
	}

	@Override
	public int update(Object entity) {
		SqlCommands sqlCommands = SqlParserManager.getSqlParser(entity
				.getClass());
		String updateSQL = sqlCommands.getUpdate();
		Map<String, Object> paramMap = ValueParser.parser(entity);
		logMessage("update", updateSQL, entity);
		
		int id = getNamedParameterJdbcTemplate().update(updateSQL, paramMap);
		return id;
	}
	
	@Override
	public <T> T queryForObject(String sqlId, Map<String, Object> paramMap, Class<T> requiredType) {
		return this.queryForObject(sqlId, paramMap, new RowMapperFactory<T>(requiredType).getRowMapper());
	}
	
	public <T> T queryForObject(String sqlId, Map<String, Object> paramMap, RowMapper<T> rowMapper) {
        XmlBean xmlBean = getSQL(sqlId);
        String sql = FreeMakerParser.process(paramMap, xmlBean.getContent(), sqlId);
        logMessage("queryForObject", sql, rowMapper);
        List<T> resultList = getNamedParameterJdbcTemplate().query(sql, paramMap, rowMapper);
        return singleResult(resultList);
    }
	
	@Override
	public <T> List<T> queryForList(String sqlId, Map<String, Object> paramMap,
			Class<T> requiredType) {
		return this.queryForList(sqlId, paramMap, new RowMapperFactory<T>(requiredType).getRowMapper());
	}
	
	public <T> List<T> queryForList(String sqlId, Map<String, Object> paramMap, RowMapper<T> rowMapper) {
        XmlBean xmlBean = getSQL(sqlId);
        String sql = FreeMakerParser.process(paramMap, xmlBean.getContent(), sqlId);
        logMessage("queryForList", sql, rowMapper);
        List<T> resultList = getNamedParameterJdbcTemplate().query(sql, paramMap, rowMapper);
        return resultList;
    }
	
	@Override
    public Map<String, Object> queryForMap(final String sqlId, final Map<String, Object> paramMap) {
		XmlBean xmlBean = getSQL(sqlId);
        String sql = FreeMakerParser.process(paramMap, xmlBean.getContent(), sqlId);
        
        return getNamedParameterJdbcTemplate().queryForMap(sql, paramMap);
    }
	
	private void logMessage(String method, String sql, Object object) {
		if (logger.isDebugEnabled()) {
            logger.debug(method + " method SQL: [" + sql + "]");
            logger.debug(method + " method parameter:" + object);
        }
		
		if (logger.isInfoEnabled()) {
			logger.info(method + " method SQL: [" + sql + "]");
            logger.info(method + " method parameter:" + object);
		}
	}
	
	private <T> T singleResult(List<T> resultList) {
        if (resultList != null) {
            int size = resultList.size();
            if (size > 0) {
                if (logger.isDebugEnabled() && size > 1) {
                    logger.debug("Incorrect result size: expected " + 1 + ", actual " + size
                            + " return the first element.");
                }
                return resultList.get(0);
            }
            if (size == 0) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Incorrect result size: expected " + 1 + ", actual " + size);
                }
                return null;
            }
        }
        return null;
    }
}
