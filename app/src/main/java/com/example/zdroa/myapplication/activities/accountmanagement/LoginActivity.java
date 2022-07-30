package com.example.zdroa.myapplication.activities.accountmanagement;

import static com.example.zdroa.myapplication.utils.MovieUtils.hideKeyboard;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.main.HomeActivity;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.QuestionnaireActivity;
import com.example.zdroa.myapplication.handlers.AnimatorHandler;
import com.example.zdroa.myapplication.handlers.LoadingSpinner;
import com.example.zdroa.myapplication.handlers.UserSession;
import com.example.zdroa.myapplication.handlers.UserSession.SessionCreationStatus;
import com.example.zdroa.myapplication.models.User;
import com.example.zdroa.myapplication.repositories.UserRepository;
import com.example.zdroa.myapplication.services.UserService;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;
import com.example.zdroa.myapplication.utils.MovieUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements ActivityNavigator {

    private EditText emailEt;
    private EditText passwordEt;
    private TextView errorMessageTv;
    private Button loginBtn;
    private RelativeLayout rootView;
    private CardView loginCv;
    private CardView appLogoCv;

    public Logger logger = new Logger(LoginActivity.class);
    LoadingSpinner loadingSpinner;
    UserSession userSession;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        // TODO: 24/04/2022 check this
        loadingSpinner = new LoadingSpinner(this, this.getWindow(), loginCv, rootView);
        userSession = new UserSession(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        userService = new UserService(UserRepository.getInstance(getApplicationContext()));

        doOnLoadAnimation();

        LoginTextWatcher.AfterTextChangedAction changedTextAction = () -> loginBtn.setEnabled(
                !TextUtils.isEmpty(emailEt.toString()) &&
                        !TextUtils.isEmpty(passwordEt.getText())
        );
        emailEt.addTextChangedListener(new LoginTextWatcher(changedTextAction));
        passwordEt.addTextChangedListener(new LoginTextWatcher(changedTextAction));
    }

    public void initViews() {
        emailEt = findViewById(R.id.login_email_text_input_edit_text);
        passwordEt = findViewById(R.id.login_password_text_input_edit_text);
        rootView = findViewById(R.id.activity_login);
        appLogoCv = findViewById(R.id.login_app_logo_card_view);
        errorMessageTv = findViewById(R.id.login_error_text_view);
        loginBtn = findViewById(R.id.login_button);
        loginCv = findViewById(R.id.login_inputs_card_view);
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

    public void loginButtonOnClick(View view) throws NoSuchAlgorithmException {
        hideKeyboard(this);
        loadingSpinner.showAndHideParentViewAndDisableUserInput();
        appLogoCv.setVisibility(View.GONE);
        userService.getByEmailAddressAndPassword(
                response -> {
                    // TODO: 26/04/2022 implement backend
                }, error -> {
                    User user = MovieUtils.getTestUser();
                    SessionCreationStatus status = userSession.create(user.getUid(), user.getFirstname(), user.getSurname(), user.getDateOfBirth(), user.getEmailAddress(), user.getPassword(), user.getKey(), user.getUserType(), user.getPersonType(), user.getWatchedMoviesList(), user.getLastActiveTime());
                    switch (status) {
                        case SESSION_CREATED: {
                            launchActivityWithFinish(this, HomeActivity.class);
                            break;
                        }
                        case SESSION_CREATED_QUESTIONNAIRE_NOT_COMPLETED: {
                            launchActivityWithFinish(this, QuestionnaireActivity.class);
                            break;
                        }
                        case SESSION_NOT_CREATED: {
                            // TODO: 15/04/2022 display some sort of error as session cannot be created, data is missing
                            logger.logError("some value missing.");
                            loadingSpinner.hideAndShowParentViewAndEnableUserInput();
                            appLogoCv.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    errorMessageTv.setText(MovieUtils.convertToMultiLineString(Arrays.asList("Error connecting to login server, server may be down.", "Please try again later.")));
                    logger.logError(error);
                    loadingSpinner.hideAndShowParentViewAndEnableUserInput();
                    appLogoCv.setVisibility(View.VISIBLE);
                },
                emailEt.getText().toString(),
                passwordEt.getText().toString());
    }

    public void loginGoToRegisterActivityOnClick(View view) {
        launchActivityWithFinish(this, RegisterActivity.class);
    }

    public void loginGoToForgotPasswordActivityOnClick(View view) {
        logger.logError("To implement.");
    }
}
