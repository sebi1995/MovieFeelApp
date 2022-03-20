package com.example.zdroa.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.aid.NetworkCheck;
import com.example.zdroa.myapplication.session.Session_Class;

public class MainActivity extends AppCompatActivity {

    public static Activity fa;
    private Session_Class session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fa = this;

        session = new Session_Class(getApplicationContext());

        if (session.getID() != null) {
            fa.finish();
            startActivity(new Intent(fa, LoginAreaActivity.class));
        }

    }

    public void main_onButtonClick(View v) {
        if (new NetworkCheck().NetworkCheck(fa)) {
            switch (v.getId()) {

                case R.id.main_b_gotoLoginActivity:
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    break;

                case R.id.main_b_gotoRegisterActivity:
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    break;
            }
        } else Toast.makeText(fa, "No internet connection detected.", Toast.LENGTH_SHORT).show();
    }
}
