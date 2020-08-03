package br.com.ficticius.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {

    private static Gson getGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        Gson gson = getGson();
        return gson.fromJson(json, classOfT);
    }

    public static String toJson(Object obj) {
        Gson gson = getGson();
        return gson.toJson(obj);
    }
}
