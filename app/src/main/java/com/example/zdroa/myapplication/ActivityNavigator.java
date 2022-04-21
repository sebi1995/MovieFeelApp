package com.example.zdroa.myapplication;

import android.app.Activity;
import android.content.Intent;

import com.example.zdroa.myapplication.activities.questionnaire.QuestionnaireActivity;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;

public interface ActivityNavigator {

    default void redirectIfSessionDoesNotMeetRequirements(UserSessionHandler userSessionHandler, Activity activity) {
        if (userSessionHandler.sessionDoesNotExist() && !(activity instanceof MainActivity)) {
            launchActivityWithFinish(activity, MainActivity.class);
            return;
        }
        if (userSessionHandler.sessionExists() && userSessionHandler.hasNotCompletedQuestionnaire()) {
            launchActivityWithFinish(activity, QuestionnaireActivity.class);
            return;
        }
    }


    default void launchActivityWithFinish(Activity sourceActivity, Class<?> targetActivity) {
        sourceActivity.finish();
        launchActivity(sourceActivity, targetActivity);
    }

    default void launchActivity(Activity sourceActivity, Class<?> targetActivity) {
        sourceActivity.startActivity(new Intent(sourceActivity, targetActivity));
    }
}
