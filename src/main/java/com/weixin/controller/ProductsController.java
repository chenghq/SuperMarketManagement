package com.weixin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import com.common.constant.ProductCst;
import com.common.mydb.page.QueryParam;
import com.common.mydb.page.QueryResult;
import com.weixin.dao.bean.Products;
import com.weixin.service.ProductsService;

/**
 * 商品的展示
 */
@Controller
@RequestMapping("/products")
public class ProductsController {
	private static final Logger logger = LoggerFactory
			.getLogger(ProductsController.class);

	@Resource
	ProductsService productsService;

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	@ResponseBody
	public void select(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		logger.info("The selected type is {}", type);

		if (StringUtils.isBlank(type)) {
			type = ProductCst.Type_01;
		}

		int pageSize = 10;
		String pageNumber = "1";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		QueryParam<Map<String, Object>> callParam = new QueryParam<Map<String, Object>>();

		callParam.setQueryParam(paramMap);
		callParam.setPageNumber(Integer.parseInt(pageNumber));
		callParam.setPageSize(pageSize);

		QueryResult<Products> products = productsService
				.queryProducts(callParam);

		PrintWriter printWriter = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		
		if (products.getDatas() == null) {
			printWriter.write("{'error': '没有更多内容了'}");
		} else {
			JSONArray json = JSONArray.fromObject(products.getDatas());
			printWriter.write(json.toString());
		}
	}

	/**
	 * 加载更多的信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/load", method = RequestMethod.GET)
	@ResponseBody
	public void load(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		logger.info("The selected type is {}", type);

		if (StringUtils.isBlank(type)) {
			type = ProductCst.Type_01;
		}

		int pageSize = 10;
		String pageNumber = request.getParameter("pageNumber");
		if (StringUtils.isBlank(pageNumber)) {
			pageNumber = "1";
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		QueryParam<Map<String, Object>> callParam = new QueryParam<Map<String, Object>>();

		callParam.setQueryParam(paramMap);
		callParam.setPageNumber(Integer.parseInt(pageNumber));
		callParam.setPageSize(pageSize);

		QueryResult<Products> products = productsService
				.queryProducts(callParam);
		PrintWriter printWriter = response.getWriter();
		response.setContentType("text/html; charset=UTF-8");
		
		if (products.getDatas() == null) {
			printWriter.write("{'error': '没有更多内容了'}");
		} else {
			JSONArray json = JSONArray.fromObject(products.getDatas());
			printWriter.write(json.toString());
		}
	}
}
