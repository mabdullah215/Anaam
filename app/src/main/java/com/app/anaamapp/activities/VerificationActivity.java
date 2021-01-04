package com.app.anaamapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.anaamapp.R;
import com.app.anaamapp.data.DataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    private String verificationid;
    private FirebaseAuth mAuth;
    String code="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mAuth = FirebaseAuth.getInstance();
        EditText etText = findViewById(R.id.et_num);
        RelativeLayout tvSubmit =findViewById(R.id.tv_proceed);
        ImageView imgBack=findViewById(R.id.img_back);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String code=etText.getText().toString().trim();
                if(code!=null)
                {
                    verifyCode(code);
                }
            }
        });

        String number=getIntent().getStringExtra("number");
        TextView tvNumDetails=findViewById(R.id.tv_num_details);
        tvNumDetails.setText("We have sent the code on: "+number);
        Toast.makeText(VerificationActivity.this, "Please wait", Toast.LENGTH_SHORT).show();
        AsyncTask.execute(new Runnable()
        {
            @Override
            public void run()
            {
                sendVerificationCode(number);
            }
        });
    }

    private void verifyCode(String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }

    private void sendVerificationCode(String number)
    {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(number)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            String number=getIntent().getStringExtra("number");
                            Toast.makeText(VerificationActivity.this, "Verification Successful.!", Toast.LENGTH_SHORT).show();
                            DataSource source=DataSource.getInstance(getBaseContext());
                            source.getCurrentUser().setNumber(number);
                            startActivity(new Intent(getBaseContext(),MainProfileSettings.class));
                            finish();

                        } else
                        {
                            Toast.makeText(VerificationActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
    {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
        {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
        {
            code = phoneAuthCredential.getSmsCode();
        }

        @Override
        public void onVerificationFailed(FirebaseException e)
        {
            Toast.makeText(VerificationActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };


}