package com.example.Bon_Appit_eat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class RecetteActivity extends RootActivity implements AddToRecetteDialogue.AddIngredientListener {

    private View button;
    private DatabaseReference mDatabase;
    private LinearLayout ll;
    private ScrollView sv;
    private GridLayout newIngredient;
    private EditText rDescription;
    private Button rPost;
    private Button mAdd;
    private TextView textViewNameElement;
    private TextView textViewQuantityElement;
    private TextView textViewQtySpinner;
    private TextView mIngredient;
    private ArrayList<String> ingredientIDList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittext);
        Log.d(TAG, "onCreate: PROUTTT");

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mAdd = findViewById(R.id.btningrédientPlus);
        mIngredient = findViewById(R.id.ingrédientRecette);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        //METTRE IMAGE ICI

/*


        setContentView(R.layout.edittext);
        newIngredient = this.findViewById(R.id.gridingrédient) ;
        if(newIngredient.getParent() != null) {
            ((ViewGroup)newIngredient.getParent()).removeView(newIngredient); // <- fix
        }
        View button =newIngredient.getChildAt(2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Button");
                newIngredient.removeViewAt(2);

            }
        });
*/

    }

    public void openDialog() {
        AddToRecetteDialogue dialog = new AddToRecetteDialogue();
        dialog.show(getSupportFragmentManager(), "add ingredient");
    }


    private View addIngredient() {
        newIngredient = findViewById(R.id.gridingrédient);

        if (newIngredient.getParent() != null) {
            ((ViewGroup) newIngredient.getParent()).removeView(newIngredient); // <- fix
        }


        View button = newIngredient.getChildAt(2);
        if (ll.getChildCount() == 2) {
            Log.d(TAG, "DANS LE IF ");
            newIngredient.removeViewAt(0);
            newIngredient.removeViewAt(0);
            newIngredient.removeViewAt(0);
            button = newIngredient.getChildAt(0);
        } else {
            button = newIngredient.getChildAt(3);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToRecetteDialogue addToRecetteDialogue = new AddToRecetteDialogue();
                addToRecetteDialogue.show(getSupportFragmentManager(), "element dialog");
                if (newIngredient.getChildCount() == 1) {
                    newIngredient.removeViewAt(0);
                } else {
                    newIngredient.removeViewAt(3);
                }
                ll.addView(addIngredient(), ll.getChildCount() - 2);
                setContentView(sv);
            }
        });

        return newIngredient;
    }

    @Override
    public void applyText(String ingredient) {
        mIngredient.setText(ingredient);
    }

    public void getIngredientId(String ingredientID) {
        ingredientIDList.add(ingredientID);
    }
}
