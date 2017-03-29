package com.a5corp.weather.cards;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a5corp.weather.BuildConfig;
import com.a5corp.weather.R;

import it.gmariotti.cardslib.library.internal.Card;

public class AboutCard extends Card
{
    private int type;
    private Context context;

    public AboutCard(Context context, int innerLayout, int type)
    {
        super(context, innerLayout);
        this.type = type;
        this.context = context;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view)
    {
        super.setupInnerViewElements(parent, view);

        switch (type)
        {
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

                parent.findViewById(R.id.lib_10).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(10);
                    }
                });

                parent.findViewById(R.id.lib_11).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        showLicense(11);
                    }
                });
                break;
            }

            case 4:
            {
                /*parent.findViewById(R.id.legal).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(context, FetchActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;*/
            }

            case 5:
            {
                //((TextView)parent.findViewById(R.id.build_version)).setText(BuildConfig.VERSION_NAME);
                break;
            }
        }
    }

    public void showLicense(int libId)
    {
        /*Intent intent = new Intent(context, LicenseActivity.class);
        intent.putExtra("libId", libId);
        context.startActivity(intent);*/
    }
}