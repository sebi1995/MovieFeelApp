package com.example.zdroa.myapplication.activities.accountmanagement;

import static com.example.zdroa.myapplication.handlers.UserSessionHandler.USER_EMAIL;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.USER_FIRSTNAME;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.USER_ID;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.USER_PASSWORD;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.USER_PERSON_TYPE;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.USER_SURNAME;
import static com.example.zdroa.myapplication.handlers.UserSessionHandler.USER_TITLE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.main.HomeActivity;
import com.example.zdroa.myapplication.handlers.HttpRequestHandler;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout loginLL;
    private EditText usernameEt;
    private EditText passwordEt;
    private UserSessionHandler userSession;
    private TextView errorMessageTv;
    private Button registerButton;
    private ProgressDialog progressDialog;


    void checkFieldsForEmptyValues(Button button) {

        String f1 = usernameEt.getText().toString();
        String f2 = passwordEt.getText().toString();

        if (f1.equals("") || f2.equals("")) {
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userSession = new UserSessionHandler(getApplicationContext());

        registerButton = findViewById(R.id.login_b_login);
        usernameEt = findViewById(R.id.login_et_username);
        passwordEt = findViewById(R.id.login_et_password);
        loginLL = findViewById(R.id.login_ll_login);
        errorMessageTv = findViewById(R.id.login_tv_login_error);


//        etUsername.setText("admin");
//        etPassword.setText("admin123");

        TextWatcher checkIfFieldsAreFilledIn = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // check Fields For Empty Values
                checkFieldsForEmptyValues(registerButton);
            }
        };
        usernameEt.addTextChangedListener(checkIfFieldsAreFilledIn);
        passwordEt.addTextChangedListener(checkIfFieldsAreFilledIn);
        checkFieldsForEmptyValues(registerButton);
    }

    public void login_login(View v) {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        loginLL.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.setTitle("Attempting to log in.");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String username = usernameEt.getText().toString();
        final String password = passwordEt.getText().toString();

        // Response received from the server
        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {

                    userSession.setId(jsonResponse.getInt(USER_ID));
                    userSession.setTitle(jsonResponse.getString(USER_TITLE));
                    userSession.setFirstName(jsonResponse.getString(USER_FIRSTNAME));
                    userSession.setSurname(jsonResponse.getString(USER_SURNAME));
                    userSession.setEmail(jsonResponse.getString(USER_EMAIL));
                    userSession.setPassword(jsonResponse.getString(USER_PASSWORD));
                    userSession.setPersonType(jsonResponse.getString(USER_PERSON_TYPE));

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    LoginActivity.this.startActivity(new Intent(this, HomeActivity.class));
                    finish();
                } else {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    loginLL.setVisibility(View.VISIBLE);
                    errorMessageTv.append("Login Failed:\n" +
                            "Wrong username or password.\n" +
                            "Please check your credentials and try again.\n");
                }

            } catch (JSONException e) {
                loginLL.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
                Toast.makeText(this, "Error connecting to server.", Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        };

        Response.ErrorListener errorListener = error -> {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            loginLL.setVisibility(View.VISIBLE);
            errorMessageTv.setText("Error connecting to service.");
            Log.e(this.getClass().getName(), error.toString());
        };
        Volley.newRequestQueue(this).add(HttpRequestHandler.login(responseListener, username, password, errorListener));
    }
}
