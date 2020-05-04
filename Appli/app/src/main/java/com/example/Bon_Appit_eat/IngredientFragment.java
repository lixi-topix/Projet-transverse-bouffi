package com.example.Bon_Appit_eat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IngredientFragment extends Fragment {
    private DatabaseReference databaseIngredient;
    private Button postButton;
    private EditText ingredientName;
    private EditText ingredientQuantity;
    private Spinner ingredientType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        postButton = view.findViewById(R.id.btnPost);
        postButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             Log.d("test","prout");   // Code here executes on main thread after user presses button
                ingredientName = view.findViewById(R.id.ingredientnom);
               // ingredientQuantity = view.findViewById(R.id.quantity);
                ingredientType = view.findViewById(R.id.spinnerIngredientType);
                String sIName=ingredientName.getEditableText().toString();
               // String sIQuantity=ingredientQuantity.getEditableText().toString();
                String sIType = ingredientType.getSelectedItem().toString();
                databaseIngredient =FirebaseDatabase.getInstance().getReference("Ingredient");
                String id = databaseIngredient.push().getKey();
                databaseIngredient.child(id).child("Name").setValue(sIName);
               // databaseIngredient.child(id).child("Quantity").setValue(sIQuantity);
                databaseIngredient.child(id).child("Type").setValue(sIType);


            }
        });
        return view;
    }
}
