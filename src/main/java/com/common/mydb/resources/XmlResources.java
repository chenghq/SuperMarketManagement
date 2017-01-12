package com.common.mydb.resources;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class XmlResources {
	private Resource[] resources;
	private JdbcTemplate jdbcTemplate;
	
	private Map<String, XmlBean> sqlContainer = new HashMap<String, XmlBean>();

    public synchronized Resource[] getResources() {
        return resources;
    }

    public synchronized void setResources(Resource[] resources) {
        this.resources = resources;
    }

	public synchronized JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public synchronized NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public synchronized void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
    /**
     * 
     */
    protected void parseResource() {
        XmlParser.getInstance().parse(getResources(), sqlContainer);
    }
    /**
     * 
     *
     * @param sqlId 
     * @return
     */
    protected XmlBean getSQL(String sqlId) {
    	XmlBean sqlBean = sqlContainer.get(sqlId);
        if (sqlBean == null || sqlBean.getContent() == null || "".equals(sqlBean.getContent())) {
        	// TODO
        }
        return sqlBean;
    }

}
