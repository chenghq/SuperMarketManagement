package com.weixin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.mydb.page.QueryParam;
import com.common.mydb.page.QueryResult;
import com.weixin.dao.ProductsDao;
import com.weixin.dao.bean.Products;
import com.weixin.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {
	@Resource
	ProductsDao productsDao;

	@Override
	public QueryResult<Products> queryProducts(QueryParam<Map<String, Object>> callParam) {
		
		Map<String, Object> paramMap = callParam.getQueryParam(); //其中的参数保持一致
		Map<String, Object> queryResult = productsDao.queryCountByParam(paramMap);
		int count = 0;
		if (queryResult != null) {
			count = Integer.parseInt(queryResult.get("count").toString());
		}
		QueryResult<Products> result = new QueryResult<Products>(count, callParam.getPageSize(), callParam.getPageNumber());
		
		paramMap.put("startIndex", result.getIndexNumber());
        paramMap.put("maxCount", result.getPageSize());
        if (result.getTotalDataCount() <= (callParam.getPageNumber() - 1) * callParam.getPageSize()) {
            result.setDatas(null);
        } else {
        	List<Products> datas = productsDao.queryByParam(paramMap);
            result.setDatas(datas);
        }
        result.setPageNumber(callParam.getPageNumber());
        result.setPageCount(callParam.getPageSize());
		return result;
	}

}
