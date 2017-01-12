package com.common.mydb.resources;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public class XmlParser {
	private static Logger logger = LoggerFactory.getLogger(XmlParser.class);
	private static XmlParser parser = new XmlParser();

	private Map<String, XmlBean> sqlMapResult = new HashMap<String, XmlBean>();

	private XmlParser() {

	}

	public static XmlParser getInstance() {
		return parser;
	}

	public synchronized void parse(Resource[] resources, Map<String, XmlBean> sqlContainer) {
		parseDocuments(createDocuments(resources));
		sqlContainer.putAll(sqlMapResult);
	}

	private Map<String, Document> createDocuments(Resource[] resources) {
		Map<String, Document> documents = new HashMap<String, Document>();

		if (resources != null && resources.length > 0) {
			SAXReader saxReader = new SAXReader();
			for (Resource resource : resources) {
				try {
					String fileName = resource.getFilename();
					InputStream reader = resource.getInputStream();
					Document doc = saxReader.read(reader);
					documents.put(fileName, doc);
				} catch (Exception e) {
					logger.error("SAXReader parse sqlMap xml error!");
					// TODO
				}
			}
		}
		return documents;
	}

	private void parseDocuments(Map<String, Document> documents) {
		try {
			Iterator<Map.Entry<String, Document>> it = documents.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Document> entry = it.next();
				logger.debug("Loadding sqlMap.xml :" + entry.getKey());
				parseDocument(entry.getKey(), entry.getValue().getRootElement());
			}
		} catch (Exception e) {
			// TODO
		}
	}

	@SuppressWarnings("unchecked")
	private void parseDocument(String fileName, Element rootElement) {
		if (rootElement != null) {
			String namespace = rootElement.attributeValue("namespace");
			String rootIsRead = rootElement.attributeValue("isRead");
			String rootDsName = rootElement.attributeValue("dsName");
			if (namespace == null || "".equals(namespace)) {
				logger.debug("SqlMap Element must have namespace : " + fileName);
				// TODO
			}
			String rootJdbcTimeout = rootElement.attributeValue("jdbcTimeout");
			List<Element> sqlElements = rootElement.elements();
			for (Element element : sqlElements) {
				String id = element.attributeValue("id");
				String isRead = element.attributeValue("isRead");
				String dsName = element.attributeValue("dsName");
				String jdbcTimeout = element.attributeValue("jdbcTimeout");
				if (isRead == null || "".equals(isRead)) {
					isRead = rootIsRead;
				}
				if (dsName == null || "".equals(dsName)) {
					dsName = rootDsName;
				}
				if (jdbcTimeout == null || "".equals(jdbcTimeout)) {
					jdbcTimeout = rootJdbcTimeout;
				}
				if (id == null || "".equals(id)) {
					logger.debug("Sql Element must have id : " + fileName);
					// TODO
				}
				String sql = element.getTextTrim();
				sqlMapResult.put(appendSqlId(namespace, id), new XmlBean(id,
						isRead, sql, dsName, jdbcTimeout));
			}
		}
	}

	private String appendSqlId(String namespace, String id) {
		return new StringBuffer().append(namespace).append(".").append(id)
				.toString();
	}
}
