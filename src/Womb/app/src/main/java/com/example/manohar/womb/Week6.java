package com.example.manohar.womb;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Week6 extends AppCompatActivity {
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week6);
        t=(TextView)findViewById(R.id.url1);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fitpregnancy.com/pregnancy/pregnancy-health/cures-morning-sickness"));
                startActivity(i);
            }
        });
    }
}
