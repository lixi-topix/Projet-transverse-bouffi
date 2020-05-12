package com.example.Bon_Appit_eat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.ArrayList;


public class Listadapter_course extends BaseAdapter {

    private ArrayList<Listcourse_Element> listelement;
    private Context context;

    Listadapter_course(Context context, ArrayList<Listcourse_Element> listelement) {
        this.context = context;
        this.listelement = listelement;
    }

    @Override
    public int getCount() {
        return listelement.size();
    }

    @Override
    public Listcourse_Element getItem(int position) {
        return (listelement.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView
            , ViewGroup parent) {
        View row;
        final ListCourseViewHolder listViewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_course_fragment, parent, false);
            listViewHolder = new ListCourseViewHolder();
            listViewHolder.img_ingredient = row.findViewById(R.id.img_ingredient);
            listViewHolder.ingredient_name = row.findViewById(R.id.ingredient_name);
            listViewHolder.ingredient_qty = row.findViewById(R.id.ingredient_qty_need);
            listViewHolder.ib_addnew = row.findViewById(R.id.ib_addnew);
            listViewHolder.editTextQuantity = row.findViewById(R.id.editTextQuantity);
            listViewHolder.ib_remove = row.findViewById(R.id.ib_remove);
            row.setTag(listViewHolder);
        } else {
            row = convertView;
            listViewHolder = (ListCourseViewHolder) row.getTag();
        }

        final Listcourse_Element listcourse_element = getItem(position);
        listViewHolder.ingredient_name.setText(listcourse_element.Ingredient_name);
        listViewHolder.img_ingredient.setImageResource(R.mipmap.default_icon);
        listViewHolder.ingredient_qty.setText(listcourse_element.Qty_needed + "");
        listViewHolder.editTextQuantity.setText(listcourse_element.CartQuantity + "");
        listViewHolder.ib_addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(position, listViewHolder.editTextQuantity, 1);
            }
        });

        listViewHolder.ib_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(position, listViewHolder.editTextQuantity, -1);

            }
        });

        listViewHolder.editTextQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final EditText Caption = (EditText) v;
                    listelement.get(position).setCartQuantity(Integer.parseInt(Caption.getText().toString()));
                }
            }
        });

        return row;
    }


    private void updateQuantity(int position, EditText edTextQuantity, int value) {
        Listcourse_Element listcourse_element = getItem(position);
        if (value > 0) {
            listcourse_element.CartQuantity = listcourse_element.CartQuantity + 1;
        } else {
            if (listcourse_element.CartQuantity > 0) {
                listcourse_element.CartQuantity = listcourse_element.CartQuantity - 1;
            }

        }
        edTextQuantity.setText(listcourse_element.CartQuantity + "");
    }


    public ArrayList<Listcourse_Element> getListelement() {
        return listelement;
    }

}
