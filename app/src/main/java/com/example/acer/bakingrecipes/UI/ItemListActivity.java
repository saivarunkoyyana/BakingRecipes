package com.example.acer.bakingrecipes.UI;


import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.bakingrecipes.Detail.ItemDetailActivity;
import com.example.acer.bakingrecipes.Detail.ItemDetailFragment;
import com.example.acer.bakingrecipes.Model.Ingredient;
import com.example.acer.bakingrecipes.Model.Recipeitem;
import com.example.acer.bakingrecipes.Model.Step;
import com.example.acer.bakingrecipes.R;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {
    private boolean mTwoPane;
    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    List<Recipeitem> recipeList;
    private String position;
    private String itemname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ingredientList = new ArrayList<>();
        stepList = new ArrayList<>();
        stepList = (List<Step>) getIntent().getSerializableExtra("steps");
        ingredientList = (List<Ingredient>) getIntent().getSerializableExtra("ingredients");
        position = getIntent().getStringExtra("position");
        itemname = getIntent().getStringExtra("itemname");


        if (findViewById(R.id.item_detail_container) != null) {

            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, stepList, mTwoPane));
    }

    public void getingredients(View view) {
        Intent intent = new Intent(ItemListActivity.this, Ingredientdetails.class);
        intent.putExtra("ingredientslist", (Serializable) ingredientList);
        intent.putExtra("position", position);
        intent.putExtra("itemname", itemname);


        startActivity(intent);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<Step> mValues;
        private final boolean mTwoPane;

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<Step> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getId().toString());
            holder.mContentView.setText(mValues.get(position).getShortDescription());


        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.content);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getAdapterPosition();
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString("link", mValues.get(pos).getVideoURL());
                            arguments.putString("thumburl", mValues.get(pos).getThumbnailURL());
                            arguments.putString("fullDesc", mValues.get(pos).getDescription());
                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(arguments);
                            mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment)
                                    .commit();
                        } else {

                            Intent intent = new Intent(mParentActivity, ItemDetailActivity.class);
                            intent.putExtra("link", mValues.get(pos).getVideoURL());
                            intent.putExtra("fullDesc", mValues.get(pos).getDescription());
                            intent.putExtra("position", pos);
                            intent.putExtra("steplist", (Serializable) mValues);
                            intent.putExtra("thumburl", mValues.get(pos).getThumbnailURL());


                            mParentActivity.startActivity(intent);
                        }
                    }
                });
            }
        }
    }
}
