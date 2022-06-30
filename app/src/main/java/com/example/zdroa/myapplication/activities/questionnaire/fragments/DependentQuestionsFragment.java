package com.example.zdroa.myapplication.activities.questionnaire.fragments;

import static com.example.zdroa.myapplication.utilities.PersonType.DEPENDANT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.questionnaire.QuestionnaireActivity;

public class DependentQuestionsFragment extends Fragment {

    public DependentQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dependent_questions, container, false);
        QuestionnaireActivity.createQuestionnaireFragment(DEPENDANT, view);
        return view;
    }
}
