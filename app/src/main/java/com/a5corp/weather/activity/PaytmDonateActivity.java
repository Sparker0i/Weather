package com.a5corp.weather.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.a5corp.weather.model.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.a5corp.weather.R;
import com.a5corp.weather.app.MyContextWrapper;
import com.a5corp.weather.permissions.Permissions;
import com.a5corp.weather.preferences.Prefs;
import com.a5corp.weather.utils.Constants;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

public class PaytmDonateActivity extends AppCompatActivity {
    Permissions permission;
    Handler handler;
    MaterialDialog pd;
    int err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm_donate);
        handler = new Handler();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        pd = new MaterialDialog.Builder(this)
                .title(getString(R.string.please_wait))
                .content(getString(R.string.loading))
                .cancelable(false)
                .progress(true , 0).build();
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
                    Log.i("Yes" , "Done");
                    imageClicked();
                } else {
                    permission.showDenialMessage(Constants.WRITE_EXTERNAL_STORAGE);
                }
                break;
            }
        }
    }

    private void imageClicked() {
        try {
            new Task().execute().get();
        }
        catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
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
    protected void attachBaseContext(Context newBase) {
        Context context = MyContextWrapper.wrap(newBase, new Prefs(newBase).getLanguage());
        super.attachBaseContext(context);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class Task extends AsyncTask<Void , Integer , Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Bitmap bm = BitmapFactory.decodeResource(getResources() , R.drawable.qr);
            final String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            OutputStream outputStream;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    pd.show();
                }
            });
            File file = new File(extStorageDirectory , "/Pictures/Donate to Simple Weather Developer.png");
            try {
                outputStream = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.PNG , 100 , outputStream);
                outputStream.flush();
                outputStream.close();
                err = 0;
            }
            catch (FileNotFoundException fex) {
                fex.printStackTrace();
                err = 1;
            }
            catch (IOException iex) {
                iex.printStackTrace();
                err = -1;
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pd.hide();
                    switch (err) {
                        case 0 : Snackbar.make(findViewById(R.id.root), getString(R.string.paytm_err_0 , extStorageDirectory) , Snackbar.LENGTH_LONG).show();
                            break;
                        case 1 : Snackbar.make(findViewById(R.id.root) , getString(R.string.paytm_err_1), Snackbar.LENGTH_LONG).show();
                            break;
                        case -1 : Snackbar.make(findViewById(R.id.root) , getString(R.string.paytm_err_min_1) , Snackbar.LENGTH_LONG).show();
                            break;
                    }
                }
            } , 2000);
            return null;
        }
    }
}
