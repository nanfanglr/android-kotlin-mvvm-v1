package com.rui.toolkit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * json的解析,用Gson,fastJson
 * <p>
 * <p>
 * Created by linet on 16/1/26.
 */
public class JsonUtils {


    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
        builder.registerTypeAdapter(Long.class, new LongTypeAdapter());
        Gson gson = builder.create();

        return gson;
    }


    /**
     * 把对象转成json字符串
     *
     * @param t
     * @return
     */
    public static <T> String parserObjectToGson(T t) {
        return (new Gson()).toJson(t);
    }

    /**
     * 根据指定key提取json字符串
     *
     * @param json
     * @param key
     * @return String or "";
     */
    public static String getGsonValueByKey(String json, String key) {
        String value = "";
        try {
            value = (new JSONObject(json)).getString(key);
        } catch (Exception e) {
            return "";
        }
        return value;
    }

    /**
     * 解析字符串,返回对象
     *
     * @param <T>
     * @param json
     * @param classOft
     * @return Object
     */
    public static <T> T parserGsonToObject(String json, Class<T> classOft) {
        return getGson().fromJson(json, classOft);
    }

    /**
     * 解析字符串,返回list对象
     *
     * @param json
     * @param typeOfT
     * @return List<T> or null;
     */
    public static <T> ArrayList<T> parserGsonToArray(String json, Type typeOfT) {
        return getGson().fromJson(json, /*
                                             * new TypeToken<List<T>>() {
											 * }.getType()
											 */typeOfT);
    }



    public static class DoubleTypeAdapter extends TypeAdapter<Double> {

        @Override
        public void write(JsonWriter out, Double value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(value);
        }

        @Override
        public Double read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return Double.valueOf(0);
            }
            String stringValue = in.nextString();
            if (stringValue != null && stringValue.length() > 0) {
                try {
                    Double value = Double.valueOf(stringValue);
                    return value;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            return Double.valueOf(0);
        }
    }

    public static class LongTypeAdapter extends TypeAdapter<Long> {

        @Override
        public void write(JsonWriter out, Long value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(value);
        }

        @Override
        public Long read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return Long.valueOf(0);
            }
            String stringValue = in.nextString();
            if (stringValue != null && stringValue.length() > 0) {
                try {
                    Long value = Long.valueOf(stringValue);
                    return value;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            return Long.valueOf(0);
        }

    }

}
