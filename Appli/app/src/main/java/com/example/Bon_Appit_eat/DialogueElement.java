package com.example.Bon_Appit_eat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogueElement extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText edit_textNameElement;
    private EditText edit_textQuantityElement;
    private DialogueElementListener listener;
    private Spinner spinner;
    private String text_spinner;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog , null);
        //view has the layout we build

        builder.setView(view)
                .setTitle("Add of a new element")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Name_new_element = edit_textNameElement.getText().toString();
                        String Quantity_new_element = edit_textQuantityElement.getText().toString();
                        //ajouter choix spinner
                        listener.applyTexts(Name_new_element,Quantity_new_element,text_spinner);
                    }
                });
        edit_textNameElement = view.findViewById(R.id.edit_name_element);
        edit_textQuantityElement = view.findViewById(R.id.edit_quantity_element);
        spinner = view.findViewById(R.id.edit_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( getActivity() ,  R.array.choose_your_quantity, android.R.layout.simple_spinner_item);
        String[] list = getResources().getStringArray(R.array.choose_your_quantity);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        return  builder.create();
    }

//context = "activity"
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogueElementListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement dialogue Element listener");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text_spinner = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface DialogueElementListener{
        void  applyTexts(String Name_new_element, String Quantity_new_element, String Quantity_qualifier );
    }

}
