package com.example.meteo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meteo.Citta;
import com.example.meteo.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.prefs.PreferenceChangeListener;

public class RicercaAdapter extends RecyclerView.Adapter<RicercaAdapter.MyViewHolder>{
    String[] id;
    Context cx;

    public RicercaAdapter(Context cx, String[] id){
        this.cx = cx;
        this.id = id;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(cx);
        View view = layoutInflater.inflate(R.layout.my_row_ricerca, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Citta citta = null;
        try {
            citta = new Citta(id[position], cx);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.viewName.setText(citta.getName());
        holder.viewCountry.setText(citta.getCountry());

        final Activity activity = (Activity) cx;

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("id", id[position]);
                activity.setResult(Activity.RESULT_OK, resultIntent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
            return id.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout mainLayout;
        TextView viewName, viewCountry;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            viewName = itemView.findViewById(R.id.viewName);
            viewCountry = itemView.findViewById(R.id.viewCountry);
        }
    }
}
