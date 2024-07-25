package com.example.manohar.womb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Edit extends AppCompatActivity {
Button add;
AlertDialog.Builder alertDialog;
EditText hemo,thy,bp,bmi;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    CollectionReference collectionReference;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent=getIntent();
        final String str=intent.getStringExtra("id");
        final String str2=intent.getStringExtra("term");

        add=(Button)findViewById(R.id.add);
        hemo=(EditText)findViewById(R.id.hemo);
        thy=(EditText)findViewById(R.id.thy);
        bp=(EditText)findViewById(R.id.bp);
        bmi=(EditText)findViewById(R.id.bmi);



        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        uid=firebaseAuth.getCurrentUser().getUid();

        collectionReference=firebaseFirestore.collection("Anganwadi").document(uid).collection("Patients").document(str).collection(str2);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String hemog=hemo.getText().toString().trim();
                final String thyr=thy.getText().toString().trim();
                final String bpr=bp.getText().toString().trim();
                final String bmid=bmi.getText().toString().trim();
                if(!TextUtils.isEmpty(hemog) && !TextUtils.isEmpty(thyr) && !TextUtils.isEmpty(bpr) && !TextUtils.isEmpty(bmid) ) {



                    Map<String,String> DataToAdd=new HashMap<>();
                    DataToAdd.put("Hemo",hemog);
                    DataToAdd.put("Thyroid",thyr);
                    DataToAdd.put("BP",bpr);
                    DataToAdd.put("BMI",bmid);
                    DataToAdd.put("Details","");




                    collectionReference.document(str2).set(DataToAdd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            alertDialog=new AlertDialog.Builder(Edit.this);
                            alertDialog.setMessage("Please come back after 24 hours for the detailed report.");
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(Trimester.this, "", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });

                            AlertDialog alert=alertDialog.create();
                            alert.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String s=e.toString();
                            Toast.makeText(Edit.this, s, Toast.LENGTH_SHORT).show();
                        }
                    });



                }
                else
                {
                    Toast.makeText(Edit.this,"Contents should not be empty.",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
