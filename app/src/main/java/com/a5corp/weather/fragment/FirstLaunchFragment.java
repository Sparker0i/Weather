package com.a5corp.weather.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.a5corp.weather.model.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a5corp.weather.GlobalActivity;
import com.a5corp.weather.R;
import com.a5corp.weather.activity.WeatherActivity;
import com.a5corp.weather.internet.CheckConnection;
import com.a5corp.weather.permissions.GPSTracker;
import com.a5corp.weather.permissions.Permissions;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;
import com.github.florent37.materialtextfield.MaterialTextField;

public class FirstLaunchFragment extends Fragment {

    View rootView;
    EditText cityInput;
    TextView message;
    Prefs preferences;
    Permissions permission;
    MaterialTextField textField;
    GPSTracker gps;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_first_launch, container, false);
        preferences = new Prefs(getContext());
        cityInput = rootView.findViewById(R.id.city_input);
        textField = rootView.findViewById(R.id.materialTextField);
        ImageView img = textField.findViewById(R.id.mtf_image);
        img.setImageAlpha(R.drawable.logo);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permission = new Permissions(getContext());
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION} , Constants.READ_COARSE_LOCATION);
            }
        });
        message = rootView.findViewById(R.id.intro_text);
        if (GlobalActivity.i == 0) {
            message.setText(getString(R.string.pick_city));
        }
        else {
            message.setText(getString(R.string.uh_oh));
        }
        Button goButton = rootView.findViewById(R.id.go_button);
        goButton.setText(getString(android.R.string.ok));
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new CheckConnection(getContext()).isNetworkAvailable()) {
                    Snackbar.make(rootView , getString(R.string.check_internet) , Snackbar.LENGTH_SHORT).show();
                }
                else if (cityInput.getText().length() > 0) {
                    launchActivity(0);
                }
                else {
                    Snackbar.make(rootView , getString(R.string.enter_city_first) , Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        cityInput.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    launchActivity(0);
                    return true;
                }
                return false;
            }
        });
        return rootView;
    }

    private void launchActivity(int mode) {
        preferences.setCity(cityInput.getText().toString());
        Intent intent = new Intent(getActivity(), WeatherActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("mode" , mode);
        Log.i("Loaded", "Weather");
        startActivity(intent);
        Log.i("Changed", "City");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode ,
                                           @NonNull String permissions[] ,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.READ_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gps = new GPSTracker(getContext());
                    if (!gps.canGetLocation())
                        gps.showSettingsAlert();
                    else {
                        preferences.setLatitude(Float.parseFloat(gps.getLatitude()));
                        preferences.setLongitude(Float.parseFloat(gps.getLongitude()));
                        launchActivity(1);
                    }
                } else {
                    permission.permissionDenied();
                }
                break;
            }
        }
    }
}
