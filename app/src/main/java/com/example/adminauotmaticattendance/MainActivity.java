package com.example.adminauotmaticattendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adminauotmaticattendance.Student_section.ViewStudentActivity;
import com.example.adminauotmaticattendance.Teacher_Section.View_Teacher.ViewTeacherActivity;
import com.example.adminauotmaticattendance.Setting_section.SaveTheme;
import com.example.adminauotmaticattendance.Setting_section.SettingActivity;
import com.example.adminauotmaticattendance.TimeTable_Section.TimeTableActivity;
import com.example.adminauotmaticattendance.TimeTable_Section.YearBranchTimetableActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity  {

    private FirebaseAuth mauth;
    private DatabaseReference databaseReference;
  TextView mgentext;
  private LinearLayout mstudents,mdashboard,mteachers,mtimetable;
  private  TextView personname;
//    private TextView username;
    private Menu mymenu;
    private MenuInflater menuitem;
    private String userid,gender,name,themeName;
    private Animation pulse;
    ProgressBar progressBar;
    MenuInflater inflater;
    SaveTheme saveTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        saveTheme=new SaveTheme(this);
        if(saveTheme.getTheme()==true)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
//        setTheme(someSettings.get(PREFFERED_THEME) ? R.style.AppTheme : R.style.CreamTheme);
        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
             setTheme(R.style.CreamTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       setTitle("Admin Dashboard");


        mauth=FirebaseAuth.getInstance();
        userid=mauth.getCurrentUser().getUid();
        personname=findViewById(R.id.personname);
       mstudents= (LinearLayout) findViewById(R.id.students);
      mteachers=(LinearLayout) findViewById(R.id.teachers);
      mdashboard=(LinearLayout) findViewById(R.id.dashboard);
      progressBar=findViewById(R.id.progressBar);
      mtimetable=findViewById(R.id.timetable);
      progressBar.setVisibility(View.VISIBLE);
      mdashboard.setVisibility(View.INVISIBLE);
        pulse = AnimationUtils.loadAnimation(this, R.anim.blink);
        progressBar.startAnimation(pulse);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.clearAnimation();
        mdashboard.setVisibility(View.VISIBLE);


        mstudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ViewStudentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                return;
            }
        });


        mteachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ViewTeacherActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                return;
            }
        });

        mtimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, YearBranchTimetableActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                return;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mymenu=menu;
        inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.setting)
        {
            Intent intent=new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.rightstart,R.anim.leftend);
            finish();
        }
        return true;
    }



    @Override
    protected void onStart() {
        super.onStart();
    }


}