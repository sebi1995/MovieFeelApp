package com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.utilities.PersonType;

public class EndingQuestionsFragment extends Fragment implements ActivityNavigator {

    public static final Integer INDEX = PersonType.values().length+1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ending_questions, container, false);
    }
}
