package com.witkey.witkeyhelp.util.retrofit;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author lingxu
 * @date 2019/7/12 10:21
 * @description
 */
public class StringConverter implements Converter<ResponseBody, String> {

    public static final StringConverter INSTANSE = new StringConverter();

    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
