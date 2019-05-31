package com.example.acer.bakingrecipes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.acer.bakingrecipes.Model.Recipeitem;
import com.example.acer.bakingrecipes.Model.Step;
import com.example.acer.bakingrecipes.R;
import com.example.acer.bakingrecipes.UI.ItemListActivity;
import com.example.acer.bakingrecipes.UI.MainActivity;

import java.io.Serializable;
import java.util.List;

public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.ViewHolder> {
    private Context context;
    private List<Step> stepList;
    private List<Recipeitem> recipeitemList;

    public Item_Adapter(MainActivity mainActivity, List<Step> stepList, List<Recipeitem> recipeitemList) {
        this.context = mainActivity;
        this.recipeitemList = recipeitemList;
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.bakingitems, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.imageView.setImageResource(R.mipmap.baking1);
        viewHolder.textView.setText(recipeitemList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return recipeitemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemimage);
            textView = itemView.findViewById(R.id.itemname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, ItemListActivity.class);
                    intent.putExtra("recipename", (Serializable) recipeitemList.get(position).getName());
                    intent.putExtra("steps", (Serializable) recipeitemList.get(position).getSteps());
                    intent.putExtra("ingredients", (Serializable) recipeitemList.get(position).getIngredients());
                    intent.putExtra("servings", (Serializable) recipeitemList.get(position).getServings());
                    intent.putExtra("position", (Serializable) position);
                    intent.putExtra("itemname", (Serializable) recipeitemList.get(position).getName());
                    intent.putExtra("steplist", (Serializable) stepList);
                    context.startActivity(intent);

                }
            });
        }
    }
}
