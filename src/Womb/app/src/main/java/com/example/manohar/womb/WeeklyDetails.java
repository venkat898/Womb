package com.example.manohar.womb;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

public class WeeklyDetails extends AppCompatActivity {
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    String hpname,hpemail,userid;


    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_details);

        toolbar=(Toolbar)findViewById(R.id.wdtoolbar);
        setSupportActionBar(toolbar);

        String string_array[]=getResources().getStringArray(R.array.week);

        listView=(ListView)findViewById(R.id.listview);
        ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.listview,R.id.listText,string_array);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        startActivity(new Intent(WeeklyDetails.this,Week1.class));
                        break;
                    case 1:
                        startActivity(new Intent(WeeklyDetails.this,Week2.class));
                        break;
                    case 2:
                        startActivity(new Intent(WeeklyDetails.this,Week3.class));
                        break;
                    case 3:
                        startActivity(new Intent(WeeklyDetails.this,Week4.class));
                        break;
                    case 4:
                        startActivity(new Intent(WeeklyDetails.this,Week5.class));
                        break;
                    case 5:
                        startActivity(new Intent(WeeklyDetails.this,Week6.class));
                        break;
                    case 6:
                        startActivity(new Intent(WeeklyDetails.this,Week7.class));
                        break;
                    case 7:
                        startActivity(new Intent(WeeklyDetails.this,Week8.class));
                        break;
                    case 8:
                        startActivity(new Intent(WeeklyDetails.this,Week9.class));
                        break;
                    case 9:
                        startActivity(new Intent(WeeklyDetails.this,Week10.class));
                        break;
                    case 10:
                        startActivity(new Intent(WeeklyDetails.this,Week11.class));
                        break;
                    case 11:
                        startActivity(new Intent(WeeklyDetails.this,Week12.class));
                        break;
                    case 12:
                        startActivity(new Intent(WeeklyDetails.this,Week13.class));
                        break;
                    case 13:
                        startActivity(new Intent(WeeklyDetails.this,Week14.class));
                        break;
                    case 14:
                        startActivity(new Intent(WeeklyDetails.this,Week15.class));
                        break;
                    case 15:
                        startActivity(new Intent(WeeklyDetails.this,Week16.class));
                        break;
                    case 16:
                        startActivity(new Intent(WeeklyDetails.this,Week17.class));
                        break;
                    case 17:
                        startActivity(new Intent(WeeklyDetails.this,Week18.class));
                        break;
                    case 18:
                        startActivity(new Intent(WeeklyDetails.this,Week19.class));
                        break;
                    case 19:
                        startActivity(new Intent(WeeklyDetails.this,Week20.class));
                        break;
                    case 20:
                        startActivity(new Intent(WeeklyDetails.this,Week21.class));
                        break;

                    case 21:
                        startActivity(new Intent(WeeklyDetails.this,Week22.class));
                        break;
                    case 22:
                        startActivity(new Intent(WeeklyDetails.this,Week23.class));
                        break;
                    case 23:
                        startActivity(new Intent(WeeklyDetails.this,Week24.class));
                        break;
                    case 24:
                        startActivity(new Intent(WeeklyDetails.this,Week25.class));
                        break;
                    case 25:
                        startActivity(new Intent(WeeklyDetails.this,Week26.class));
                        break;
                    case 26:
                        startActivity(new Intent(WeeklyDetails.this,Week27.class));
                        break;
                    case 27:
                        startActivity(new Intent(WeeklyDetails.this,Week28.class));
                        break;
                    case 28:
                        startActivity(new Intent(WeeklyDetails.this,Week29.class));
                        break;
                    case 29:
                        startActivity(new Intent(WeeklyDetails.this,Week30.class));
                        break;
                    case 30:
                        startActivity(new Intent(WeeklyDetails.this,Week31.class));
                        break;
                    case 31:
                        startActivity(new Intent(WeeklyDetails.this,Week32.class));
                        break;
                    case 32:
                        startActivity(new Intent(WeeklyDetails.this,Week33.class));
                        break;
                    case 33:
                        startActivity(new Intent(WeeklyDetails.this,Week34.class));
                        break;
                    case 34:
                        startActivity(new Intent(WeeklyDetails.this,Week35.class));
                        break;
                    case 35:
                        startActivity(new Intent(WeeklyDetails.this,Week36.class));
                        break;
                    case 36:
                        startActivity(new Intent(WeeklyDetails.this,Week37.class));
                        break;
                    case 37:
                        startActivity(new Intent(WeeklyDetails.this,Week38.class));
                        break;
                    case 38:
                        startActivity(new Intent(WeeklyDetails.this,Week39.class));
                        break;
                    case 39:
                        startActivity(new Intent(WeeklyDetails.this,Week40.class));
                        break;
                }
            }
        });


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        collectionReference=firebaseFirestore.getInstance().collection("Anganwadi");
        hpemail= firebaseAuth.getCurrentUser().getEmail().toString();
        userid=firebaseAuth.getCurrentUser().getUid().toString();

        collectionReference.document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    hpname=task.getResult().getString("Name");
                }
                else
                {
                    Toast.makeText(WeeklyDetails.this, "Error with database, please login again.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String d=e.toString();
                Toast.makeText(WeeklyDetails.this, d, Toast.LENGTH_SHORT).show();
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
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withSelectedItemByPosition(2)
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
                                startActivity(new Intent(WeeklyDetails.this,HomePage.class));
                                break;
                            case 2:
                                closeOptionsMenu();
                                break;
                            case 3:
                                finish();
                                startActivity(new Intent(WeeklyDetails.this,NewRecord.class));
                                break;
                            case 4:
                                finish();
                                startActivity(new Intent(WeeklyDetails.this,ToDo.class));
                                break;
                            case 6:
                                firebaseAuth.signOut();
                                finish();
                                Intent close=new Intent(getApplicationContext(),MainActivity.class);
                                close.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                //close.putExtra("CloseApp",true);
                                startActivity(close);
                                break;
                        }
                        return true;
                    }
                })
                .withCloseOnClick(true)
                .build();


    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
