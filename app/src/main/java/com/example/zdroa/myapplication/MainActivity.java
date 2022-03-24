package com.example.zdroa.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.activities.accountmanagement.LoginActivity;
import com.example.zdroa.myapplication.activities.accountmanagement.RegisterActivity;
import com.example.zdroa.myapplication.activities.main.movies.MoviesActivity;
import com.example.zdroa.myapplication.activities.questionnaire.QuestionnaireActivity;
import com.example.zdroa.myapplication.aid.NetworkCheck;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.utils.AppSettings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserSessionHandler userSession = new UserSessionHandler(this);
        if (AppSettings.IS_LOGIN_SYSTEM_ENABLED) {
            if (userSession.getId() != null) {
                if (userSession.getPersonType() == null) {
                    startActivityIfDeviceHasNetworkConnectionOrShowToast(QuestionnaireActivity.class);
                    return;
                } else {
                    startActivityIfDeviceHasNetworkConnectionOrShowToast(MoviesActivity.class);
                }
            }
        } else {
            startActivityIfDeviceHasNetworkConnectionOrShowToast(MoviesActivity.class);
        }
        findViewById(R.id.main_b_navigate_to_login).setOnClickListener(view -> startActivityIfDeviceHasNetworkConnectionOrShowToast(LoginActivity.class));
        findViewById(R.id.main_b_navigate_to_register).setOnClickListener(view -> startActivityIfDeviceHasNetworkConnectionOrShowToast(RegisterActivity.class));
    }

    private void startActivityIfDeviceHasNetworkConnectionOrShowToast(Class<?> activity) {
        if (deviceHasNetworkConnection(this)) {
            startActivity(new Intent(this, activity));
            finish();
        }
    }

    private boolean deviceHasNetworkConnection(Activity activity) {
        boolean networkConnection = NetworkCheck.hasNetworkConnection(activity);
        if (!networkConnection) {
            Toast.makeText(this, "Network connectivity issues.", Toast.LENGTH_SHORT).show();
        }
        return networkConnection;
    }

}
