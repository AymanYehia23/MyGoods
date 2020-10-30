package com.example.mygoods;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Locale;

public class MyAdapter extends BaseAdapter {
    private Context c;
    private ArrayList<Shoes>shoesArrayList;
    private Shoes shoes1;
    private ArrayList<Shoes>arrayList= new ArrayList<>();
    private ArrayList<Shoes>ordered_list= new ArrayList<>();


    public MyAdapter(Context context,ArrayList<Shoes> shoes) {
        this.c = context;
        this.shoesArrayList = shoes;
        arrayList.addAll(shoesArrayList);
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
        return shoes1.getId();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        final int index =i;

        if(v==null)
        {
            v = LayoutInflater.from(c).inflate(R.layout.info,null,false);
        }

        TextView tv_id = v.findViewById(R.id.info_tv_id);
        TextView tv_model = v.findViewById(R.id.info_tv_model);
        TextView tv_size = v.findViewById(R.id.info_tv_size);
        TextView tv_color = v.findViewById(R.id.info_tv_color);
        TextView tv_quantity = v.findViewById(R.id.info_tv_quantity);
        TextView tv_price = v.findViewById(R.id.info_tv_price);
        ImageView btn_minus = v.findViewById(R.id.info_btn_minus);
        ImageView btn_plus = v.findViewById(R.id.info_btn_plus);
        final Button btn_sale = v.findViewById(R.id.info_btn_sale);
        final CheckBox checkBox = v.findViewById(R.id.info_chBox);
        final EditText et_q = v.findViewById(R.id.info_et_q);

        shoes1 = getItem(i);


        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shoes shoes = getItem(index);
                MyDataBase db = new MyDataBase(c);
                int q = shoes.getQuantity();
                if(q>0){
                    q--;
                    db.updateShoes(shoes,q);
                    notifyDataSetChanged();
                }
                else if(q==0){
                    db.deleteShoes(shoes);
                    shoesArrayList.remove(index);
                    notifyDataSetChanged();
                }
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shoes shoes = getItem(index);
                MyDataBase db = new MyDataBase(c);
                int q = shoes.getQuantity();
                q++;
                db.updateShoes(shoes,q);
                notifyDataSetChanged();
            }
        });

        btn_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.getVisibility()==View.GONE) {
                    All_model_Activity.btn_confirm.setVisibility(View.VISIBLE);
                    checkBox.setVisibility(View.VISIBLE);
                    et_q.setVisibility(View.VISIBLE);
                }
                else {
                    checkBox.setVisibility(View.GONE);
                    et_q.setVisibility(View.GONE);
                }
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()) {
                    if(!(et_q.getText().toString()).equals("")&&((Integer.parseInt((et_q.getText().toString()))>0))) {
                        et_q.setEnabled(false);
                        int q = Integer.parseInt(et_q.getText().toString());
                        if (q <= shoesArrayList.get(index).getQuantity()) {
                            double p = q * (shoesArrayList.get(index).getPrice());
                            Shoes order_shoes = new Shoes(shoesArrayList.get(index).getId(), shoesArrayList.get(index).getModel(), shoesArrayList.get(index).getSize(),
                                    shoesArrayList.get(index).getColor(), q, p);
                            ordered_list.add(order_shoes);
                        }
                        else {
                            Toast.makeText(c, "This quantity is not available from this product", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(c, "You must set number", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    et_q.setEnabled(true);
                }

            }
        });

        All_model_Activity.btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ordered_list.size() > 0) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(c, R.style.myDialog));
                    alertDialog.setTitle("You have selected this: ");
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < ordered_list.size(); i++) {
                        sb.append((i + 1) + "- Quantity: " + ordered_list.get(i).getQuantity() + " of: " + ordered_list.get(i).getModel()
                                + " Size: " + ordered_list.get(i).getSize() + " Color: " + ordered_list.get(i).getColor() +
                                " Price: " + ordered_list.get(i).getPrice() + "\n");
                    }
                    alertDialog.setMessage(sb);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for (int j = 0; j < shoesArrayList.size(); j++) {
                                for (int k = 0; k < ordered_list.size(); k++) {
                                    if (shoesArrayList.get(j).getId() == ordered_list.get(k).getId()) {
                                        int q1 = shoesArrayList.get(j).getQuantity();
                                        int q2 = ordered_list.get(k).getQuantity();
                                        int q = q1 - q2;
                                        MyDataBase db = new MyDataBase(c);
                                        db.updateShoes(shoesArrayList.get(j), q);
                                        db.insertOrder(ordered_list.get(k));
                                    }
                                }
                            }
                            notifyDataSetChanged();

                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
                else
                {
                    Toast.makeText(c,"No products found",Toast.LENGTH_LONG).show();
                }
            }

        });


        tv_id.setText(shoes1.getId()+"");
        tv_model.setText(shoes1.getModel().toLowerCase(Locale.getDefault()));
        tv_size.setText(shoes1.getSize()+"");
        tv_color.setText(shoes1.getColor().toLowerCase(Locale.getDefault()));
        tv_quantity.setText(shoes1.getQuantity()+"");
        tv_price.setText(shoes1.getPrice()+"");


        return v;
    }

    public void filter (String CharText)
    {
        CharText = CharText.toLowerCase(Locale.getDefault());
        shoesArrayList.clear();
        if(CharText.length()==0)
        {
            shoesArrayList.addAll(arrayList);
        }
        else {
            for (Shoes shoes : arrayList)
            {
                if(shoes.getModel().toLowerCase(Locale.getDefault()).contains(CharText)){
                shoesArrayList.add(shoes);
                }
                else if(String.valueOf(shoes.getSize()).toLowerCase(Locale.getDefault()).contains(CharText)){
                    shoesArrayList.add(shoes);
                }
                else if(shoes.getColor().toLowerCase(Locale.getDefault()).contains(CharText)){
                    shoesArrayList.add(shoes);
                }

            }
        }
        notifyDataSetChanged();
    }

}
