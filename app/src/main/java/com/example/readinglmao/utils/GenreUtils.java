package com.example.readinglmao.utils;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.readinglmao.model.Genre;

public class GenreUtils {

    // Đọc file JSON từ assets
    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    // Lấy danh sách thể loại từ file JSON
    public static List<Genre> getGenres(Context context) {
        String json = loadJSONFromAsset(context, "genres.json");
        if (json == null) {
            return new ArrayList<>();
        }
        return new Gson().fromJson(json, new TypeToken<List<Genre>>() {}.getType());
    }
}

