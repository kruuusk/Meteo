package com.example.meteo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.meteo.Citta;
import com.example.meteo.JsonReader;
import com.example.meteo.adapter.ListAdapter;
import com.example.meteo.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rView;
    Button addBtn;
    JSONArray jsonArray;
    ArrayList<Citta> citta = new ArrayList<Citta>();
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File(getFilesDir(), "cittaPreferite.txt");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            citta = (ArrayList<Citta>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        rView = findViewById(R.id.rView);
        addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] arrayIdPresenti = new String[citta.size()];

                for(int i=0;i<arrayIdPresenti.length;i++){
                    arrayIdPresenti[i] = citta.get(i).getIdCod();
                }
                jsonArray = JsonReader.loadJSONFromAsset(getApplicationContext(), "cityRidotta.json");

                Intent intent = new Intent(getApplicationContext(),RicercaActivity.class);
                intent.putExtra("json_citta", jsonArray.toString());
                intent.putExtra("array_id_presenti", arrayIdPresenti);
                startActivityForResult(intent,0);
            }
        });
        adapter = new ListAdapter(this, citta);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK){
                Citta nuovaCitta = null;

                try {
                    nuovaCitta = new Citta(data.getStringExtra("id"), getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                citta.add(nuovaCitta);

                File file = new File(getFilesDir(),"cittaPreferite.txt");
                FileOutputStream fileOutputStream = null;
                ObjectOutputStream objectOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file);
                    objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(citta);
                    objectOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
            }
        }
    }
}
