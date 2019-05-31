package com.example.acer.bakingrecipes.Detail;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.acer.bakingrecipes.Model.Step;
import com.example.acer.bakingrecipes.R;
import com.example.acer.bakingrecipes.UI.ItemListActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {
    private String videolink;
    private String fullDesc;
    private String thumburl;
    private List<Step> stepArrayList;
    private int position;
    private Button back;
    private Button forth;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        stepArrayList = new ArrayList<>();
        back = findViewById(R.id.previous);
        forth = findViewById(R.id.next);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        stepArrayList = (List<Step>) getIntent().getSerializableExtra("steplist");

        position = getIntent().getIntExtra("position", 0);
        if (position == 0)
            back.setEnabled(false);
        if (position == stepArrayList.size() - 1)
            forth.setEnabled(false);

        if (savedInstanceState == null) {
            videolink = getIntent().getStringExtra("link");
            fullDesc = getIntent().getStringExtra("fullDesc");
            thumburl = getIntent().getStringExtra("thumburl");

            Bundle arguments = new Bundle();
            arguments.putString("link", videolink);
            arguments.putString("fullDesc", fullDesc);
            arguments.putString("thumburl", thumburl);

            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("link", videolink);
        outState.putString("fullDesc", fullDesc);
        outState.putString("thumburl", thumburl);
    }

    public void previous(View view) {
        position--;
        forth.setEnabled(true);

        if (position == 0 || position < 0) {
            back.setEnabled(false);
            Toast.makeText(this, "no more previous", Toast.LENGTH_SHORT).show();
        }


        videolink = stepArrayList.get(position).getVideoURL();
        fullDesc = stepArrayList.get(position).getDescription();
        thumburl = stepArrayList.get(position).getThumbnailURL();

        Bundle bundle = new Bundle();
        bundle.putString("link", videolink);
        bundle.putString("fullDesc", fullDesc);
        bundle.putString("thumburl", thumburl);

        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();


    }


    public void forth(View view) {
        back.setEnabled(true);
        position++;

        if (position == stepArrayList.size() - 1) {

            forth.setEnabled(false);
            Toast.makeText(this, "Yayy! cooking is done", Toast.LENGTH_SHORT).show();


        } else {
            forth.setEnabled(true);
        }
        videolink = stepArrayList.get(position).getVideoURL();

        thumburl = stepArrayList.get(position).getThumbnailURL();
        fullDesc = stepArrayList.get(position).getDescription();


        Bundle bundle = new Bundle();
        bundle.putString("link", videolink);
        bundle.putString("fullDesc", fullDesc);
        bundle.putString("thumburl", thumburl);

        ItemDetailFragment fragment = new ItemDetailFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();


    }

}

