package com.example.a123.wifictrl.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a123.wifictrl.MainActivity;
import com.example.a123.wifictrl.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_three extends Fragment {

    private View rootView;
    public Fragment_three() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_fragment_three, container, false);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }
    private void initView() {
        //btn_kd= (Button) rootView.findViewById(R.id.btn_kd);
    }

}
