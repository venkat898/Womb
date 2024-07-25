package com.example.manohar.womb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    Toolbar toolbar;
    EditText name,email,password,phone,region;
    Button register;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar=(Toolbar)findViewById(R.id.tool3);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.menu);


        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        phone=(EditText)findViewById(R.id.phone);
        region=(EditText)findViewById(R.id.region);
        register=(Button)findViewById(R.id.registration);



        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        final CollectionReference defaultCollection=FirebaseFirestore.getInstance().collection("Anganwadi");



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String sname=name.getText().toString();
                String semail=email.getText().toString().trim();
                final String sphone=phone.getText().toString().trim();
                final String sregion=region.getText().toString();
                String spassword=password.getText().toString().trim();


                if(!TextUtils.isEmpty(sname) && !TextUtils.isEmpty(spassword) && !TextUtils.isEmpty(semail) && !TextUtils.isEmpty(sphone) && !TextUtils.isEmpty(sregion)) {

                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    firebaseAuth.createUserWithEmailAndPassword(semail, spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {

                                String uid= firebaseAuth.getCurrentUser().getUid();

                                Map<String,String> DataToAdd=new HashMap<>();
                                DataToAdd.put("Name",sname);
                                DataToAdd.put("Phone",sphone);
                                DataToAdd.put("Region",sregion);

                                defaultCollection.document(uid).set(DataToAdd);

                                progressDialog.dismiss();
                                textclear();
                                sendEmailVerification();

                           }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Main2Activity.this,"Registration Failed",Toast.LENGTH_LONG).show();
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            String s=e.toString();
                            Toast.makeText(Main2Activity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    Toast.makeText(Main2Activity.this,"Contents should not be empty.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void textclear() {
        password.getText().clear();
        email.getText().clear();
        phone.getText().clear();
        region.getText().clear();
        name.getText().clear();
    }
    public void sendEmailVerification()
    {
        final FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Main2Activity.this, "Successfully Registered. Verify Your E-Mail.", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        startActivity(new Intent(Main2Activity.this,Main3Activity.class));
                    }
                    else
                    {
                        Toast.makeText(Main2Activity.this, "Verification mail hasn't been sent.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
