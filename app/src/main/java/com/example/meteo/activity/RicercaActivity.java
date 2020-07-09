package com.example.meteo.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.meteo.R;
import com.example.meteo.adapter.RicercaAdapter;

import org.json.JSONArray;
import org.json.JSONException;

public class RicercaActivity extends AppCompatActivity {
    RecyclerView rViewRicerca;
    JSONArray arrayRicerca;
    String[] arrayId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca);

        rViewRicerca = findViewById(R.id.viewRicerca);

        Intent intent = getIntent();

        try {
            arrayRicerca = new JSONArray(intent.getStringExtra("json_citta"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] arrayIdPresenti = intent.getStringArrayExtra("array_id_presenti");

        for(int i=0;i<arrayIdPresenti.length;i++){
            for(int j=0;j<arrayRicerca.length();j++){
                try {
                    if(arrayRicerca.getJSONObject(j).getString("id").equals(arrayIdPresenti[i])){
                        arrayRicerca.remove(j);
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        arrayId = new String[arrayRicerca.length()];

        for(int k=0;k<arrayId.length;k++){
            try {
                arrayId[k] =arrayRicerca.getJSONObject(k).getString("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        RicercaAdapter ricercaAdapter = new RicercaAdapter(this,arrayId);
        rViewRicerca.setAdapter(ricercaAdapter);
        rViewRicerca.setLayoutManager(new LinearLayoutManager(this));
    }
}
