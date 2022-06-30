package com.example.zdroa.myapplication.activities.questionnaire;

import static com.example.zdroa.myapplication.utilities.PersonType.ANXIOUS;
import static com.example.zdroa.myapplication.utilities.PersonType.DEPENDANT;
import static com.example.zdroa.myapplication.utilities.PersonType.DEPRESSIVE;
import static com.example.zdroa.myapplication.utilities.PersonType.HISTRIONIC;
import static com.example.zdroa.myapplication.utilities.PersonType.NARCISSIST;
import static com.example.zdroa.myapplication.utilities.PersonType.OBSESSIVE;
import static com.example.zdroa.myapplication.utilities.PersonType.PARANOID;
import static com.example.zdroa.myapplication.utilities.PersonType.SCHIZOID;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.questionnaire.fragments.InitializerFragment;
import com.example.zdroa.myapplication.communicators.FragmentCommunicator;
import com.example.zdroa.myapplication.handlers.LoadingSpinner;
import com.example.zdroa.myapplication.handlers.UserSession;
import com.example.zdroa.myapplication.repositories.UserRepository;
import com.example.zdroa.myapplication.services.UserService;
import com.example.zdroa.myapplication.utilities.PersonType;
import com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireAnswer;
import com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestion;
import com.example.zdroa.myapplication.utilities.QuestionnaireComponentCreator;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;
import com.example.zdroa.myapplication.utils.MovieUtils;
import com.google.common.collect.ImmutableMap;

import org.json.JSONException;
import org.json.JSONObject;

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

    private static final ImmutableMap<PersonType, Integer> PERSON_TYPE_ROOT_LAYOUTS_MAP = ImmutableMap.<PersonType, Integer>builder()
            .put(ANXIOUS, R.id.fragment_anxious_linear_layout)
            .put(PARANOID, R.id.fragment_paranoid_linear_layout)
            .put(HISTRIONIC, R.id.fragment_histrionic_linear_layout)
            .put(OBSESSIVE, R.id.fragment_obsessive_linear_layout)
            .put(NARCISSIST, R.id.fragment_narcissist_linear_layout)
            .put(SCHIZOID, R.id.fragment_schizoid_linear_layout)
            .put(DEPRESSIVE, R.id.fragment_depressive_linear_layout)
            .put(DEPENDANT, R.id.fragment_dependent_linear_layout)
            .build();

    public static void createQuestionnaireFragment(PersonType personType, View view) {
        Integer resourceId = PERSON_TYPE_ROOT_LAYOUTS_MAP.get(personType);
        if (resourceId == null) {
            LOGGER.logError("Resource id for " + personType.toString() + " not present in PERSON_TYPE_ROOT_LAYOUTS_MAP.");
            return;
        }
        LinearLayout rootLayout = view.findViewById(resourceId);
        List<PersonalityQuestionnaireQuestion> questions = QuestionnaireResultsHandler.getQuestions(personType);
        if (questions.isEmpty()) {
            LOGGER.logError("No questions found for " + personType.toString());
        } else {
            QuestionnaireComponentCreator.createQuestionnaire(view.getContext(), personType, questions, rootLayout);
        }
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
        if (QuestionnaireResultsHandler.allQuestionsAreAnswered()) {
            chronometer.stop();
            long questionnaireDuration = SystemClock.elapsedRealtime() - chronometer.getBase();
//                timerDuration = timerDuration.substring(0, timerDuration.length() - 3);
            List<PersonType> personType = QuestionnaireResultsHandler.calculateResultPersonType();
            userService.registerQuestionnaire(
                    response -> {
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
                    },
                    error -> {
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
                    },
                    userSession.getUid(),
                    questionnaireDuration,
                    personType
            );
        } else {
            viewPager.setCurrentItem(QuestionnaireResultsHandler.getIndexForFirstFragmentWithUnAnsweredQuestion());
            loadingSpinner.hideAndShowParentViewAndEnableUserInput();
        }
    }

    private View getOrInitAndGet(View view, int id) {
        if (view == null) {
            return findViewById(id);
        }
        return view;
    }

    private void randomAnswer() {
        for (PersonType personType : PersonType.values()) {
            for (PersonalityQuestionnaireQuestion question : QuestionnaireResultsHandler.getQuestions(personType)) {
                int randomIndex = new Random().nextInt(PersonalityQuestionnaireAnswer.getValidAnswers().size()) + 1;
                PersonalityQuestionnaireAnswer.getByIndex(randomIndex).ifPresent(answer -> {
                    QuestionnaireResultsHandler.setAnswerForQuestion(personType, question, answer);
                });
            }
        }
    }
}
