package com.example.acer.bakingrecipes.UI;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.bakingrecipes.Widget.IngWidget;
import com.example.acer.bakingrecipes.Adapter.IngredientAdapter;
import com.example.acer.bakingrecipes.Model.Ingredient;
import com.example.acer.bakingrecipes.R;


import java.util.ArrayList;
import java.util.List;

public class Ingredientdetails extends AppCompatActivity {
    private List<Ingredient> ingredientList;
    private TextView name;
    private TextView quantity;
    private TextView measure;
    private RecyclerView rec;
    private static String itemname;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientdetails);
        ingredientList = new ArrayList<>();
        name = findViewById(R.id.ingredientname);
        quantity = findViewById(R.id.quantity);
        measure = findViewById(R.id.measure);
        rec = findViewById(R.id.rec);
        ingredientList = (List<Ingredient>) getIntent().getSerializableExtra("ingredientslist");
        String position = getIntent().getStringExtra("position");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemname = getIntent().getStringExtra("itemname");


        IngredientAdapter ingredientAdapter = new IngredientAdapter(this, ingredientList);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setAdapter(ingredientAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ingredients) {

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < ingredientList.size(); i++) {
                String quantity = ingredientList.get(i).getQuantity();
                String measure = ingredientList.get(i).getMeasure();
                String ingredient = ingredientList.get(i).getIngredient();

                stringBuilder.append(ingredient+"\t" + quantity + measure+"\n");
            }
            SharedPreferences sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ingredientlist", stringBuilder.toString());
            editor.putString("itemname", itemname);


            editor.apply();


            Intent intent1 = new Intent(this, IngWidget.class);
            intent1.setAction("android.appwidget.action.APPWIDGET_UPDATE");
            int id[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), IngWidget.class));
            intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, id);
            getApplicationContext().sendBroadcast(intent1);

            Toast.makeText(this, "Added to Widget", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
