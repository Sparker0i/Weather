package com.a5corp.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstLaunch extends AppCompatActivity {

    static TextView cityInput;
    int init = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        cityInput = (TextView) findViewById(R.id.city_input);
        Button goButton = (Button) findViewById(R.id.go_button);
        goButton.setText(getString(R.string.first_go_text));
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonFunction();
                if (init == 1) {
                    buttonFunction();
                    Intent intent = new Intent(FirstLaunch.this, WeatherActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Log.i("Loaded" , "Weather");
                    startActivity(intent);
                }
                Log.i("Changed" , "City");
            }
        });
    }

    public void buttonFunction() {
        GlobalActivity.cp.setCity(cityInput.getText().toString());
        init = 1;
    }
}