package com.example.adminauotmaticattendance.Attendance_section.View_Attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminauotmaticattendance.Attendance_section.SubjectAttendanceActivity;
import com.example.adminauotmaticattendance.R;

public class view_attenViewHolders  extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mname,mac,mattend,mtotalclass,mattendance;

    public view_attenViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mname=itemView.findViewById(R.id.name);
        mattendance=itemView.findViewById(R.id.attendancepercent);
         mac =itemView.findViewById(R.id.ac);
        mattend=itemView.findViewById(R.id.attended);
        mtotalclass=itemView.findViewById(R.id.totalclasses);

    }


    @Override
    public void onClick(View v) {
//        Toast.makeText(v.getContext(), "working", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(v.getContext(), SubjectAttendanceActivity.class);
        Bundle b=new Bundle();
        b.putString("subject",mname.getText().toString());
        b.putString("attended",mattend.getText().toString());
        b.putString("totalclasses",mtotalclass.getText().toString());
        b.putString("ac",mac.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);

    }
}
