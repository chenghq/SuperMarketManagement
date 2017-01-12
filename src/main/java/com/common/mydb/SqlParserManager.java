package com.common.mydb;

import java.util.HashMap;
import java.util.Map;

public class SqlParserManager {
	private static Map<Class<? extends Object>, SqlCommands> commandsMap = new HashMap<Class<? extends Object>, SqlCommands>();

	public static SqlCommands getSqlParser(Class<? extends Object> clazz) {
		SqlCommands sqlParser = commandsMap.get(clazz);
		if (sqlParser == null) {
			sqlParser = new SqlCommands(clazz);
			synchronized (commandsMap) {
				if (commandsMap.get(clazz) == null) {
					commandsMap.put(clazz, sqlParser);
				}
			}
		}
		return sqlParser;
	}
}
