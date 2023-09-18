package com.example.a123.wifictrl.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.dlong.dl10netassistant.OnNetThreadListener;
import com.dlong.dl10netassistant.TcpClientThread;
import com.example.a123.wifictrl.MainActivity;
import com.example.a123.wifictrl.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_two extends Fragment {


    private View rootView;

    public Fragment_two() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment_two, container, false);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_fragment_two, container, false);
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

//        tcp= new TcpClientThread("10.10.100.254",8899, (OnNetThreadListener) this);  //连接TCP
//        tcp.start();


    }


}
