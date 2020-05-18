package com.example.Bon_Appit_eat;

import android.net.Uri;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DetailsRecetteActivity extends RootActivity {
    private TextView mRecipeName;
    private TextView mRecipeDesc;
    private ImageView mRecipeImage;
    private String mRecipeKey;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recette_details);

        mRecipeName = findViewById(R.id.RecipeDetailsName);
        mRecipeDesc = findViewById(R.id.descriptionsRecette);
        mRecipeImage = findViewById(R.id.ReceipeDetailImage);

        mRecipeKey = Objects.requireNonNull(getIntent().getExtras()).getString("recipe_key");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Receipes").child(mRecipeKey);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRecipeName.setText((String) dataSnapshot.child("name").getValue());
                mRecipeDesc.setText((String) dataSnapshot.child("description").getValue());
                FirebaseStorage.getInstance().getReference().child("Recipes").child(mRecipeKey).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Picasso.with(getApplicationContext())
                                    .load(task.getResult())
                                    .into(mRecipeImage);
                        }
                    }
                });
                ArrayList<String> ingredients = (ArrayList<String>) dataSnapshot.child("ingredient").getValue();
                ArrayList<String> qtyIngredients = (ArrayList<String>) dataSnapshot.child("ingredientQuantity").getValue();
                /*LinearLayout gridLayout = ((LinearLayout) findViewById(R.id.linear)).getChildAt(((LinearLayout) findViewById(R.id.linear)).getChildCount() - 2);
                assert ingredients != null;
                for (int i = 0; i < ingredients.size(); i++) {
                    gridLayout.setRowCount(gridLayout.getRowCount() + 1);
                    TextView name = new TextView;
                    gridLayout.add
                }
                System.out.println(ingredients + "\n" + qtyIngredients);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
