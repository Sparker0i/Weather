package com.a5corp.weather.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a5corp.weather.BuildConfig;
import com.a5corp.weather.R;
import com.a5corp.weather.utils.CardInfo;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
import it.gmariotti.cardslib.library.view.CardViewNative;
import it.gmariotti.cardslib.library.view.base.CardViewWrapper;
import it.gmariotti.cardslib.library.view.component.CardThumbnailView;

/**
 * A placeholder fragment containing a simple view.
 */
public class AboutFragment extends Fragment {

    CardViewNative[] cvn;
    View rootView;
    Card card;
    String[] name = {"Open Weather Map" , "Weather Icons" , "Material Drawer" , "Material Dialogs" , "SystemBarTint" , "AndroidIconics" , "MPAndroidChart" , "Material Icons" , "Bottom Bar" , "CardsLib"};
    String[] creator = {"" , "Erik Flowers" , "Mike Penz" , "Aidan Follestad" , "Jeff Gilfelt" , "Mike Penz" , "Philipp Jahoda" , "Google" , "Iiro Krankka" , "Gabriele Mariotti"};
    String[] description = {"All the Weather Data You See in this app is due to the data being provided by Open Weather Map. You must be thanking them first" ,
        "Weather Icons is the only icon font and CSS with 222 weather themed icons, ready to be dropped right into Bootstrap, or any project that needs high quality weather, maritime, and meteorological based icons!" ,
            "The flexible easy to use, all in one drawer library for your project.",
            "Beautiful Dialog Boxes that conform to the Material Design Guidelines and available for free",
            "Apply background tinting to the Android system UI when using KitKat translucent modes",
            "This library allows you to include vector icons everywhere in your project. No limits are given. With no limit, use any Color at any time, provide a contour and many additional customizations",
            "MPAndroidChart is a powerful & easy to use chart library for Android. It runs on API level 8 and upwards.",
            "Material design icons are the official icon set from Google that are designed under the material design guidelines.",
            "An easy to use library for implementing a Bottom Bar, just like the one you see in Google Photos app",
            "Card Library provides an easy way to display a UI Card using the Official Google CardView in your Android app."
    };

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_about, container, false);
        TextView madeBy = (TextView) rootView.findViewById(R.id.made_by);
        TextView sourceAt = (TextView) rootView.findViewById(R.id.source_at);
        TextView appIcon = (TextView) rootView.findViewById(R.id.app_icon);
        TextView verText = (TextView) rootView.findViewById(R.id.ver_text);
        cvn = new CardViewNative[11];
        sourceAt.setMovementMethod(LinkMovementMethod.getInstance());           //To make the link clickable
        madeBy.setMovementMethod(LinkMovementMethod.getInstance());

        Typeface weatherFont;
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        appIcon.setTypeface(weatherFont);
        appIcon.setText(getString(R.string.app_icon));
        String verId = "Version " + BuildConfig.VERSION_NAME;
        verText.setText(verId);
        constructCards();
        return rootView;
    }

    public void constructCards() {
        for (int i = 0; i < 10; ++i) {
            String f = "card" + (i + 1);
                int resID = getResources().getIdentifier(f, "id", getContext().getPackageName());
                cvn[i] = (CardViewNative) rootView.findViewById(resID);
                cvn[i].setBackgroundColor(Color.WHITE);
                String libraryName = name[i];
                TextView libName = (TextView) cvn[i].findViewById(R.id.libraryName);
                libName.setTextColor(Color.BLACK);
                libName.setText(libraryName);
                String libCreator = creator[i];
                TextView libCre = (TextView) cvn[i].findViewById(R.id.libraryCreator);
                libCre.setText(libCreator);
                String libDescription = description[i];
                TextView libDesc = (TextView) cvn[i].findViewById(R.id.libraryDescription);
                libDesc.setText(libDescription);
        }
    }
}
