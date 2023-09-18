package com.example.a123.wifictrl;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Button denglu;
    CheckBox checkBox;
    EditText edit_zh, edit_mm;

    SQLiteDatabase bd;      //建立数据库

    static final String db_name = "Users";//数据库名称
    static final String dt_name = "user";  //数据表名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        denglu= (android.widget.Button) findViewById(R.id.denglu);
        checkBox= (CheckBox) findViewById(R.id.checkBox);
        edit_zh = (EditText) findViewById(R.id.edit_zh);
        edit_mm = (EditText) findViewById(R.id.edit_mm);
        bd = openOrCreateDatabase(db_name, Context.MODE_PRIVATE, null);//打开数据库
        checkBox.setOnCheckedChangeListener(this);//选择框
        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

    }

    public void sign_in(View v) //注册按钮 跳转到注册页面
    {

        Intent intent = new Intent(login.this, sign_in.class);

        startActivity(intent);
    }

    //判断输入的账号和密码是否正确
    public void dengji(View view) {
        Cursor c = bd.rawQuery("SELECT * FROM " + dt_name, null);

        if (c.moveToFirst()) {
            do {
                if (edit_zh.getText().toString().equals(c.getString(0)) && edit_mm.getText().toString().equals(c.getString(1))) {
                    String user=edit_zh.getText().toString();
                    Intent it = new Intent(this, MainActivity.class);  //跳转页面
                    it.putExtra("username",user);
                    startActivity(it);
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                    bd.close();  //关闭数据库
                } else {
                    //Toast.makeText(getApplicationContext(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            } while (c.moveToNext());
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if(checkBox.isChecked()==false){
            denglu.setEnabled(false);
        }else{
            denglu.setEnabled(true);
        }
    }
}


