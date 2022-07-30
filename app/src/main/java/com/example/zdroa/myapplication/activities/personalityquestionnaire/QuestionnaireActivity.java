package com.example.zdroa.myapplication.activities.personalityquestionnaire;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Response;
import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.InitializerFragment;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.models.PQAnswer;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.models.PQQuestion;
import com.example.zdroa.myapplication.communicators.FragmentCommunicator;
import com.example.zdroa.myapplication.handlers.LoadingSpinner;
import com.example.zdroa.myapplication.handlers.UserSession;
import com.example.zdroa.myapplication.repositories.UserRepository;
import com.example.zdroa.myapplication.services.UserService;
import com.example.zdroa.myapplication.utilities.PersonType;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;
import com.example.zdroa.myapplication.utils.MovieUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuestionnaireActivity extends AppCompatActivity implements ActivityNavigator {

    ViewPager viewPager;
    private UserService userService;
    UserSession userSession;
    public static final Logger LOGGER = new Logger(QuestionnaireActivity.class);

    public static FragmentCommunicator communicator;

    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        innitViews();
        userService = new UserService(UserRepository.getInstance(getApplicationContext()));
        userSession = new UserSession(getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        communicator = new FragmentCommunicator(getSupportFragmentManager());
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        viewPager.setAdapter(communicator);
        viewPager.setOffscreenPageLimit(PersonType.values().length);
        viewPager.setCurrentItem(InitializerFragment.INDEX);
    }

    private void innitViews() {
        chronometer = findViewById(R.id.questionnaire_timer_chronometer);
        viewPager = findViewById(R.id.questionnaire_container_view_pager);
    }

    @Override
    public void onBackPressed() {
        MovieUtils.showLongToast(this, "Please go back to the first screen and press the exit button quit and save progress.");
    }

    public void questionnaireExitButtonOnClick(View view) {
        LOGGER.logInfo("Exiting personality questionnaire.");
        // TODO: 06/05/2022 save progress?
        finish();
    }


    LoadingSpinner loadingSpinner;
    CardView mainCv;
    RelativeLayout rootView;

    public void questionnaireFinishButtonOnClick(View view) {
        mainCv = (CardView) getOrInitAndGet(mainCv, R.id.fragment_ending_main_card_view);
        rootView = (RelativeLayout) getOrInitAndGet(rootView, R.id.fragment_ending);
        if (loadingSpinner == null) {
            if (mainCv == null) {
                LOGGER.logError("mainCv was not initialized.");
            }
            if (rootView == null) {
                LOGGER.logError("rootView was not initialized.");
            }
            loadingSpinner = new LoadingSpinner(this, this.getWindow(), mainCv, rootView);
        }
        // TODO: 16/05/2022 remove?
        randomAnswer();

        loadingSpinner.showAndHideParentViewAndDisableUserInput();
        try {
            if (PQHandler.allQuestionsAreAnswered()) {
                chronometer.stop();
                long questionnaireDuration = SystemClock.elapsedRealtime() - chronometer.getBase();
    //                timerDuration = timerDuration.substring(0, timerDuration.length() - 3);
                    List<PersonType> personType = PQHandler.getMatchingPersonTypes(Arrays.asList(PersonType.values()));
                    userService.registerQuestionnaire(
                            getSuccessListener(personType),
                            getErrorListener(),
                            userSession.getUid(),
                            questionnaireDuration,
                            personType
                    );
            } else {
                viewPager.setCurrentItem(PQHandler.getIndexForFirstFragmentWithUnAnsweredQuestion());
                loadingSpinner.hideAndShowParentViewAndEnableUserInput();
            }
        } catch (Exception e) {
            LOGGER.logError(e);
        }
    }

    private Response.ErrorListener getErrorListener() {
        return error -> {
            LOGGER.logError(error);
            //print error?
            //ask for forgiveness
            //save the answers somewhere
            //try again later?
//                        new AlertDialog.Builder(this).setMessage("fail")
//                                .setNegativeButton("Retry", null)
//                                .create()
//                                .show();
            loadingSpinner.hideAndShowParentViewAndEnableUserInput();
        };
    }

    private Response.Listener<String> getSuccessListener(List<PersonType> personType) {
        return response -> {
            //response true or false for save status
            //if true update person type in userSession
            //then redirect to home
            try {
                if (new JSONObject(response).getBoolean("success")) {
                    userSession.setPersonType(personType);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loadingSpinner.hideAndShowParentViewAndEnableUserInput();
        };
    }

    private View getOrInitAndGet(View view, int id) {
        if (view == null) {
            return findViewById(id);
        }
        return view;
    }

    private void randomAnswer() {
        for (PersonType personType : PersonType.values()) {
            try {
                for (PQQuestion question : PQQuestions.getQuestions(personType)) {
                    int randomIndex = new Random().nextInt(PQAnswer.getValidAnswers().size()) + 1;
                    PQAnswer.getByIndex(randomIndex).ifPresent(answer -> {
                        try {
                            PQHandler.setAnswerForQuestion(personType, question, answer);
                        } catch (Exception e) {
                            LOGGER.logError(e);
                        }
                    });
                }
            } catch (Exception e) {
                LOGGER.logError(e);
            }
        }
    }
}
