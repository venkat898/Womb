package com.example.manohar.womb;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class HomePage extends AppCompatActivity {
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference,collectionReference2;
    String hpname,hpemail,userid;
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout l;
    ProgressDialog progressDialog;
    Random random;
    ArrayList<String> arrayList;

    public int dayCount;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        toolbar=(Toolbar)findViewById(R.id.hptoolbar);
        setSupportActionBar(toolbar);

        random=new Random();
        l=(LinearLayout)findViewById(R.id.id);
        textView=(TextView)findViewById(R.id.text);

        l.setBackgroundResource(R.drawable.mom2);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        collectionReference=firebaseFirestore.collection("Anganwadi");
        collectionReference2=firebaseFirestore.collection("Quotes");
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
                    progressDialog.dismiss();
                    Toast.makeText(HomePage.this, "Error with database, please login again.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String d=e.toString();
                progressDialog.dismiss();
                Toast.makeText(HomePage.this, d, Toast.LENGTH_SHORT).show();
            }
        });


        String as="Z6loaZNqtZHU62Fuop7U";

        collectionReference2.document(as).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                progressDialog=new ProgressDialog(HomePage.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                    arrayList = new ArrayList<>();
                    for (int i = 1; i < 11; i++) {
                        String s = Integer.toString(i);
                        arrayList.add(documentSnapshot.getString(s));
                    }
                    int a = random.nextInt(10);
                    String str = arrayList.get(a).toString();
                    textView.setText(str);
                    progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                String s=e.toString();
                Toast.makeText(HomePage.this, s, Toast.LENGTH_SHORT).show();
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
                .withScrollToTopAfterClick(true)
                .withDelayOnDrawerClose(1)
                .addDrawerItems(
                        item1.withIcon(R.drawable.ic_home_black_24dp),item2.withIcon(R.drawable.ic_event_note_black_24dp),item3.withIcon(R.drawable.ic_add_circle_outline_black_24dp),item4.withIcon(R.drawable.ic_star_half_black_24dp),
                        new SectionDrawerItem().withName("Account").withTextColorRes(R.color.background), item5.withIcon(R.drawable.ic_input_black_24dp)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        switch (position)
                        {
                            case 1:
                                closeOptionsMenu();
                                break;
                            case 2:
                                startActivity(new Intent(HomePage.this,WeeklyDetails.class));
                                break;
                            case 3:
                                startActivity(new Intent(HomePage.this,NewRecord.class));
                                break;
                            case 4:
                                startActivity(new Intent(HomePage.this,ToDo.class));
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
                }).build();



    }



    @Override
    public void onBackPressed() {
        firebaseAuth=FirebaseAuth.getInstance();
        if (doubleBackToExitPressedOnce) {
//            Intent close=new Intent(getApplicationContext(),MainActivity.class);
//            close.addCategory(Intent.CATEGORY_HOME);
//            close.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(close);
            finishAffinity();
            return;
        }


        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();

        new CountDownTimer(3000,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                doubleBackToExitPressedOnce=false;
            }
        }.start();
    }

}
