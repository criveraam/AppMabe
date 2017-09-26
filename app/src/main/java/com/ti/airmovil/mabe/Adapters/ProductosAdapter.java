package com.ti.airmovil.mabe.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ti.airmovil.mabe.Helper.Config;
import com.ti.airmovil.mabe.Helper.OnLoadMoreListener;
import com.ti.airmovil.mabe.Models.ProductosModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.ti.airmovil.mabe.R;

import org.w3c.dom.Text;

/**
 * Created by tecnicoairmovil on 25/09/17.
 */

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.MyViewHolder>{
    private List<ProductosModel> lista;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public ProductosAdapter(Context mContext, List<ProductosModel> lista, RecyclerView mRecyclerView) {
        this.lista = lista;
        this.mContext = mContext;
        this.mRecyclerView = mRecyclerView;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mRecyclerView.getLayoutManager();
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_productos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String cadena = lista.get(position).getNombre().substring(0,20);
        holder.textViewNombre.setText(cadena + "...");
        holder.textViewPrecio.setText(Config.nf.format(Double.parseDouble(lista.get(position).getPrecio())));
        String urlImage = lista.get(position).getImagen();
        new DownloadImageTask((ImageView) holder.imageViewProducto).execute(urlImage);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewProducto;
        public TextView textViewNombre, textViewPrecio;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewProducto = (ImageView) itemView.findViewById(R.id.imageView_articulo);
            textViewNombre = (TextView) itemView.findViewById(R.id.textView_nombre_articulo);
            textViewPrecio = (TextView) itemView.findViewById(R.id.textView_precio_articulo);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
