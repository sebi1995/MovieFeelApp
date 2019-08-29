package com.example.zdroa.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.aid.MovieUtils;
import com.example.zdroa.myapplication.exec.RandomIntegerClass;
import com.example.zdroa.myapplication.requests.Register_Request;
import com.example.zdroa.myapplication.requests.Username_Check_Request;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private Calendar myCalendar = Calendar.getInstance();
    private SimpleDateFormat sqlDate;
    private boolean surnameValid = false;
    private boolean firstnameValid = false;
    private boolean emailValid = false;
    private boolean repeatEmailValid = false;
    private boolean usernameValid = false;
    private boolean passwordValid = false;
    private Button bRegister;
    private Spinner sTitle;

    private TextInputLayout tilFirstname;
    private TextInputLayout tilSurname;
    private TextInputLayout tilDOB;
    private TextInputLayout tilEmail;
    private TextInputLayout tilRepeatemail;
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;
    private EditText etFirstname;
    private EditText etSurname;
    private EditText etDOB;
    private EditText etEmail;
    private EditText etRepeatemail;
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvUsernameCheck;
    private Boolean usernameChecked = false;
    private RadioGroup unOrEmail;

    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fa = this;

        setupPageComponents();

        sTitle.setAdapter(
                new ArrayAdapter<>(
                        this,
                        R.layout.registerspinner,
                        R.id.spinnerText,
                        MovieUtils.TITLES_ENTRIES_LIST));

        bRegister.setEnabled(false);

        usernameSelect(unOrEmail);

        //add onTextChangedListeners
        etSurname.addTextChangedListener(new MyTextWatcher(etSurname));
        etFirstname.addTextChangedListener(new MyTextWatcher(etFirstname));
        etEmail.addTextChangedListener(new MyTextWatcher(etEmail));
        etRepeatemail.addTextChangedListener(new MyTextWatcher(etRepeatemail));
        etPassword.addTextChangedListener(new MyTextWatcher(etPassword));
    }

    private void setupPageComponents() {
        sTitle = findViewById(R.id.register_spinner_title);
        etFirstname = findViewById(R.id.register_et_firstname);
        etSurname = findViewById(R.id.register_et_surname);
        etDOB = findViewById(R.id.register_et_DOB);
        etEmail = findViewById(R.id.register_et_email);
        etRepeatemail = findViewById(R.id.register_et_repeatemail);
        etUsername = findViewById(R.id.register_et_username);
        etPassword = findViewById(R.id.register_et_password);
        bRegister = findViewById(R.id.register_b_register);
        tilFirstname = findViewById(R.id.register_til_firstname);
        tilSurname = findViewById(R.id.register_til_surname);
        tilDOB = findViewById(R.id.register_til_dob);
        tilEmail = findViewById(R.id.register_til_email);
        tilRepeatemail = findViewById(R.id.register_til_repeatemail);
        tilUsername = findViewById(R.id.register_til_username);
        tilPassword = findViewById(R.id.register_til_password);
        tvUsernameCheck = findViewById(R.id.register_tv_usernameChecker);
        unOrEmail = findViewById(R.id.register_rg_username_selector);
    }

    public void registerRequest(View v) {
        final String title = sTitle.getSelectedItem().toString();
        final String firstname = etFirstname.getText().toString();
        final String surname = etSurname.getText().toString();
        final String SQLdate = sqlDate.format(myCalendar.getTime());
        final String email = etEmail.getText().toString();
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        final int gen_key = RandomIntegerClass.randomInt(1, 100);
        final String usernameORemail = String.valueOf(usernameChecked);

        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (new JSONObject(response).getBoolean("success")) {
                        fa.finish();
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
            }
        };

        Register_Request registerRequest = new Register_Request(title, firstname, surname, SQLdate, email, username, password, gen_key, usernameORemail, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);
    }

    public void register_showCalendar(View v) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(java.util.Calendar.YEAR, year);
                myCalendar.set(java.util.Calendar.MONTH, monthOfYear);
                myCalendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
                sqlDate = new SimpleDateFormat("yyyyMMdd", Locale.UK);

                etDOB.setText(sdf.format(myCalendar.getTime()));
            }
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
                    if (surnameValid && firstnameValid && emailValid && repeatEmailValid && passwordValid) {
                        bRegister.setEnabled(true);
                    }
                    etUsername.removeTextChangedListener(new MyTextWatcher(etUsername));
                    tvUsernameCheck.setVisibility(View.GONE);
                    etUsername.setVisibility(View.GONE);
                    break;
                case R.id.register_rb_username:
                    usernameChecked = true;
                    if (surnameValid && firstnameValid && emailValid && repeatEmailValid && !usernameValid && passwordValid) {
                        bRegister.setEnabled(false);
                    } else if (surnameValid && firstnameValid && emailValid && repeatEmailValid && usernameValid && passwordValid) {
                        bRegister.setEnabled(true);
                    }
                    etUsername.addTextChangedListener(new MyTextWatcher(etUsername));
                    tvUsernameCheck.setVisibility(View.VISIBLE);
                    etUsername.setVisibility(View.VISIBLE);
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
                        bRegister.setEnabled(false);
                    }
                    surnameValid = surnameCheck();
                    break;
                case R.id.register_et_firstname:
                    if (!firstnameCheck()) {
                        bRegister.setEnabled(false);
                    }
                    firstnameValid = firstnameCheck();
                    break;
                case R.id.register_et_email:
                    if (!emailCheck()) {
                        bRegister.setEnabled(false);
                    }
                    emailValid = emailCheck();
                    break;
                case R.id.register_et_repeatemail:
                    if (!repeatEmailCheck()) {
                        bRegister.setEnabled(false);
                    }
                    repeatEmailValid = repeatEmailCheck();
                    break;
                case R.id.register_et_username:
                    if (!usernameCheck()) {
                        bRegister.setEnabled(false);
                    }
                    usernameValid = usernameCheck();
                    break;
                case R.id.register_et_password:
                    if (!passwordCheck()) {
                        bRegister.setEnabled(false);
                    }
                    passwordValid = passwordCheck();
                    break;
            }

            if (surnameValid && firstnameValid && emailValid && repeatEmailValid && passwordValid) {
                if (usernameChecked) {
                    if (usernameValid) {
                        bRegister.setEnabled(true);
                    } else bRegister.setEnabled(false);
                } else bRegister.setEnabled(true);
            }

        }

        private boolean surnameCheck() {
            String input = etSurname.getText().toString().trim();
            if (input.isEmpty()) {
                tilSurname.setError("Please enter your surname.");
                requestFocus(tilSurname);
                return false;
            } else if (input.length() < 3) {
                tilSurname.setError("Surname cannot be less than 3 characters.");
                requestFocus(tilSurname);
                return false;
            } else if (input.length() > 15) {
                tilSurname.setError("Surname cannot be longer than 15 characters.");
                requestFocus(tilSurname);
                return false;
            } else {
                tilSurname.setErrorEnabled(false);
            }
            return true;
        }

        private boolean firstnameCheck() {
            String input = etFirstname.getText().toString().trim();

            if (input.isEmpty()) {
                tilFirstname.setError("Please enter your surname.");
                requestFocus(tilFirstname);
                return false;
            } else if (input.length() < 3) {
                tilFirstname.setError("Firstname cannot be less than 3 characters.");
                requestFocus(tilFirstname);
                return false;
            } else if (input.length() > 40) {
                tilFirstname.setError("Firstname cannot be longer than 40 characters.");
                requestFocus(tilFirstname);
                return false;
            } else tilFirstname.setErrorEnabled(false);

            return true;
        }

        private boolean emailCheck() {
            String input = etEmail.getText().toString().trim();

            if (input.length() < 6) {
                tilEmail.setError("Email cannot be less than 6 characters.");
                requestFocus(tilEmail);
                return false;
            } else if (input.length() > 40) {
                tilEmail.setError("Email cannot be more than 40 characters.");
                requestFocus(tilEmail);
                return false;
            } else if (!isValidEmail(input)) {
                tilEmail.setError("Please enter a valid email");
                requestFocus(tilEmail);
                return false;
            } else tilEmail.setErrorEnabled(false);

            return true;
        }

        private boolean repeatEmailCheck() {
            String input = etRepeatemail.getText().toString().trim();

            if (input.length() < 6) {
                tilRepeatemail.setError("Email cannot be less than 6 characters.");
                requestFocus(tilRepeatemail);
                return false;
            } else if (input.length() > 40) {
                tilRepeatemail.setError("Email cannot be more than 40 characters.");
                requestFocus(tilRepeatemail);
                return false;
            } else if (!isValidEmail(input)) {
                tilRepeatemail.setError("Please enter a valid email");
                requestFocus(tilRepeatemail);
                return false;
            } else if (!etEmail.getText().toString().trim().equals(input)) {
                tilRepeatemail.setError("Please enter the same email address twice.");
                requestFocus(tilRepeatemail);
                return false;
            } else {
                tilRepeatemail.setErrorEnabled(false);
                tilEmail.setErrorEnabled(false);
            }

            return true;
        }

        private boolean usernameCheck() {
            if (usernameChecked) {
                String input = etUsername.getText().toString().trim();

                if (input.isEmpty()) {
                    tilUsername.setError("Username cannot be empty.");
                    requestFocus(tilUsername);
                    return false;

                } else if (input.length() < 3) {
                    tilUsername.setError("Username cannot be less than 3 characters.");
                    requestFocus(tilUsername);
                    return false;

                } else if (input.length() > 15) {
                    tilUsername.setError("Username cannot be more than 15 characters.");
                    requestFocus(tilUsername);
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
                        tilUsername.setError("Username must contain numbers and letters.");
                        requestFocus(tilUsername);
                        return false;
                    }
                }
                final boolean[] username_exists = {false};
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean usernameExists = jsonResponse.getBoolean("usernameExists");

                            if (usernameExists) {
                                username_exists[0] = true;
                            } else {
                                etUsername.setTextColor(Color.GREEN);
                                tvUsernameCheck.setText("Username Available");
                                tvUsernameCheck.setTextColor(Color.GREEN);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Username_Check_Request unCheckRequest = new Username_Check_Request(etUsername.getText().toString(), usernameChecked, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(unCheckRequest);

                if (username_exists[0]) {
                    tilUsername.setError("Username unavailable");
                    requestFocus(tilUsername);
                    return false;
                } else tilUsername.setErrorEnabled(false);
            }
            return true;
        }

        private boolean passwordCheck() {
            String input = etPassword.getText().toString().trim();

            if (input.isEmpty()) {
                tilPassword.setError("Password cannot be empty.");
                requestFocus(tilPassword);
                return false;

            } else if (input.length() < 3) {
                tilPassword.setError("Password cannot be less than 3 characters.");
                requestFocus(tilPassword);
                return false;

            } else if (input.length() > 12) {
                tilPassword.setError("Password cannot be more than 12 characters.");
                requestFocus(tilPassword);
                return false;

            } else if (usernameChecked) {
                String username = etUsername.getText().toString().trim();
                if (username.contains(input)) {
                    tilPassword.setError("Password cannot be similar to username.");
                    requestFocus(tilPassword);
                    return false;
                } else tilPassword.setErrorEnabled(false);
                return true;

            } else if (!usernameChecked) {
                String email = etEmail.getText().toString().trim();
                if (email.contains(input)) {
                    tilPassword.setError("Password cannot be silimar to email.");
                    requestFocus(tilPassword);
                    return false;
                } else tilPassword.setErrorEnabled(false);
                return true;

            } else tilPassword.setErrorEnabled(false);

            return true;
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}
