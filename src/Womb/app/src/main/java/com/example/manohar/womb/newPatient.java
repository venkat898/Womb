package com.example.manohar.womb;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class newPatient extends AppCompatActivity {
    TextView t1,t2,t4,t5,t6,t7,t8,t9;
    String rname,userid,rterm;
    String naam,age,month,trimester,hemo,thy,bp,bmi,det;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference,collectionReference1,collectionReference3;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);
        t1=(TextView)findViewById(R.id.name);
        t2=(TextView)findViewById(R.id.age);

        t4=(TextView)findViewById(R.id.trimester);
        t5=(TextView)findViewById(R.id.hemoglobin);
        t6=(TextView)findViewById(R.id.thyroid);
        t7=(TextView)findViewById(R.id.bp);
        t8=(TextView)findViewById(R.id.bmi);
        t9=(TextView)findViewById(R.id.details);




        Intent intent=getIntent();
        rname=intent.getStringExtra("id");
        rterm=intent.getStringExtra("Trimester");


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        collectionReference=firebaseFirestore.collection("Anganwadi");
        userid=firebaseAuth.getCurrentUser().getUid();



        collectionReference1=collectionReference.document(userid).collection("Patients");

        collectionReference3=collectionReference1.document(rname).collection(rterm);


        collectionReference3.document(rterm).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                hemo=documentSnapshot.getString("Hemo");
                thy=documentSnapshot.getString("Thyroid");
                bp=documentSnapshot.getString("BP");
                bmi=documentSnapshot.getString("BMI");
                det=documentSnapshot.getString("Details");

                if(det.isEmpty())
                {
                    alertDialog=new AlertDialog.Builder(newPatient.this);
                    alertDialog.setTitle("Hello");
                    alertDialog.setMessage("We will notify you as soon as we examine the report.");
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


                t5.setText(hemo);
                t6.setText(thy);
                t7.setText(bp);
                t8.setText(bmi);
                t9.setText(det);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String s=e.toString();
                Toast.makeText(newPatient.this, s,Toast.LENGTH_SHORT).show();
            }
        });


        collectionReference1.document(rname).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    naam=documentSnapshot.get("Name").toString();
                    age=documentSnapshot.get("Age").toString();
                    month=documentSnapshot.get("Month").toString();
                    trimester=documentSnapshot.get("Trimester").toString();
                    //Toast.makeText(newPatient.this, naam, Toast.LENGTH_SHORT).show();

                    t1.setText(naam);
                    t2.setText(age);
                    t4.setText(trimester);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String s=e.toString();
                Toast.makeText(newPatient.this, s, Toast.LENGTH_LONG).show();
            }
        });




    }
}
