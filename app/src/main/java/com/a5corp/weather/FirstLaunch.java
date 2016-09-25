package com.a5corp.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstLaunch extends AppCompatActivity {;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launch);
        final TextView cityInput = (TextView) findViewById(R.id.city_input);
        Button goButton = (Button) findViewById(R.id.go_button);
        goButton.setText("GO");
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCity(cityInput.getText().toString());
            }
        });
        Intent intent = new Intent(FirstLaunch.this, WeatherActivity.class);
        FirstLaunch.this.startActivity(intent);
    }

    public void changeCity(String city){
        WeatherFragment wf = (WeatherFragment)getSupportFragmentManager()
                .findFragmentById(R.id.container);
        wf.changeCity(city);
        new CityPreference(this).setCity(city);
    }
}
