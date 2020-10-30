package com.example.mygoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn_all,btn_add,btn_history;
    MyDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_add = findViewById(R.id.main_btn_add);
        btn_all = findViewById(R.id.main_btn_all_model);
        btn_history = findViewById(R.id.main_btn_history);

        //random data to test
        Shoes shoes1 = new Shoes("nike",42,"red",12,2150);
        Shoes shoes2 = new Shoes("adidas",44,"black",8,3400);
        Shoes shoes3 = new Shoes("nike",41,"red",4,2600);
        Shoes shoes4 = new Shoes("fila",43,"blue",18,1890);
        Shoes shoes5 = new Shoes("fila",42,"red",7,1800);
        Shoes shoes6 = new Shoes("nike",43,"black",15,1790);
        Shoes shoes7 = new Shoes("nike",44,"white",9,2150);
        Shoes shoes8 = new Shoes("adidas",42,"red",12,3250);
        Shoes shoes9 = new Shoes("adidas",45,"black",4,3500);
        Shoes shoes10 = new Shoes("fila",41,"red",14,1150);


        db = new MyDataBase(MainActivity.this);
        db.deleteAllShoes();
        db.insertShoes(shoes1);
        db.insertShoes(shoes2);
        db.insertShoes(shoes3);
        db.insertShoes(shoes4);
        db.insertShoes(shoes5);
        db.insertShoes(shoes6);
        db.insertShoes(shoes7);
        db.insertShoes(shoes8);
        db.insertShoes(shoes9);
        db.insertShoes(shoes10);


        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),All_model_Activity.class);
                startActivity(intent);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),Add_Activity.class);
                startActivity(intent);
            }
        });

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),OrdersHistory.class);
                startActivity(intent);
            }
        });

    }
}