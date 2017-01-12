package com.common.mydb.resources;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.mydb.utils.SqlUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * @author 13072937
 * 
 */
public class FreeMakerParser {
	private static final String DEFAULT_TEMPLATE_KEY = "default_template_key";
	private static final String DEFAULT_TEMPLATE_EXPRESSION = "default_template_expression";
	private static final Configuration CONFIGURER = new Configuration();
	static {
		CONFIGURER.setClassicCompatible(true);
		CONFIGURER.setSharedVariable("hash", new TemplateMethodModel() {
			
			@SuppressWarnings("rawtypes")
			@Override
			public Object exec(List args) throws TemplateModelException {
				String paramValue = args.get(0).toString();
		        if (paramValue != null) {
		            return SqlUtils.HashAlgorithm.KETAMA_HASH.hash(SqlUtils.computeMd5(paramValue), 0);
		        }
		        return 0;
			}
		});
	}
	private static Map<String, Template> templateCache = new HashMap<String, Template>();
	private static Map<String, Template> expressionCache = new HashMap<String, Template>();

	public static String process(String expression, Map<String, Object> root) {
		StringReader reader = null;
		StringWriter out = null;
		Template template = null;
		try {
			if (expressionCache.get(expression) != null) {
				template = expressionCache.get(expression);
			}
			if (template == null) {
				template = createTemplate(DEFAULT_TEMPLATE_EXPRESSION,
						new StringReader(expression));
				expressionCache.put(expression, template);
			}
			out = new StringWriter();
			template.process(root, out);
			return out.toString();
		} catch (Exception e) {
			// TODO
			return null;
		} finally {
			if (reader != null) {
				reader.close();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				return null;
			}
		}
	}

	private static Template createTemplate(String templateKey,
			StringReader reader) throws IOException {
		Template template = new Template(DEFAULT_TEMPLATE_KEY, reader,
				CONFIGURER);
		template.setNumberFormat("#");
		return template;
	}

	public static String process(Map<String, Object> root, String sql,
			String sqlId) {
		StringReader reader = null;
		StringWriter out = null;
		Template template = null;
		try {
			if (templateCache.get(sqlId) != null) {
				template = templateCache.get(sqlId);
			}
			if (template == null) {
				reader = new StringReader(sql);
				template = createTemplate(DEFAULT_TEMPLATE_KEY, reader);
				templateCache.put(sqlId, template);
			}
			out = new StringWriter();
			template.process(root, out);
			return out.toString();
		} catch (Exception e) {
			// TODO
			return null;
		} finally {
			if (reader != null) {
				reader.close();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				return null;
			}
		}
	}
}
