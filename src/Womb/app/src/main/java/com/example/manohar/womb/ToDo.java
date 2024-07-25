package com.example.manohar.womb;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class ToDo extends AppCompatActivity implements Tab1.OnFragmentInteractionListener,Tab2.OnFragmentInteractionListener{
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;
    String hpname,hpemail,userid;
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);


        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Dos").setIcon(R.drawable.ic_check_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setText("Don'ts").setIcon(R.drawable.ic_close_black_24dp));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        viewPager=(ViewPager)findViewById(R.id.viewpager);
        final PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        toolbar=(Toolbar)findViewById(R.id.tdtoolbar);
        setSupportActionBar(toolbar);

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
                    Toast.makeText(ToDo.this, "Error with database, please login again.", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String d=e.toString();
                Toast.makeText(ToDo.this, d, Toast.LENGTH_SHORT).show();
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
                .withSelectedItemByPosition(4)
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
                                finish();
                                startActivity(new Intent(ToDo.this,HomePage.class));
                                break;
                            case 2:
                                finish();
                                startActivity(new Intent(ToDo.this,WeeklyDetails.class));
                                break;
                            case 3:
                                finish();
                                startActivity(new Intent(ToDo.this,NewRecord.class));
                                break;
                            case 4:
                                closeOptionsMenu();
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


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
