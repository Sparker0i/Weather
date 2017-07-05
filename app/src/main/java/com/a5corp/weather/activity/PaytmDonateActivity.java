package com.a5corp.weather.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.a5corp.weather.R;
import com.a5corp.weather.permissions.Permissions;
import com.a5corp.weather.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PaytmDonateActivity extends AppCompatActivity {
    Permissions permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm_donate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        permission = new Permissions(this);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , Constants.WRITE_EXTERNAL_STORAGE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode ,
                                           @NonNull String permissions[] ,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageClicked();
                } else {
                    permission.showDenialMessage(Constants.WRITE_EXTERNAL_STORAGE);
                }
                break;
            }
        }
    }

    private void imageClicked() {
        Bitmap bm = BitmapFactory.decodeResource(getResources() , R.drawable.qr);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        Log.i("Storage" , extStorageDirectory);
        OutputStream outputStream;
        File file = new File(extStorageDirectory , "/Pictures/Donate to Simple Weather Developer.png");
        try {
            outputStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG , 100 , outputStream);
            outputStream.flush();
            outputStream.close();
            Snackbar.make(findViewById(R.id.root), "Saved to : " + extStorageDirectory + "/Pictures/" + "Donate to Simple Weather Developer.png" , Snackbar.LENGTH_LONG).show();
        }
        catch (FileNotFoundException fex) {
            fex.printStackTrace();
            Snackbar.make(findViewById(R.id.root) , "Could Not Save the QR Code to gallery, please check the relevant permissions on your phone and try again later", Snackbar.LENGTH_LONG).show();
        }
        catch (IOException iex) {
            iex.printStackTrace();
            Snackbar.make(findViewById(R.id.root) , "Please Try Again Later" , Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
