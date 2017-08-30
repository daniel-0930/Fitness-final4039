package com.example.zhangzeyao.fitness_final4039;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zhangzeyao.fitness_final4039.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText userNameBox;
    private EditText userEmailBox;
    private EditText userPasswordBox;
    private EditText userConPwdBox;
    private CheckBox policyCheck;
    private Button nextStepButton;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        userNameBox = (EditText)findViewById(R.id.nameReBox);
        userEmailBox = (EditText)findViewById(R.id.emailReBox);
        userPasswordBox = (EditText)findViewById(R.id.passwordReBox);
        userConPwdBox = (EditText)findViewById(R.id.conpasReBox);
        policyCheck = (CheckBox)findViewById(R.id.checkReBox);
        nextStepButton = (Button)findViewById(R.id.nextStepButton);

        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptoNextStep();
            }
        });
    }

    public void attemptoNextStep(){
        final String username = userNameBox.getText().toString().trim();
        final String email = userEmailBox.getText().toString().trim();
        String password = userPasswordBox.getText().toString().trim();
        String conpwd = userConPwdBox.getText().toString().trim();



        if(username.equals("")||email.equals("")||password.equals("")||conpwd.equals("") || !policyCheck.isChecked()){
            if (username.equals("")){
                userNameBox.setError("Name should not be blank");
            }
            if(email.equals("")){
                userEmailBox.setError("Email should not be blank");
            }
            if(password.equals("")){
                userPasswordBox.setError("Password should not be blank");
            }
            if(conpwd.equals("")){
                userConPwdBox.setError("Confirm Password should not be blank");
            }
            if(!policyCheck.isChecked()){
                Toast.makeText(RegisterActivity.this, "Please check the policy of the application of Fitness", Toast.LENGTH_SHORT).show();
            }

        } else if (!emailCheck(email)){
            userEmailBox.setError("User's Email is not valid");

        } else if(!conpwd.equals(password)){
            userConPwdBox.setError("Password is not confirmed");
        }else{
            progressDialog.setMessage("Registering User...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        progressDialog.dismiss();
                        User user = new User();
                        user.setUserEmail(email);
                        user.setUserName(username);
                        user.setUserId(firebaseAuth.getCurrentUser().getUid());
                        Intent nextIntent = new Intent(RegisterActivity.this, RegisterStep2Activity.class);
                        nextIntent.putExtra("User",user);
                        startActivity(nextIntent);
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Failure:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
