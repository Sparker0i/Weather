package com.a5corp.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.a5corp.weather.R;
import com.a5corp.weather.app.MyContextWrapper;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LicenseActivity extends AppCompatActivity
{
    int libID;
    Toolbar toolbar;
    private String license;
    private String library;
    private String link;
    @BindView(R.id.license) TextView licText;
    @BindView(R.id.fab) FloatingActionButton fab;
    @OnClick(R.id.fab) void onClick() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Context context = MyContextWrapper.wrap(newBase, new Prefs(newBase).getLanguage());
        super.attachBaseContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        ButterKnife.bind(this);
        libID = getIntent().getExtras().getInt(Constants.LIBRARY_ID);

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
            case 11 :
            {
                library = getString(R.string.lib_11);
                license = getString(R.string.lib_11_license) + getString(R.string.apache_license);
                link = getString(R.string.lib_11_link);
                break;
            }
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(library);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fab.setImageDrawable(new IconicsDrawable(this)
                            .icon(MaterialDesignIconic.Icon.gmi_github)
                            .color(Color.WHITE));
        licText.setText(Html.fromHtml(license));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
