package com.example.meteo;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class JsonReader {
    Context cx;
    public static JSONArray loadJSONFromAsset(Context cx, String nomeFile) {
        JSONArray jsonArray = null;
        String json = null;

        try {
            AssetManager assetManager = cx.getAssets();
            InputStream inputStream = assetManager.open(nomeFile);
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer,"UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            jsonArray = new JSONArray(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}
