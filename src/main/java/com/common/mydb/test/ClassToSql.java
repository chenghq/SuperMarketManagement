package com.common.mydb.test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.common.mydb.Column;
import com.common.mydb.Entity;
import org.apache.commons.lang3.StringUtils;

/**
 * *
 * ���ڲ��Ե���
 *
 * @author 13072937
 */
public class ClassToSql {

    /**
     * ��classת����String sql����
     *
     * @param obj
     * @throws Exception
     */
    public static void columnToSql(String xmlSql, Object obj) {

        /**
         * ��ȡ���ݿ��ı��� ��@Entity��������Ϣ
         */
        Entity entity = obj.getClass().getAnnotation(Entity.class);
        String table_name = entity.name();

        if (StringUtils.isBlank(table_name)) {
            // TODO
            System.out.println("The table is null, please config the table name...");
        }

        System.out.println(table_name);

        /**
         * ��ȡ�ñ������е���
         */
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (null == column || StringUtils.isBlank(column.name())) {
                System.out.println("The column is not config.");
            } else {
                try {
                    System.out.println("column: " + column.name());
                    Object value = getFieldValueByGetMethod(obj, field.getName());
                    if (value != null) {
                        xmlSql = xmlSql.replace(":" + column.name(), value.toString());
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        System.out.println(xmlSql);
    }

    /**
     * ���ܣ�ͨ������;�����ֶ����ֻ���ֶε�ֵ ##### ���Ȼ�ȡ�ֶε�get������
     */
    private static Object getFieldValueByGetMethod(Object obj, String fieldName)
            throws Exception {
        Object o = null;
        if (StringUtils.isNotBlank(fieldName) && null != obj) { // �ֶ�Ϊ��ֱ�ӷ��ؿ�
            Class<?> clazz = obj.getClass();
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
            Method getMethod = pd.getReadMethod();// ���get����

            if (pd != null) {
                o = getMethod.invoke(obj);// ִ��get��������һ��Object
            }
        }
        return o;
    }

}
