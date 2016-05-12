package com.sms.sendsms.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Navruz on 06.04.2016.
 */
public class ToStringConverterFactory extends Converter.Factory {
    @Override
    public Converter<Object, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<Object, String> delegate = retrofit.stringConverter(type, annotations);
        return new Converter<Object, String>() {
            @Override
            public String convert(Object value) throws IOException {
                return delegate.convert(value);
            }
        };
    }
}