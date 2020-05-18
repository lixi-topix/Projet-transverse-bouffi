package com.example.Bon_Appit_eat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FrigoActivity extends RootActivity implements DialogueElement.DialogueElementListener {

    ArrayList<Listcourse_Element> listcourse_Element = new ArrayList<>();
    Button PushToFrigo;
    Button AddNewListElement;
    ArrayList<Listcourse_Element> productBuy = new ArrayList<>();
    DatabaseReference mDatabase;
    DatabaseReference rDatabase;
    DatabaseReference r2Database;
    List<String> mListIngredient;
    List<String> mListIdIngredient;
    List<Long> rListQtyIngredient;
    List<String> rListIdIngredient;
    List<String> mListTypeIngredient;
    List<String> rlistID;
    Context context = this;
    private ListView listView;
    private ListAdapter listAdapter;
    private DatabaseReference databaseReference;
    private DatabaseReference FrigoIngredient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setallTheNeededElement();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frigo);
        listView = findViewById(R.id.customListView);
        listAdapter = new Listadapter_course(this, listcourse_Element);
        listView.setAdapter(listAdapter);
        PushToFrigo = findViewById(R.id.updateFrigo);
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
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    private void PushToFrigo() {

        //gérer la duplication et le cumule des aliments.

        productBuy.clear();
        for (int i = 0; i < listcourse_Element.size(); i++) {
            if (listcourse_Element.get(i).CartQuantity > 0) {
                Listcourse_Element ingredients = new Listcourse_Element(
                        listcourse_Element.get(i).ID_ingrédient
                        , listcourse_Element.get(i).Ingredient_name
                        , listcourse_Element.get(i).Qty_needed
                        , listcourse_Element.get(i).Ingredient_img
                        , listcourse_Element.get(i).CartQuantity
                );

                if (0 != productBuy.size()) {
                    int sum = ingredients.CartQuantity;
                    boolean check = false;
                    for (Listcourse_Element element :
                            productBuy) {
                        if (element.Ingredient_name.equals(ingredients.Ingredient_name)) {
                            sum = sum + element.CartQuantity;
                            check = true;
                            element.setCartQuantity(sum);
                        }

                    }
                    // s'il n'existe pas déjà on l'ajoute
                    if (!check) {
                        productBuy.add(ingredients);
                    }
                    // si la liste est vide on l'ajoute
                } else {
                    productBuy.add(ingredients);
                }

            }


        }
        for (Listcourse_Element element :
                productBuy) {
            for (int j = 0; j < rListQtyIngredient.size(); j++) {
                if (element.ID_ingrédient.equals(rlistID.get(j))) {
                    if ((rListQtyIngredient.get(j) - element.CartQuantity) <= 0) {
                        System.out.println(listcourse_Element);
                        listcourse_Element.remove(element);
                        System.out.println(listcourse_Element);
                        databaseReference
                                .child("Frigo")
                                .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .child(element.ID_ingrédient)
                                .setValue(0);
                    } else {
                        databaseReference
                                .child("Frigo")
                                .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .child(element.ID_ingrédient)
                                .setValue(rListQtyIngredient.get(j) - element.CartQuantity);
                    }
                }

            }

        }

    }


    public void setallTheNeededElement() {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ingredient");
        mListIngredient = new ArrayList<>();
        mListIdIngredient = new ArrayList<>();
        mListTypeIngredient = new ArrayList<>();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Ingredient ingredient = ds.getValue(Ingredient.class);
                    assert ingredient != null;
                    mListIngredient.add(ingredient.getName());
                    mListIdIngredient.add(ds.getKey());
                    mListTypeIngredient.add(ingredient.getType());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mAuth = FirebaseAuth.getInstance();
        rDatabase = FirebaseDatabase.getInstance().getReference().child("Frigo").child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        rListQtyIngredient = new ArrayList<>();
        rlistID = new ArrayList<>();

        rDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rlistID.clear();
                rListQtyIngredient.clear();
                listcourse_Element.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    rlistID.add(ds.getKey());
                    rListQtyIngredient.add((Long) ds.getValue());
                }

                for (int i = 0; i < rListQtyIngredient.size(); i++) {
                    for (int j = 0; j < mListIdIngredient.size(); j++) {
                        if (mListIdIngredient.get(j).equals(rlistID.get(i)) && rListQtyIngredient.get(i) > 0) {
                            listcourse_Element.add(new Listcourse_Element(rlistID.get(i), mListIngredient.get(j), rListQtyIngredient.get(i) + mListTypeIngredient.get(j), 0, 0));
                        }
                    }

                }
                listAdapter = new Listadapter_course(context, listcourse_Element);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    // open our dialog box
    public void openDialog() {
        DialogueElement dialogueElement = new DialogueElement();
        dialogueElement.show(getSupportFragmentManager(), "element dialog");
    }


    public void Creation_new_element(String Name_new_element, String Quantity_new_element, String Quantity_qualifier) {

        for (int i = 0; i < mListIngredient.size(); i++) {

            if (mListIngredient.get(i).equals(Name_new_element)) {
                listcourse_Element.add(new Listcourse_Element(mListIdIngredient.get(i), Name_new_element, Quantity_new_element + "" + Quantity_qualifier, 1, 0));
                listAdapter = new Listadapter_course(this, listcourse_Element);
                listView.setAdapter(listAdapter);
            }
        }// else does not exist in the data base can't add it

    }
}
