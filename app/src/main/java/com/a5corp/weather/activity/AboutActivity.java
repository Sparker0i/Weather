package com.a5corp.weather.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AbsListView;

import com.a5corp.weather.R;
import com.a5corp.weather.cards.AboutCard;
import com.a5corp.weather.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.listener.SwipeOnScrollListener;

public class AboutActivity extends AppCompatActivity
{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.about_list) CardListView aboutList;
    CardArrayAdapter cardArrayAdapter;
    ArrayList<Card> cards;
    @BindView(R.id.fab) FloatingActionButton fab;
    @OnClick(R.id.fab) void onClick() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Constants.MAIL));
        startActivity(Intent.createChooser(intent , "Choose an app"));
    }
    private int previousVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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

        cards.add(new AboutCard(this , R.layout.about_card_layout_1 , 1));
        cards.add(new AboutCard(this , R.layout.about_card_layout_2 , 2));
        cards.add(new AboutCard(this , R.layout.about_card_layout_3 , 3));
        cards.add(new AboutCard(this , R.layout.about_card_layout_5 , 5));
        cards.add(new AboutCard(this , R.layout.about_card_layout_6 , 6));

        cardArrayAdapter = new CardArrayAdapter(this, cards);

        if(aboutList != null) {
            aboutList.setAdapter(cardArrayAdapter);
            aboutList.setOnScrollListener(new SwipeOnScrollListener()
            {
                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
                {
                    if (firstVisibleItem > previousVisibleItem)
                        fab.hide();
                    else if (firstVisibleItem < previousVisibleItem)
                        fab.show();

                    previousVisibleItem = firstVisibleItem;
                }
            });
        }
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