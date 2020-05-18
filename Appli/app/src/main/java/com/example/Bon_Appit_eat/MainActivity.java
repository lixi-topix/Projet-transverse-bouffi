package com.example.Bon_Appit_eat;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class MainActivity extends RootActivity {

    private RecyclerView mRecipeList;
    private FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipeList = findViewById(R.id.recipe_list);

        mRecipeList.setHasFixedSize(true);
        mRecipeList.setLayoutManager(new LinearLayoutManager(this));
        fetch();
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

        FirebaseRecyclerOptions<Recipe> options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(query, new SnapshotParser<Recipe>() {
                    @NonNull
                    @Override
                    public Recipe parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return Objects.requireNonNull(snapshot.getValue(Recipe.class));
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<Recipe, RecipeViewHolder>(options) {
            @NonNull
            @Override
            public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recipe_row, parent, false);

                return new RecipeViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position, @NonNull Recipe model) {
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
}
