package com.example.Bon_Appit_eat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class ListFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference ingredientReference;
    public ListFragment(){
        //required empty public constructor
    }
    Ingredient ingredient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        final ListView listView = (ListView) view.findViewById(R.id.list_course_view);
        final ArrayList<Ingredient> list_ingredient = new ArrayList<>();
        final ArrayList<String> list_nameIngredient = new ArrayList<>();
        //make request to the ddb and add it to the arraylist and concatenate with quantity

        //test request db


        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                list_nameIngredient
        );
        ingredient = new Ingredient();

        ingredientReference = FirebaseDatabase.getInstance().getReference("Ingredient");
        ingredientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    ingredient = ds.getValue(Ingredient.class);
                    list_ingredient.add(ingredient);
                    list_nameIngredient.add(ingredient.getName());
                }
                listView.setAdapter(listViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //add to database
                databaseReference
                        .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                        .child("Frigo")
                        .child(list_nameIngredient.get(position))
                        .setValue(list_ingredient.get(position).getType());

                list_nameIngredient.remove(list_nameIngredient.get(position));


                ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        list_nameIngredient
                );
                listView.setAdapter(listViewAdapter);
            }
        });
        // Inflate the layout for this fragment

        return view;

    }
}
