package com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.handlers.UserSession;
import com.example.zdroa.myapplication.utils.AppSettings;

public class InitializerFragment extends Fragment {

    public static final Integer INDEX = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initializer_questions, container, false);
        UserSession userSession = new UserSession(inflater.getContext().getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE), inflater.getContext().getApplicationContext().getSharedPreferences(AppSettings.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit());
        ((TextView) view.findViewById(R.id.initializer_fragment_page_title_text_view))
                .setText("Welcome!" + "\n" + userSession.getFirstName() + " " + userSession.getSurname());
        return view;
    }

}
