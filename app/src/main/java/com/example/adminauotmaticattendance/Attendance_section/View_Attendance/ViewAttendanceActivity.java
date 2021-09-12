package com.example.adminauotmaticattendance.Attendance_section.View_Attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adminauotmaticattendance.R;
import com.example.adminauotmaticattendance.Setting_section.SaveTheme;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAttendanceActivity extends AppCompatActivity {


    private TextView memptylist;
    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter mviewattendadapter;
    private  RecyclerView.LayoutManager mviewattendlayoutManager;
    private ProgressBar mprogressbar;
    private String branch,subject;
    FirebaseAuth mauth;
    DatabaseReference userdb;
    int count=0;
    SaveTheme saveTheme;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        saveTheme=new SaveTheme(this);
        if(saveTheme.getTheme()==true)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            setTheme(R.style.CreamTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            userid=bundle.getString("userid");
            String name=bundle.getString("name");
            setTitle(name +"'s Attendance");
        }
//        mauth=FirebaseAuth.getInstance();
//        userid=mauth.getCurrentUser().getUid();
         userdb= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(userid);

        mprogressbar=findViewById(R.id.progressBar);
        mrecyclerView=findViewById(R.id.recyclerView);
        mrecyclerView.setNestedScrollingEnabled(false);
        mrecyclerView.setHasFixedSize(true);
        memptylist=findViewById(R.id.emptylist);
        memptylist.setVisibility(View.INVISIBLE);
        mviewattendlayoutManager=new LinearLayoutManager(ViewAttendanceActivity.this);
        mrecyclerView.setLayoutManager(mviewattendlayoutManager);
        mviewattendadapter=new view_attenAdapter(getdatasetviewattend(),ViewAttendanceActivity.this);

        getsubjectid();
        mrecyclerView.setAdapter(mviewattendadapter);


    }


    private void getsubjectid() {

        userdb.child("subjects").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot match:dataSnapshot.getChildren())
                    {
                        fetchsubjectsinfo(match.getKey());
                    }
                }else{
                    mprogressbar.setVisibility(View.INVISIBLE);
                    memptylist.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchsubjectsinfo(String key){
        DatabaseReference databaseReference= userdb.child("subjects").child(key);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String name=(String) snapshot.getKey();
                   String attended=String.valueOf(snapshot.child("Attended").getValue());
                   String ac=String.valueOf(snapshot.child("Total Ac").getValue());
                   String totalclass= String.valueOf(snapshot.child("Total Classes").getValue());
                    String percentAttendance="";
                   if(Integer.valueOf(totalclass)==0)
                   {
                       percentAttendance="0";
                   }else {
                       percentAttendance = String.valueOf(((Integer.valueOf(ac) + Integer.valueOf(attended)) * 100) / (Integer.valueOf(totalclass)));
                   }
                   viewobject obk=new viewobject(name,ac,attended,totalclass,percentAttendance);
                    resultsViewAttend.add(obk);
                }
                mviewattendadapter.notifyDataSetChanged();
                mprogressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private ArrayList<viewobject> resultsViewAttend=new ArrayList<viewobject>();
    private List<viewobject> getdatasetviewattend() {
        return resultsViewAttend;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==android.R.id.home) {
            super.onBackPressed();
            finish();
            overridePendingTransition(R.anim.leftstart,R.anim.rightend);
        }
        return super.onOptionsItemSelected(item);
    }

}