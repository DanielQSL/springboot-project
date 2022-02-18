package com.company.project.utils;

import com.company.project.pojo.EnumVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 枚举工具类
 *
 * @author qianshuailong
 * @date 2019/10/30
 */
@Slf4j
public class EnumUtil {

    private static final int ONE = 1;

    private static final int TWO = 2;

    private EnumUtil() {
    }

    /***
     *  取枚举的key 和 value 返回list
     * @param enumt 枚举类
     * @param methodNames 取枚举类的方法
     * @param <T> 泛型T
     * @return 结果对象
     */
    public static <T> List<EnumVO> getEnumToList(Class<T> enumt, String... methodNames) {
        List<EnumVO> enumList = new ArrayList<>();
        if (!enumt.isEnum()) {
            return enumList;
        }
        //获取枚举类里所有的枚举
        T[] enums = enumt.getEnumConstants();
        if (enums.length == 0) {
            //如果没有枚举键和值结束
            return enumList;
        }
        int count = methodNames.length;
        //默认的取 key的方法
        String keyMethod = "getValue";
        //默认的取 value 的方法
        String valueMethod = "getDesc";
        if (count >= ONE && !methodNames[0].equals(StringUtils.EMPTY)) {
            //如果方法的长度是大于等于1的,并且不为空
            keyMethod = methodNames[0];
        }
        if (count == TWO && !methodNames[1].equals(StringUtils.EMPTY)) {
            //如果方法的长度是等于2的,并且不为空
            valueMethod = methodNames[1];
        }
        try {
            for (T anEnum : enums) {
                EnumVO enumVo = new EnumVO();
                //得到枚举类里每条值
                //获取key值
                Object resultKey = getMethodValue(keyMethod, anEnum);
                if (resultKey.equals(StringUtils.EMPTY)) {
                    continue;
                }
                //获取value值
                Object resultValue = getMethodValue(valueMethod, anEnum);
                if (resultValue.equals(StringUtils.EMPTY)) {
                    resultValue = anEnum;
                }
                enumVo.setValue(resultKey.toString());
                enumVo.setLabel(resultValue.toString());
                enumList.add(enumVo);
            }
        } catch (Exception e) {
            log.error("枚举类转成List异常", e);
        }
        return enumList;
    }

    private static <T> Object getMethodValue(String methodName, T obj, Object... args) {
        Object result = "";
        try {
            //获取方法数组，这里只要公有的方法
            Method[] methods = obj.getClass().getMethods();
            if (methods.length <= 0) {
                return result;
            }
            Method method = null;
            for (Method value : methods) {
                //忽略大小写取方法
                if (value.getName().equalsIgnoreCase(methodName)) {
                    //如果存在，则取出正确的方法名称
                    method = value;
                    break;
                }
            }
            if (method == null) {
                return result;
            }
            result = method.invoke(obj, args);
            if (result == null) {
                result = "";
            }
            return result;
        } catch (Exception e) {
            log.error("EnumUtil.getMethodValue error", e);
        }
        return result;
    }

}
