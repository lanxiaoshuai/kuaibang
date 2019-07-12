package com.witkey.witkeyhelp.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.SerializedName;

/**
 * @author lingxu
 * @date 2019/7/12 10:16
 * @description gson排除策略
 * 解决java对象里属性名跟json里字段名不匹配的情况
 */
public class Exclude implements ExclusionStrategy {

    @Override
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        SerializedName ns = field.getAnnotation(SerializedName.class);
        if(ns != null)
            return false;
        return true;
    }
}