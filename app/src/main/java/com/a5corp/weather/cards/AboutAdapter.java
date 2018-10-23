package com.a5corp.weather.cards;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a5corp.weather.R;
import com.a5corp.weather.model.AboutModel;
import com.a5corp.weather.activity.LicenseActivity;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AboutModel> aboutCards;

    public AboutAdapter(List<AboutModel> aboutCards) {
        this.aboutCards = aboutCards;
    }

    public static class About1ViewHolder extends RecyclerView.ViewHolder {
        public ViewGroup parent;
        public Context context;

        About1ViewHolder(View view) {
            super(view);
            this.context = view.getContext();
            setUpOnClick(view);
        }

        void setUpOnClick(View parent) {
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
        }
    }

    public static class About2ViewHolder extends RecyclerView.ViewHolder {
        Context context;

        About2ViewHolder(View view) {
            super(view);
            this.context = view.getContext();
            setUpOnClick(view);
        }

        void setUpOnClick(View parent) {
            (parent.findViewById(R.id.credits)).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(context.getString(R.string.dev_2_link)));
                            context.startActivity(intent);
                        }
                    }
            );
        }
    }

    public static class About3ViewHolder extends RecyclerView.ViewHolder {
        public ViewGroup parent;
        private Context context;

        About3ViewHolder(View view) {
            super(view);
            this.context = view.getContext();
            view.callOnClick();
            setUpOnClick(view);
        }

        private void showLicense(int libId) {
            Intent intent = new Intent(context, LicenseActivity.class);
            intent.putExtra("libId", libId);
            context.startActivity(intent);
        }

        void setUpOnClick(View parent) {
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
        }
    }

    public static class About4ViewHolder extends RecyclerView.ViewHolder {
        Context context;

        About4ViewHolder(View view) {
            super(view);
            this.context = view.getContext();
        }
    }

    public static class About5ViewHolder extends RecyclerView.ViewHolder {
        Context context;

        About5ViewHolder(View view) {
            super(view);
            this.context = view.getContext();
            setUpOnClick(view);
        }

        void setUpOnClick(View parent) {
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate out card list item

        View view;
        switch (viewType) {
            case AboutModel.ABOUT_1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.about_card_layout_1, parent, false);
                return new About1ViewHolder(view);
            case AboutModel.ABOUT_2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.about_card_layout_2, parent, false);
                return new About2ViewHolder(view);
            case AboutModel.ABOUT_3:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.about_card_layout_3, parent, false);
                return new About3ViewHolder(view);
            case AboutModel.ABOUT_4:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.about_card_layout_4, parent, false);
                return new About4ViewHolder(view);
            case AboutModel.ABOUT_5:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.about_card_layout_5, parent, false);
                return new About5ViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (aboutCards.get(position).type) {
            case 0:
                return AboutModel.ABOUT_1;
            case 1:
                return AboutModel.ABOUT_2;
            case 2:
                return AboutModel.ABOUT_3;
            case 3:
                return AboutModel.ABOUT_4;
            case 4:
                return AboutModel.ABOUT_5;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Bind data for the item at position
        AboutModel object = aboutCards.get(position);
        if (object != null) {
            switch (object.type) {
                case AboutModel.ABOUT_1:

            }
        }
    }

    @Override
    public int getItemCount() {
        // Return the total number of items

        return aboutCards.size();
    }
}
