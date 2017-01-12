package com.common.mydb.page;

import java.io.Serializable;

/**
 * 
 * @author 13072937
 *
 * @param <T>
 */
public class QueryParam<T> implements Serializable {

	private static final long serialVersionUID = -4676609538098805055L;

	private int pageNumber = 1;
	private int pageSize = 10;
	private String orderBy;
	private boolean orderAsc;
	private boolean needCount = true;
	private T queryParam;

	/**
	 * 
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber
	 *            the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * 
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 
	 * @return the needCount
	 */
	public boolean isNeedCount() {
		return needCount;
	}

	/**
	 * @param needCount
	 *            the needCount to set
	 */
	public void setNeedCount(boolean needCount) {
		this.needCount = needCount;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrderAsc() {
		return orderAsc;
	}

	public void setOrderAsc(boolean orderAsc) {
		this.orderAsc = orderAsc;
	}

	public T getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(T queryParam) {
		this.queryParam = queryParam;
	}

}
