package com.adarshya.thuliproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSignup extends AppCompatActivity implements View.OnClickListener {

    public EditText mUserNameField;
    public EditText mUserContactField;
    public EditText mUserAddressField;
    private EditText mUserEmailField;
    private EditText mUserPasswordField;
    private EditText mUserPasswordconfirmField;
    private Button mUserSignupButton;
    private TextView mLoginLink;
    String username, userphno, useraddress, useremail;
    String userlocation=" ";

    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final Spinner mSpinner = (Spinner) findViewById(R.id.user_location_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.location_array, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userlocation = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mUserNameField = (EditText) findViewById(R.id.user_name_field);
        mUserContactField = (EditText) findViewById(R.id.user_contact_field);
        mUserAddressField = (EditText) findViewById(R.id.user_address_field);
        mUserEmailField = (EditText) findViewById(R.id.user_email_field);
        mUserPasswordField = (EditText) findViewById(R.id.user_password_field);
        mUserPasswordconfirmField = (EditText) findViewById(R.id.user_passwordconfirm_field);
        mUserSignupButton = (Button) findViewById(R.id.user_signup_button);
        mLoginLink = (TextView) findViewById(R.id.login_link);

        mLoginLink.setOnClickListener(this);
        mUserSignupButton.setOnClickListener(this);
    }
    private void Sign(){

        username=mUserNameField.getText().toString().trim();
        userphno=mUserContactField.getText().toString().trim();
        useraddress=mUserAddressField.getText().toString().trim();
        useremail = mUserEmailField.getText().toString().trim();
        String userpassword = mUserPasswordField.getText().toString();
        String usercpassword = mUserPasswordconfirmField.getText().toString();
        if(username.isEmpty())
        {   mUserNameField.setError("Enter Name");
            mUserNameField.requestFocus();
        } else if(userphno.isEmpty())
        {   mUserContactField.setError("Enter Contact");
            mUserContactField.requestFocus();
        } else if(useraddress.isEmpty()) {
            mUserAddressField.setError("Enter Address");
            mUserAddressField.requestFocus();
        }
        else if(useremail.isEmpty()) {
            mUserEmailField.setError("Enter Email");
            mUserEmailField.requestFocus();
        }
        else if(userpassword.isEmpty()) {
            mUserPasswordField.setError("Enter Password");
            mUserPasswordField.requestFocus();
        }
        else if(usercpassword.isEmpty()){
            mUserPasswordField.setError("Enter Password Confirmation");
            mUserPasswordField.requestFocus();
        }
        else if(userpassword.equals(usercpassword)){
            mAuth.createUserWithEmailAndPassword(useremail, userpassword).addOnCompleteListener(UserSignup.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendEmailVerification();
                    } else {
                        Toast.makeText(UserSignup.this, "Not successful", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else {
            Toast.makeText(this, "Password not matching!", Toast.LENGTH_LONG).show();
        }
    }
    private void sendEmailVerification()
    {
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendContractorData();
                        Toast.makeText(UserSignup.this, "Successfully verified ", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(UserSignup.this, UserMain.class));
                    }
                }
            });
        }
        else{
            Toast.makeText(UserSignup.this, "Verify email", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendContractorData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef= firebaseDatabase.getReference("user");
        UserProfile userprofile = new UserProfile(username, userphno, useraddress, userlocation, useremail);
        myRef.child(mAuth.getUid()).setValue(userprofile);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_signup_button:
                Sign();
                break;
            case R.id.login_link:{
                Intent I = new Intent(UserSignup.this, Login.class);
                startActivity(I);
                finish();
            }
        }

    }
}
