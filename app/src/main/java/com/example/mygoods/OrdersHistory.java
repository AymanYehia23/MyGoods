package com.example.mygoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class OrdersHistory extends AppCompatActivity {
    private ListView order_lv;
    private OrdersAdapter ordersAdapter;
    private ArrayList<Shoes>shoesArrayList = new ArrayList<>();
    private MyDataBase db = new MyDataBase(this);
    public static Button btn_deleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_history);
        order_lv = findViewById(R.id.orders_lv);
        btn_deleteAll = findViewById(R.id.orders_btn_deleteAll);
        shoesArrayList = db.getAllOrders();

        if(shoesArrayList.size()>0) {
            btn_deleteAll.setEnabled(true);
        }
        else
            btn_deleteAll.setEnabled(false);

        ordersAdapter = new OrdersAdapter(OrdersHistory.this,shoesArrayList);
        order_lv.setAdapter(ordersAdapter);

        btn_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shoesArrayList.size()>0) {
                    btn_deleteAll.setEnabled(true);
                    db.deleteAllOrders();
                    shoesArrayList.clear();
                    ordersAdapter.notifyDataSetChanged();
                }
                else
                    btn_deleteAll.setEnabled(false);
            }
        });
    }
}