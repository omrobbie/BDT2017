package com.omrobbie.bdt2017;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button login;
    SharedPreferences sharedPreferences;

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("lifecycle", "onCreate");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getBoolean("isLogged", false)) {
                    Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                sharedPreferences.edit().putBoolean("isLogged", true);
            }
        });

        // data untuk listview dan recyclerview
        final ArrayList<String> data = new ArrayList<>();

        for(int i = 0; i < 24; i++) {
            data.add("data ke " + Integer.toString(i));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);

        // menggunakan listview
        ListView lv_result = (ListView) findViewById(R.id.lv_result);
        lv_result.setAdapter(adapter);

        // menggunakan recyclerview
        RecyclerView rv_result = (RecyclerView) findViewById(R.id.rv_result);
        rv_result.setLayoutManager(new LinearLayoutManager(this));
        rv_result.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(
                        android.R.layout.simple_list_item_1,
                        parent, false
                );
                MyViewHolder vh = new MyViewHolder(v);
                return vh;
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                TextView tv = (TextView) holder.itemView;
                tv.setText(data.get(position));
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });
    }
}