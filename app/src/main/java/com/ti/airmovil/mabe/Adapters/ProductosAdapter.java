package com.ti.airmovil.mabe.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ti.airmovil.mabe.Activities.DetalleActivity;
import com.ti.airmovil.mabe.Activities.MainActivity;
import com.ti.airmovil.mabe.Helper.Config;
import com.ti.airmovil.mabe.Models.ProductosModel;

import java.io.InputStream;
import java.util.List;

import com.ti.airmovil.mabe.Models.Test;
import com.ti.airmovil.mabe.R;
import com.ti.airmovil.mabe.Activities.TestActivity;

/**
 * Created by tecnicoairmovil on 25/09/17.
 */

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.MyViewHolder>{
    private static final String TAG = ProductosAdapter.class.getSimpleName();
    private List<ProductosModel> lista;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private int bandera;

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
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_up);
        animation.setDuration(1000);
        view.setAnimation(animation);
        view.animate();
        animation.start();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ProductosModel lists = lista.get(position);
        holder.imageViewProducto.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.zoom_in));
        holder.textViewNumerador.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_left));
        holder.textViewPrecio.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_left));
        holder.textViewNombre.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_left));
        holder.imageViewSubmenu.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_up));
        holder.textViewNombre.setText(lists.getNombre());
        holder.textViewPrecio.setText(Config.nf.format(Double.parseDouble(lists.getPrecio())));
        holder.textViewNumerador.setText(String.valueOf(position + 1));
        String urlImage = lists.getImagen();
        new DownloadImageTask((ImageView) holder.imageViewProducto).execute(urlImage);

        holder.imageViewProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Config.compruebaConexion(mContext)){
                    Log.e(TAG, "ID DEL PRODUCTO::: " + lists.getIdProducto());
                    Intent intent = new Intent(view.getContext(), TestActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra("id_producto", lists.getIdProducto());
                    view.getContext().startActivity(intent);
                }else{
                    Toast.makeText(mContext, "No hay conexion a internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Config.compruebaConexion(mContext)){Log.e(TAG, "ID DEL PRODUCTO::: " + lists.getIdProducto());
                    Intent intent = new Intent(view.getContext(), TestActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra("id_producto", lists.getIdProducto());
                    view.getContext().startActivity(intent);
                }else{
                    Toast.makeText(mContext, "No hay conexion a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewNombre, textViewPrecio, textViewNumerador;
        public ImageView imageViewProducto, imageViewSubmenu;
        public CardView cardViewItem;
        public MyViewHolder(View itemView) {
            super(itemView);
            cardViewItem = (CardView) itemView.findViewById(R.id.card_view);
            imageViewProducto = (ImageView) itemView.findViewById(R.id.imageView_articulo);
            imageViewSubmenu = (ImageView) itemView.findViewById(R.id.imageView_submenu);
            textViewNombre = (TextView) itemView.findViewById(R.id.textView_nombre_articulo);
            textViewPrecio = (TextView) itemView.findViewById(R.id.textView_precio_articulo);
            textViewNumerador = (TextView) itemView.findViewById(R.id.textView_numerador);
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
