package com.example.zdroa.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.aid.NetworkCheck;
import com.example.zdroa.myapplication.session.SessionHandler;

public class MainActivity extends AppCompatActivity {

    private SessionHandler sessionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finish();
//        startActivity(new Intent(this, RegisterActivity.class));
        startActivity(new Intent(this, PopulateListsActivity.class));

//        sessionHandler = new SessionHandler(getApplicationContext());
//
//        if (sessionHandler.getID() != null) {
//            finish();
//            startActivity(new Intent(this, LoginAreaActivity.class));
//        }

    }

    public void main_onButtonClick(View v) {
        if (NetworkCheck.check(this)) {
            switch (v.getId()) {
                case R.id.main_b_gotoLoginActivity:
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    break;

                case R.id.main_b_gotoRegisterActivity:
                    startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                    break;
            }
        } else {
            Toast.makeText(this, "No internet connection detected.", Toast.LENGTH_SHORT).show();
        }
    }
}
