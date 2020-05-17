package com.example.Bon_Appit_eat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

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
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter adapter;


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
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Receipes");

        mRecipeList.setHasFixedSize(true);
        mRecipeList.setLayoutManager(new LinearLayoutManager(this));
        fetch();

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
        adapter.startListening();
        updateUIConnected(null);
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Receipes");

        FirebaseRecyclerOptions<Recettes> options = new FirebaseRecyclerOptions.Builder<Recettes>()
                        .setQuery(query, new SnapshotParser<Recettes>() {
                            @NonNull
                            @Override
                            public Recettes parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return Objects.requireNonNull(snapshot.getValue(Recettes.class));
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Recettes, RecipeViewHolder>(options) {
            @NonNull
            @Override
            public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recipe_row, parent, false);

                return new RecipeViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position, @NonNull Recettes model) {
                holder.setName(model.getName());
                holder.setDesc(model.getDescription());

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        };
        mRecipeList.setAdapter(adapter);
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView recipeName;
        public TextView recipeDesc;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.recipe_linear);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeDesc = itemView.findViewById(R.id.recipe_desc);
        }

        public void setName(String name) {
            recipeName.setText(name);
        }

        public void setDesc(String desc) {
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
                updateUIConnected(new Intent(getApplicationContext(), FrigoActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
