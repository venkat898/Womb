package com.example.manohar.womb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);
        Logo logo=new Logo();
        logo.start();

    }

    private class Logo extends Thread {
        public void run() {
            try {
                sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(Welcome.this, MainActivity.class);
            startActivity(intent);
            Welcome.this.finish();

        }
    }
}