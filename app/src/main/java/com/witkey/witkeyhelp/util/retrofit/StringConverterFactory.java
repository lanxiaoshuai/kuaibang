package com.witkey.witkeyhelp.util.retrofit;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author lingxu
 * @date 2019/7/12 10:20
 * @description retrofit--ResponseBody 到 String 的转换
 */

public class StringConverterFactory extends Converter.Factory {

    public static final StringConverterFactory INSTANSE = new StringConverterFactory();

    public static StringConverterFactory create() {
        return INSTANSE;
    }

    // 我们只关实现从ResponseBody 到 String 的转换，所以其它方法可不覆盖
    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return StringConverter.INSTANSE;
        }
        //其它类型我们不处理，返回null就行
        return null;
    }
}
