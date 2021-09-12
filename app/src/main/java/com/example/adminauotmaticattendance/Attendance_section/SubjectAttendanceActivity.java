package com.example.adminauotmaticattendance.Attendance_section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adminauotmaticattendance.R;

public class SubjectAttendanceActivity extends AppCompatActivity {

    ProgressBar progressBar;
    CardView card;
    private TextView msubject,mAttendance,mAc,mAttended,mTotalclasses;
    String ac="",attended="",totalclasses="",subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_attendance);
        setTitle("Subject Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        card=findViewById(R.id.card);
        card.setVisibility(View.INVISIBLE);
        msubject=findViewById(R.id.subject);
        mAttendance=findViewById(R.id.attendance);
        mAc=findViewById(R.id.ac);
        mAttended=findViewById(R.id.attended);
        mTotalclasses=findViewById(R.id.totalclasses);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            subject=bundle.getString("subject");
            ac=bundle.getString("ac");
            attended=bundle.getString("attended");
            totalclasses=bundle.getString("totalclasses");
            msubject.setText(subject);
            mAttendance.setText(String.valueOf((Integer.valueOf(ac) + Integer.valueOf(attended)))+"/"+totalclasses);
            mAc.setText(ac);
            mAttended.setText(attended);
            mTotalclasses.setText(totalclasses);

        }

        if(!totalclasses.equals(""))
        {
            progressBar.setVisibility(View.INVISIBLE);
            card.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        if(true)
        {
            onBackPressed();
            overridePendingTransition(R.anim.leftstart,R.anim.rightend);
            return true;
        }else{
            return false;
        }
    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() ==android.R.id.home) {
//          onBackPressed();
//            overridePendingTransition(R.anim.leftstart,R.anim.rightend);
//        }
//        return super.onOptionsItemSelected(item);
//    }
}