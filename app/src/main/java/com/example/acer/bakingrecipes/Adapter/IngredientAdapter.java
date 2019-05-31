package com.example.acer.bakingrecipes.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.bakingrecipes.Model.Ingredient;
import com.example.acer.bakingrecipes.R;
import com.example.acer.bakingrecipes.UI.Ingredientdetails;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private Context context;
    private List<Ingredient> ingredientList;

    public IngredientAdapter(Ingredientdetails ingredientdetails, List<Ingredient> ingredientList) {
        this.context = ingredientdetails;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredientcard, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.name.setText(ingredientList.get(position).getIngredient());
        viewHolder.quantity.setText(ingredientList.get(position).getQuantity());
        viewHolder.measure.setText(ingredientList.get(position).getMeasure());

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, measure;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ingredientname);
            quantity = itemView.findViewById(R.id.quantity);
            measure = itemView.findViewById(R.id.measure);
        }
    }
}
