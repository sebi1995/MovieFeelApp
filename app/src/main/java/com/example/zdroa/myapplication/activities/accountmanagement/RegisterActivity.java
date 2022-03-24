package com.example.zdroa.myapplication.activities.accountmanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.handlers.HttpRequestHandler;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterActivity extends AppCompatActivity {

    private final Calendar myCalendar = Calendar.getInstance();
    private SimpleDateFormat sqlDate;
    private boolean surname = false;
    private boolean firstname = false;
    private boolean email = false;
    private boolean repeatEmail = false;
    private boolean username = false;
    private boolean password = false;
    private boolean usernameChecked = false;

    private Button registerBtn;
    private Spinner sTitle;

    private TextInputLayout firstnameTIL;
    private TextInputLayout surnameTIL;
    private TextInputLayout dobTIL;
    private TextInputLayout emailTIL;
    private TextInputLayout repeatEmailTIL;
    private TextInputLayout usernameTIL;
    private TextInputLayout passwordTIL;

    private EditText firstnameET;
    private EditText surnameET;
    private EditText dobET;
    private EditText emailET;
    private EditText repeatEmailEt;
    private EditText usernameET;
    private EditText passwordET;

    private TextView usernameCheckTV;


    private RadioGroup unOrEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sTitle = findViewById(R.id.register_spinner_title);
        sTitle.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.registerspinner,
                R.id.spinnerText,
                Arrays.asList("Title", "Mr", "Mrs", "Ms", "Miss", "Dr")));

        firstnameET = findViewById(R.id.register_et_firstname);
        surnameET = findViewById(R.id.register_et_surname);
        dobET = findViewById(R.id.register_et_DOB);
        emailET = findViewById(R.id.register_et_email);
        repeatEmailEt = findViewById(R.id.register_et_repeatemail);
        usernameET = findViewById(R.id.register_et_username);
        passwordET = findViewById(R.id.register_et_password);

        registerBtn = findViewById(R.id.register_b_register);
        registerBtn.setEnabled(false);

        firstnameTIL = findViewById(R.id.register_til_firstname);
        surnameTIL = findViewById(R.id.register_til_surname);
        dobTIL = findViewById(R.id.register_til_dob);
        emailTIL = findViewById(R.id.register_til_email);
        repeatEmailTIL = findViewById(R.id.register_til_repeatemail);
        usernameTIL = findViewById(R.id.register_til_username);
        passwordTIL = findViewById(R.id.register_til_password);
        usernameCheckTV = findViewById(R.id.register_tv_usernameChecker);

        unOrEmail = findViewById(R.id.register_rg_username_selector);

        usernameSelect(unOrEmail);

        //Set onTextChanged listeners

        surnameET.addTextChangedListener(new MyTextWatcher(surnameET));
        firstnameET.addTextChangedListener(new MyTextWatcher(firstnameET));
        emailET.addTextChangedListener(new MyTextWatcher(emailET));
        repeatEmailEt.addTextChangedListener(new MyTextWatcher(repeatEmailEt));
        passwordET.addTextChangedListener(new MyTextWatcher(passwordET));
    }

    public void registerRequest(View v) {
        final String title = sTitle.getSelectedItem().toString();
        final String firstname = firstnameET.getText().toString();
        final String surname = surnameET.getText().toString();
        final String SQLdate = sqlDate.format(myCalendar.getTime());
        final String email = emailET.getText().toString();
        final String username = usernameET.getText().toString();
        final String password = passwordET.getText().toString();
        final int gen_key = new Random().nextInt((100 - 1) + 1) + 1;
        final String usernameORemail = String.valueOf(usernameChecked);

        // Response received from the server
        Response.Listener<String> responseListener = response -> {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                System.out.println(success);
                if (success) {
                    finish();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Registration Failed")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
        Volley.newRequestQueue(RegisterActivity.this).add(HttpRequestHandler.registerUser(responseListener, title, firstname, surname, SQLdate, email, username, password, gen_key, usernameORemail));
    }

    public void register_showCalendar(View v) {
        final DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
            sqlDate = new SimpleDateFormat("yyyyMMdd", Locale.UK);

            dobET.setText(sdf.format(myCalendar.getTime()));
        };

        new DatePickerDialog(RegisterActivity.this, date, myCalendar
                .get(java.util.Calendar.YEAR), myCalendar.get(java.util.Calendar.MONTH),
                myCalendar.get(java.util.Calendar.DAY_OF_MONTH)).show();

    }

    private void usernameSelect(RadioGroup radioGroup) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.register_rb_email:
                    usernameChecked = false;
                    if (surname && firstname && email && repeatEmail && password) {
                        registerBtn.setEnabled(true);
                    }
                    usernameET.removeTextChangedListener(new MyTextWatcher(usernameET));
                    usernameCheckTV.setVisibility(View.GONE);
                    usernameET.setVisibility(View.GONE);
                    break;
                case R.id.register_rb_username:
                    usernameChecked = true;
                    if (surname && firstname && email && repeatEmail && !username && password) {
                        registerBtn.setEnabled(false);
                    } else if (surname && firstname && email && repeatEmail && username && password) {
                        registerBtn.setEnabled(true);
                    }
                    usernameET.addTextChangedListener(new MyTextWatcher(usernameET));
                    usernameCheckTV.setVisibility(View.VISIBLE);
                    usernameET.setVisibility(View.VISIBLE);
                    break;
            }
        });
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (view.getId()) {
                case R.id.register_et_surname:
                    if (!surnameCheck()) {
                        registerBtn.setEnabled(false);
                    }
                    surname = surnameCheck();
                    break;
                case R.id.register_et_firstname:
                    if (!firstnameCheck()) {
                        registerBtn.setEnabled(false);
                    }
                    firstname = firstnameCheck();
                    break;
                case R.id.register_et_email:
                    if (!emailCheck()) {
                        registerBtn.setEnabled(false);
                    }
                    email = emailCheck();
                    break;
                case R.id.register_et_repeatemail:
                    if (!repeatEmailCheck()) {
                        registerBtn.setEnabled(false);
                    }
                    repeatEmail = repeatEmailCheck();
                    break;
                case R.id.register_et_username:
                    if (!usernameCheck()) {
                        registerBtn.setEnabled(false);
                    }
                    username = usernameCheck();
                    break;
                case R.id.register_et_password:
                    if (!passwordCheck()) {
                        registerBtn.setEnabled(false);
                    }
                    password = passwordCheck();
                    break;
            }

            if (surname && firstname && email && repeatEmail && password) {
                if (usernameChecked) {
                    if (username) {
                        registerBtn.setEnabled(true);
                    } else registerBtn.setEnabled(false);
                } else registerBtn.setEnabled(true);
            }

        }

        private boolean surnameCheck() {
            String input = surnameET.getText().toString().trim();
            if (input.isEmpty()) {
                surnameTIL.setError("Please enter your surname.");
                requestFocus(surnameTIL);
                return false;
            } else if (input.length() < 3) {
                surnameTIL.setError("Surname cannot be less than 3 characters.");
                requestFocus(surnameTIL);
                return false;
            } else if (input.length() > 15) {
                surnameTIL.setError("Surname cannot be longer than 15 characters.");
                requestFocus(surnameTIL);
                return false;
            } else surnameTIL.setErrorEnabled(false);
            return true;
        }

        private boolean firstnameCheck() {
            String input = firstnameET.getText().toString().trim();

            if (input.isEmpty()) {
                firstnameTIL.setError("Please enter your surname.");
                requestFocus(firstnameTIL);
                return false;
            } else if (input.length() < 3) {
                firstnameTIL.setError("Firstname cannot be less than 3 characters.");
                requestFocus(firstnameTIL);
                return false;
            } else if (input.length() > 40) {
                firstnameTIL.setError("Firstname cannot be longer than 40 characters.");
                requestFocus(firstnameTIL);
                return false;
            } else firstnameTIL.setErrorEnabled(false);

            return true;
        }

        private boolean emailCheck() {
            String input = emailET.getText().toString().trim();

            if (input.length() < 6) {
                emailTIL.setError("Email cannot be less than 6 characters.");
                requestFocus(emailTIL);
                return false;
            } else if (input.length() > 40) {
                emailTIL.setError("Email cannot be more than 40 characters.");
                requestFocus(emailTIL);
                return false;
            } else if (!isValidEmail(input)) {
                emailTIL.setError("Please enter a valid email");
                requestFocus(emailTIL);
                return false;
            } else emailTIL.setErrorEnabled(false);

            return true;
        }

        private boolean repeatEmailCheck() {
            String input = repeatEmailEt.getText().toString().trim();

            if (input.length() < 6) {
                repeatEmailTIL.setError("Email cannot be less than 6 characters.");
                requestFocus(repeatEmailTIL);
                return false;
            } else if (input.length() > 40) {
                repeatEmailTIL.setError("Email cannot be more than 40 characters.");
                requestFocus(repeatEmailTIL);
                return false;
            } else if (!isValidEmail(input)) {
                repeatEmailTIL.setError("Please enter a valid email");
                requestFocus(repeatEmailTIL);
                return false;
            } else if (!emailET.getText().toString().trim().equals(input)) {
                repeatEmailTIL.setError("Please enter the same email address twice.");
                requestFocus(repeatEmailTIL);
                return false;
            } else {
                repeatEmailTIL.setErrorEnabled(false);
                emailTIL.setErrorEnabled(false);
            }

            return true;
        }

        private boolean usernameCheck() {
            if (usernameChecked) {
                String input = usernameET.getText().toString().trim();

                if (input.isEmpty()) {
                    usernameTIL.setError("Username cannot be empty.");
                    requestFocus(usernameTIL);
                    return false;

                } else if (input.length() < 3) {
                    usernameTIL.setError("Username cannot be less than 3 characters.");
                    requestFocus(usernameTIL);
                    return false;

                } else if (input.length() > 15) {
                    usernameTIL.setError("Username cannot be more than 15 characters.");
                    requestFocus(usernameTIL);
                    return false;

                } else {
                    boolean containsNumbers = false;
                    boolean containsLetters = false;
                    for (char c : input.toCharArray()) {
                        if (Character.isDigit(c)) {
                            containsNumbers = true;
                        } else if (Character.isLetter(c)) {
                            containsLetters = true;
                        }
                    }
                    if (!containsNumbers && containsLetters || containsNumbers && !containsLetters) {
                        usernameTIL.setError("Username must contain numbers and letters.");
                        requestFocus(usernameTIL);
                        return false;
                    }
                }
                AtomicBoolean usernameExists = new AtomicBoolean(false);
                Volley.newRequestQueue(RegisterActivity.this).add(HttpRequestHandler.checkUsernameExists(response -> {
                            try {
                                if (new JSONObject(response).getBoolean("usernameExists")) {
                                    usernameExists.set(true);
                                } else {
                                    usernameET.setTextColor(Color.GREEN);
                                    usernameCheckTV.setText("Username Available");
                                    usernameCheckTV.setTextColor(Color.GREEN);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        usernameET.getText().toString(),
                        usernameChecked));

                if (usernameExists.get()) {
                    usernameTIL.setError("Username unavailable");
                    requestFocus(usernameTIL);
                    return false;
                } else usernameTIL.setErrorEnabled(false);
            }
            return true;
        }

        private boolean passwordCheck() {
            String input = passwordET.getText().toString().trim();

            if (input.isEmpty()) {
                passwordTIL.setError("Password cannot be empty.");
                requestFocus(passwordTIL);
                return false;

            } else if (input.length() < 3) {
                passwordTIL.setError("Password cannot be less than 3 characters.");
                requestFocus(passwordTIL);
                return false;

            } else if (input.length() > 12) {
                passwordTIL.setError("Password cannot be more than 12 characters.");
                requestFocus(passwordTIL);
                return false;

            } else if (usernameChecked) {
                String username = usernameET.getText().toString().trim();
                if (username.contains(input)) {
                    passwordTIL.setError("Password cannot be similar to username.");
                    requestFocus(passwordTIL);
                    return false;
                } else passwordTIL.setErrorEnabled(false);
                return true;

            } else if (!usernameChecked) {
                String email = emailET.getText().toString().trim();
                if (email.contains(input)) {
                    passwordTIL.setError("Password cannot be silimar to email.");
                    requestFocus(passwordTIL);
                    return false;
                } else passwordTIL.setErrorEnabled(false);
                return true;

            } else passwordTIL.setErrorEnabled(false);

            return true;
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
