package com.example.Bon_Appit_eat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class ListActivity extends RootActivity implements DialogueElement.DialogueElementListener {

    private ListView listView;
    private ListAdapter listAdapter;
    ArrayList<Listcourse_Element> listcourse_Element = new ArrayList<>();
    Button PushToFrigo;
    Button AddNewListElement;
    ArrayList<Listcourse_Element> productBuy = new ArrayList<>();
    private DatabaseReference databaseReference;
    private DatabaseReference FrigoIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_listviewcourse);
        setallTheNeededElement();
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
        AddNewListElement = findViewById(R.id.AddNewListElement);
        AddNewListElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        //gérer l'écriture dans l'edit text
    }

    private void PushToFrigo()
    {
        //gérer la duplication et le cumule des aliments.
        //FrigoIngredient = FirebaseDatabase.getInstance().getReference("Frigo/"+mAuth.getCurrentUser().getUid()+"Ingredient");
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
                databaseReference
                    .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                    .child("Frigo")
                    .child(ingredients.Ingredient_name)
                    .setValue(ingredients.CartQuantity);
            }
        }

    }

    public void setallTheNeededElement() {
        //TODO:  requete dans les recettes et selections des plats
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

    // open our dialog box
    public void openDialog(){
        DialogueElement dialogueElement = new DialogueElement();
        dialogueElement.show(getSupportFragmentManager(), "element dialog");
    }


    public void Creation_new_element(String Name_new_element, String Quantity_new_element, String Quantity_qualifier) {

        listcourse_Element.add(new Listcourse_Element(Name_new_element,Quantity_new_element+""+Quantity_qualifier, 1,0));
        listAdapter = new Listadapter_course(this,listcourse_Element);
        listView.setAdapter(listAdapter);
    }

}
