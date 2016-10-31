package com.example.rodri.testetecnonutri;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rodri on 30/10/2016.
 * Classe responsavel por controlar a Recycle View.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<HashMap<String, Object>> lista;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mLinearLayout;
        public LinearLayout userSection;
        public LinearLayout cardSection;
        public TextView userName;
        public TextView userObjectivo;
        public TextView diaRefeicao;
        public TextView kcals;
        public ImageView imageUser;
        public ImageView imagePost;

        public ViewHolder(View v) {
            super(v);
            mLinearLayout = (LinearLayout) v.findViewById(R.id.linearLayoutCard);
            userSection = (LinearLayout) v.findViewById(R.id.cardUserSection);
            cardSection =(LinearLayout) v.findViewById(R.id.cardImageSection);
            userName = (TextView) v.findViewById(R.id.profileName);
            userObjectivo = (TextView) v.findViewById(R.id.profileObjetivo);
            diaRefeicao = (TextView) v.findViewById(R.id.diaRefeicao);
            kcals = (TextView) v.findViewById(R.id.kcal);
            imageUser = (ImageView) v.findViewById(R.id.profilePicture);
            imagePost = (ImageView) v.findViewById(R.id.imageFeed);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context, List<HashMap<String, Object>> lista) {
        this.context = context;
        this.lista = lista;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.imageUser.setImageDrawable(null);
        holder.imagePost.setImageDrawable(null);
        holder.userSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUserDetailsActivity();
            }
        });
        holder.cardSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPostDetailsActivity();
            }
        });
        holder.userName.setText((String)this.lista.get(position).get("name"));
        holder.userObjectivo.setText((String)this.lista.get(position).get("objetivo"));
        holder.diaRefeicao.setText("Refeição de "+(String)this.lista.get(position).get("date"));
        holder.kcals.setText((String)this.lista.get(position).get("energy") + " Kcals");

        (new ImageLoaderTask(this.context) {
            @Override
            protected void onPostExecute(Drawable drawable) {
                super.onPostExecute(drawable);
                holder.imagePost.setImageDrawable(drawable);
            }
        }).execute((String)this.lista.get(position).get("postPicture"));

        (new ImageLoaderTask(this.context) {
            @Override
            protected void onPostExecute(Drawable drawable) {
                super.onPostExecute(drawable);
                holder.imageUser.setImageDrawable(drawable);
            }
        }).execute((String)this.lista.get(position).get("profilePicture"));

    }
    public void gotoUserDetailsActivity() {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void gotoPostDetailsActivity() {
        Intent intent = new Intent(context, PostDetailsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lista.size();
    }

    private class ImageLoaderTask extends AsyncTask<String, Void, Drawable> {

        Context context;

        ImageLoaderTask(Context context) {
            this.context = context;
        }

        @Override
        protected Drawable doInBackground(String... params) {

            //InputStream iStream = null;
            String imgUrl = (String) params[0];

            try {
                return downloadImage(imgUrl);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private Drawable downloadImage(String _url)
        {
            //Prepare to download image
            URL url;
            BufferedOutputStream out;
            InputStream in;
            BufferedInputStream buf;

            //BufferedInputStream buf;
            try {
                url = new URL(_url);
                in = url.openStream();

                // Read the inputstream
                buf = new BufferedInputStream(in);

                // Convert the BufferedInputStream to a Bitmap
                Bitmap bMap = BitmapFactory.decodeStream(buf);
                if (in != null) {
                    in.close();
                }
                if (buf != null) {
                    buf.close();
                }

                return new BitmapDrawable(bMap);

            } catch (Exception e) {
                Log.e("Error reading file", e.toString());
            }

            return null;
        }

    }
}
