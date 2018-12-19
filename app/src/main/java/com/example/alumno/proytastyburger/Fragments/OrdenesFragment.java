package com.example.alumno.proytastyburger.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alumno.proytastyburger.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdenesFragment extends Fragment {


    public OrdenesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ordenes, container, false);
    }

}
