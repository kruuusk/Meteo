package com.example.meteo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.meteo.Citta;
import com.example.meteo.activity.DetailActivity;
import com.example.meteo.MySingleton;
import com.example.meteo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    ArrayList<Citta> citta;
    Context cx;

    public ListAdapter(Context cx, ArrayList<Citta> citta){
        this.cx = cx;
        this.citta = citta;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(cx);
        View view = layoutInflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.viewCitta.setText(citta.get(position).getName());
        String url = "https://samples.openweathermap.org/data/2.5/weather?id=" + citta.get(position).getIdCod() + "&appid=b6907d289e10d714a6e88b30761fae22";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String condizione = "";
                        try {
                            condizione += response.getJSONArray("weather").getJSONObject(0).get("description").toString();
                            if(condizione.contains("thunderstorm")){
                                holder.viewCondition.setImageResource(R.drawable.ico_temporale_1);
                            }

                            if(condizione.contains("drizzle")){
                                holder.viewCondition.setImageResource(R.drawable.ico_drizzle);
                            }

                            if(condizione.contains("rain")){
                                holder.viewCondition.setImageResource(R.drawable.ico_drizzle);
                            }

                            if(condizione.contains("freezing rain")){
                                holder.viewCondition.setImageResource(R.drawable.ico_neve_1);
                            }

                            if(condizione.contains("snow")){
                                holder.viewCondition.setImageResource(R.drawable.ico_neve_1);
                            }

                            if(condizione.contains("few clouds")){
                                holder.viewCondition.setImageResource(R.drawable.fewclouds);
                            }

                            if(condizione.contains("scattered clouds")){
                                holder.viewCondition.setImageResource(R.drawable.ico_nuvoloso_2);
                            }

                            if(condizione.contains("broken clouds")){
                                holder.viewCondition.setImageResource(R.drawable.brokenclouds);
                            }

                            if(condizione.contains("overcast clouds")){
                                holder.viewCondition.setImageResource(R.drawable.brokenclouds);
                            }

                            if(condizione.contains("clear sky")){
                                holder.viewCondition.setImageResource(R.drawable.icona_soleggiato1);
                            }

                            if(condizione.contains("mist")){
                                holder.viewCondition.setImageResource(R.drawable.ico_nebbia_1);
                            }

                            if(condizione.contains("fog")){
                                holder.viewCondition.setImageResource(R.drawable.ico_nebbia_1);
                            }

                            condizione = "Condizione: " + condizione;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        MySingleton.getInstance(cx).addToRequestQueue(jsonObjectRequest);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cx, DetailActivity.class);
                intent.putExtra("id",citta.get(position).getIdCod());
                cx.startActivity(intent);
            }
        });

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citta.remove(position);

                File file = new File(cx.getFilesDir(), "cittaPreferite.txt");
                FileOutputStream fos = null;
                ObjectOutputStream oos = null;
                try {
                    fos = new FileOutputStream(file);
                    oos = new ObjectOutputStream(fos);
                    oos.writeObject(citta);
                    oos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (citta == null)
            return 0;
        else
            return citta.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout mainLayout;
        TextView viewCitta;
        ImageView viewCondition;
        Button btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            viewCitta = itemView.findViewById(R.id.textViewCitta);
            viewCondition = itemView.findViewById(R.id.imageViewCondition);
            btn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
