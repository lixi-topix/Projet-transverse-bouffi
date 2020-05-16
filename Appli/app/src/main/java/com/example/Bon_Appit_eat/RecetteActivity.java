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

import androidx.appcompat.app.AppCompatActivity;

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
    private ArrayList<String> ingredientIDList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        sv = new ScrollView(this);
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        setContentView(sv);





        TextView tv = new TextView(this);
        tv.setText("We are already thrilled by your new recipe!");
        ll.addView(tv);

        EditText et = new EditText(this);
        et.setHint("Name of the recipe");
        ll.addView(et);



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

        ll.addView(addIngredient());


        setContentView(R.layout.edittext);
        rDescription = this.findViewById(R.id.descriptionsRecette) ;
        if(rDescription.getParent()!=null){
            ((ViewGroup)rDescription.getParent()).removeView(rDescription);
        }
        ll.addView(rDescription);
        rPost= this.findViewById(R.id.btnPostRecette) ;
        if(rPost.getParent()!=null){
            ((ViewGroup)rPost.getParent()).removeView(rPost);
        }
        ll.addView(rPost);
        setContentView(sv);

    }




    private View addIngredient(){
        setContentView(R.layout.edittext);
        newIngredient = this.findViewById(R.id.gridingrédient) ;
        if(newIngredient.getParent() != null) {
            ((ViewGroup)newIngredient.getParent()).removeView(newIngredient); // <- fix
        }

        if (ll.getChildCount()==2) {
            Log.d(TAG, "DANS LE IF ");
            newIngredient.removeViewAt(0);
            newIngredient.removeViewAt(0);
            newIngredient.removeViewAt(0);
            button =newIngredient.getChildAt(0);
        }else{
            button=newIngredient.getChildAt(3);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToRecetteDialogue addToRecetteDialogue = new AddToRecetteDialogue();
                addToRecetteDialogue.show(getSupportFragmentManager(), "element dialog");
                if (newIngredient.getChildCount()==1) {
                    newIngredient.removeViewAt(0);
                } else {
                    newIngredient.removeViewAt(3);
                }
                ll.addView(addIngredient(), ll.getChildCount()-2);
                setContentView(sv);
            }
        });

        return newIngredient;
    }

    @Override
    public void applyText(String ingredientID) {
        ingredientIDList.add(ingredientID);
    }
}
