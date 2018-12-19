package com.example.alumno.proytastyburger.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.alumno.proytastyburger.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    View view;
    ViewFlipper viewFlipper;


    public InicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inicio, container, false);
        int images[] = {R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};

        viewFlipper = view.findViewById(R.id.vflipper);

        //for(int i=0;i<images.length;i++){
         //   flipperImages(images[i]);
        //}

        for(int image : images){
            flipperImages(image);
        }


        return view;
    }

    public void flipperImages(int image){

        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);

    }

}
