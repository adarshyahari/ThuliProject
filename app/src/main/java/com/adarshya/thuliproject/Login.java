package com.adarshya.thuliproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;
    private RadioButton mChoiceButton;
    private RadioGroup mChoiceGroup;
    private Button mLoginButton;
    private TextView mGatewayLink;
    private TextView mForgotPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = (EditText) findViewById(R.id.email_field);
        mPasswordField = (EditText) findViewById(R.id.password_field);
        mChoiceGroup = (RadioGroup) findViewById( R.id.choice_group);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mGatewayLink = (TextView) findViewById(R.id.gateway_link);
        mForgotPassword=(TextView) findViewById(R.id.forgot_password);

        mGatewayLink.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    finish();
                    Toast.makeText(Login.this, "User connected ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, UserMain.class));
                } else {
                    Toast.makeText(Login.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    private void login(){
        String userEmail = mEmailField.getText().toString();
        String userPaswd = mPasswordField.getText().toString();
        choice = mChoiceGroup.getCheckedRadioButtonId();
        mChoiceButton = (RadioButton) findViewById(choice);
        Toast.makeText(Login.this, mChoiceButton.getText().toString(), Toast.LENGTH_SHORT).show();
        if (userEmail.isEmpty()) {
            mEmailField.setError("Enter Email");
            mEmailField.requestFocus();
        } else if (userPaswd.isEmpty()) {
            mPasswordField.setError("Enter Password");
            mPasswordField.requestFocus();
        }else {
            mAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkEmailVerification();

                    } else {
                        Toast.makeText(Login.this, "Not successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void checkEmailVerification()
    {
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag==true) {
            if (mChoiceButton.getText().toString().equals("User")){
                startActivity(new Intent(Login.this, UserMain.class));
            } else if(mChoiceButton.getText().toString().equals("Contractor")){
                startActivity(new Intent(Login.this, ContractorMain.class));
            }
        }
        else{
            Toast.makeText(Login.this, "Verify email", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                login();
                break;
            case R.id.gateway_link:
                finish();
                startActivity(new Intent(Login.this, Gateway.class));
                break;
            case R.id.forgot_password:
                startActivity(new Intent(Login.this, PasswordReset.class));
                break;

        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


}

