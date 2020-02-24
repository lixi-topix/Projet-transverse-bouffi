package com.example.Bon_Appit_eat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private FrigoFragment frigoFragment;
    private ListFragment listFragment ;
    private MenuFragment menuFragment ;
    private RecetteFragment recetteFragment ;
    private SettingsFragment settingsFragment ;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        frigoFragment = new FrigoFragment();
        listFragment = new ListFragment();
        menuFragment = new MenuFragment();
        recetteFragment = new RecetteFragment();
        settingsFragment = new SettingsFragment();
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InitializeFragments(frigoFragment);
            }
        });
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

        }
        return super.onOptionsItemSelected(item);

    }


    private void InitializeFragments(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save changes
    }


}
