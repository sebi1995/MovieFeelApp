package com.example.zdroa.myapplication.activities.accountmanagement;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.BasicActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.main.HomeActivity;
import com.example.zdroa.myapplication.handlers.AnimatorHandler;
import com.example.zdroa.myapplication.handlers.LoadingSpinner;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.handlers.UserSessionHandler.UserType;
import com.example.zdroa.myapplication.models.Movie;
import com.example.zdroa.myapplication.models.User;
import com.example.zdroa.myapplication.repositories.UserRepository;
import com.example.zdroa.myapplication.services.UserService;
import com.example.zdroa.myapplication.utilities.PersonType;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;
import com.example.zdroa.myapplication.utils.MovieUtils;
import com.google.gson.GsonBuilder;

import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements BasicActivity, ActivityNavigator {

    private EditText emailEt;
    private EditText passwordEt;
    private UserSessionHandler userSessionHandler;
    private TextView errorMessageTv;
    private Button loginBtn;
    private LoadingSpinner loadingSpinner;
    private RelativeLayout rootView;
    private CardView loginCv;
    private CardView appLogoCv;
    private TextView registerTv;
    private TextView forgotPasswordTv;
    private UserService userService;

    public Logger logger = new Logger(LoginActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        loadingSpinner = new LoadingSpinner(this, this.getWindow(), loginCv, rootView, true);
        userSessionHandler = new UserSessionHandler(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        userService = new UserService(new UserRepository(getApplicationContext()));

        doOnLoadAnimation();

        registerTv.setOnClickListener(view -> {
            launchActivityWithFinish(this, RegisterActivity.class);
        });

        forgotPasswordTv.setOnClickListener(view -> {
            logger.logError("To implement.");
        });

        LoginTextWatcher.AfterTextChangedAction changedTextAction = () -> loginBtn.setEnabled(
                !TextUtils.isEmpty(emailEt.toString()) &&
                        !TextUtils.isEmpty(passwordEt.getText())
        );

        emailEt.addTextChangedListener(new LoginTextWatcher(changedTextAction));
        passwordEt.addTextChangedListener(new LoginTextWatcher(changedTextAction));
        loginBtn.setOnClickListener(onLoginButtonClickListener());
    }

    private View.OnClickListener onLoginButtonClickListener() {
        return view -> {
            // TODO: 03/04/2022 finish impl
            hideKeyboard();
            loadingSpinner.showAndHideParentViewAndDisableUserInput();
            hideLogo();
            try {
                userService.getByEmailAddressAndPassword(
                        response -> {
                            User user = new GsonBuilder().create().fromJson(response, User.class);
                            user = new User(1).setFirstname("sebastian").setSurname("zdroana").setDateOfBirth(OffsetDateTime.MAX).setEmailAddress("s.zdroana@gmail.com").setPassword("test").setKey("adw#f3.3@2]f;awfAFWFd").setWatchedMoviesList(Arrays.asList(new Movie(1), new Movie(2), new Movie(3), new Movie(4), new Movie(5), new Movie(9), new Movie(14), new Movie(15), new Movie(16), new Movie(20), new Movie(22), new Movie(34), new Movie(33), new Movie(31), new Movie(50), new Movie(55), new Movie(44))).setUserType(UserType.USER).setPersonType(PersonType.PARANOID);
                            if (userSessionHandler.createSession(user.getUid(), user.getFirstname(), user.getSurname(), user.getDateOfBirth(), user.getEmailAddress(), user.getPassword(), user.getKey(), user.getUserType(), user.getPersonType(), user.getWatchedMoviesList())) {
                                launchActivityWithFinish(this, HomeActivity.class);
                            } else {
                                // TODO: 15/04/2022 display some sort of error as session cannot be created, data is missing
                                logger.logError("some value missing.");
                                loadingSpinner.hideAndShowParentViewAndEnableUserInput();
                                showLogo();
                            }
                        }, error -> {
                            User user = new User(1).setFirstname("sebastian").setSurname("zdroana").setDateOfBirth(OffsetDateTime.MAX).setEmailAddress("s.zdroana@gmail.com").setPassword("test").setKey("adw#f3.3@2]f;awfAFWFd").setWatchedMoviesList(Arrays.asList(new Movie(1), new Movie(2), new Movie(3), new Movie(4), new Movie(5), new Movie(9), new Movie(14), new Movie(15), new Movie(16), new Movie(20), new Movie(22), new Movie(34), new Movie(33), new Movie(31), new Movie(50), new Movie(55), new Movie(44))).setUserType(UserType.USER).setPersonType(PersonType.PARANOID);
                            if (userSessionHandler.createSession(user.getUid(), user.getFirstname(), user.getSurname(), user.getDateOfBirth(), user.getEmailAddress(), user.getPassword(), user.getKey(), user.getUserType(), user.getPersonType(), user.getWatchedMoviesList())) {
                                launchActivityWithFinish(this, HomeActivity.class);
                            } else {
                                // TODO: 15/04/2022 display some sort of error as session cannot be created, data is missing
                                logger.logError("some value missing.");
                                loadingSpinner.hideAndShowParentViewAndEnableUserInput();
                                showLogo();
                            }
                            errorMessageTv.setText(MovieUtils.convertToMultiLineString(Arrays.asList("Error connecting to login server, server may be down.", "Please try again later.")));
                            logger.logError(error);
                            loadingSpinner.hideAndShowParentViewAndEnableUserInput();
//                            showLogo();
                        },
                        emailEt.getText().toString(),
                        passwordEt.getText().toString());
            } catch (NoSuchAlgorithmException e) {
                logger.logError(e);
            }
        };
    }

    private void showLogo() {
        appLogoCv.setVisibility(View.VISIBLE);
    }

    private void hideLogo() {
        appLogoCv.setVisibility(View.GONE);
    }


    private void hideKeyboard() {
        if (this.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void doOnLoadAnimation() {
        new CountDownTimer(1200, 1000) {
            @Override
            public void onTick(long l) {
                AnimatorHandler.getMoveYAxisViewAnimation(appLogoCv, 5f, 1000).start();
                AnimatorHandler.getShrinkAnimation(appLogoCv, 0.7f, 1000).start();
            }

            @Override
            public void onFinish() {
                loginCv.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    public void initViews() {
        emailEt = findViewById(R.id.login_email_text_input_edit_text);
        passwordEt = findViewById(R.id.login_password_text_input_edit_text);
        rootView = findViewById(R.id.activity_login);
        appLogoCv = findViewById(R.id.login_app_logo_card_view);
        errorMessageTv = findViewById(R.id.login_error_text_view);
        loginBtn = findViewById(R.id.login_button);
        loginCv = findViewById(R.id.login_inputs_card_view);
        registerTv = findViewById(R.id.login_register_text_view);
        forgotPasswordTv = findViewById(R.id.login_forgot_password_text_view);
    }
}
