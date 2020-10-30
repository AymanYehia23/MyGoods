package com.example.mygoods;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class Add_Activity extends AppCompatActivity {

    EditText et_model,et_size,et_color,et_quantity,et_price;
    Button btn_add;
    MyDataBase db = new MyDataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_);

        et_model = findViewById(R.id.add_et_model);
        et_size = findViewById(R.id.add_et_size);
        et_color = findViewById(R.id.add_et_color);
        et_quantity = findViewById(R.id.add_et_quantity);
        et_price =findViewById(R.id.add_et_price);
        btn_add = findViewById(R.id.add_btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(et_model.getText().toString().equals("")) && !(et_size.getText().toString().equals(""))
                 && !(et_color.getText().toString().equals("")) && !(et_quantity.getText().toString().equals(""))
                 &&!(et_price.getText().toString().equals("")) && ((Integer.parseInt((et_price.getText().toString()))>0))
                 &&((Integer.parseInt((et_quantity.getText().toString()))>0)) &&((Integer.parseInt((et_size.getText().toString()))>0)))
                {
                    String model = et_model.getText().toString();
                    model.toLowerCase(Locale.getDefault());
                    int size = Integer.parseInt(et_size.getText().toString());
                    String color = et_color.getText().toString();
                    color.toLowerCase(Locale.getDefault());
                    int quantity = Integer.parseInt(et_quantity.getText().toString());
                    double price = Double.parseDouble(et_price.getText().toString());
                    Shoes shoes = new Shoes(model,size,color,quantity,price);
                    db.insertShoes(shoes);
                }
                else
                    {
                        Toast.makeText(Add_Activity.this,"Enter data correctly",Toast.LENGTH_LONG).show();
                    }
            }
        });
    }
}