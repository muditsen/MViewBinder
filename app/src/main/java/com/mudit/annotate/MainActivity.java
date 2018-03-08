package com.mudit.annotate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mudit.customanno.MyViewBinder;

public class MainActivity extends AppCompatActivity {


    @MyViewBinder(R.id.tv_hello)
    TextView tvHello2;

    @MyViewBinder(R.id.tv_hello2)
    TextView tvHello3;

    @MyViewBinder(R.id.tv_hello3)
    TextView tvHello4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity_MBinder mainActivity_mBinder = new MainActivity_MBinder(this);
        tvHello2.setText("Binder 1");
        tvHello3.setText("Binder 2");
        tvHello4.setText("Binder 3");

    }
}
