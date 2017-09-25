package com.ti.airmovil.mabe.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ti.airmovil.mabe.Models.Test;
import com.ti.airmovil.mabe.R;

import java.util.List;

/**
 * Created by tecnicoairmovil on 25/09/17.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
    private List<Test> testList;
    private Context context;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(List<Test> testList, Context context) {
        this.testList = testList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.list_productos, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Test tests = testList.get(position);
        //Pass the values of feeds object to Views
        holder.title.setText(tests.getName());
        holder.content.setText(tests.getName());
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView content, title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView_nombre_articulo);
            content = (TextView) itemView.findViewById(R.id.textView_precio_articulo);
        }
    }

}
