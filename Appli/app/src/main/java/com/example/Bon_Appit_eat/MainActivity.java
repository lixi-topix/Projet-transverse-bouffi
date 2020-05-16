package com.example.Bon_Appit_eat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends RootActivity {

    private FrigoFragment frigoFragment;
    private ListFragment listFragment;
    private MenuFragment menuFragment;
    private RecetteActivity recetteActivity;
    private SettingsFragment settingsFragment;
    private IngredientFragment ingredientFragment;
    //---add
    private TextView textViewNameElement;
    private TextView textViewQuantityElement;
    private TextView textViewQtySpinner;
    private FloatingActionButton addButton;
    //later unit by menu_main

    private RecyclerView mRecipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        frigoFragment = new FrigoFragment();
        listFragment = new ListFragment();
        menuFragment = new MenuFragment();
        ingredientFragment = new IngredientFragment();
        settingsFragment = new SettingsFragment();


        mRecipeList = findViewById(R.id.recipe_list);
        addButton = findViewById(R.id.fab);

        mRecipeList.setHasFixedSize(true);
        mRecipeList.setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUIConnected(new Intent(getApplicationContext(), TestActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Recipe, >

        updateUIConnected(null);
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name) {
            TextView recipeName = mView.findViewById(R.id.recipe_name);
            recipeName.setText(name);
        }

        public void setDesc(String desc) {
            TextView recipeDesc = mView.findViewById(R.id.recipe_desc);
            recipeDesc.setText(desc);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_Frigo:
                //InitializeFragments(frigoFragment);
                return true;
            case R.id.navigation_Liste:
                //InitializeFragments(listFragment);
                updateUIConnected(new Intent(getApplicationContext(), ListActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.navigation_Menu:
                //InitializeFragments(menuFragment);
                return true;
            case R.id.navigation_Recette:
                updateUIConnected(new Intent(getApplicationContext(), RecetteActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.navigation_Settings:
                //InitializeFragments(settingsFragment);
                return true;
            case R.id.navigation_ajouterIngrédient:
                //InitializeFragments(ingredientFragment);
                return true;
            case R.id.test:
                updateUIConnected(new Intent(getApplicationContext(), TestActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.profileActivity:
                updateUIConnected(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.logout:
                mAuth.signOut();
                updateUIConnected(null);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
