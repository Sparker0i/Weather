package com.a5corp.weather.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a5corp.weather.BuildConfig;
import com.a5corp.weather.R;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * A placeholder fragment containing a simple view.
 */
public class AboutFragment extends Fragment {

    CardViewNative[] cvn;
    View rootView;
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
    String[] version = {
            "2.5",
            "2.0",
            "5.8.1",
            "0.9.2.3",
            "1.0.3",
            "2.8.2",
            "3.0.1",
            "2.2.0",
            "2.1.1",
            "2.1.0"
    };
    String[] license = {
            "CC BY SA-3.0",
            "CC BY SA-3.0",
            "Apache-2.0",
            "MIT",
            "Apache-2.0",
            "Apache-2.0",
            "",
            "Apache-2.0",
            "Apache-2.0",
            ""
    };
    String[] url = {
            "http://openweathermap.org/",
            "https://erikflowers.github.io/weather-icons/",
            "http://mikepenz.github.io/MaterialDrawer/",
            "https://github.com/afollestad/material-dialogs",
            "https://github.com/jgilfelt/SystemBarTint",
            "http://mikepenz.github.io/Android-Iconics",
            "https://github.com/PhilJay/MPAndroidChart/",
            "http://material.io/icons/",
            "https://github.com/roughike/BottomBar",
            "https://github.com/gabrielemariotti/cardslib"
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
                cvn[i].setRadius(20);
                Log.i("Radius" , Float.toString(cvn[i].getRadius()));
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
                String libVersion = version[i];
                TextView libVer = (TextView) cvn[i].findViewById(R.id.libraryVersion);
                libVer.setText(libVersion);
                String libLicense = license[i];
                TextView libLic = (TextView) cvn[i].findViewById(R.id.libraryLicense);
                libLic.setText(libLicense);
                final int j = i;
                cvn[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = url[j];
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                    }
                });
            cvn[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String uri = url[j];
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                    Vibrator vz = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vz.vibrate(25);
                    return false;
                }
            });
        }
    }
}
