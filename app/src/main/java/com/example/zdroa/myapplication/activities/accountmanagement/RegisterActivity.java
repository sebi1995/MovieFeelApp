package com.example.zdroa.myapplication.activities.accountmanagement;

import static com.example.zdroa.myapplication.utilities.RegistrationValidator.Validator.DATE_OF_BIRTH;
import static com.example.zdroa.myapplication.utilities.RegistrationValidator.Validator.EMAIL_ADDRESS;
import static com.example.zdroa.myapplication.utilities.RegistrationValidator.Validator.FIRSTNAME;
import static com.example.zdroa.myapplication.utilities.RegistrationValidator.Validator.PASSWORD;
import static com.example.zdroa.myapplication.utilities.RegistrationValidator.Validator.SURNAME;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.repositories.UserRepository;
import com.example.zdroa.myapplication.services.UserService;
import com.example.zdroa.myapplication.utilities.RegistrationValidator;
import com.example.zdroa.myapplication.utils.Logger;
import com.example.zdroa.myapplication.utils.MovieDateFormatter;
import com.google.android.material.textfield.TextInputLayout;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements ActivityNavigator {

    private Button registerBtn;
    private EditText firstnameEt;
    private EditText surnameEt;
    private EditText dateOfBirthEt;
    private EditText emailAddressEt;
    private EditText passwordEt;

    private Logger logger = new Logger(RegisterActivity.class);

    private RegistrationValidator registrationValidator;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        userService = new UserService(UserRepository.getInstance(getApplicationContext()));


        dateOfBirthEt.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        dateOfBirthEt.setText(MovieDateFormatter.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        registrationValidator = new RegistrationValidator((editText, errorMessage) -> {
            TextInputLayout parent = (TextInputLayout) editText.getParent().getParent();
            if (errorMessage != null) {
                parent.setError(errorMessage);
            } else {
                parent.setErrorEnabled(false);
            }
            registerBtn.setEnabled(
                    registrationValidator.isFirstnameValid() &&
                            registrationValidator.isSurnameValid() &&
                            registrationValidator.isDateOfBirthValid() &&
                            registrationValidator.isEmailAddressValid() &&
                            registrationValidator.isPasswordValid());
        });

        firstnameEt.addTextChangedListener(registrationValidator.createTextWatcher(FIRSTNAME, firstnameEt));
        surnameEt.addTextChangedListener(registrationValidator.createTextWatcher(SURNAME, surnameEt));
        dateOfBirthEt.addTextChangedListener(registrationValidator.createTextWatcher(DATE_OF_BIRTH, dateOfBirthEt));
        emailAddressEt.addTextChangedListener(registrationValidator.createTextWatcher(EMAIL_ADDRESS, emailAddressEt));
        passwordEt.addTextChangedListener(registrationValidator.createTextWatcher(PASSWORD, passwordEt, emailAddressEt));

        registerBtn.setOnClickListener(view -> {
            try {
                userService.createUser(
                        response -> {
                            // TODO: 14/04/2022 implement backend functionallity and then a proper response handler and error handler here

//                                    try {
//                                        JSONObject jsonResponse = new JSONObject(response);
//                                        boolean success = jsonResponse.getBoolean("success");
//                                        if (success) {
                            launchActivityWithFinish(this, LoginActivity.class);
//                                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                            builder.setMessage("Registration Failed")
                                    .setNegativeButton("Retry", (dialogInterface, i) -> {
                                        logger.logError(dialogInterface.toString());
                                        logger.logError(String.valueOf(i));
                                    })
                                    .create()
                                    .show();
//                                        }
//                                    } catch (JSONException e) {
//                        logger.logError(e);
//                                    }
                        },
                        error -> {
//                    logger.logError(error);
                        },
                        firstnameEt.getText().toString(),
                        surnameEt.getText().toString(),
                        dateOfBirthEt.getText().toString(),
                        emailAddressEt.getText().toString(),
                        passwordEt.getText().toString());
            } catch (NoSuchAlgorithmException e) {
                logger.logError(e);
            }
        });
    }

    public void initViews() {
        firstnameEt = findViewById(R.id.register_firstname_text_input_edit_text);
        surnameEt = findViewById(R.id.register_surname_text_input_edit_text);
        dateOfBirthEt = findViewById(R.id.register_date_of_birth_text_input_edit_text);
        emailAddressEt = findViewById(R.id.register_email_address_text_input_edit_text);
        passwordEt = findViewById(R.id.register_password_text_input_edit_text);
        registerBtn = findViewById(R.id.register_register_button);
    }

    /**
     * shows keyboard for view
     *
     * @param view
     */
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
