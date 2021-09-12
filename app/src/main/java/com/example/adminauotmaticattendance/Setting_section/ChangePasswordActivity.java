package com.example.adminauotmaticattendance.Setting_section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.adminauotmaticattendance.Account_Access_Section.LoginActivity;
import com.example.adminauotmaticattendance.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText mcurrentpass,mnewpass,mconfirmnewpass;
    Button mresetPass;
    DatabaseReference mdatabaseReference;
    FirebaseUser user;
    FirebaseAuth mauth;
    String currentpassword,newpassword,confirmpassword,userid,email;
    GoogleSignInClient  googleSignInClient;
    LinearLayout mresetpage;
    ProgressBar progressBar;
    private Boolean wificonnected=false,mobileconnectd=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.passAppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

// Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        mauth= FirebaseAuth.getInstance();
         progressBar=findViewById(R.id.progressBar1);
         progressBar.setVisibility(View.INVISIBLE);
        mresetpage=findViewById(R.id.resetpage);
        mcurrentpass=findViewById(R.id.pass);
        mnewpass=findViewById(R.id.newpass1);
        mconfirmnewpass=findViewById(R.id.newpass2);
        mresetPass=findViewById(R.id.resetpass);
        mauth=FirebaseAuth.getInstance();
        user = mauth.getCurrentUser();
        userid=user.getUid();
        email=user.getEmail();
//        mdatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Students").child(userid);
//        getuserinfo();
        mcurrentpass.requestFocus(1);
        mresetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mresetpage.getWindowToken(), 0);
                currentpassword=mcurrentpass.getText().toString();
                newpassword=mnewpass.getText().toString();
                confirmpassword=mconfirmnewpass.getText().toString();
//                Toast.makeText(ChangePasswordActivity.this, currentpassword+newpassword+confirmpassword, Toast.LENGTH_SHORT).show();
                ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                if(networkInfo!=null && networkInfo.isConnected())
                {
                    wificonnected=networkInfo.getType()== ConnectivityManager.TYPE_WIFI;
                    mobileconnectd=networkInfo.getType()== ConnectivityManager.TYPE_MOBILE;

                    if(wificonnected || mobileconnectd) {
                        if (!currentpassword.equals("") && !newpassword.equals("") && !confirmpassword.equals("") && newpassword.equals(confirmpassword)) {

                            progressBar.setVisibility(View.VISIBLE);
                            mresetpage.setAlpha(0.4f);

                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(email, currentpassword);
//                            Toast.makeText(ChangePasswordActivity.this, credential.toString(), Toast.LENGTH_SHORT).show();
                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                user.updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    mresetpage.setAlpha(1f);
                                                            Toast.makeText(ChangePasswordActivity.this, "Password Updated!", Toast.LENGTH_SHORT).show();
                                                  final Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            progressBar.setVisibility(View.VISIBLE);
                                                            mresetpage.setAlpha(0.4f);
                                                        }
                                                    }, 1000);

                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            mauth.signOut();
                                                            googleSignInClient.signOut();
                                                            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            Toast.makeText(ChangePasswordActivity.this, "Login Again with New Password", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                    }, 2300);
                                                        } else {
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            mresetpage.setAlpha(1f);
                                                            mnewpass.setText("");
                                                            mconfirmnewpass.setText("");
                                                            mnewpass.requestFocus(4);
                                                            if (newpassword.length() < 6) {
                                                                Toast.makeText(ChangePasswordActivity.this, "New Password is short!", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(ChangePasswordActivity.this, "Error! Password Not Updated!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }
                                                });
                                            } else {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                mresetpage.setAlpha(1f);
                                                mnewpass.setText("");
                                                mconfirmnewpass.setText("");
                                                mnewpass.requestFocus(4);
                                                Toast.makeText(ChangePasswordActivity.this, "Check Your Old Password", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            }
                             }else{
                                 Toast.makeText(ChangePasswordActivity.this,"Internet is too slow!", Toast.LENGTH_SHORT).show();
                                }
                                }else{
                                   Toast.makeText(ChangePasswordActivity.this,"No Internet!", Toast.LENGTH_SHORT).show();
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
//    private void getuserinfo()
//    {
//        mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists() && snapshot.getChildrenCount()>0)
//                {
//                    Map map=(Map) snapshot.getValue();
//
//                    if(map.get("email")!=null)
//                    {
//                        email=map.get("email").toString();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public void hidekeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}