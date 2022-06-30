package com.example.zdroa.myapplication;

import android.app.Activity;
import android.content.Intent;

import com.example.zdroa.myapplication.activities.main.HomeActivity;
import com.example.zdroa.myapplication.activities.questionnaire.QuestionnaireActivity;
import com.example.zdroa.myapplication.handlers.UserSession;
import com.example.zdroa.myapplication.models.User;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;
import com.example.zdroa.myapplication.utils.MovieUtils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface ActivityNavigator {

    default void validateUserSessionAndRedirectToHomeOrRedirect(UserSession userSession, Activity activity) {
        if (AppSettings.LOGIN_SYSTEM_ENABLED) {
            //if the user tries to access the activity and there's no active session, redirect to main as long as not already on main
            if (userSession.sessionDoesNotExist() && !(activity instanceof MainActivity)) {
                launchActivityWithFinish(activity, MainActivity.class);
                return;
            }
            //if session in progress but the user hasn't done the questionnaire, redirect to questionnaire page
            if (userSession.sessionExists() && userSession.hasNotCompletedQuestionnaire()) {
                launchActivityWithFinish(activity, QuestionnaireActivity.class);
                return;
            }
            //if there is a session in progress, check how old the session is and direct to home if not too old
            if (userSession.sessionExists()) {
                Optional<OffsetDateTime> lastActiveTime = userSession.getLastActiveTime();
                if (lastActiveTime.isPresent()) {
                    Duration sessionActiveTime = Duration.between(lastActiveTime.get(), OffsetDateTime.now());
                    if (sessionActiveTime.getSeconds() > AppSettings.SESSION_TIMEOUT_DURATION) {
                        userSession.clear();
                        launchActivityWithFinish(activity, MainActivity.class);
                        return;
                    }
                }
                launchActivityWithFinish(activity, HomeActivity.class);
                return;
            }
        } else {
            //testing purposes to skip login
            if (activity instanceof MainActivity) {
                launchActivityWithFinish(activity, HomeActivity.class);
                userSession.clear();
                User testUser = MovieUtils.getTestUser();
                userSession.create(testUser.getUid(),
                        testUser.getFirstname(),
                        testUser.getSurname(),
                        testUser.getDateOfBirth(),
                        testUser.getEmailAddress(),
                        testUser.getPassword(),
                        testUser.getKey(),
                        testUser.getUserType(),
                        testUser.getPersonType(),
                        testUser.getWatchedMoviesList(),
                        OffsetDateTime.now());
                launchActivityWithFinish(activity, HomeActivity.class);
            }
        }
    }

    default void launchActivityWithFinish(Activity sourceActivity, Class<?> targetActivity) {
        launchActivityWithFinish(sourceActivity, targetActivity, null);
    }

    default void launchActivityWithFinish(Activity sourceActivity, Class<?> targetActivity, Logger logger) {
        if (logger != null && sourceActivity == null) {
            logger.logError("Source activity is null.");
            return;
        }
        sourceActivity.finish();
        launchActivity(sourceActivity, targetActivity);
    }

    default void launchActivity(Activity sourceActivity, Class<?> targetActivity) {
        sourceActivity.startActivity(new Intent(sourceActivity, targetActivity));
    }
}
