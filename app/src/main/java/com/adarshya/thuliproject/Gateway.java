package com.adarshya.thuliproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Gateway extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup mRadioGroup;
    private Button mNextButton;
    private RadioButton mRadioButton;
    public int radio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(this);
    }
    private void next(){
        radio = mRadioGroup.getCheckedRadioButtonId();
        mRadioButton = (RadioButton) findViewById(radio);
        if (mRadioButton.getText().toString().equals("User")) {
            startActivity(new Intent(Gateway.this,UserSignup.class));
        } else if(mRadioButton.getText().toString().equals("Contractor")){
            startActivity(new Intent(Gateway.this, ContractorSignup.class));
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_button:
                next();
                break;
        }

    }

}

