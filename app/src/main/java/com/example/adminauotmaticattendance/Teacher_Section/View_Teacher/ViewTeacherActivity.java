package com.example.adminauotmaticattendance.Teacher_Section.View_Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminauotmaticattendance.R;
import com.example.adminauotmaticattendance.Setting_section.SaveTheme;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewTeacherActivity extends AppCompatActivity {

    private Spinner mdepartment;
    private RecyclerView mrecyclerView;
    private RecyclerView.Adapter mviewteacheradapter;
    private  RecyclerView.LayoutManager mviewteacherlayoutManager;
    private ProgressBar mprogressbar;
    private TextView emptylist;
    SaveTheme saveTheme;
    String userid,department;
    int count=0,size=0;
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
        setContentView(R.layout.activity_view_teacher);
        setTitle("Teachers List");
        Bundle bundle=getIntent().getExtras();
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        mdepartment=findViewById(R.id.spinnerdepart);
        emptylist=findViewById(R.id.emptylist);
        emptylist.setVisibility(View.INVISIBLE);
        mprogressbar=findViewById(R.id.progressBar);
        mrecyclerView=findViewById(R.id.recyclerView);
        mrecyclerView.setNestedScrollingEnabled(false);
        mrecyclerView.setHasFixedSize(true);
        mviewteacherlayoutManager=new LinearLayoutManager(ViewTeacherActivity.this);
        mrecyclerView.setLayoutManager(mviewteacherlayoutManager);
        mviewteacheradapter=new view_TeacherAdapter(getdatasetviewteacher(), ViewTeacherActivity.this);
//        department = mdepartment.getSelectedItem().toString();
//        getteacherid();
        if(bundle!=null)
        {
            String department=bundle.getString("depart");
            mdepartment.setSelection(((ArrayAdapter<String>)mdepartment.getAdapter()).getPosition(department));
        }
        mrecyclerView.setAdapter(mviewteacheradapter);

        mdepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
            resultsViewteacher.clear();
            mviewteacheradapter.notifyDataSetChanged();
            count=0;
            emptylist.setVisibility(View.INVISIBLE);
            mprogressbar.setVisibility(View.VISIBLE);
            String selectedDepartment = (String) mdepartment.getItemAtPosition(i);
            department=selectedDepartment;
            getteacherid();
            mviewteacheradapter.notifyDataSetChanged();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

    }




    private void getteacherid() {

        DatabaseReference userdb= FirebaseDatabase.getInstance().getReference().child("Users").child("Teachers");
        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
//                  size=(int) dataSnapshot.getChildrenCount();
                    for(DataSnapshot match:dataSnapshot.getChildren())
                    {
                        fetchteachersinfo(match.getKey());
                    }

                }else{
                    emptylist.setVisibility(View.VISIBLE);
                    mprogressbar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchteachersinfo(String key){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Teachers").child(key);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String key=snapshot.getKey().toString();
                    String name=snapshot.child("name").getValue().toString();
                    String profilepic=snapshot.child("profilepic").getValue().toString();
                    String Department=snapshot.child("department").getValue().toString();
                    String phone=snapshot.child("phone").getValue().toString();
                    String email=snapshot.child("email").getValue().toString();
                   if(Department.equals(department)){
                       count++;
                       viewteacherobject obk=new viewteacherobject(profilepic,name,Department,phone,email,key);
                       resultsViewteacher.add(obk);
                   }
                }
                if(count!=0)
                {
                    emptylist.setVisibility(View.INVISIBLE);
                }else{
                    emptylist.setVisibility(View.VISIBLE);
                }
                mviewteacheradapter.notifyDataSetChanged();
                mprogressbar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private ArrayList<viewteacherobject> resultsViewteacher=new ArrayList<viewteacherobject>();
    private List<viewteacherobject> getdatasetviewteacher() {
        return resultsViewteacher;
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
