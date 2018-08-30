package com.a5corp.weather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.a5corp.weather.R;

import java.util.ArrayList;

public class NewAboutActivity extends AppCompatActivity {

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

        /*
        cards.add(new AboutCard(this , R.layout.about_card_layout_1 , 1));
        cards.add(new AboutCard(this , R.layout.about_card_layout_2 , 2));
        cards.add(new AboutCard(this , R.layout.about_card_layout_3 , 3));
        cards.add(new AboutCard(this , R.layout.about_card_layout_4 , 4));
        cards.add(new AboutCard(this , R.layout.about_card_layout_5 , 5));
        */

        mRecyclerView = findViewById(R.id.about_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        /*CardView view = new CardView(this);
        getLayoutInflater().inflate(R.layout.about_card_layout_1 , view , false);
        mRecyclerView.addView(view);

        view = new CardView(this);
        getLayoutInflater().inflate(R.layout.about_card_layout_2 , view , false);
        mRecyclerView.addView(view);

        view = new CardView(this);
        getLayoutInflater().inflate(R.layout.about_card_layout_3 , view , false);
        mRecyclerView.addView(view);

        view = new CardView(this);
        getLayoutInflater().inflate(R.layout.about_card_layout_4 , view , false);
        mRecyclerView.addView(view);

        view = new CardView(this);
        getLayoutInflater().inflate(R.layout.about_card_layout_5 , view , false);
        mRecyclerView.addView(view);*/
        AboutAdapter adapter = new AboutAdapter(cards , this);
        mRecyclerView.setAdapter(adapter);
    }
}
