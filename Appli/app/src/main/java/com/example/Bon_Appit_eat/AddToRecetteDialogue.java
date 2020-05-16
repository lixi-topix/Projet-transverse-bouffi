package com.example.Bon_Appit_eat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddToRecetteDialogue extends AppCompatDialogFragment {

    private AddIngredientListener listener;
    private DatabaseReference mDatabase;
    private List<String> mListIngredient;
    private List<String> mListIdIngredient;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ingredient");
        mListIngredient = new ArrayList<>();
        mListIdIngredient = new ArrayList<>();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Ingredient ingredient = ds.getValue(Ingredient.class);
                    assert ingredient != null;
                    mListIngredient.add(ingredient.getName());
                    mListIdIngredient.add(ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addtorecettedialogue, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, mListIngredient);
        final AutoCompleteTextView textView = view.findViewById(R.id.edit_name_element);
        textView.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Add ingredients")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ingredient = textView.getText().toString().trim();
                        String id = mListIdIngredient.get(mListIngredient.indexOf(ingredient));
                        //listener.applyText(id);
                    }
                });


        //editTextIngredient = view.findViewById(R.id.edit_name_element);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddIngredientListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddIngredientListener");
        }
    }

    public interface AddIngredientListener {
        void applyText(String ingredientID);
    }


}