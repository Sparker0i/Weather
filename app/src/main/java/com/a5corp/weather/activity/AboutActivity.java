package com.a5corp.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.a5corp.weather.R;
import com.a5corp.weather.app.MyContextWrapper;
import com.a5corp.weather.cards.MyAdapter;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity
{
    @BindView(R.id.toolbar) Toolbar toolbar;
    ArrayList<CardView> cards;
    LinearLayoutManager mLayoutManager;
    @BindView(R.id.fab) FloatingActionButton fab;
    RecyclerView mRecyclerView;
    @OnClick(R.id.fab) void onClick() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Constants.MAIL));
        startActivity(Intent.createChooser(intent , "Choose an app"));
    }
    private int previousVisibleItem;

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = MyContextWrapper.wrap(newBase, new Prefs(newBase).getLanguage());
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
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

        int[] dataset = {R.layout.about_card_layout_1 , R.layout.about_card_layout_2 , R.layout.about_card_layout_3 , R.layout.about_card_layout_4 , R.layout.about_card_layout_5};
        MyAdapter cardArrayAdapter = new MyAdapter(dataset);

        mRecyclerView.setAdapter(cardArrayAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}