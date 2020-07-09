package com.example.meteo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.meteo.Citta;
import com.example.meteo.MySingleton;
import com.example.meteo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {
    TextView viewCitta, viewTemp, viewCond, viewUmid, viewPress, viewVento;
    Citta citta;
    Intent intent;
    ConstraintLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        intent = getIntent();
        citta = null;
        try {
            citta = new Citta(intent.getStringExtra("id"), getApplicationContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        viewCitta = findViewById(R.id.viewCitta);
        viewTemp = findViewById(R.id.viewTemp);
        viewCond = findViewById(R.id.viewCond);
        viewUmid = findViewById(R.id.viewUmid);
        viewPress = findViewById(R.id.viewPress);
        viewVento = findViewById(R.id.viewVento);

        viewCitta.setVisibility(View.GONE);
        viewTemp.setVisibility(View.GONE);
        viewCond.setVisibility(View.GONE);
        viewUmid.setVisibility(View.GONE);
        viewPress.setVisibility(View.GONE);
        viewVento.setVisibility(View.GONE);

        viewCitta.setText(citta.getName());

        try {
            richiestaMeteo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void richiestaMeteo() throws JSONException {

        String url = "https://samples.openweathermap.org/data/2.5/weather?id=" + intent.getStringExtra("id") + "&appid=b6907d289e10d714a6e88b30761fae22";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String condizione = "";
                        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
                        try {
                            condizione += response.getJSONArray("weather").getJSONObject(0).get("description").toString();
                            switch(condizione) {
                                case "broken clouds":
                                    condizione = "Parzialmente nuvoloso";
                                    break;
                                case "scattered clouds":
                                    condizione = "Nuvole sparse";
                                    break;
                                case "light rain":
                                    condizione = "Pioggia leggera";
                                    break;
                                case "moderate rain":
                                    condizione = "Pioggia moderata";
                                    break;
                                case "mist":
                                    condizione = "Nebbia";
                                    break;
                                case "few clouds":
                                    condizione = "Scarsamente nuvoloso";
                                    break;
                                case "overcast clouds":
                                    condizione = "Nuvoloso";
                                    break;
                                case "sunny":
                                    condizione = "Soleggiato";
                                    break;
                                case "heavy rain":
                                    condizione = "Pioggia Intensa";
                                case "heavy intensity rain":
                                    condizione = "Pioggia Intensa";
                                    break;
                                case "clear sky":
                                    condizione = "Sereno";
                                    break;
                                default:
                                    break;
                            }
                            condizione = "Condizione: " + condizione;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        double temperatura = 0;
                        try {
                            temperatura = response.getJSONObject("main").getDouble("temp");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DecimalFormat formato = new DecimalFormat("0.0");
                        temperatura = temperatura - 273.15;
                        String temperatura_citta = "Temperatura: ";
                        temperatura_citta += formato.format(temperatura) + " °C";

                        String umidita = "Percentuale umidita: ";
                        try {
                            umidita += response.getJSONObject("main").get("humidity").toString() + "%";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String pressione = "Pressione: ";
                        try {
                            pressione += response.getJSONObject("main").get("pressure").toString() + " hPa";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String ventoS = "Velocita del vento: ";
                        try {
                            ventoS += response.getJSONObject("wind").get("speed").toString() + " m/s";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        viewTemp.setText(temperatura_citta);
                        viewCond.setText(condizione);
                        viewUmid.setText(umidita);
                        viewPress.setText(pressione);
                        viewVento.setText(ventoS);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

        this.viewCitta.setVisibility(View.VISIBLE);
        viewTemp.setVisibility(View.VISIBLE);
        viewCond.setVisibility(View.VISIBLE);
        viewUmid.setVisibility(View.VISIBLE);
        viewPress.setVisibility(View.VISIBLE);
        viewVento.setVisibility(View.VISIBLE);
    }
}

