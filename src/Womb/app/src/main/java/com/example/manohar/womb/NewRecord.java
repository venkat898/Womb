package com.example.manohar.womb;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewRecord extends AppCompatActivity {
    Toolbar toolbar;
    AlertDialog.Builder alertDialog;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference,collectionReference1;
    String hpname,hpemail,userid;
    ListView listView;
    Button add;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<String> arrayList2=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter,arrayAdapter2;

    ProgressDialog progressDialog,progressDialog2;

    private static final String TAG = "DocSnippets";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);
        toolbar=(Toolbar)findViewById(R.id.nrtoolbar);
        setSupportActionBar(toolbar);

        progressDialog=new ProgressDialog(this);
        progressDialog2=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        //arrayAdapter2=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList2);

          listView=(ListView)findViewById(R.id.newrecordlist);
          add=(Button)findViewById(R.id.newrecordbutton);

        listView.setAdapter(arrayAdapter);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        collectionReference=firebaseFirestore.getInstance().collection("Anganwadi");
        hpemail= firebaseAuth.getCurrentUser().getEmail().toString();
        userid=firebaseAuth.getCurrentUser().getUid().toString();



        collectionReference1=collectionReference.document(userid).collection("Patients");
        collectionReference1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    String string = documentSnapshot.getId().toString();
                    arrayList.add(string);
                    arrayAdapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();
//               Toast.makeText(NewRecord.this, userlist[0], Toast.LENGTH_SHORT).show();
//             Toast.makeText(NewRecord.this, userlist[1], Toast.LENGTH_SHORT).show();
//                listCreator();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String s=e.toString();
                Toast.makeText(NewRecord.this, s, Toast.LENGTH_SHORT).show();
            }
        });




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewRecord.this,Adding.class);
                startActivity(intent);
            }
        });




        collectionReference.document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    hpname=task.getResult().getString("Name");
                }
                else
                {
                    Toast.makeText(NewRecord.this, "Error with database, please login again.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String d=e.toString();
                Toast.makeText(NewRecord.this, d, Toast.LENGTH_SHORT).show();
            }
        });






        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog=new AlertDialog.Builder(NewRecord.this);
                final int a=position;
                final String string3=(String) arrayList.get(position);
                alertDialog.setTitle("Are you sure you want to delete "+ string3+ " ?");
                alertDialog.setCancelable(false);

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog2.setMessage("Please wait...");
                        progressDialog2.show();
                        progressDialog2.setCanceledOnTouchOutside(false);
                        collectionReference1.document(string3).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                collectionReference1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        arrayList.remove(a);
                                        arrayAdapter.notifyDataSetChanged();
                                        progressDialog2.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        String s=e.toString();
                                        progressDialog2.dismiss();
                                        Toast.makeText(NewRecord.this, s, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                String s=e.toString();
                                progressDialog2.dismiss();
                                Toast.makeText(NewRecord.this, s, Toast.LENGTH_SHORT).show();
                            }
                        });


                        //Toast.makeText(Trimester.this, "", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alert=alertDialog.create();
                alert.show();

                return true;
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string=(String)arrayList.get(position);
                Intent intent=new Intent(NewRecord.this,Trimester.class);
                intent.putExtra("id",string);
                startActivity(intent);
            }
        });

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withTextColorRes(R.color.black)
                .addProfiles(
                        new ProfileDrawerItem().withName(hpname).withEmail(hpemail).withIcon(getResources().getDrawable(R.drawable.ic_face_black_24dp))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();



        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        PrimaryDrawerItem item2=new PrimaryDrawerItem().withIdentifier(2).withName("Weekly details");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("New");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("Dos and Don'ts");
        SecondaryDrawerItem item5= new SecondaryDrawerItem().withIdentifier(5).withName("Logout");
        //SecondaryDrawerItem item6= new SecondaryDrawerItem().withIdentifier(6).withName("Logout");

//create the drawer and remember the `Drawer` result object
        final Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withSelectedItemByPosition(3)
                .addDrawerItems(
                        item1.withIcon(R.drawable.ic_home_black_24dp),item2.withIcon(R.drawable.ic_event_note_black_24dp),item3.withIcon(R.drawable.ic_add_circle_outline_black_24dp),item4.withIcon(R.drawable.ic_star_half_black_24dp),
                        new SectionDrawerItem().withName("Account").withTextColorRes(R.color.background),item5.withIcon(R.drawable.ic_input_black_24dp)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position)
                        {
                            case 1:
                                finish();
                                startActivity(new Intent(NewRecord.this,HomePage.class));
                                break;
                            case 2:
                                finish();
                                startActivity(new Intent(NewRecord.this,WeeklyDetails.class));
                                break;
                            case 3:
                                closeOptionsMenu();
                                break;
                            case 4:
                                finish();
                                startActivity(new Intent(NewRecord.this,ToDo.class));
                                break;
                            case 6:
                                firebaseAuth.signOut();
                                finish();
                                Intent close=new Intent(getApplicationContext(),MainActivity.class);
                                close.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                                close.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                close.putExtra("CloseApp",true);
                                startActivity(close);
                                break;
                        }
                        return true;
                    }
                })
                .withCloseOnClick(true)
                .build();








    }







    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }


}
