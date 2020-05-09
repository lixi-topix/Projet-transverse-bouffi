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
    Button PushToFrigo;
    Button AddNewListElement;
    ArrayList<Listcourse_Element> productBuy = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_listviewcourse);
        setallTheNeededElement();
        System.out.println(listcourse_Element.size());
        listView =  findViewById(R.id.customListView);
        listAdapter = new Listadapter_course(this,listcourse_Element);
        listView.setAdapter(listAdapter);
        PushToFrigo = findViewById(R.id.PushToFrigo);
        PushToFrigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushToFrigo();
            }
        });
        //TO DO : button add an element to list course
        //gérer l'écriture dans l'edit text

    }

    private void PushToFrigo()
    {
        productBuy.clear();
        for(int i=0;i<  listcourse_Element.size();i++)
        {
            if(listcourse_Element.get(i).CartQuantity > 0)
            {
                Listcourse_Element ingredients = new Listcourse_Element(
                        listcourse_Element.get(i).Ingredient_name
                        ,listcourse_Element.get(i).Qty_needed
                        ,listcourse_Element.get(i).Ingredient_img
                        ,listcourse_Element.get(i).CartQuantity
                );
                ingredients.CartQuantity = listcourse_Element.get(i).CartQuantity;
                productBuy.add(ingredients);
            }
        }
    }

    public void setallTheNeededElement() {

        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));
        listcourse_Element.add(new Listcourse_Element("farine","100g", 1,0));


    }

}
