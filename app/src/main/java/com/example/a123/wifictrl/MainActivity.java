package com.example.a123.wifictrl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dlong.dl10netassistant.OnNetThreadListener;
import com.dlong.dl10netassistant.TcpClientThread;
import com.example.a123.wifictrl.fragment.Fragment_four;
import com.example.a123.wifictrl.fragment.Fragment_one;
import com.example.a123.wifictrl.fragment.Fragment_three;
import com.example.a123.wifictrl.fragment.Fragment_two;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnNetThreadListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private List<Entry> entries;
    LineChart lineChart;
    TextView txv_yhm;

    TextView textView4;
    //实例化4个fragment
    private Fragment_one fragment_one;
    private Fragment_two fragment_two;
    private Fragment_three fragment_three;
    private Fragment_four fragment_four;
    private FragmentManager fragmentManager;
    EditText edt_ip, edt_dk;
    Button btn_tcp;
    Switch jd1,jd2,kt,fs,ml,dg;//开关变量
    Switch kting,ct1,xsj,ys,zw,cw;
    String receive_two;



    static byte[]  a = new byte[0];
    TcpClientThread tcp;   //创建TCP连接

    //底部按钮点击监听，点击后实现页面显示
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_hj:
                    setNavigationItemSelection(0);
                    return true;
                case R.id.navigation_cs:
                    setNavigationItemSelection(1);
                    return true;
                case R.id.navigation_dk:
                    setNavigationItemSelection(2);
                    return true;

            }
            return false;
        }

    };
    @Override
    protected void onStart() {
        super.onStart();
        jd1=fragment_two.getView().findViewById(R.id.jd1);
        jd2=fragment_two.getView().findViewById(R.id.jd2);
        kt=fragment_two.getView().findViewById(R.id.kt);
        fs=fragment_two.getView().findViewById(R.id.fs);
        ml=fragment_two.getView().findViewById(R.id.ml);
        dg=fragment_two.getView().findViewById(R.id.dg);
        //
        kting=fragment_two.getView().findViewById(R.id.switch1);
        ct1=fragment_two.getView().findViewById(R.id.switch3);
        xsj=fragment_two.getView().findViewById(R.id.switch4);
        ys=fragment_two.getView().findViewById(R.id.switch5);
        zw=fragment_two.getView().findViewById(R.id.switch6);
        cw=fragment_two.getView().findViewById(R.id.switch7);
        //
        lineChart=fragment_one.getView().findViewById(R.id.dy_line_chart1);
        //
        btn_tcp = fragment_three.getView().findViewById(R.id.btn_tcp);
        edt_ip = fragment_three.getView().findViewById(R.id.edt_ip);
        edt_dk = fragment_three.getView().findViewById(R.id.edt_dk);

        //
        jd1.setOnCheckedChangeListener(this);
        jd2.setOnCheckedChangeListener(this);
        kt.setOnCheckedChangeListener(this);
        fs.setOnCheckedChangeListener(this);
        ml.setOnCheckedChangeListener(this);
        dg.setOnCheckedChangeListener(this);
        //
        btn_tcp.setOnClickListener(this);
        //
        kting.setOnCheckedChangeListener(this);
        ct1.setOnCheckedChangeListener(this);
        xsj.setOnCheckedChangeListener(this);
        ys.setOnCheckedChangeListener(this);
        zw.setOnCheckedChangeListener(this);
        cw.setOnCheckedChangeListener(this);
        //setLineChart();

        ///////

        try {
            Thread.sleep(800);
            // 延时2秒
            setLineChart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();
        //设置默认显示页面

        setNavigationItemSelection(2);
        setNavigationItemSelection(1);
        setNavigationItemSelection(0);

        initView();
        Intent it = getIntent();
        String yonghuming = it.getStringExtra("username");
        txv_yhm.setText(yonghuming + "のHOME");
        ////
        HeConfig.init("HE2307061322011906", "e0e81b790f794cb0af6e0649ed8d9c2d");
        HeConfig.switchToDevService();
        entries=new ArrayList<>();
        QWeather.getWeather7D(this, "101030500", new QWeather.OnResultWeatherDailyListener() {
            @Override
            public void onError(Throwable throwable) {
                // 查询失败，处理错误情况
                //   Log.e(TAG, "Failed to get weather data: " + throwable.getMessage());
                                for(int i=0;i<7;i++){
                                    entries.add(new Entry(i,new Random().nextInt(5)+30));
                                }
            }
            @Override
            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                // 获取到天气预报数据，进行处理
                List<WeatherDailyBean.DailyBean> dailyList = weatherDailyBean.getDaily();
                // 在这里使用获取到的天气数据进行展示或其他操作
                // ...3
                int i1=0;
                if(Code.OK == weatherDailyBean.getCode()){
                    for (WeatherDailyBean.DailyBean item: dailyList
                    ) {

                        String a=item.getTempMax();
                        entries.add(new Entry(i1,Float.parseFloat(a)));
                        i1++;
                    }
                }else{
                    for(int i=0;i<7;i++){
                        entries.add(new Entry(i,new Random().nextInt(5)+30));
                    }
                }

            }
        });
    }


    //初始化控件
    private void initView()
    {

                    txv_yhm= (TextView) findViewById(R.id.txv_yhm);
                }

                //页面显示选择显示
                private void setNavigationItemSelection(int index) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                hideFragment(transaction);
                switch (index) {
                    case 0:
                        if (fragment_one == null) {
                            fragment_one = new Fragment_one();
                            transaction.add(R.id.content, fragment_one);
                        } else {
                            transaction.show(fragment_one);
                        }
                        //TODO setFragmentOneTextView()此方法测试 可删除
                        setFragmentOneTextView();
                        break;
                    case 1:
                        if (fragment_two == null) {
                            fragment_two = new Fragment_two();
                            transaction.add(R.id.content, fragment_two);
                } else {
                    transaction.show(fragment_two);
                }
                break;
            case 2:
                if (fragment_three == null) {
                    fragment_three = new Fragment_three();
                    transaction.add(R.id.content, fragment_three);
                } else {
                    transaction.show(fragment_three);
                }
                break;
            case 3:
                if (fragment_four == null) {
                    fragment_four = new Fragment_four();
                    transaction.add(R.id.content, fragment_four);
                } else {
                    transaction.show(fragment_four);
                }
                break;
        }
        transaction.commit();
    }

    //隐藏不需要的页面
    private void hideFragment(FragmentTransaction transaction) {
        if (fragment_one != null) {
            transaction.hide(fragment_one);
        }
        if (fragment_two != null) {
            transaction.hide(fragment_two);
        }
        if (fragment_three != null) {
            transaction.hide(fragment_three);
        }
        if (fragment_four != null) {
            transaction.hide(fragment_four);
        }

    }


    public void send(View v)  //发送TCP内容
    {
        tcp.send("A" .getBytes());
    }

    public void senda(View v)  //发送TCP内容
    {
        tcp.send("B" .getBytes());

    }

    //TODO 这个方法控制Fragment里面的控件 ，使用fragment_one举例
    private void setFragmentOneTextView(){
        //最好加个判断以防程序崩溃
        if(fragment_one!=null){

        }
        if(fragment_four!=null)
        {
            textView4= (TextView) fragment_four.getView().findViewById(R.id.textView4);
            textView4.setText("测试");
        }

    }

    @Override
    public void onAcceptSocket(@NotNull String s) {

    }

    @Override
    public void onConnectFailed(@NotNull String s) {

    }

    @Override
    public void onConnected(@NotNull String s) {

    }

    @Override
    public void onDisconnect(@NotNull String s) {

    }

    @Override
    public void onError(@NotNull String s, @NotNull String s1) {

    }

    @Override
    public void onReceive(@NotNull String s, int i, long l, @NotNull byte[] bytes) {

        //Log.d("onReceive",new String(bytes));
        a=bytes;
        Log.d("onReceive",new String(a));
    }
    public static byte[] send_data()   //发送数据的方法
    {
        return a;
    }

    //switch监听事件 控制开关
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//      String str[]={"0J1","0J2","0J3","0J4","0J5","0J6"} ;
//           char sda[] = str[0].toCharArray();
//            sda[sda.length - 1] = 'b';
//            str[0] = new String(sda);

        switch (compoundButton.getId())
        {
            case R.id.jd1:
            {
                if(b)
                {tcp.send("J1".getBytes());     //打开继电器1 2
                    //Log.d("开关","开");
                }
                else
                {
                    tcp.send("J2" .getBytes());  //关闭继电器1 2
                    Log.d("开关","关");
                }
            }break;
            case R.id.jd2:
            {
                if(b)
                {  tcp.send("J3" .getBytes());//
                    //Log.d("开关","开");
                }
                else
                {
                    tcp.send("J4" .getBytes());
                    //vLog.d("开关","关");
                }
            }break;
            case R.id.kt:
            {
                if(b)
                {tcp.send("F1" .getBytes());        //打开风扇1
                    //Log.d("开关","开");
                }
                else
                {
                    tcp.send("F2" .getBytes());     //关闭风扇1
                    //Log.d("开关","关");
                }
            }break;
            case R.id.fs:
            {
                if(b)
                {tcp.send("F3" .getBytes());         //打开风扇2
                    //Log.d("开关","开");
                }
                else
                {
                    tcp.send("F4" .getBytes());     //关闭风扇2
                    //Log.d("开关","关");
                }
            }break;
            case R.id.ml:
            {
                if(b)
                {tcp.send("L3" .getBytes());         //打开蜂鸣器
                    //Log.d("开关","开");
                }
                else
                {
                    tcp.send("L4" .getBytes());      //关闭蜂鸣器
                    //Log.d("开关","关");
                }
            }break;
            case R.id.dg:
            {
                if(b)
                {tcp.send("L1" .getBytes());     //打开LED灯
                    //Log.d("开关","开");
                }
                else
                {
                    tcp.send("L2" .getBytes());  //关闭LED灯
                    //Log.d("开关","关");
                }
            }break;
            //////////
            case R.id.switch1:
            {
                if(b)
                {
                    tcp.send("2".getBytes());  //连接客厅ip

                }
                else{
                    tcp.send("1".getBytes()); //关闭客厅ip

                }
            }break;
            case R.id.switch3:
            {
                if(b)
                {
                    tcp.send("3".getBytes());  //连接餐厅ip
                }
                else{
                    tcp.send("1".getBytes()); //关闭餐厅ip
                }
            }break;
            case R.id.switch4:
            {
                if(b)
                {
                    tcp.send("4".getBytes());  //连接洗手间ip
                }
                else{
                    tcp.send("1".getBytes()); //关闭洗手间ip
                }
            }break;
            case R.id.switch5:
            {
                if(b)
                {
                    tcp.send("5".getBytes());  //连接浴室ip
                }
                else{
                    tcp.send("1".getBytes()); //关闭浴室ip
                }
            }break;
            case R.id.switch6:
            {
                if(b)
                {
                    tcp.send("6".getBytes());  //连接主卧ip
                }
                else{
                    tcp.send("1".getBytes()); //关闭主卧ip
                }
            }break;
            case R.id.switch7:
            {
                if(b)
                {
                    tcp.send("7".getBytes());  //连接次卧ip
                }
                else{
                    tcp.send("1".getBytes()); //关闭次卧ip
                }break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        String text_ip = edt_ip.getText().toString();
        if(text_ip==null){
            Toast.makeText(getApplicationContext(), "连接失败!", Toast.LENGTH_SHORT).show();
        }else {
            tcp = new TcpClientThread(text_ip + "",Integer.parseInt(edt_dk.getText().toString()), this);
            Toast.makeText(getApplicationContext(), "连接成功!", Toast.LENGTH_SHORT).show();
            tcp.start();
        }
    }
    ///////折线图

    private void   setLineChart(){
//        for(int i=0;i<7;i++){
//            entries.add(new Entry(i,i*3000));
//        }

        //这行代码将布局文件中的 LineChart 组件与代码中的 LineChart 对象关联起来
        lineChart=(LineChart) findViewById(R.id.dy_line_chart1);
//      这行代码创建了一个 LineDataSet 对象，用于存储折线图的数据。
//      entries 是一个包含数据点的列表，这里可能是从其他地方获取的实际数据。
//       "数据" 是这条线的标签。
        LineDataSet dataSet=new LineDataSet(entries,"未来7天最高温度显示");
        //这行代码设置折线的颜色
        dataSet.setColor(Color.parseColor("#000000"));
        //这行代码设置折线上数据点的颜色为黑色
        dataSet.setCircleColor(Color.parseColor("#000000"));
        //这行代码设置折线上数据点的半径为6个单位（单位可以是像素或其他）。
        dataSet.setCircleRadius(6f);
        //这行代码设置折线的宽度为1.5个单位。
        dataSet.setLineWidth(1.5f);
        //这行代码设置显示在数据点上的值的文本颜色为黑色。
        dataSet.setValueTextColor(Color.parseColor("#000000"));
        //这行代码设置显示在数据点上的值的文本大小为17个单位。
        dataSet.setValueTextSize(15f);
        //这行代码获取右侧的 Y 轴，并将其赋值给 rightAxis 变量
        YAxis rightAxis =lineChart.getAxisRight();
        //这行代码禁用右侧的 Y 轴
        rightAxis.setEnabled(false);
        //这行代码获取 X 轴，并将其赋值给 xAxis 变量。
        XAxis xAxis=lineChart.getXAxis();
        //这行代码设置 X 轴文本的颜色为红色。
        xAxis.setTextColor(Color.BLUE);
        //这行代码设置 X 轴文本的大小为15个单位。
        xAxis.setTextSize(15f);
        //这行代码设置是否绘制 X 轴的轴线。
        xAxis.setDrawAxisLine(true);
        //这行代码设置是否绘制 X 轴的网格线。
        xAxis.setDrawGridLines(true);
        //这行代码设置是否绘制 X 轴的标签
        xAxis.setDrawLabels(true);
        //这行代码设置 X 轴的位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        Date currentDate=new Date();
        DateFormat format=new SimpleDateFormat("dd");
        String[] datalist=new String[7];
        List<String> dataList=new  ArrayList<String>();
        for (int i=1;i<=7;i++){
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DATE,+i);
            Date date=calendar.getTime();
            String dateString =format.format(date);
            datalist[i-1]=dateString;
        }


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                return String.valueOf(datalist[(int) v]).concat("日");
            }
        });
        //这行代码获取折线图的图例(Legend)对象，并将其赋值给 legend 变量
        Legend legend=lineChart.getLegend();
        //这行代码设置图例的显示形式为线条。
        legend.setForm(Legend.LegendForm.LINE);
        //这行代码设置图例文本的大小为15个单位。
        legend.setTextSize(15f);
        //这行代码设置图例图形的大小为15个单位。
        legend.setFormSize(15f);
        //这行代码设置图例文本的颜色为蓝色。
        legend.setTextColor(Color.BLACK);
        //这行代码获取折线图的描述(Description)对象，并将其赋值给 description 变量。
        Description description=lineChart.getDescription();
        //这行代码禁用折线图的描述
        description.setEnabled(false);
        //这行代码创建一个 LineData 对象，用于存储折线图的数据。
        //dataSet 是之前创建的 LineDataSet 对象。
        LineData lineData=new LineData(dataSet);
        //这行代码禁用折线图的触摸功能，即禁止用户与图表进行交互。
        lineChart.setTouchEnabled(false);
        //这行代码将 LineData 对象设置为折线图的数据源。
        lineChart.setData(lineData);
        lineChart.invalidate();

    }

}
