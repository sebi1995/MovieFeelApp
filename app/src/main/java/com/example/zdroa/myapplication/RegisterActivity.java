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
import com.example.zdroa.myapplication.exec.randInt;
import com.example.zdroa.myapplication.requests.Register_Request;
import com.example.zdroa.myapplication.requests.Username_Check_Request;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sqlDate;
    boolean surname = false, firstname = false, email = false, repeatEmail = false, username = false, password = false;
    Button bRegister;
    Spinner sTitle;

    TextInputLayout tilFirstname, tilSurname, tilDOB, tilEmail, tilRepeatemail, tilUsername, tilPassword;
    EditText etFirstname, etSurname, etDOB, etEmail, etRepeatemail, etUsername, etPassword;
    TextView tvUsernameCheck;

    Boolean usernameChecked = false;

    RadioGroup unOrEmail;

    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fa = this;

        sTitle = (Spinner) findViewById(R.id.register_spinner_title);
        String[] items = new String[]{"Title", "Mr", "Mrs", "Ms", "Miss", "Dr"};
        ArrayAdapter adapter = new ArrayAdapter<>(RegisterActivity.this, R.layout.registerspinner, R.id.spinnerText, items);
        sTitle.setAdapter(adapter);

        etFirstname = (TextInputEditText) findViewById(R.id.register_et_firstname);
        etSurname = (TextInputEditText) findViewById(R.id.register_et_surname);
        etDOB = (TextInputEditText) findViewById(R.id.register_et_DOB);
        etEmail = (TextInputEditText) findViewById(R.id.register_et_email);
        etRepeatemail = (TextInputEditText) findViewById(R.id.register_et_repeatemail);
        etUsername = (TextInputEditText) findViewById(R.id.register_et_username);
        etPassword = (TextInputEditText) findViewById(R.id.register_et_password);

        bRegister = (Button) findViewById(R.id.register_b_register);
        bRegister.setEnabled(false);

        tilFirstname = (TextInputLayout) findViewById(R.id.register_til_firstname);
        tilSurname = (TextInputLayout) findViewById(R.id.register_til_surname);
        tilDOB = (TextInputLayout) findViewById(R.id.register_til_dob);
        tilEmail = (TextInputLayout) findViewById(R.id.register_til_email);
        tilRepeatemail = (TextInputLayout) findViewById(R.id.register_til_repeatemail);
        tilUsername = (TextInputLayout) findViewById(R.id.register_til_username);
        tilPassword = (TextInputLayout) findViewById(R.id.register_til_password);
        tvUsernameCheck = (TextView) findViewById(R.id.register_tv_usernameChecker);

        unOrEmail = (RadioGroup) findViewById(R.id.register_rg_username_selector);

        usernameSelect(unOrEmail);

        //Set onTextChanged listeners

        etSurname.addTextChangedListener(new MyTextWatcher(etSurname));
        etFirstname.addTextChangedListener(new MyTextWatcher(etFirstname));
        etEmail.addTextChangedListener(new MyTextWatcher(etEmail));
        etRepeatemail.addTextChangedListener(new MyTextWatcher(etRepeatemail));
        etPassword.addTextChangedListener(new MyTextWatcher(etPassword));
    }

    public void registerRequest(View v) {
        final String title = sTitle.getSelectedItem().toString();
        final String firstname = etFirstname.getText().toString();
        final String surname = etSurname.getText().toString();
        final String SQLdate = sqlDate.format(myCalendar.getTime());
        final String email = etEmail.getText().toString();
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        final int gen_key = randInt.randomInt(1, 100);
        final String usernameORemail = String.valueOf(usernameChecked);

        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    System.out.println(success);
                    if (success) {
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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.register_rb_email:
                        usernameChecked = false;
                        if (surname && firstname && email && repeatEmail && password){
                            bRegister.setEnabled(true);
                        }
                        etUsername.removeTextChangedListener(new MyTextWatcher(etUsername));
                        tvUsernameCheck.setVisibility(View.GONE);
                        etUsername.setVisibility(View.GONE);
                        break;
                    case R.id.register_rb_username:
                        usernameChecked = true;
                        if (surname && firstname && email && repeatEmail && !username && password){
                            bRegister.setEnabled(false);
                        } else if (surname && firstname && email && repeatEmail && username && password){
                            bRegister.setEnabled(true);
                        }
                        etUsername.addTextChangedListener(new MyTextWatcher(etUsername));
                        tvUsernameCheck.setVisibility(View.VISIBLE);
                        etUsername.setVisibility(View.VISIBLE);
                        break;
                }
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
                    if (!surnameCheck()){
                        bRegister.setEnabled(false);
                    }
                    surname = surnameCheck();
                    break;
                case R.id.register_et_firstname:
                    if (!firstnameCheck()){
                        bRegister.setEnabled(false);
                    }
                    firstname = firstnameCheck();
                    break;
                case R.id.register_et_email:
                    if (!emailCheck()){
                        bRegister.setEnabled(false);
                    }
                    email = emailCheck();
                    break;
                case R.id.register_et_repeatemail:
                    if (!repeatEmailCheck()){
                        bRegister.setEnabled(false);
                    }
                    repeatEmail = repeatEmailCheck();
                    break;
                case R.id.register_et_username:
                    if (!usernameCheck()){
                        bRegister.setEnabled(false);
                    }
                    username = usernameCheck();
                    break;
                case R.id.register_et_password:
                    if (!passwordCheck()){
                        bRegister.setEnabled(false);
                    }
                    password = passwordCheck();
                    break;
            }

            System.out.println("surname: " + surname);
            System.out.println("firstname: " + firstname);
            System.out.println("email: " + email);
            System.out.println("repeatEmail: " + repeatEmail);
            System.out.println("username: " + username);
            System.out.println("password: " + password);

            if (surname && firstname && email && repeatEmail && password) {
                if (usernameChecked) {
                    if (username) {
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
            } else tilSurname.setErrorEnabled(false);
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
