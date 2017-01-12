package com.common.mydb.page;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author 13072937
 * 
 * @param <T> 
 */
public class QueryResult<T> implements Serializable{
    /**
     */
    private static final long serialVersionUID = -1053229249849727406L;

    private List<T> datas;

    private Boolean lastPage;

    private Integer totalDataCount;

    private int     pageNumber = 1;

    private int     pageSize   = 10;

    private Integer pageCount;

    private int     indexNumber;

    /**
     * @param totalDataCount
     * @param pageSize
     * @param pageNumber
     */
    public QueryResult(int totalDataCount, int pageSize, int pageNumber) {
        super();
        this.totalDataCount = totalDataCount;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        if (this.pageNumber < 1) {
            this.pageNumber = 1;
        }
        if (this.totalDataCount <= 0) {
            return;
        }
        if (this.totalDataCount <= (this.pageNumber - 1) * this.pageSize) {
            this.pageNumber = (this.totalDataCount + this.pageSize - 1) / this.pageSize;
        }
        this.indexNumber = (this.pageNumber - 1) * this.pageSize;
        this.pageCount = (this.totalDataCount + this.pageSize - 1) / this.pageSize;
        this.lastPage = (this.pageNumber == this.pageCount ? true : false);
    }

    public QueryResult() {
        super();
    }

    /**
     * @return the datas
     */
    public List<T> getDatas() {
        return datas;
    }

    /**
     * @param datas the datas to set
     */
    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    /**
     * @return the totalDataCount
     */
    public Integer getTotalDataCount() {
        return totalDataCount;
    }

    /**
     * @param totalDataCount the totalDataCount to set
     */
    public void setTotalDataCount(Integer totalDataCount) {
        this.totalDataCount = totalDataCount;
    }

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * 
     * @return the pageCount
     */
    public Integer getPageCount() {
        return pageCount;
    }

    /**
     * @param pageCount the pageCount to set
     */
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getLastPage() {
		return lastPage;
	}

	public void setLastPage(Boolean lastPage) {
		this.lastPage = lastPage;
	}

	/**
     * @return the lastPage
     */
    public int getIndexNumber() {
        return indexNumber;
    }
}

