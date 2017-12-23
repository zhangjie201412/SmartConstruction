package com.jay.iot.smartconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by H151136 on 12/14/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText mUserEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private CheckBox mRememberCheckBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton = findViewById(R.id.bt_login);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PileActivity.class);
                startActivity(intent);
            }
        });
    }
}
