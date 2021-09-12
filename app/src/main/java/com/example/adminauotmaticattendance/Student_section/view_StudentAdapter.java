package com.example.adminauotmaticattendance.Student_section;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminauotmaticattendance.Attendance_section.View_Attendance.ViewAttendanceActivity;
import com.example.adminauotmaticattendance.Profile_section.StudentEditProfileActivity;
import com.example.adminauotmaticattendance.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

import static android.app.AlertDialog.*;

public class view_StudentAdapter extends RecyclerView.Adapter<view_StudentViewHolder> {
    private List<viewstudentobject> viewstudentobjectList;
    private Context context;
   FirebaseAuth auth;
   FirebaseUser user;

    public view_StudentAdapter(List<viewstudentobject> viewstudentobjectList, Context context)
    {
        this.viewstudentobjectList = viewstudentobjectList;
        this.context=context;
    }
    @NonNull
    @Override
    public view_StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         auth=FirebaseAuth.getInstance();
         user=auth.getCurrentUser();
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_student_item,null,false);
        RecyclerView.LayoutParams lp =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        view_StudentViewHolder rcv=new view_StudentViewHolder((layoutView));
        return rcv;

    }

    @Override
    public void onBindViewHolder(@NonNull view_StudentViewHolder holder, int position) {
        holder.mname.setText(viewstudentobjectList.get(position).getName());
        if(!viewstudentobjectList.get(position).getProfilepic().equals("default"))
        {
            Glide.with(context).load(viewstudentobjectList.get(position).getProfilepic()).into(holder.mprofilepic);
        }else{
            Glide.with(context).load(R.mipmap.profile).into(holder.mprofilepic);
        }
        holder.mrollno.setText(viewstudentobjectList.get(position).getRollno());
        holder.memail.setText(viewstudentobjectList.get(position).getEmail());
        holder.mphone.setText(viewstudentobjectList.get(position).getPhone());
        holder.mkey.setText(viewstudentobjectList.get(position).getKey());
        holder.meditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("userid",viewstudentobjectList.get(position).getKey());
                Intent intent=new Intent(context, StudentEditProfileActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
               // Toast.makeText(context, "working", Toast.LENGTH_SHORT).show();
//                context.overridePendingTransition(R.anim.rightstart,R.anim.leftend );
                return;
            }
        });
        holder.mattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("userid",viewstudentobjectList.get(position).getKey());
                bundle.putString("name",viewstudentobjectList.get(position).getName());
                Intent intent=new Intent(context, ViewAttendanceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtras(bundle);
                context.startActivity(intent);
               // Toast.makeText(context, "working", Toast.LENGTH_SHORT).show();
//                context.overridePendingTransition(R.anim.rightstart,R.anim.leftend );
                return;
            }
        });
        holder.mremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "deededf", Toast.LENGTH_SHORT).show();
                Builder dialog=new Builder(context);
                dialog.setTitle("Are You Sure?");
                dialog.setMessage("This Student will be deleted permanently!");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(viewstudentobjectList.get(position).getKey());
                        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isSuccessful())
                                       {
                                           Toast.makeText(context, "Student Deleted", Toast.LENGTH_SHORT).show();
                                           Intent intent=new Intent(context, ViewStudentActivity.class);
                                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                           context.startActivity(intent);
                                       }else{
                                           Toast.makeText(context, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                                       }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return viewstudentobjectList.size();
    }
}
