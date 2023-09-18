package com.example.a123.wifictrl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class sign_in extends AppCompatActivity {

    static final String db_name="Users";//数据库名称
    static final String dt_name="user";  //数据表名称

    public SQLiteDatabase bd;

    EditText zc_zhanghao,zc_mm,zc_mm_qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        zc_zhanghao= (EditText) findViewById(R.id.zc_zhanghao);
        zc_mm= (EditText) findViewById(R.id.zc_mima);
        zc_mm_qr=(EditText) findViewById(R.id.zc_mm_qr);

        bd=openOrCreateDatabase(db_name, Context.MODE_PRIVATE,null);
        String createTable="CREATE TABLE IF NOT EXISTS "+
                dt_name+
                "(zhanghao VARCHAR(32),"+
                "mima VARCHAR(64))";
        bd.execSQL(createTable);
    }

    public void login(View v)  //跳回登录界面
    {
        bd.close();
        Intent it=new Intent(this,login.class);
        startActivity(it);
    }

    //像数据库中添加数据的方法
    private void addDate(String toString, String toString1)
    {
        ContentValues cv=new ContentValues(2);

        cv.put("zhanghao",toString);
        cv.put("mima",toString1);

        bd.insert(dt_name,null,cv);

    }

    public void zhuce(View view)
    {
        if(zc_mm.getText().toString().equals(zc_mm_qr.getText().toString()))
        {
            addDate(zc_zhanghao.getText().toString(), zc_mm.getText().toString());
            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "两次输入密码不相同,请重新输入", Toast.LENGTH_SHORT).show();
        }

    }
}