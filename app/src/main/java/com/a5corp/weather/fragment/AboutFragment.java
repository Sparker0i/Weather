package com.a5corp.weather.fragment;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a5corp.weather.BuildConfig;
import com.a5corp.weather.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        TextView madeBy = (TextView) rootView.findViewById(R.id.made_by);
        TextView sourceAt = (TextView) rootView.findViewById(R.id.source_at);
        TextView icons = (TextView) rootView.findViewById(R.id.icons);
        TextView owmField = (TextView) rootView.findViewById(R.id.owm_field);
        TextView appIcon = (TextView) rootView.findViewById(R.id.app_icon);
        TextView verText = (TextView) rootView.findViewById(R.id.ver_text);
        TextView matDialog = (TextView) rootView.findViewById(R.id.material_dialogs);

        sourceAt.setMovementMethod(LinkMovementMethod.getInstance());           //To make the link clickable
        madeBy.setMovementMethod(LinkMovementMethod.getInstance());
        icons.setMovementMethod(LinkMovementMethod.getInstance());
        owmField.setMovementMethod(LinkMovementMethod.getInstance());
        matDialog.setMovementMethod(LinkMovementMethod.getInstance());

        Typeface weatherFont;
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        appIcon.setTypeface(weatherFont);
        appIcon.setText(getString(R.string.day_cloudy));
        String verId = "Version " + BuildConfig.VERSION_NAME;
        verText.setText(verId);
        return rootView;
    }
}
