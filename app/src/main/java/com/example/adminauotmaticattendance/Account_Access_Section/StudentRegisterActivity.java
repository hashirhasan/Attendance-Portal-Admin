package com.example.adminauotmaticattendance.Account_Access_Section;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.adminauotmaticattendance.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StudentRegisterActivity extends AppCompatActivity {

    private Button msignup;
    private EditText mEmail,mPassword,mName,myear,mrollno;
    private Spinner mbranch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);


        mName= findViewById(R.id.name);
        mEmail=findViewById(R.id.email);
        mrollno=findViewById(R.id.rollno);
        myear=findViewById(R.id.year);
        mbranch=findViewById(R.id.branch);
        msignup=findViewById(R.id.signup);

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mName.getText().toString();
                final String email = mEmail.getText().toString();
                final String year = myear.getText().toString();
                final String rollno = mrollno.getText().toString();
                final String branch = mbranch.getSelectedItem().toString();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(year).child(branch).child(rollno);
                            HashMap userinfo=new HashMap();
                            userinfo.put("name",name);
                            userinfo.put("email",email);
                            userinfo.put("branch",branch);
                            userinfo.put("rollno",rollno);
                            databaseReference.updateChildren(userinfo);
                            DatabaseReference studentsub1reference=databaseReference.child("subjects").child("Maths");
                                HashMap sub1info=new HashMap();
                                sub1info.put("Attended",0);
                                sub1info.put("Total Classes",0);
                                sub1info.put("Total Ac",0);
                                studentsub1reference.updateChildren(sub1info);
                            DatabaseReference studentsub2reference=databaseReference.child("subjects").child("Physics");
                            HashMap sub2info=new HashMap();
                            sub2info.put("Attended",0);
                            sub2info.put("Total Classes",0);
                            sub2info.put("Total Ac",0);
                            studentsub2reference.updateChildren(sub2info);
                            DatabaseReference studentsub3reference=databaseReference.child("subjects").child("English");
                            HashMap sub3info=new HashMap();
                            sub3info.put("Attended",0);
                            sub3info.put("Total Classes",0);
                            sub3info.put("Total Ac",0);
                            studentsub3reference.updateChildren(sub3info);

                    }
                });
            }

}