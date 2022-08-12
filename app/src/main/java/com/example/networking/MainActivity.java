package com.example.networking;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {

    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=brom";

    RecyclerView RecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Mountain> mountains = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(mountains);

        RecyclerView = findViewById(R.id.RecyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(recyclerViewAdapter);

        new JsonTask(this).execute(JSON_URL);
    }

    @Override
    public void onPostExecute(String json) {
        Log.d("TEST", "onPostExecute: " + json);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Mountain>>(){}.getType();
        ArrayList<Mountain> tmpList = gson.fromJson(json, type);
        Log.d("TEST", "onPostExecute: " + tmpList.toString());
        recyclerViewAdapter.setMountains(tmpList);
        recyclerViewAdapter.notifyDataSetChanged();
        Log.d("MainActivity", json);
    }
}