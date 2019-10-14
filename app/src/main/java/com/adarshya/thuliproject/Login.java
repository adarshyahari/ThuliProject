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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private TextView mSignUpLink;
    private TextView mForgotPassword;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = (EditText) findViewById(R.id.email_field);
        mPasswordField = (EditText) findViewById(R.id.password_field);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mSignUpLink = (TextView) findViewById(R.id.sign_up_link);
        mForgotPassword = (TextView) findViewById(R.id.forgot_password);

        mSignUpLink.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(Login.this, ContractorMain.class));
        }
    }

    private void login() {
        String userEmail = mEmailField.getText().toString();
        String userPaswd = mPasswordField.getText().toString();
        if (userEmail.isEmpty()) {
            mEmailField.setError("Enter Email");
            mEmailField.requestFocus();
        } else if (userPaswd.isEmpty()) {
            mPasswordField.setError("Enter Password");
            mPasswordField.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                                    startActivity(new Intent(Login.this, ContractorMain.class));
                                }
                    else {
                        Toast.makeText(Login.this, "Not successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                finish();
                login();
                break;
            case R.id.sign_up_link:
                finish();
                startActivity(new Intent(Login.this, SignUp.class));
                break;
            case R.id.forgot_password:
                startActivity(new Intent(Login.this, PasswordReset.class));
                break;

        }
    }

}


