package com.example.Bon_Appit_eat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ListFragment extends Fragment {

    public ListFragment(){
        //required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        final ListView listView = (ListView) view.findViewById(R.id.list_course_view);


        final ArrayList<String> list_ingredient = new ArrayList<>();
        //make request to the ddb and add it to the arraylist et concatenate avec la quantité

        list_ingredient.add("tomate");
        list_ingredient.add("fruit");
        list_ingredient.add("pattate");


        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                list_ingredient
        );


        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list_ingredient.remove(list_ingredient.get(position));

                ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        list_ingredient
                );
                listView.setAdapter(listViewAdapter);
            }
        });
        // Inflate the layout for this fragment

        return view;

    }
}
