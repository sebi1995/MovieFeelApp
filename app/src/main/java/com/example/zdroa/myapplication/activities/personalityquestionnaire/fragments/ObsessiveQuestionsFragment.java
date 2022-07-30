package com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments;

import static com.example.zdroa.myapplication.utilities.PersonType.OBSESSIVE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.PQQuestions;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.PQComponentCreator;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.models.PQQuestion;
import com.example.zdroa.myapplication.utils.Logger;

import java.util.List;

public class ObsessiveQuestionsFragment extends Fragment {

    private static final Logger LOGGER = new Logger(ObsessiveQuestionsFragment.class);
    public static final Integer INDEX = OBSESSIVE.getIndex();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obsessive_questions, container, false);
        LinearLayout rootLayout = view.findViewById(R.id.fragment_obsessive_linear_layout);
        try {
            List<PQQuestion> questions = PQQuestions.getQuestions(OBSESSIVE);
            PQComponentCreator.createFragmentComponent(view.getContext(), OBSESSIVE, questions, rootLayout);
        } catch (Exception e) {
            LOGGER.logError(e);
        }
        return view;
    }
}
