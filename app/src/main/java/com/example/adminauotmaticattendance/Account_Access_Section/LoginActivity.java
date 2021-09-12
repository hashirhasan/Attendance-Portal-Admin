package com.example.adminauotmaticattendance.Account_Access_Section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adminauotmaticattendance.MainActivity;
import com.example.adminauotmaticattendance.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button mlogin;
    private TextView msignup,signintext,forgot;
    private EditText mEmail,mPassword;
     private ConstraintLayout layout;
     private LinearLayout signinlayout;
//    private ProgressBar progressBar;
    private FirebaseAuth mauth;
    private ImageView mloginimg;
    private ProgressDialog dialog;
    private Boolean wificonnected=false,mobileconnectd=false;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

     GoogleSignInClient mGoogleSignInClient;
    private  static int  RC_SIGN_IN=100;
    GoogleSignInAccount acct,account;
    int r=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Admin Login");
        layout=findViewById(R.id.layout);
        signinlayout=findViewById(R.id.signinlayout);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.INVISIBLE);
        firebaseAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null && r==0)
                {
//                    Toast.makeText(LoginActivity.this, "hurray", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                    finish();
                    return;
                }
            }
        };
        mauth= FirebaseAuth.getInstance();
        msignup=findViewById(R.id.signup);
        signintext=findViewById(R.id.sigintext);
        mlogin=findViewById(R.id.login);
        forgot=findViewById(R.id.forgot);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mloginimg=findViewById(R.id.loginimg);
//        progressBar.setVisibility(View.INVISIBLE);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(), FogotPassActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.rightstart,R.anim.leftend);
                return;
            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(layout.getWindowToken(), 0);
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
                if(networkInfo!=null && networkInfo.isConnected())
                {
                    wificonnected=networkInfo.getType()== ConnectivityManager.TYPE_WIFI;
                    mobileconnectd=networkInfo.getType()== ConnectivityManager.TYPE_MOBILE;

                    if(wificonnected || mobileconnectd)
                    {
                if (!email.isEmpty() && !password.isEmpty()) {
                    dialog = new ProgressDialog(LoginActivity.this);
                    dialog.setMessage("Logging in....");
                    dialog.show();
//                    progressBar.setVisibility(View.VISIBLE);
//                    signinlayout.setVisibility(View.INVISIBLE);
                    mauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {

//                                progressBar.setVisibility(View.INVISIBLE);
//                                signinlayout.setVisibility(View.VISIBLE);
                                  dialog.dismiss();
                                mPassword.setText("");
                                mPassword.requestFocus();
                                mauth.fetchSignInMethodsForEmail(email).
                                        addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                                boolean check = !task.getResult().getSignInMethods().isEmpty();
                                                if (!check) {
                                                    Toast.makeText(LoginActivity.this, "Email not registered", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                    });
                }
                }else{
                        Toast.makeText(LoginActivity.this,"Internet is too slow!", Toast.LENGTH_SHORT).show();
                    }
                    }else{
                        Toast.makeText(LoginActivity.this,"No Internet!", Toast.LENGTH_SHORT).show();
                    }
            }
        });




    }




    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mauth.removeAuthStateListener(firebaseAuthStateListener);
    }

    public void hidekeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

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