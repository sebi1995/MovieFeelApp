package com.example.zdroa.myapplication.activities.questionnaire.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.questionnaire.QuestionnaireActivity;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;
import com.example.zdroa.myapplication.utils.AppSettings;

public class InitializerFragment extends Fragment {

    public InitializerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initializer_questions, container, false);

        UserSessionHandler userSessionHandler = new UserSessionHandler(inflater.getContext().getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), inflater.getContext().getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());

        ImageView imageView = view.findViewById(R.id.fragment_initializer_iv_cancel);
        imageView.setOnClickListener(v -> QuestionnaireActivity.fa.finish());

        TextView page_title = view.findViewById(R.id.fragment_initializer_tv_title);
        page_title.setText("Welcome!");
        page_title.append("\n" + userSessionHandler.getFirstName() + " " + userSessionHandler.getSurname());

        TextView textView = view.findViewById(R.id.initializer_tv_welcome_msg);

        textView.setText("This will be the hardest part of using MovieFeel!");
        textView.append("\n\nPlease respond to the following statements by pressing the true, false or unsure options.");
        textView.append("\n\nTaking your time to accurately answer the following question will help MovieFeel determine what type of movies to suggest to help you in your needs.");
        textView.append("\n\nSo please take your time and answer as accurate as possible! All your data is kept safe and confidential.");
        textView.append("\n\nSwipe from right to left to start. You may go back to previous questions at any time. To do so just swipe backwards.");

        return view;
    }

}
