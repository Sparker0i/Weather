package com.a5corp.weather.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a5corp.weather.R;
import com.a5corp.weather.model.Log;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AboutModel> aboutCards;
    private Context context;

    public interface ChildClickListener {
        void onClick(int position);
    }

    public AboutAdapter(List<AboutModel> aboutCards , Context context) {
        this.aboutCards = aboutCards;
        this.context = context;
    }

    private void showLicense(int libId) {
        Intent intent = new Intent(context, LicenseActivity.class);
        intent.putExtra("libId", libId);
        context.startActivity(intent);
    }

    public ChildClickListener onClickListener(final ViewGroup parent) {
        return new ChildClickListener() {
            @Override
            public void onClick(int position) {
                switch (position) {
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

                    case 2: {
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

                    case 4: {
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

                    case 5: {
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
        };
    }

    public static class About1ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ViewGroup parent;
        public ChildClickListener listener;

        public About1ViewHolder(View view , ChildClickListener listener) {
            super(view);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(1);
        }
    }

    public static class About2ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ChildClickListener listener;

        public About2ViewHolder(View view , ChildClickListener listener) {
            super(view);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("Position" , getAdapterPosition() + "");
            listener.onClick(2);
        }
    }

    public static class About3ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ViewGroup parent;
        public ChildClickListener listener;

        public About3ViewHolder(View view , ChildClickListener listener) {
            super(view);
            view.callOnClick();
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(3);
        }
    }

    public static class About4ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ChildClickListener listener;

        public About4ViewHolder(View view , ChildClickListener listener) {
            super(view);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("Position" , getAdapterPosition() + "");
            listener.onClick(4);
        }
    }

    public static class About5ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ChildClickListener listener;

        public About5ViewHolder(View view , ChildClickListener listener) {
            super(view);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("Position" , getAdapterPosition() + "");
            listener.onClick(5);
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
                return new About1ViewHolder(view , onClickListener(parent));
            case AboutModel.ABOUT_2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.about_card_layout_2, parent, false);
                return new About2ViewHolder(view , onClickListener(parent));
            case AboutModel.ABOUT_3:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.about_card_layout_3, parent, false);
                return new About3ViewHolder(view , onClickListener(parent));
            case AboutModel.ABOUT_4:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.about_card_layout_4, parent, false);
                return new About4ViewHolder(view , onClickListener(parent));
            case AboutModel.ABOUT_5:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.about_card_layout_5, parent, false);
                return new About5ViewHolder(view , onClickListener(parent));
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
