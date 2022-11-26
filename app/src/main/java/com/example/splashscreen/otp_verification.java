package com.example.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp_verification extends AppCompatActivity {
    private EditText otpET1,otpET2,otpET3,otpET4,otpET5,otpET6;
    FirebaseAuth mAuth;
    Button verify_otp_button,back_button;
    String otp_entered,verificationID,class_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        verify_otp_button = findViewById(R.id.verify_button);
        Intent i = getIntent();
        class_name = i.getStringExtra("class_name");
        String mobile_val = "+91 ";
        mobile_val += i.getStringExtra("mobile");
        final TextView mobile_no = findViewById(R.id.mobile_num_text);
        mobile_no.setText(mobile_val);
        otpET1 = findViewById(R.id.otpET1);
        otpET2 = findViewById(R.id.otpET2);
        otpET3 = findViewById(R.id.otpET3);
        otpET4 = findViewById(R.id.otpET4);
        otpET5 = findViewById(R.id.otpET5);
        otpET6 = findViewById(R.id.otpET6);
        back_button = findViewById(R.id.back_button);
        mAuth = FirebaseAuth.getInstance();
//        sendverificationcode(i.getStringExtra("mobile"));
        next_et();
        verify_otp_button.setOnClickListener(view -> {
            if(true)
            {
                otp_entered = otpET1.getText().toString();
                otp_entered += otpET2.getText().toString();
                otp_entered += otpET3.getText().toString();
                otp_entered += otpET4.getText().toString();
                otp_entered += otpET5.getText().toString();
                otp_entered += otpET6.getText().toString();
//                verifycode(otp_entered);
                Intent intent = new Intent(otp_verification.this,student_homescreen.class);
                startActivity(intent);
                finish();
            }
        });
        back_button.setOnClickListener(view -> {
            if(class_name.equals("parent_login")){
                Intent intent = new Intent(otp_verification.this,parent_login.class);
                startActivity(intent);
                finish();
            }
            else if(class_name.equals("student_login")){
                Intent intent = new Intent(otp_verification.this,student_login.class);
                startActivity(intent);
                finish();
            }
            else  if(class_name.equals("teacher_login")){
                Intent intent = new Intent(otp_verification.this,teacher_login.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(otp_verification.this,"Error generated",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendverificationcode(String mobile_no) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+mobile_no)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String otp_generated = credential.getSmsCode();
            if (otp_generated != null){
                verifycode(otp_generated);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(otp_verification.this,"Verification failed",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.

            // Save verification ID and resending token so we can use them later
            super.onCodeSent(s, token);
            verificationID = s;
        }
    };
    private void verifycode(String otp_generated) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID,otp_generated);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(otp_verification.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                    if(class_name.equals("parent_login")){
                        Intent intent = new Intent(otp_verification.this,parent_homescreen.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(class_name.equals("student_login")){
                        Intent intent = new Intent(otp_verification.this,student_homescreen.class);
                        startActivity(intent);
                        finish();
                    }
                    else  if(class_name.equals("teacher_login")){
                        Intent intent = new Intent(otp_verification.this,teacher_homescreen.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(otp_verification.this,"Error generated",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(otp_verification.this,"Invalid OTP entered",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void next_et() {
        otpET1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    otpET2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otpET2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    otpET3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otpET3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    otpET4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        otpET4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    otpET5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });otpET5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    otpET6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}