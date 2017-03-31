package com.a5corp.weather.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;

import com.a5corp.weather.R;
import com.a5corp.weather.cards.AboutCard;
import com.github.clans.fab.FloatingActionButton;
import com.mikepenz.materialdrawer.DrawerBuilder;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.listener.SwipeOnScrollListener;

public class AboutActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private CardListView aboutList;
    private CardArrayAdapter cardArrayAdapter;
    private ArrayList<Card> cards;
    private FloatingActionButton fab;
    private int previousVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CoordinatorLayout root = (CoordinatorLayout) findViewById(R.id.root);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setColorNormal(getResources().getColor(R.color.accent));
        fab.hide(false);
        initCards();
        initFab();
    }

    public void initCards()
    {
        cards = new ArrayList<>();

        cards.add(new AboutCard(this,R.layout.about_card_layout_1,1));
        cards.add(new AboutCard(this,R.layout.about_card_layout_2,2));
        cards.add(new AboutCard(this,R.layout.about_card_layout_3,3));
        cards.add(new AboutCard(this,R.layout.about_card_layout_5,5));

        cardArrayAdapter = new CardArrayAdapter(this, cards);
        aboutList = (CardListView) findViewById(R.id.about_list);

        if(aboutList != null)
            aboutList.setAdapter(cardArrayAdapter);
    }

    public void initFab()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab.show(true);
            }
        }, 500);

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.app_link_direct)));
                startActivity(intent);
            }
        });

        aboutList.setOnScrollListener(new SwipeOnScrollListener()
        {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (firstVisibleItem > previousVisibleItem)
                    fab.hide(true);
                else if (firstVisibleItem < previousVisibleItem)
                    fab.show(true);

                previousVisibleItem = firstVisibleItem;
            }
        });
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