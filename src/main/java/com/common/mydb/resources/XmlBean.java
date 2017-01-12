package com.common.mydb.resources;

public class XmlBean {
	private String id;
	private boolean isRead;
	private String content;
	private String dsName;
	private int jdbcTimeout;

	public XmlBean(String id, String isRead, String content, String dsName,
			String jdbcTimeout) {
		super();
		this.id = id;
		if (isRead != null) {
			try {
				this.isRead = Boolean.valueOf(isRead);
			} catch (NumberFormatException e) {
				this.isRead = Boolean.FALSE;
			}
		}
		this.content = content;
		this.dsName = dsName;
		if (jdbcTimeout != null) {
			try {
				this.jdbcTimeout = Integer.valueOf(jdbcTimeout);
			} catch (NumberFormatException e) {
				this.jdbcTimeout = 0;
			}
		}
	}

	public XmlBean(String id, boolean isRead, String content, String dsName) {
		super();
		this.id = id;
		this.isRead = isRead;
		this.content = content;
		this.dsName = dsName;
		this.jdbcTimeout = 0;
	}

	public XmlBean() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	/**
	 * @return the jdbcTimeout
	 */
	public int getJdbcTimeout() {
		return jdbcTimeout;
	}

	/**
	 * @param jdbcTimeout
	 *            the jdbcTimeout to set
	 */
	public void setJdbcTimeout(int jdbcTimeout) {
		this.jdbcTimeout = jdbcTimeout;
	}

}
