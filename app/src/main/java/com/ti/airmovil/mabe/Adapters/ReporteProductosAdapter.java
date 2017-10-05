package com.ti.airmovil.mabe.Adapters;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ti.airmovil.mabe.Dialog.CustomBottomSheetDialogFragment;
import com.ti.airmovil.mabe.Dialog.DialogoDetalle;
import com.ti.airmovil.mabe.Models.ProductosModel;
import com.ti.airmovil.mabe.Models.ReporteProductosModel;
import com.ti.airmovil.mabe.R;

import java.util.List;

/**
 * Created by tecnicoairmovil on 04/10/17.
 */

public class ReporteProductosAdapter extends RecyclerView.Adapter<ReporteProductosAdapter.MyViewHolderA>{
    private static final String TAG = ReporteProductosAdapter.class.getSimpleName();
    private List<ReporteProductosModel> lista;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public ReporteProductosAdapter(Context mContext, List<ReporteProductosModel> lista, RecyclerView mRecyclerView) {
        this.lista = lista;
        this.mContext = mContext;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public MyViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_reportes, parent, false);
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_up);
        animation.setDuration(500);
        view.setAnimation(animation);
        view.animate();
        animation.start();
        return new MyViewHolderA(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolderA holder, int position) {
        final ReporteProductosModel lists = lista.get(position);
        holder.textView_numerador_reporte_productos.setText(String.valueOf(position + 1));
        holder.textView_nombre_producto.setText(lists.getNombreProducto());
        holder.textView_precio_mabe.setText(lists.getPrecioMabe());
        holder.textView_walmart_precio.setText(lists.getPrecioWalmart());
        holder.textView_bestbay_precio.setText(lists.getPrecioBestBuy());
        holder.textView_walmart_porcentaje.setText(lists.getPorcentajeWalmart());
        holder.textView_bestbuy_porcentaje.setText(lists.getPorcentajeBestBuy());
        holder.textView_walmart_porcentaje.setTextColor(lists.getColorWalmart());
        holder.textView_bestbuy_porcentaje.setTextColor(lists.getColorBestBuy());
        holder.cardView_contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("***", "VIEW:::>");
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolderA extends RecyclerView.ViewHolder{
        private CardView cardView_contenedor;
        private TextView textView_precio_mabe;
        private TextView textView_numerador_reporte_productos;
        private TextView textView_nombre_producto;
        private TextView textView_walmart_precio;
        private TextView textView_walmart_porcentaje;
        private TextView textView_bestbay_precio;
        private TextView textView_bestbuy_porcentaje;
        public MyViewHolderA(View itemView) {
            super(itemView);
            cardView_contenedor = (CardView) itemView.findViewById(R.id.card_view_list_productos);
            textView_numerador_reporte_productos = (TextView) itemView.findViewById(R.id.textView_numerador_reporte_productos);
            textView_nombre_producto = (TextView) itemView.findViewById(R.id.textView_nombre_producto);
            textView_precio_mabe = (TextView) itemView.findViewById(R.id.textView_precio_mabe);
            textView_walmart_precio = (TextView) itemView.findViewById(R.id.textView_walmart_precio);
            textView_walmart_porcentaje = (TextView) itemView.findViewById(R.id.textView_walmart_porcentaje);
            textView_bestbay_precio = (TextView) itemView.findViewById(R.id.textView_bestbay_precio);
            textView_bestbuy_porcentaje = (TextView) itemView.findViewById(R.id.textView_bestbuy_porcentaje);
        }
    }
}
