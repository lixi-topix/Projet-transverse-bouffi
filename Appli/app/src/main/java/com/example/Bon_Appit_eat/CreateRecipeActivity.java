package com.example.Bon_Appit_eat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;


public class CreateRecipeActivity extends RootActivity implements AddToRecetteDialogue.AddIngredientListener {

    private View button;
    private DatabaseReference mDatabase;
    private LinearLayout ll;
    private ScrollView sv;
    private GridLayout newIngredient;
    private EditText rDescription;
    private Button rPost;
    private ArrayList<String> ingredientIDList;
    private ArrayList<String> ingredientQuantity;
    private static final int GALLERY_INTENT = 1;
    private ImageButton imageButton;
    private Uri mResultUri;
    private Uri mDownloadUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ingredientIDList = new ArrayList<>();
        ingredientQuantity = new ArrayList<>();
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

        setContentView(R.layout.activity_create_recipe);

        imageButton = this.findViewById(R.id.btnAddImageRecipe);
        if (imageButton.getParent() != null) {
            ((ViewGroup) imageButton.getParent()).removeView(imageButton);
        }
        ll.addView(imageButton);


        //METTRE IMAGE ICI

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        ll.addView(addIngredient());



        rDescription = this.findViewById(R.id.descriptionsRecette);
        if (rDescription.getParent() != null) {
            ((ViewGroup) rDescription.getParent()).removeView(rDescription);
        }
        ll.addView(rDescription);
        rPost = this.findViewById(R.id.btnPostRecette);
        if (rPost.getParent() != null) {
            ((ViewGroup) rPost.getParent()).removeView(rPost);
        }
        ll.addView(rPost);
        setContentView(sv);

        rPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference newPost = FirebaseDatabase.getInstance().getReference().child("Receipes");
                String id = newPost.push().getKey();

                uploadImage(FirebaseStorage.getInstance().getReference().child("Recipes").child(id));

                Recipe recettesPost = new Recipe();
                EditText tempText = (EditText) ll.getChildAt(1);
                recettesPost.setName(tempText.getText().toString().trim());
                tempText = (EditText) ll.getChildAt(ll.getChildCount() - 2);
                recettesPost.setDescription(tempText.getText().toString().trim());
                for (int i = 4; i < ll.getChildCount() - 2; i++) {
                    Log.d(TAG, "i=" + i);
                    GridLayout tempG = (GridLayout) ll.getChildAt(i);
                    tempText = (EditText) tempG.getChildAt(1);
                    ingredientQuantity.add(tempText.getText().toString().trim());
                }
                recettesPost.setIngredientQuantity(ingredientQuantity);
                recettesPost.setIngredient(ingredientIDList);
                recettesPost.setUrl(mDownloadUri.toString().trim());


                newPost.child(id).setValue(recettesPost);



                updateUIConnected(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    private void uploadImage(final StorageReference ref) {
        if (mResultUri != null) {
            ref.putFile(mResultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        mDownloadUri = task.getResult();
                    }
                }
            });
        } else {
            Bitmap bitmap = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            ref.putBytes(data).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw Objects.requireNonNull(task.getException());
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        mDownloadUri = task.getResult();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            assert data != null;
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                mResultUri = result.getUri();

                imageButton.setImageURI(mResultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
            }
        }
    }


    private View addIngredient() {
        setContentView(R.layout.activity_create_recipe);
        newIngredient = this.findViewById(R.id.gridingrédient);
        if (newIngredient.getParent() != null) {
            ((ViewGroup) newIngredient.getParent()).removeView(newIngredient); // <- fix
        }

        if (ll.getChildCount() == 3) {
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
    public void applyText(String ingredientID) {
        ingredientIDList.add(ingredientID);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ingredient").child(ingredientID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Ingredient tempIng = dataSnapshot.getValue(Ingredient.class);
                GridLayout temporary = (GridLayout) ll.getChildAt(ll.getChildCount() - 3);
                TextView text = (TextView) temporary.getChildAt(0);
                text.setText(tempIng.getName());
                text = (TextView) temporary.getChildAt(2);
                text.setText(tempIng.getType());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
