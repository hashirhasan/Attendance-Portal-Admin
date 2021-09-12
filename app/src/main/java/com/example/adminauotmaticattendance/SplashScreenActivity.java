package com.example.adminauotmaticattendance;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adminauotmaticattendance.Account_Access_Section.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class
SplashScreenActivity  extends AppCompatActivity {

    public static Boolean started=false;
    private FirebaseAuth mauth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(getApplication(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }else{
            Intent intent=new Intent(getApplication(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }
    }
}
