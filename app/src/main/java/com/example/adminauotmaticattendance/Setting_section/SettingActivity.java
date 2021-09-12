package com.example.adminauotmaticattendance.Setting_section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.adminauotmaticattendance.MainActivity;
import com.example.adminauotmaticattendance.R;
import com.example.adminauotmaticattendance.SplashScreenActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

    Button msignout;
    Switch mswitch;
    FirebaseAuth mauth;
    TextView mtheme1,mtheme2;
    GoogleSignInClient googleSignInClient;
    SaveTheme saveTheme;
    LinearLayout mchangepass;
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
        setContentView(R.layout.activity_setting);
        setTitle("Settings");
     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

// Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        mauth= FirebaseAuth.getInstance();
        msignout=findViewById(R.id.signout);
        mswitch=findViewById(R.id.switchbtn);
        mtheme1=findViewById(R.id.theme1);
        mtheme2=findViewById(R.id.theme2);
        mchangepass=findViewById(R.id.changepassword);
       if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
       {
           mtheme1.setTypeface(mtheme1.getTypeface(), Typeface.NORMAL);
           mtheme1.setTextSize(20);
           mtheme1.setTextColor(Color.parseColor("#000000"));
           mtheme2.setTypeface(mtheme2.getTypeface(), Typeface.BOLD);
           mtheme2.setTextSize(22);
           mtheme2.setTextColor(Color.parseColor("#D81B60"));
           mswitch.setChecked(true);
       }else{
           mtheme1.setTypeface(mtheme1.getTypeface(), Typeface.BOLD);
           mtheme2.setTypeface(mtheme2.getTypeface(), Typeface.NORMAL);
           mtheme1.setTextColor(Color.parseColor("#D81B60"));
           mtheme2.setTextColor(Color.parseColor("#000000"));
           mtheme1.setTextSize(22);
           mtheme2.setTextSize(20);

       }
       if(saveTheme.getTheme()==true)
       {
           mswitch.setChecked(true);
       }

      mswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked)
              {
                  saveTheme.setTheme(true);
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                  restartActivity();
              }else{
                  saveTheme.setTheme(false);
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                  restartActivity();
              }
          }

          private void restartActivity() {
              Intent i=new Intent(getApplicationContext(),SettingActivity.class);
              startActivity(i);
              finish();
          }
      });

       mchangepass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(), ChangePasswordActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               overridePendingTransition(R.anim.rightstart,R.anim.leftend);
               return;
           }
       });

        msignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut();
                mauth.signOut();
                Intent intent=new Intent(getApplicationContext(), SplashScreenActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.leftstart,R.anim.rightend);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.leftstart,R.anim.rightend);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() ==android.R.id.home) {
          Intent i=new Intent(getApplicationContext(),MainActivity.class);
          startActivity(i);
              finish();
            overridePendingTransition(R.anim.leftstart,R.anim.rightend);
        }
        return super.onOptionsItemSelected(item);
    }
}