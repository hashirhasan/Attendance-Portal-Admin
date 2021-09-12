package com.example.adminauotmaticattendance.TimeTable_Section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.adminauotmaticattendance.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TimeTableActivity extends AppCompatActivity{

    private ProgressBar progressBar;
    TextView notimetable;
    private PhotoView mimageView;
    final static float move=200;
    float ratio=1.0f;
    int basedist;
    float baseratio;
    String branch,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            branch=bundle.getString("branch");
            year=bundle.getString("year");
        }
        setTitle("Time Table --> "+branch+"("+year+" year)");
        progressBar=findViewById(R.id.progressBar);
        mimageView=findViewById(R.id.timetableview);
        notimetable=findViewById(R.id.notimetable);
        notimetable.setVisibility(View.INVISIBLE);
        StorageReference mImageStorage = FirebaseStorage.getInstance().getReference();
        StorageReference ref = mImageStorage.child("timetable").child(year).child(branch).child("timetable.jpeg");

        ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downUri = task.getResult();
                    String imageUrl = downUri.toString();
                    Glide.with(getApplication()).load(imageUrl).fitCenter().into(mimageView);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mimageView.getDrawable()!=null) {
                                progressBar.setVisibility(View.INVISIBLE);
                                handler.removeCallbacksAndMessages(null);
                            }
                            handler.postDelayed(this, 1000);
                        }
                    }, 1000);
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    notimetable.setVisibility(View.VISIBLE);
                }
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