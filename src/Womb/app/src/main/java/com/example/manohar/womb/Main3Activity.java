package com.example.manohar.womb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main3Activity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    EditText username,password;
    Button login2;
    TextView forgot;
    String user,pass;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        username=(EditText)findViewById(R.id.user);
        password=(EditText)findViewById(R.id.pass);
        login2=(Button) findViewById(R.id.login2);
        forgot=(TextView)findViewById(R.id.forgot);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        FirebaseUser currentuser=firebaseAuth.getCurrentUser();
        if(currentuser!=null)
        {
            finish();
            startActivity(new Intent(Main3Activity.this,HomePage.class));
        }

        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Logging you in...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                user=username.getText().toString();
                pass=password.getText().toString();

                if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pass))
                {
                    firebaseAuth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();

                                VerifyEmail();
                            }
                            else
                            {
                                progressDialog.dismiss();

                                Toast.makeText(Main3Activity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            String error= e.toString();
                            Toast.makeText(Main3Activity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    progressDialog.dismiss();
                    Toast.makeText(Main3Activity.this, "Enter the Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.getText().clear();
                Intent intent3=new Intent(Main3Activity.this,Main4Activity.class);
                startActivity(intent3);
            }
        });
    }
    public void VerifyEmail()
    {
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean verified=firebaseUser.isEmailVerified();
        if(verified)
        {
            startActivity(new Intent(Main3Activity.this,HomePage.class));
        }
        else
        {
            Toast.makeText(Main3Activity.this, "Verify Your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
