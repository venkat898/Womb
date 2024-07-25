package com.example.manohar.womb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikepenz.fastadapter.listeners.OnLongClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adding extends AppCompatActivity {
    Toolbar toolbar;
    EditText name,age,month,phone;
    Button add;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    CollectionReference cr,cr1;
    ArrayList<String> arrayList;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        toolbar=(Toolbar)findViewById(R.id.tool3);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.menu);


        name=(EditText)findViewById(R.id.name);
        age=(EditText)findViewById(R.id.age);
        month=(EditText)findViewById(R.id.month);
        phone=(EditText)findViewById(R.id.phone);
        add=(Button)findViewById(R.id.add);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        final String uid=firebaseAuth.getCurrentUser().getUid().toString();

        cr=firebaseFirestore.collection("Anganwadi").document(uid).collection("Patients");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sname=name.getText().toString();
                final String sage=age.getText().toString().trim();
                final String sphone=phone.getText().toString().trim();
                final String smonth=month.getText().toString();


                arrayList=new ArrayList<String>();



                if(!TextUtils.isEmpty(sname) && !TextUtils.isEmpty(sage) && !TextUtils.isEmpty(smonth) && !TextUtils.isEmpty(sphone) ) {


                    if (Integer.parseInt(smonth) < 9) {


                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                        progressDialog.setCanceledOnTouchOutside(false);


                        cr.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    arrayList.add(documentSnapshot.getId().toString());
                                }

                                if ((arrayList.contains(sname)) == false) {

                                    Map<String, String> DataToAdd = new HashMap<>();
                                    DataToAdd.put("Name", sname);
                                    DataToAdd.put("Age", sage);
                                    DataToAdd.put("Month", smonth);
                                    DataToAdd.put("Trimester", sphone);

                                    cr.document(sname).set(DataToAdd).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            String s=e.toString();
                                            Toast.makeText(Adding.this, s, Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    progressDialog.dismiss();

                                    finish();
                                    startActivity(new Intent(getApplicationContext(), NewRecord.class));
                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Adding.this, "Name already exists.", Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(Adding.this, "Months should be less than 9 ", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Adding.this,"Contents should not be empty.",Toast.LENGTH_LONG).show();
                }

            }
        });



    }


}
