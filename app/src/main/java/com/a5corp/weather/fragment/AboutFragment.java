package com.a5corp.weather.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a5corp.weather.BuildConfig;
import com.a5corp.weather.R;

import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * A placeholder fragment containing a simple view.
 */
public class AboutFragment extends Fragment {

    CardViewNative[] cvn;
    View rootView;

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_about, container, false);
        TextView madeBy = (TextView) rootView.findViewById(R.id.made_by);
        TextView sourceAt = (TextView) rootView.findViewById(R.id.source_at);
        TextView icons = (TextView) rootView.findViewById(R.id.icons);
        TextView owmField = (TextView) rootView.findViewById(R.id.owm_field);
        TextView appIcon = (TextView) rootView.findViewById(R.id.app_icon);
        TextView verText = (TextView) rootView.findViewById(R.id.ver_text);
        TextView matDialog = (TextView) rootView.findViewById(R.id.material_dialogs);
        cvn = new CardViewNative[3];
        sourceAt.setMovementMethod(LinkMovementMethod.getInstance());           //To make the link clickable
        madeBy.setMovementMethod(LinkMovementMethod.getInstance());
        icons.setMovementMethod(LinkMovementMethod.getInstance());
        owmField.setMovementMethod(LinkMovementMethod.getInstance());
        matDialog.setMovementMethod(LinkMovementMethod.getInstance());

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
        for (int i = 0; i < 3; ++i) {
            String f = "card" + (i + 1);
            if (i != 10) {
                int resID = getResources().getIdentifier(f, "id", getContext().getPackageName());
                cvn[i] = (CardViewNative) rootView.findViewById(resID);
                cvn[i].setBackgroundColor(Color.WHITE);
                String libraryName = "This";
                TextView libName = (TextView) cvn[i].findViewById(R.id.libraryName);
                libName.setText(libraryName);
                String libCreator = "Sparker0i";
                TextView libCre = (TextView) cvn[i].findViewById(R.id.libraryCreator);
                libCre.setText(libCreator);
            }
        }
    }
}
