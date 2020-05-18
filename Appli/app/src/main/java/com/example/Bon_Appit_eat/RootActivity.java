package com.example.Bon_Appit_eat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public abstract class RootActivity extends AppCompatActivity {
    protected FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    protected void updateUIConnected(Intent intent) {
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else {
            if (intent != null) {
                startActivity(intent);
            }
        }
    }

    protected void updateUINotConnected(Intent intent) {
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            if (intent != null) {
                startActivity(intent);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mAuth != null) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_Frigo:
                updateUIConnected(new Intent(getApplicationContext(), FrigoActivity.class));
                return true;
            case R.id.navigation_Liste:
                updateUIConnected(new Intent(getApplicationContext(), ListActivity.class));
                return true;
            case R.id.navigation_create_recipe:
                updateUIConnected(new Intent(getApplicationContext(), CreateRecipeActivity.class));
                return true;
            case R.id.navigation_ajouterIngrédient:
                updateUIConnected(new Intent(getApplicationContext(), AddIngredientActivity.class));
                return true;
            case R.id.logout:
                mAuth.signOut();
                updateUIConnected(null);
                finish();
                return true;
            case R.id.navigation_all_recipes:
                updateUIConnected(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
