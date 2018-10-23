package com.a5corp.weather.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.a5corp.weather.R;
import com.a5corp.weather.cards.AboutAdapter;
import com.a5corp.weather.model.AboutModel;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<AboutModel> cards;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initCards();
    }

    public void initCards()
    {
        cards = new ArrayList<>();

        cards.add(new AboutModel(0));
        cards.add(new AboutModel(1));
        cards.add(new AboutModel(2));
        cards.add(new AboutModel(3));
        cards.add(new AboutModel(4));

        mRecyclerView = findViewById(R.id.about_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AboutAdapter adapter = new AboutAdapter(cards);
        mRecyclerView.setAdapter(adapter);
    }
}
