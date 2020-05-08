package com.example.Bon_Appit_eat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListView listView;
    private ListAdapter listAdapter;
    ArrayList<Listcourse_Element> listcourse_Element = new ArrayList<>();
    Button btnPlaceOrder;
    ArrayList<Listcourse_Element> productBuy = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getProduct();
        listView =  findViewById(R.id.customListView);
        listAdapter = new Listadapter_course(this,listcourse_Element);
        listView.setAdapter(listAdapter);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
    }

    private void placeOrder()
    {
        productBuy.clear();
        for(int i=0;i<  listcourse_Element.size();i++)
        {
            if(listcourse_Element.get(i).CartQuantity > 0)
            {
                Listcourse_Element products = new Listcourse_Element(
                        listcourse_Element.get(i).Ingredient_name
                        ,listcourse_Element.get(i).Qty_needed
                        ,listcourse_Element.get(i).Ingredient_img
                        ,listcourse_Element.get(i).CartQuantity
                );
                products.CartQuantity = listcourse_Element.get(i).CartQuantity;
                productBuy.add(products);
            }
        }
    }

    public void getProduct() {


    }

}
