package com.example.mygoods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class OrdersAdapter extends BaseAdapter {
    private Context c;
    private ArrayList<Shoes>shoesArrayList;

    public OrdersAdapter (Context context , ArrayList<Shoes>shoesArrayList)
    {
        this.c = context;
        this.shoesArrayList = shoesArrayList;
    }

    @Override
    public int getCount() {
        return shoesArrayList.size();
    }

    @Override
    public Shoes getItem(int i) {
        return shoesArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Shoes shoes = getItem(i);
        View v = view;
        if(v==null) {
            v = LayoutInflater.from(c).inflate(R.layout.order, null, false);
        }
        final int index = i;

        TextView tv_date = v.findViewById(R.id.order_date_tv);
        TextView tv_time = v.findViewById(R.id.order_time_tv);
        TextView tv_id = v.findViewById(R.id.order_id_tv);
        TextView tv_model = v.findViewById(R.id.order_tv_model);
        TextView tv_size = v.findViewById(R.id.order_tv_size);
        TextView tv_color = v.findViewById(R.id.order_tv_color);
        TextView tv_quantity = v.findViewById(R.id.order_tv_quantity);
        TextView tv_price = v.findViewById(R.id.order_tv_price);
        ImageView delete_iv = v.findViewById(R.id.order_delete_iv);

        tv_id.setText(shoes.getId()+"");
        tv_model.setText(shoes.getModel());
        tv_size.setText(shoes.getSize()+"");
        tv_color.setText(shoes.getColor());
        tv_quantity.setText(shoes.getQuantity()+"");
        tv_price.setText(shoes.getPrice()+"");
        tv_time.setText(shoes.getTime());
        tv_date.setText(shoes.getDate());


        delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDataBase db = new MyDataBase(c);
                db.deleteOrderedShoes(shoesArrayList.get(index));
                shoesArrayList.remove(index);
                if(shoesArrayList.size()>0) {
                    OrdersHistory.btn_deleteAll.setEnabled(true);
                }
                else
                    OrdersHistory.btn_deleteAll.setEnabled(false);
                notifyDataSetChanged();
            }
        });

        return v;
    }
}
