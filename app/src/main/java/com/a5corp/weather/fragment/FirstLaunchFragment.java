package com.a5corp.weather.fragment;

import android.content.Intent;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.a5corp.weather.activity.GlobalActivity;
import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.internet.CheckConnection;
import com.a5corp.weather.preferences.Preferences;
import com.a5corp.weather.utils.Utils;

import it.sephiroth.android.library.tooltip.Tooltip;

public class FirstLaunchFragment extends Fragment {

    View rootView;
    EditText cityInput;
    TextView message;
    Preferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_first_launch, container, false);
        preferences = new Preferences(getContext());
        cityInput = (EditText) rootView.findViewById(R.id.city_input);
        message = (TextView) rootView.findViewById(R.id.intro_text);
        if (GlobalActivity.i == 0) {
            message.setText(getString(R.string.pick_city));
        }
        else {
            message.setText(getString(R.string.uh_oh));
        }
        Button goButton = (Button) rootView.findViewById(R.id.go_button);
        goButton.setText(getString(R.string.first_go_text));
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new CheckConnection(getContext()).isNetworkAvailable()) {
                    Snackbar.make(rootView , "Please check your Internet connection." , Snackbar.LENGTH_SHORT).show();
                }
                else if (cityInput.getText().length() > 0) {
                    preferences.setCity(cityInput.getText().toString());
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Log.i("Loaded", "Weather");
                    startActivity(intent);
                    Log.i("Changed", "City");
                }
                else {
                    Snackbar.make(rootView , "Enter a City Name First" , Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        if (new Utils(getContext()).isInstallFromUpdate() && !preferences.getv3ResetShown()) {
            function();
            Log.i("Update" , "True");
        }
        return rootView;
    }

    private void function() {
        if (!preferences.getv3ResetShown()) {
            Tooltip.make(getContext(),
                    new Tooltip.Builder(101)
                            .anchor(cityInput, Tooltip.Gravity.BOTTOM)
                            .closePolicy(new Tooltip.ClosePolicy()
                                    .insidePolicy(true, false)
                                    .outsidePolicy(true, false), 3000)
                            .activateDelay(800)
                            .showDelay(300)
                            .text("Due to some internal refactoring, this version resets your city preference, This won't happen again")
                            .maxWidth(500)
                            .withArrow(true)
                            .withOverlay(true)
                            .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                            .build()
            ).show();
            Log.i("Shown Tip" , "True");
            //Snackbar.make(rootView , "Due to some internal refactoring, this version resets your city preference, This won't happen again" , BaseTransientBottomBar.LENGTH_INDEFINITE);
            //preferences.setv3TargetShown(true);
        }
    }
}
