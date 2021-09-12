package com.example.adminauotmaticattendance.Student_section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adminauotmaticattendance.MainActivity;
import com.example.adminauotmaticattendance.R;
import com.example.adminauotmaticattendance.Setting_section.SaveTheme;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentActivity extends AppCompatActivity {

    private Spinner mbranch,myear;
    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter mviewstudentadapter;
    private  RecyclerView.LayoutManager mviewstudentlayoutManager;
    private ProgressBar mprogressbar;
    private TextView emptylist;
    SaveTheme saveTheme;
    String branch,year;
    int count=0,incr=0;
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
        setContentView(R.layout.activity_view_student);
        setTitle("Students List");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        mbranch=findViewById(R.id.branch);
        myear=findViewById(R.id.year);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            String branch=bundle.getString("branch");
            String year=bundle.getString("year");
            mbranch.setSelection(((ArrayAdapter<String>)mbranch.getAdapter()).getPosition(branch));
            myear.setSelection(((ArrayAdapter<String>)myear.getAdapter()).getPosition(year));
        }

        emptylist=findViewById(R.id.emptylist);
        emptylist.setVisibility(View.INVISIBLE);
        mprogressbar=findViewById(R.id.progressBar);
        mrecyclerView=findViewById(R.id.recyclerView);
        mrecyclerView.setNestedScrollingEnabled(false);
        mrecyclerView.setHasFixedSize(true);
        mviewstudentlayoutManager=new LinearLayoutManager(ViewStudentActivity.this);
        mrecyclerView.setLayoutManager(mviewstudentlayoutManager);
        mviewstudentadapter=new view_StudentAdapter(getdatasetviewstudent(), ViewStudentActivity.this);
        branch = mbranch.getSelectedItem().toString();
        year= myear.getSelectedItem().toString();
//        getstudentid();
        mrecyclerView.setAdapter(mviewstudentadapter);

        mbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
            incr=0;
            resultsViewstudent.clear();
            mviewstudentadapter.notifyDataSetChanged();
            count=0;
            emptylist.setVisibility(View.INVISIBLE);
            mprogressbar.setVisibility(View.VISIBLE);
            String selectedbranch = (String) mbranch.getItemAtPosition(i);
            branch=selectedbranch;
            getstudentid();
            mviewstudentadapter.notifyDataSetChanged();
            count=1;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });



        myear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (count == 1) {
                    incr=0;
                    resultsViewstudent.clear();
                    mviewstudentadapter.notifyDataSetChanged();
                    emptylist.setVisibility(View.INVISIBLE);
                    mprogressbar.setVisibility(View.VISIBLE);
                    String selectedYear = (String) myear.getItemAtPosition(i);
                    year = selectedYear;
                    getstudentid();
                    mviewstudentadapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }




    private void getstudentid() {

        Query userdb= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").orderByChild("rollno");
        userdb.addListenerForSingleValueEvent(sval);
    }

    private void fetchstudentsinfo(String key){
        Query databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(key);
        databaseReference.addListenerForSingleValueEvent(val);

    }
    private ArrayList<viewstudentobject> resultsViewstudent=new ArrayList<viewstudentobject>();
    private List<viewstudentobject> getdatasetviewstudent() {
        return resultsViewstudent;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==android.R.id.home) {
            Intent i=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.leftstart,R.anim.rightend);
        }
        return super.onOptionsItemSelected(item);
    }


    ValueEventListener sval= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists())
            {
//                    Toast.makeText(ViewAttendanceActivity.this, "hurray", Toast.LENGTH_SHORT).show();
                for(DataSnapshot match:dataSnapshot.getChildren())
                {
                    fetchstudentsinfo(match.getKey());
                }
            }else{
                emptylist.setVisibility(View.VISIBLE);
                mprogressbar.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };



    ValueEventListener val=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists())
            {
                String key=snapshot.getKey().toString();
                String name=snapshot.child("name").getValue().toString();
                String profilepic=snapshot.child("profilepic").getValue().toString();
                String rollno=snapshot.child("rollno").getValue().toString();
                String email=snapshot.child("email").getValue().toString();
                String Year=snapshot.child("year").getValue().toString();
                String Branch=snapshot.child("branch").getValue().toString();
                String phone=snapshot.child("phone").getValue().toString();
                if(Branch.equals(branch) && Year.equals(year)){
                    incr++;
                    viewstudentobject obk=new viewstudentobject(profilepic,name,rollno,email,phone,key);
                    resultsViewstudent.add(obk);
                }
            }
            if(incr!=0)
            {
                emptylist.setVisibility(View.INVISIBLE);
            }else{
                emptylist.setVisibility(View.VISIBLE);
            }
            mviewstudentadapter.notifyDataSetChanged();
            mprogressbar.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}
