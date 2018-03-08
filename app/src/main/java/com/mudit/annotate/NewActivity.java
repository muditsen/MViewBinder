package com.mudit.annotate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mudit.customanno.MyViewBinder;

import org.w3c.dom.Text;

public class NewActivity extends AppCompatActivity {


    @MyViewBinder(R.id.tv_world)
    TextView tvWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        NewActivity_MBinder newActivity_mBinder = new NewActivity_MBinder(this);
    }
}
