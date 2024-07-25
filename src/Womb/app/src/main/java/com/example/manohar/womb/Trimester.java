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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Trimester extends AppCompatActivity {
RadioButton rb;
RadioGroup rg;
Button edit,show;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference,collectionReference1,collectionReference3;
    AlertDialog.Builder alertDialog;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimester);

        final Intent intent=getIntent();
        final String id=intent.getStringExtra("id");

        edit=(Button)findViewById(R.id.edit);
        show=(Button)findViewById(R.id.show);


        rg=(RadioGroup)findViewById(R.id.rg);


        progressDialog=new ProgressDialog(this);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Anganwadi");
        userid=firebaseAuth.getCurrentUser().getUid();


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=rg.getCheckedRadioButtonId();
                rb=(RadioButton)findViewById(i);

                final String str=rb.getText().toString();
                Intent intent=new Intent(Trimester.this,Edit.class);
                intent.putExtra("id",id);
                intent.putExtra("term",str);
                startActivity(intent);
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);

                int i=rg.getCheckedRadioButtonId();
                rb=(RadioButton)findViewById(i);

                final String str=rb.getText().toString();


                collectionReference1=collectionReference.document(userid).collection("Patients").document(id).collection(str);


                collectionReference1.document(str).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String hemo=documentSnapshot.getString("Hemo");
                        String thy=documentSnapshot.getString("Thyroid");
                        String bp=documentSnapshot.getString("BP");
                        String bmi=documentSnapshot.getString("BMI");
                        String det=documentSnapshot.getString("Details");


                        if(!TextUtils.isEmpty(hemo) && !TextUtils.isEmpty(thy) && !TextUtils.isEmpty(bp) && !TextUtils.isEmpty(bmi) )
                        {
                            progressDialog.dismiss();
                            Intent intent=new Intent(Trimester.this,newPatient.class);
                            intent.putExtra("id",id);
                            intent.putExtra("Trimester",str);
                            startActivity(intent);
                        }
                        else
                        {
                            progressDialog.dismiss();
                            alertDialog=new AlertDialog.Builder(Trimester.this);
                            alertDialog.setTitle("Enter the trimester details");
                            alertDialog.setCancelable(false);

                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Toast.makeText(Trimester.this, "", Toast.LENGTH_SHORT).show();
                                }
                            });

                            AlertDialog alert=alertDialog.create();
                            alert.show();

                        }



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String s=e.toString();
                        Toast.makeText(Trimester.this, s, Toast.LENGTH_SHORT).show();
                    }
                });







            }
        });

    }
}
