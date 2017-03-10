package com.khome.kdaydin.sudroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.khome.kdaydin.sudroid.MainActivity.USRCRE;

public class LoginInfo extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText mUsername;
    EditText mPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mUsername = (EditText) findViewById(R.id.get_username);
        mPass = (EditText) findViewById(R.id.get_pass);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        sharedPreferences = getSharedPreferences(USRCRE, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCheck(mUsername)&&inputCheck(mPass)){
                    editor.putString("USERNAME",mUsername.getText().toString());
                    editor.putString("PASS",mPass.getText().toString());
                    editor.putInt("HAS_ID_PASS",1);
                    editor.apply();
                    Toast.makeText(LoginInfo.this, "Login Info Added!", Toast.LENGTH_SHORT).show();
                    // Attempt Login and Finish this activity
                    Intent i = new Intent(getApplicationContext(),BannerLogin.class);
                    startActivity(i);
                    finish();
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    boolean inputCheck(EditText mEditText){
        if(mEditText.getText().toString().matches("")){
            mEditText.requestFocus();
            mEditText.setError("Cannot be empty");
            return false;
        }
        else {
            return true;
        }
    }
}
