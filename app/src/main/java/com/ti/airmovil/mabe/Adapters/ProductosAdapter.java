package com.ti.airmovil.mabe.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ti.airmovil.mabe.Helper.OnLoadMoreListener;
import com.ti.airmovil.mabe.Models.ProductosModel;

import java.util.List;

import com.ti.airmovil.mabe.R;
/**
 * Created by tecnicoairmovil on 25/09/17.
 */

public class ProductosAdapter extends RecyclerView.Adapter{

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context mContext;
    private List<ProductosModel> list;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private RecyclerView mRecyclerView;

    public ProductosAdapter(List<ProductosModel> list) {
        this.mContext = mContext;
        this.list = list;
        this.mRecyclerView = mRecyclerView;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.mRecyclerView.getLayoutManager();
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_productos, parent, false);
            vh = new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false);
            vh = new LoadingViewHolder(view);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {

            final ProductosModel lista = list.get(position);
            final MyViewHolder myholder = (MyViewHolder) holder;

            myholder.textViewNombre.setText(lista.getNombre());
            myholder.textViewPrecio.setText(lista.getPrecio());

        } else {
            ((LoadingViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loading);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textViewNombre, textViewPrecio;

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
