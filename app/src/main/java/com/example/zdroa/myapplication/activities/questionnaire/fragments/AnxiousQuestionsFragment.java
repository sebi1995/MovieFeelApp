package com.example.zdroa.myapplication.activities.questionnaire.fragments;

import static com.example.zdroa.myapplication.utilities.PersonType.ANXIOUS;
import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestions.ANXIOUS_PERSON_TYPE_QUESTIONS;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.questionnaire.QuestionnaireActivity;
import com.example.zdroa.myapplication.utilities.QuestionnaireComponentCreator;
import com.google.common.collect.ImmutableMap;

// TODO: 22/04/2022 refactor everything
public class AnxiousQuestionsFragment extends Fragment {

    public static final int INDEX = 1;

    public AnxiousQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anxious_questions, container, false);
        QuestionnaireActivity.createQuestionnaireFragment(ANXIOUS, view);
        return view;
    }
}
