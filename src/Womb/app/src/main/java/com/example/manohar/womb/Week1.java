package com.example.manohar.womb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

public class Week1 extends AppCompatActivity {
    TextView t;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week1);
        toolbar=(android.support.v7.widget.Toolbar) findViewById(R.id.week1tool);
        setSupportActionBar(toolbar);
        t=(TextView)findViewById(R.id.url1);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fitpregnancy.com/nutrition/prenatal-nutrition/rules-eat"));
                startActivity(i);
            }
        });
    }
}
