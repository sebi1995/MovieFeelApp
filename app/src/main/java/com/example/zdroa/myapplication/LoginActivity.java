package com.example.zdroa.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.requests.Login_Request;
import com.example.zdroa.myapplication.session.SessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout llLogin;
    private EditText etUsername;
    private EditText etPassword;
    private SessionHandler sessionHandler;
    private TextView errorMsg;
    private ProgressDialog progressDoalog;

    public static Activity fa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fa = this;
//
//        session = new Session_Class(getApplicationContext());
//
//        etUsername = (EditText) findViewById(R.id.login_et_username);
//        etPassword = (EditText) findViewById(R.id.login_et_password);
//        llLogin = (LinearLayout) findViewById(R.id.login_ll_login);
//        errorMsg = (TextView) findViewById(R.id.login_tv_login_error);
//
//
////        etUsername.setText("admin");
////        etPassword.setText("admin123");
//
//        etUsername.addTextChangedListener(checkIfFieldsAreFilledIn);
//        etPassword.addTextChangedListener(checkIfFieldsAreFilledIn);
//        checkFieldsForEmptyValues();
        Intent intent = new Intent(this, MoviesActivity.class);
        startActivity(intent);
    }


    private TextWatcher checkIfFieldsAreFilledIn = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues() {
        Button regBtn = (Button) findViewById(R.id.login_b_login);

        String f1 = etUsername.getText().toString();
        String f2 = etPassword.getText().toString();

        if (f1.equals("") || f2.equals("")) {
            regBtn.setEnabled(false);
        } else {
            regBtn.setEnabled(true);
        }
    }

    public void loginOnClick(View v) {
        View view = fa.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        llLogin.setVisibility(View.GONE);


        progressDoalog = new ProgressDialog(fa);
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Attempting to log in.");
        progressDoalog.setMax(100);
        progressDoalog.setCancelable(false);
        progressDoalog.show();

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        int user_id = jsonResponse.getInt("user_id");
                        String username = jsonResponse.getString("user_username");
                        String password = jsonResponse.getString("user_password");
                        String title = jsonResponse.getString("user_title");
                        String surname = jsonResponse.getString("user_surname");
                        String firstname = jsonResponse.getString("user_firstname");
                        String pType = jsonResponse.getString("person_type");

                        sessionHandler.setId(user_id);
                        sessionHandler.setUserUsername(username);
                        sessionHandler.setUserPassword(password);
                        sessionHandler.setUserTitle(title);
                        sessionHandler.setUserSurname(surname);
                        sessionHandler.setUserFirstname(firstname);

                        if (pType.equals("null")) {
//                            sessionHandler.("user_type_bool", null, false);
                        } else {
//                            sessionHandler.setVar("user_type_bool", null, true);
//                            sessionHandler.setVar("user_type", pType, null);
                        }

                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
//                        MainActivity.fa.finish();
//                        LoginActivity.fa.finish();
                        if (RegisterActivity.fa != null) {
                            RegisterActivity.fa.finish();
                        }
                        LoginActivity.this.startActivity(new Intent(fa, LoginAreaActivity.class));
                    } else {
                        if (progressDoalog.isShowing()) {
                            progressDoalog.dismiss();
                        }
                        llLogin.setVisibility(View.VISIBLE);
                        errorMsg.setText("Login Failed:\n" +
                                "Wrong username or password.\n" +
                                "Please check your credentials and try again.\n");
                    }

                } catch (JSONException e) {
                    llLogin.setVisibility(View.VISIBLE);
                    progressDoalog.dismiss();
                    Toast.makeText(fa, "Error connecting to server.", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
            }
        };

        Login_Request loginRequest = new Login_Request(username, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(fa);
        queue.add(loginRequest);
    }
}
