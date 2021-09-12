package com.example.adminauotmaticattendance.Teacher_Section.View_Teacher;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminauotmaticattendance.R;

public class view_TeacherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mname,mdepartment,mphone,memail,meditprofile;
   public ImageView mprofilepic;
    public view_TeacherViewHolder(@NonNull View itemView) {
        super(itemView);
//        itemView.setOnClickListener(this);
        mname = itemView.findViewById(R.id.name);
       mprofilepic=itemView.findViewById(R.id.profilepic);
       mdepartment = itemView.findViewById(R.id.department);
       mphone = itemView.findViewById(R.id.phone);
       memail = itemView.findViewById(R.id.email);
       meditprofile=itemView.findViewById(R.id.edit_pro);

    }


    @Override
    public void onClick(View v) {
//        Toast.makeText(v.getContext(), "working", Toast.LENGTH_SHORT).show();
//        Intent intent=new Intent(v.getContext(), UpdateAcActivity.class);
//        Bundle b=new Bundle();
//        b.putString("key",mkey.getText().toString());
//        b.putString("rollno",mrollno.getText().toString());
//        b.putString("total Ac",mtotalac.getText().toString());
//        b.putString("branch",mbranch.getText().toString());
//        b.putString("subject",msubject.getText().toString());
//        intent.putExtras(b);
//        v.getContext().startActivity(intent);
    }
}