package com.example.a123.wifictrl.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a123.wifictrl.MainActivity;
import com.example.a123.wifictrl.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_one extends Fragment {

    private View rootView;
    private Context mContext;
    Button jiazai;
    TextView wendu,shidu,co,pm,guangzhao,keranqiti,textView21;

    TextView text_data1,text_data2;

    byte[] receive = new byte[0];  //接收主类传来的数据
    String conduct;   //发送测试数据使用

    int i=0;
    String sd,c,gz;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_fragment_one, container, false);
            initView();

        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    //初始化控件
    private void initView() {
        this.mContext = getActivity();
        //txv_wd = (TextView) rootView.findViewById(R.id.txv_wd);

        //初始化
        wendu = rootView.findViewById(R.id.wendu);
        shidu = rootView.findViewById(R.id.shidu);
        co = rootView.findViewById(R.id.td);
        pm = rootView.findViewById(R.id.PM);
        guangzhao = rootView.findViewById(R.id.guangzhao);
        keranqiti= rootView.findViewById(R.id.keranqiti);
        text_data1=rootView.findViewById(R.id.text_data);
        text_data2=rootView.findViewById(R.id.text_data1);
        jiazai=rootView.findViewById(R.id.jiazai);
        textView21=rootView.findViewById(R.id.textView21);
        //定时器
        new CountDownTimer(500000, 1000) {
            @Override
            public void onTick(long l) {
                receive = MainActivity.send_data();
                conduct= new String(receive);
                String[] separated = conduct.split("[,]");
                ///


                //测试使用
                System.out.println(separated.length);
                if(separated.length>3) {
                    if (!separated[0].equals("")) {
//                        if(separated[0].equals("01"))
//                        {
//                            keranqiti.setText("主设备");
//                            textView21.setVisibility(View.VISIBLE);
//                            keranqiti.invalidate();
//                        }
//                        else {
//                            keranqiti.setText("设备1");
//                            textView21.setVisibility(View.VISIBLE);
//                            keranqiti.invalidate();
//
//                        }
                        switch (separated[0]){
                            case "007":
                                keranqiti.setText("客卧");
                                textView21.setVisibility(View.VISIBLE);
                                keranqiti.invalidate();
                                break;
                            case "002":
                                keranqiti.setText("客厅");
                                textView21.setVisibility(View.VISIBLE);
                                keranqiti.invalidate();
                                break;
                            case "003":
                                keranqiti.setText("餐厅");
                                textView21.setVisibility(View.VISIBLE);
                                keranqiti.invalidate();
                                break;
                            case "004":
                                keranqiti.setText("洗手间");
                                textView21.setVisibility(View.VISIBLE);
                                keranqiti.invalidate();
                                break;
                            case "005":
                                keranqiti.setText("浴室");
                                textView21.setVisibility(View.VISIBLE);
                                keranqiti.invalidate();
                                break;
                            case "006":
                                keranqiti.setText("主卧");
                                textView21.setVisibility(View.VISIBLE);
                                keranqiti.invalidate();
                                break;
                            default:
                                keranqiti.setText("主设备");
                                textView21.setVisibility(View.VISIBLE);
                                keranqiti.invalidate();
                                break;
                        }
                    }

                    if (!separated[1].equals("")) {
                        wendu.setText(separated[1]);
                        wendu.invalidate();

                    }
                    if (!separated[2].equals("")) {
                        shidu.setText(separated[2]);
                        shidu.invalidate();

                    }
                    if (!separated[3].equals("")) {
                        co.setText(separated[3]);
                        co.invalidate();

                    }
                    if (!separated[4].equals("")) {
                        guangzhao.setText(separated[4]);
                        guangzhao.invalidate();

                    }
                    if (!separated[5].equals("")) {
                        pm.invalidate();
                        pm.setText(separated[5]);

                    }
                    if (!separated[6].equals("")) {
                        if(separated[6].length()>1){
                            text_data1.invalidate();
                            text_data1.setText("有人来了！");
                        }else{
                            text_data1.invalidate();
                            text_data1.setText("无警告！");
                        }
                    }
                    if (!separated[7].equals("")) {
                        if(separated[7].length()>1){
                            text_data2.invalidate();
                            text_data2.setText("有人爬窗！");
                        }else{
                            text_data2.invalidate();
                            text_data2.setText("无警告！");
                        }
                    }
                }

             }

            @Override
            public void onFinish() {

            }
        }.start();

    }
}
