package com.example.Bon_Appit_eat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements DialogueElement.DialogueElementListener {

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
    private ImageButton addButton;
    //later unit by menu


    private FirebaseAuth mAuth;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        frigoFragment = new FrigoFragment();
        listFragment = new ListFragment();
        menuFragment = new MenuFragment();
        ingredientFragment = new IngredientFragment();
        settingsFragment = new SettingsFragment();

        //add
        textViewNameElement = findViewById(R.id.texttest1);
        textViewQuantityElement = findViewById(R.id.texttest2);
        textViewQtySpinner = findViewById(R.id.texttest3);
        addButton = findViewById(R.id.addButton);
        // click on button open a dialog
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        updateUI(mAuth.getCurrentUser());
    }

    // open our dialog box
    public void openDialog(){
        DialogueElement dialogueElement = new DialogueElement();
        dialogueElement.show(getSupportFragmentManager(), "element dialog");
    }

    @Override
    public void applyTexts(String Name_new_element, String Quantity_new_element, String Quantity_qualifier) {
        textViewNameElement.setText(Name_new_element);
        textViewQuantityElement.setText(Quantity_new_element);
        textViewQtySpinner.setText(Quantity_qualifier);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.navigation_Frigo:
                InitializeFragments(frigoFragment);
                return true;
            case R.id.navigation_Liste:
                InitializeFragments(listFragment);
                return true;
            case R.id.navigation_Menu:
                InitializeFragments(menuFragment);
                return true;
            case R.id.navigation_Recette:
                Log.d("prout", "test 1 ");
                Intent RecetteIntent = new Intent(getApplicationContext(), RecetteActivity.class);
                RecetteIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(RecetteIntent);

                Log.d("prout", "test 2 ");
                return true;
            case R.id.navigation_Settings:
                InitializeFragments(settingsFragment);
                return true;
            case R.id.navigation_ajouterIngrédient:
                InitializeFragments(ingredientFragment);
                return true;
            case R.id.test:
                startActivity(new Intent(getApplicationContext(), TestActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            case R.id.logout:
                mAuth.signOut();
                updateUI(mAuth.getCurrentUser());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void InitializeFragments(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save changes
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
        }
    }
}
