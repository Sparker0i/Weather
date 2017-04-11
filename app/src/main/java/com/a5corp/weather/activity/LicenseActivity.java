package com.a5corp.weather.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.a5corp.weather.R;

public class LicenseActivity extends AppCompatActivity
{
    int libID;
    Toolbar toolbar;
    private String license;
    private String library;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);

        libID = getIntent().getExtras().getInt("libId");

        switch (libID)
        {
            case 1:
            {
                library = getString(R.string.lib_1);
                license = getString(R.string.lib_1_license) + getString(R.string.mit_license);
                link = getString(R.string.lib_1_link);
                break;
            }
            case 2:
            {
                library = getString(R.string.lib_2);
                license = getString(R.string.lib_2_license) + getString(R.string.apache_license);
                link = getString(R.string.lib_2_link);
                break;
            }
            case 3:
            {
                library = getString(R.string.lib_3);
                license = getString(R.string.lib_3_license) + getString(R.string.apache_license);
                link = getString(R.string.lib_3_link);
                break;
            }
            case 4:
            {
                library = getString(R.string.lib_4);
                license = getString(R.string.lib_4_license) + getString(R.string.apache_license);
                link = getString(R.string.lib_4_link);
                break;
            }
            case 5:
            {
                library = getString(R.string.lib_5);
                license = getString(R.string.lib_5_license) + getString(R.string.apache_license);
                link = getString(R.string.lib_5_link);
                break;
            }
            case 6:
            {
                library = getString(R.string.lib_6);
                license = getString(R.string.lib_6_license) + getString(R.string.mit_license);
                link = getString(R.string.lib_6_link);
                break;
            }
            case 7:
            {
                library = getString(R.string.lib_7);
                license = getString(R.string.lib_7_license) + getString(R.string.mit_license);
                link = getString(R.string.lib_7_link);
                break;
            }
            case 8:
            {
                library = getString(R.string.lib_8);
                license = getString(R.string.lib_8_license) + getString(R.string.apache_license);
                link = getString(R.string.lib_8_link);
                break;
            }
            case 9:
            {
                library = getString(R.string.lib_9);
                license = getString(R.string.lib_9_license) + getString(R.string.apache_license);
                link = getString(R.string.lib_9_link);
                break;
            }
            case 10:
            {
                library = getString(R.string.lib_10);
                license = getString(R.string.lib_10_license) + getString(R.string.apache_license);
                link = getString(R.string.lib_10_link);
                break;
            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        View customToolbar = getLayoutInflater().inflate(R.layout.custom_toolbar_2,null);
        ((TextView)customToolbar.findViewById(R.id.toolbar_text)).setText(library);

        Button button = (Button) customToolbar.findViewById(R.id.toolbar_button);
        button.setText(getString(R.string.github));
        button.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(link));
                        startActivity(intent);
                    }
                }
        );

        toolbar.addView(customToolbar);

        ((TextView)findViewById(R.id.license)).setText(Html.fromHtml(license));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
