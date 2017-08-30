package com.example.zhangzeyao.fitness_final4039;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.content.Intent;

import com.example.zhangzeyao.fitness_final4039.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ViewSwitcher viewSwitcher;

    private Button loginButton1;

    private Button loginButton2;

    private Button signUpButton;

    private Button cancelButton;
    private TextView forgetPasswordView;
    private EditText emailBox;
    private EditText passwordBox;

    private CheckBox skipCheckBox;

    private SharedPreferences mPreference;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference dbReference;

    private DatabaseHelperUser dbUser;

    @Override
    protected void onStart(){
        super.onStart();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("user");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapShot:dataSnapshot.getChildren()){
                    dbUser.addUser(userSnapShot.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewSwitcher = (ViewSwitcher)findViewById(R.id.loginviewSwitcher);
        loginButton1 = (Button)findViewById(R.id.loginButton);
        loginButton2 = (Button)findViewById(R.id.loginButton2);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        cancelButton = (Button)findViewById(R.id.cancelButton);
        forgetPasswordView = (TextView)findViewById(R.id.forgetPassLink);
        emailBox = (EditText) findViewById(R.id.emailBox);
        passwordBox = (EditText)findViewById(R.id.passwordBox);
        skipCheckBox = (CheckBox)findViewById(R.id.skipCheckBox);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        dbUser = new DatabaseHelperUser(getApplicationContext());

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbReference = firebaseDatabase.getReference("user");

        Log.i("User",String.valueOf(dbUser.GetAllUser().size()));
        mPreference = getSharedPreferences("User",MODE_PRIVATE);

        Animation in = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);

        viewSwitcher.setAnimation(in);
         viewSwitcher.setAnimation(out);


        if(getSharedPreferences("User", MODE_PRIVATE).getBoolean("isLogin",false))
        {
            Intent mainIntent = new Intent(MainActivity.this,MainUserActivity.class);
            Gson gson = new Gson();
            String json = getSharedPreferences("Student", MODE_PRIVATE).getString("currStudent","");
            User user = gson.fromJson(json,User.class);
            mainIntent.putExtra("User",user);
            startActivity(mainIntent);
            finish();
        }

        loginButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSwitcher.showNext();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSwitcher.showPrevious();
            }
        });

        loginButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempToLogin();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(signupIntent);
            }
        });
        forgetPasswordView.setFocusable(false);
        forgetPasswordView.setClickable(true);
        forgetPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.sendPasswordResetEmail(emailBox.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"Email sent successfully",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,"Failure"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        forgetPasswordView.setMovementMethod(LinkMovementMethod.getInstance());


    }

    public void attempToLogin(){

            progressDialog.setMessage("Logging in...");

            if(emailBox.getText().toString().trim().equals("") || passwordBox.getText().toString().trim().equals("")){
                if(emailBox.getText().toString().trim().equals("")){
                    emailBox.setError("Email Box cannot be empty");
                }
                if(emailCheck(emailBox.getText().toString().trim())){
                    emailBox.setError("Email is not valide");
                }
                if (passwordBox.getText().toString().trim().equals("")){
                    passwordBox.setError("Password cannot be empty");
                }
            } else{
                final SharedPreferences.Editor editor = mPreference.edit();
                final Gson gson = new Gson();
                if(skipCheckBox.isChecked()){
                    editor.putBoolean("isLogin",true);
                }
                progressDialog.setMessage("Logging in.....");
                progressDialog.show();




                firebaseAuth.signInWithEmailAndPassword(emailBox.getText().toString().trim(),passwordBox.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    progressDialog.dismiss();

                                    Calendar c = Calendar.getInstance();
                                    int second = c.get(Calendar.SECOND);
                                    int minute = c.get(Calendar.MINUTE);
                                    int hour = c.get(Calendar.HOUR_OF_DAY);
                                    int year = c.get(Calendar.YEAR);
                                    int month = c.get(Calendar.MONTH) + 1;
                                    int day = c.get(Calendar.DATE);

                                    String currenttime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
                                    User user = dbUser.getUser(emailBox.getText().toString().trim());
                                    user.setLoginTime(currenttime);
                                    SharedPreferences.Editor editor = mPreference.edit();
                                    editor.putBoolean("isLogin", true);
                                    Gson gson = new Gson();
                                    String json = gson.toJson(user);
                                    editor.putString("currUser", json);
                                    Intent newIntent = new Intent(MainActivity.this,MainUserActivity.class);
                                    newIntent.putExtra("User",user);
                                    startActivity(newIntent);



                                }
                                else{
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Failure:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


        }


    }

    public Boolean emailCheck(String inputEmail){
        boolean hasAt = false;
        boolean hasDot = false;
        String address = "";
        for(int i=0;i<inputEmail.length() && !hasAt;i++)
        {
            if(inputEmail.charAt(i) == '@')
            {
                hasAt = true;
                address = inputEmail.substring(i);
            }

        }

        if(hasAt){
            for(int i=0; i<address.length() && !hasDot;i++){

                if(address.charAt(i) == '.'){
                    hasDot = true;
                }
            }
            if(hasDot) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }


    }
}
