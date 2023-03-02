package com.company.project.utils;

import java.lang.reflect.Field;

/**
 * 对象工具类
 */
public class ObjectUtils {

    /**
     * 判断对象中属性值是否全为空
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }

        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) != null) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
