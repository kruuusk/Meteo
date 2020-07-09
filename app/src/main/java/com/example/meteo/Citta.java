package com.example.meteo;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;

public class Citta implements Serializable {
    private String idCod;
    private String name;
    private String country;

    public Citta(String idCod, Context cx) throws JSONException {
        this.idCod = idCod;
        JSONArray jsonArray = JsonReader.loadJSONFromAsset(cx, "cityRidotta.json");

        for(int i=0;i<jsonArray.length();i++){
            if(this.idCod.equals(jsonArray.getJSONObject(i).getString("id"))){
                this.name = jsonArray.getJSONObject(i).getString("name");
                this.country = jsonArray.getJSONObject(i).getString("country");
                break;
            }
        }
    }

    public String getIdCod() {
        return idCod;
    }

    public void setIdCod(String idCod) {
        this.idCod = idCod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
