package com.example.Bon_Appit_eat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements DialogueElement.DialogueElementListener {

    private FrigoFragment frigoFragment;
    private ListFragment listFragment ;
    private MenuFragment menuFragment ;
    private RecetteFragment recetteFragment ;
    private SettingsFragment settingsFragment ;
    //---add
    private TextView textViewNameElement;
    private TextView textViewQuantityElement;
    private TextView textViewQtySpinner;
    private ImageButton addButton;
    //later unit by menu
    DatabaseReference databaseReference;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("ingredients");

        frigoFragment = new FrigoFragment();
        listFragment = new ListFragment();
        menuFragment = new MenuFragment();
        recetteFragment = new RecetteFragment();
        settingsFragment = new SettingsFragment();

        //add
        textViewNameElement = (TextView) findViewById(R.id.texttest1);
        textViewQuantityElement = (TextView) findViewById(R.id.texttest2);
        textViewQtySpinner = (TextView) findViewById(R.id.texttest3);
        addButton = findViewById(R.id.addButton);
        // click on button open a dialog
        addButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openDialog();
            }
        });


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
                InitializeFragments(recetteFragment);
                return true;
            case R.id.navigation_Settings:
                InitializeFragments(settingsFragment);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
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

    /*public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }*/
}
