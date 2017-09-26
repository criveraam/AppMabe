package com.ti.airmovil.mabe.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ti.airmovil.mabe.Models.ProductosModel;
import com.ti.airmovil.mabe.Models.Test;
import com.ti.airmovil.mabe.R;

import java.util.List;

/**
 * Created by tecnicoairmovil on 25/09/17.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.PersonViewHolder>{
    private List<ProductosModel> lista;
    private Context context;
    private LayoutInflater inflater;

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            personName = (TextView)itemView.findViewById(R.id.textView_nombre_articulo);
            personAge = (TextView)itemView.findViewById(R.id.textView_precio_articulo);
        }
    }

}
