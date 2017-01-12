package com.weixin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * ������
 * 
 * @author 13072937
 *
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory
			.getLogger(CommonInterceptor.class);

	/*
	 * ��������ӳ�䵽��Ҫ���ص�·��
	 * 
	 * private String mappingURL;
	 * 
	 * public void setMappingURL(String mappingURL) { this.mappingURL =
	 * mappingURL; }
	 */
	/**
	 * ��ҵ��������������֮ǰ������ �������false �ӵ�ǰ������������ִ��������������afterCompletion(),���˳���������
	 * �������true ִ����һ��������,ֱ�����е���������ִ����� ��ִ�б����ص�Controller Ȼ�������������,
	 * �����һ������������ִ�����е�postHandle() �����ٴ����һ������������ִ�����е�afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			// RequestUtil.saveRequest();
		}
		logger.info("==============ִ��˳��: 1��preHandle================");
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());

		logger.info("requestUri:" + requestUri);
		logger.info("contextPath:" + contextPath);
		logger.info("url:" + url);

		String username = (String) request.getSession().getAttribute("user");
		if (username == null) {
			logger.info("Interceptor����ת��loginҳ�棡");
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(
					request, response);
			return false;
		} else {
			// TODO ��֤�����Ƿ���ڻ����Ƿ���ȷ
			return true;
		}
	}

	/**
	 * ��ҵ��������������ִ����ɺ�,������ͼ֮ǰִ�еĶ��� ����modelAndView�м������ݣ����統ǰʱ��
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("==============ִ��˳��: 2��postHandle================");
		if (modelAndView != null) { // ���뵱ǰʱ��
			modelAndView.addObject("var", "����postHandle");
		}
	}

	/**
	 * ��DispatcherServlet��ȫ����������󱻵���,������������Դ��
	 * 
	 * �����������׳��쳣ʱ,��ӵ�ǰ����������ִ�����е���������afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("==============ִ��˳��: 3��afterCompletion================");
	}
}
