package com.example.Bon_Appit_eat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddIngredientActivity extends RootActivity {
    private DatabaseReference databaseIngredient;
    private Button postButton;
    private EditText ingredientName;
    private Spinner ingredientType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);
        postButton = findViewById(R.id.btnPost);
        postButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ingredientName = findViewById(R.id.ingredientnom);
                // ingredientQuantity = view.findViewById(R.id.quantity);
                ingredientType = findViewById(R.id.spinnerIngredientType);
                String sIName = ingredientName.getEditableText().toString();
                // String sIQuantity=ingredientQuantity.getEditableText().toString();
                String sIType = ingredientType.getSelectedItem().toString();
                databaseIngredient = FirebaseDatabase.getInstance().getReference("Ingredient");
                String id = databaseIngredient.push().getKey();
                databaseIngredient.child(id).child("Name").setValue(sIName);
                // databaseIngredient.child(id).child("Quantity").setValue(sIQuantity);
                databaseIngredient.child(id).child("Type").setValue(sIType);
            }
        });
    }
}
