package com.a5corp.weather.cards;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;

import com.a5corp.weather.R;
import com.a5corp.weather.activity.LicenseActivity;

public class AboutCard {
    private int type;
    private Context context;

    public AboutCard(Context context, int innerLayout, int type) {
        //super(context);
        this.type = type;
        this.context = context;
    }

    public void setupInnerViewElements(ViewGroup parent, View view) {

        switch (type) {
            case 1:
            {
                (parent.findViewById(R.id.more_apps)).setOnClickListener(
                        new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(context.getString(R.string.other_apps_link)));
                                context.startActivity(intent);
                            }
                        }
                );
                (parent.findViewById(R.id.name)).setOnClickListener(
                        new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(context.getString(R.string.github_link)));
                                context.startActivity(intent);
                            }
                        }
                );
                break;
            }

            case 2:
            {
                (parent.findViewById(R.id.credits)).setOnClickListener(
                        new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(context.getString(R.string.dev_2_link)));
                                context.startActivity(intent);
                            }
                        }
                );

                break;
            }

            case 3:
            {
                parent.findViewById(R.id.lib_1).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(1);
                    }
                });

                parent.findViewById(R.id.lib_2).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(2);
                    }
                });

                parent.findViewById(R.id.lib_3).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(3);
                    }
                });

                parent.findViewById(R.id.lib_4).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(4);
                    }
                });

                parent.findViewById(R.id.lib_5).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(5);
                    }
                });

                parent.findViewById(R.id.lib_6).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(6);
                    }
                });

                parent.findViewById(R.id.lib_7).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(7);
                    }
                });

                parent.findViewById(R.id.lib_8).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(8);
                    }
                });

                parent.findViewById(R.id.lib_9).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(9);
                    }
                });
                parent.findViewById(R.id.lib_10).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLicense(10);
                    }
                });
                parent.findViewById(R.id.lib_11).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLicense(11);
                    }
                });
                break;
            }

            case 4:
            {
                parent.findViewById(R.id.license_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(context.getString(R.string.license_link)));
                        context.startActivity(intent);
                    }
                });
                break;
            }

            case 5:
            {
                parent.findViewById(R.id.insp_1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(context.getString(R.string.insp_1_link)));
                        context.startActivity(intent);
                    }
                });
                parent.findViewById(R.id.insp_2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(context.getString(R.string.insp_2_link)));
                        context.startActivity(intent);
                    }
                });
                parent.findViewById(R.id.insp_3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(context.getString(R.string.insp_3_link)));
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    private void showLicense(int libId) {
        Intent intent = new Intent(context, LicenseActivity.class);
        intent.putExtra("libId", libId);
        context.startActivity(intent);
    }
}