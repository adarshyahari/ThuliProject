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

public class ContractorSignup extends AppCompatActivity implements View.OnClickListener {

    public EditText mContractorCompanyField;
    public EditText mContractorNameField;
    public EditText mContractorContactField;
    public EditText mContractorAddressField;
    public Spinner mContractorLocationSpinner;
    private EditText mContractorEmailField;
    private EditText mContractorPasswordField;
    private EditText mContractorPasswordconfirmField;
    private Button mContractorSignupButton;
    private TextView mContractorLoginLink;

    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    String compname, contname, contphno, contaddress, contemail;
    String contlocation=" ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final Spinner mSpinner = (Spinner) findViewById(R.id.contractor_location_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.location_array, R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                contlocation = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mContractorCompanyField = (EditText) findViewById(R.id.contractor_company_field);
        mContractorNameField = (EditText) findViewById(R.id.contractor_name_field);
        mContractorContactField = (EditText) findViewById(R.id.contractor_contact_field);
        mContractorAddressField = (EditText) findViewById(R.id.contractor_address_field);
        mContractorEmailField = (EditText) findViewById(R.id.contractor_email_field);
        mContractorPasswordField = (EditText) findViewById(R.id.contractor_password_field);
        mContractorPasswordconfirmField = (EditText) findViewById(R.id.contractor_passwordconfirm_field);
        mContractorSignupButton = (Button) findViewById(R.id.contractor_signup_button);
        mContractorLoginLink = (TextView) findViewById(R.id.contractor_login_link);
        mContractorLoginLink.setOnClickListener(this);
        mContractorSignupButton.setOnClickListener(this);
    }
    private void Sign(){

        compname = mContractorCompanyField.getText().toString().trim();
        contname=mContractorNameField.getText().toString().trim();
        contphno=mContractorContactField.getText().toString().trim();
        contaddress=mContractorAddressField.getText().toString().trim();
        contemail = mContractorEmailField.getText().toString().trim();
        String password = mContractorPasswordField.getText().toString();
        String cpassword = mContractorPasswordconfirmField.getText().toString();

        if(compname.isEmpty()){
            mContractorCompanyField.setError("Enter Company Name");
            mContractorCompanyField.requestFocus();
        } else if(contname.isEmpty())
        {   mContractorNameField.setError("Enter Name");
            mContractorNameField.requestFocus();
        } else if(contphno.isEmpty())
        {   mContractorContactField.setError("Enter Contact");
            mContractorContactField.requestFocus();
        } else if(contaddress.isEmpty()) {
            mContractorAddressField.setError("Enter Address");
            mContractorAddressField.requestFocus();
        } else if(contemail.isEmpty()) {
            mContractorEmailField.setError("Enter Email");
            mContractorEmailField.requestFocus();
        } else if(password.isEmpty()) {
            mContractorPasswordField.setError("Enter Password");
            mContractorPasswordField.requestFocus();
        } else if(cpassword.isEmpty()){
            mContractorPasswordField.setError("Enter Password Confirmation");
            mContractorPasswordField.requestFocus();
        } else if(password.equals(cpassword)){
            mAuth.createUserWithEmailAndPassword(contemail, password).addOnCompleteListener(ContractorSignup.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        sendEmailVerification();
                    } else {
                        Toast.makeText(ContractorSignup.this, "Not successful", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ContractorSignup.this, "Successfully verified ", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(ContractorSignup.this, ContractorMain.class));

                    }
                }
            });
        }
        else{
            Toast.makeText(ContractorSignup.this, "Verify email", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendContractorData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef= firebaseDatabase.getReference("contractor");
        ContractorProfile contprofile = new ContractorProfile(compname, contname, contphno, contaddress, contlocation, contemail);
        myRef.child(mAuth.getUid()).setValue(contprofile);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contractor_signup_button:
                Sign();
                break;
            case R.id.contractor_login_link:{
                finish();
                Intent I = new Intent(ContractorSignup.this, Login.class);
                startActivity(I);
            }
        }

    }
}

