package com.example.mygoods;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;


import java.util.ArrayList;

public class All_model_Activity extends AppCompatActivity {

    private ListView lv;
    private MyAdapter myAdapter;
    private MyDataBase db = new MyDataBase(this);
    private ArrayList<Shoes> shoes = new ArrayList<>();
    public static Button btn_confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_all_);
        lv = findViewById(R.id.all_model_lv);
        btn_confirm = findViewById(R.id.all_model_btn_confirm);
        shoes = db.getAllShoes();
        myAdapter = new MyAdapter(All_model_Activity.this,shoes);
        lv.setAdapter(myAdapter);

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query)
                    {
                        return false;
                    }


                    @Override
                    public boolean onQueryTextChange(String newText)
                    {
                        if(TextUtils.isEmpty(newText)){
                            myAdapter.filter("");
                            lv.clearTextFilter();
                        }
                        else {
                            myAdapter.filter(newText);
                        }
                        return true;
                    }
                });

        return true;
    }
}
