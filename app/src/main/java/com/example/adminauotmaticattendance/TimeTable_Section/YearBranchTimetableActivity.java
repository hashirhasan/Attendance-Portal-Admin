package com.example.adminauotmaticattendance.TimeTable_Section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.adminauotmaticattendance.R;
import com.example.adminauotmaticattendance.Setting_section.SaveTheme;
import com.example.adminauotmaticattendance.TimeTable_Section.TimeTableActivity;

public class YearBranchTimetableActivity extends AppCompatActivity {

    Spinner mbranch,myear;
    String branch,year;
    private Button mtimetable,muploadtimetable;
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

        if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
        {
            setTheme(R.style.CreamTheme);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_branch_timetable);
        setTitle("Time-Table");
        mbranch=findViewById(R.id.branch);
        myear=findViewById(R.id.year);
        mtimetable=findViewById(R.id.opentimetable);
        muploadtimetable=findViewById(R.id.uploadtimetable);
        mbranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i
                    , long id) {
                String selectedBranch = (String) mbranch.getItemAtPosition(i);
                branch=selectedBranch;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        myear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String selectedSubject = (String) myear.getItemAtPosition(i);
                year=selectedSubject;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mtimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(YearBranchTimetableActivity.this, TimeTableActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("branch",branch);
                bundle.putString("year",year);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
            }
        });

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













