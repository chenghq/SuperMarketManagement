package com.weixin.service;

import java.util.Map;

import com.common.mydb.page.QueryParam;
import com.common.mydb.page.QueryResult;
import com.weixin.dao.bean.Products;


public interface ProductsService {
	QueryResult<Products> queryProducts(QueryParam<Map<String, Object>> callParam);
}
