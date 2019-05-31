package com.example.acer.bakingrecipes.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acer.bakingrecipes.Adapter.Item_Adapter;
import com.example.acer.bakingrecipes.Model.Ingredient;
import com.example.acer.bakingrecipes.Model.Recipeitem;
import com.example.acer.bakingrecipes.Model.Step;
import com.example.acer.bakingrecipes.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    private List<Recipeitem> recipeitemList;

    private String bakingurl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        requestQueue = Volley.newRequestQueue(this);
        Bakingdata();
        stepList = new ArrayList<>();
        ingredientList = new ArrayList<>();
        recipeitemList = new ArrayList<>();

        networkConnected();
    }

    private boolean networkConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();

        } catch (Exception ignored) {

        }
        return connected;
    }

    private void Bakingdata() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, bakingurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (networkConnected()) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    recipeitemList = Arrays.asList(gson.fromJson(response, Recipeitem[].class));

                    Item_Adapter item_adapter = new Item_Adapter(MainActivity.this, stepList, recipeitemList);
                    recyclerView.setAdapter(item_adapter);
                    if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    }
                    recyclerView.setHasFixedSize(true);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (!networkConnected()) {

                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    builder.setTitle(" Network Connection").setMessage("Please Check Internet Connection")
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            });
                    builder.show();

                }

            }
        });
        requestQueue.add(stringRequest);
    }
}
