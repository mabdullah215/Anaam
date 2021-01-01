package com.app.anaamapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.anaamapp.R;

public class EnterOTPActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_o_t_p);
        EditText etNumber=findViewById(R.id.et_num);
        RelativeLayout tvProceed=findViewById(R.id.tv_proceed);
        ImageView imgBack=findViewById(R.id.img_back);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               finish();
            }
        });

        tvProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String num=etNumber.getText().toString().trim();

                if(num.isEmpty())
                {
                    Toast.makeText(EnterOTPActivity.this, "Please Enter the number", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(getBaseContext(),VerificationActivity.class).putExtra("number",num));

            }
        });

    }
}