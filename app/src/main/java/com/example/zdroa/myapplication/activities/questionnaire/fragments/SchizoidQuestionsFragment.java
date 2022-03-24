package com.example.zdroa.myapplication.activities.questionnaire.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.questionnaire.QuestionnaireActivity;

public class SchizoidQuestionsFragment extends Fragment {

    private static int a1 = 0;
    private static int a2 = 0;
    private static int a3 = 0;
    private static int a4 = 0;
    private static int a5 = 0;
    private static int a6 = 0;
    private static int a7 = 0;

    private TextView textView;

    public SchizoidQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schizoid_questions, container, false);

        RadioGroup radioGroup1 = (RadioGroup) view.findViewById(R.id.schizoid_rg_1);
        RadioGroup radioGroup2 = (RadioGroup) view.findViewById(R.id.schizoid_rg_2);
        RadioGroup radioGroup3 = (RadioGroup) view.findViewById(R.id.schizoid_rg_3);
        RadioGroup radioGroup4 = (RadioGroup) view.findViewById(R.id.schizoid_rg_4);
        RadioGroup radioGroup5 = (RadioGroup) view.findViewById(R.id.schizoid_rg_5);
        RadioGroup radioGroup6 = (RadioGroup) view.findViewById(R.id.schizoid_rg_6);
        RadioGroup radioGroup7 = (RadioGroup) view.findViewById(R.id.schizoid_rg_7);

        QuestionnaireActivity.setBoolTreeMap(36, false);
        QuestionnaireActivity.setBoolTreeMap(37, false);
        QuestionnaireActivity.setBoolTreeMap(38, false);
        QuestionnaireActivity.setBoolTreeMap(39, false);
        QuestionnaireActivity.setBoolTreeMap(40, false);
        QuestionnaireActivity.setBoolTreeMap(41, false);
        QuestionnaireActivity.setBoolTreeMap(42, false);

        addOnCheckedChangeListener(radioGroup1, view);
        addOnCheckedChangeListener(radioGroup2, view);
        addOnCheckedChangeListener(radioGroup3, view);
        addOnCheckedChangeListener(radioGroup4, view);
        addOnCheckedChangeListener(radioGroup5, view);
        addOnCheckedChangeListener(radioGroup6, view);
        addOnCheckedChangeListener(radioGroup7, view);

        return view;
    }

    private void addOnCheckedChangeListener(final RadioGroup radioGroup, final View view) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case 106:
                        a1 = 1;
                        break;//true
                    case 107:
                        a1 = 0;
                        break;//false
                    case 108:
                        a1 = 1;
                        break;//unsure
                    case 109:
                        a2 = 1;
                        break;//true
                    case 110:
                        a2 = 0;
                        break;//false
                    case 111:
                        a2 = 1;
                        break;//unsure
                    case 112:
                        a3 = 1;
                        break;//true
                    case 113:
                        a3 = 0;
                        break;//false
                    case 114:
                        a3 = 0;
                        break;//unsure
                    case 115:
                        a4 = 1;
                        break;//true
                    case 116:
                        a4 = 0;
                        break;//false
                    case 117:
                        a4 = 1;
                        break;//unsure
                    case 118:
                        a5 = 1;
                        break;//true
                    case 119:
                        a5 = 0;
                        break;//false
                    case 120:
                        a5 = 1;
                        break;//unsure
                    case 121:
                        a6 = 1;
                        break;//true
                    case 122:
                        a6 = 0;
                        break;//false
                    case 123:
                        a6 = 1;
                        break;//unsure
                    case 124:
                        a7 = 1;
                        break;//true
                    case 125:
                        a7 = 0;
                        break;//false
                    case 126:
                        a7 = 0;
                        break;//unsure
                }

                if (radioGroup.getId() == R.id.schizoid_rg_1 ||
                        radioGroup.getId() == R.id.schizoid_rg_2 ||
                        radioGroup.getId() == R.id.schizoid_rg_3 ||
                        radioGroup.getId() == R.id.schizoid_rg_4 ||
                        radioGroup.getId() == R.id.schizoid_rg_5 ||
                        radioGroup.getId() == R.id.schizoid_rg_6 ||
                        radioGroup.getId() == R.id.schizoid_rg_7) {

                    switch (radioGroup.getId()) {
                        case R.id.schizoid_rg_1:
                            textView = (TextView) view.findViewById(R.id.schizoid_rg_tv_1);
                            QuestionnaireActivity.setBoolTreeMap(36, true);
                            break;
                        case R.id.schizoid_rg_2:
                            textView = (TextView) view.findViewById(R.id.schizoid_rg_tv_2);
                            QuestionnaireActivity.setBoolTreeMap(37, true);
                            break;
                        case R.id.schizoid_rg_3:
                            textView = (TextView) view.findViewById(R.id.schizoid_rg_tv_3);
                            QuestionnaireActivity.setBoolTreeMap(38, true);
                            break;
                        case R.id.schizoid_rg_4:
                            textView = (TextView) view.findViewById(R.id.schizoid_rg_tv_4);
                            QuestionnaireActivity.setBoolTreeMap(39, true);
                            break;
                        case R.id.schizoid_rg_5:
                            textView = (TextView) view.findViewById(R.id.schizoid_rg_tv_5);
                            QuestionnaireActivity.setBoolTreeMap(40, true);
                            break;
                        case R.id.schizoid_rg_6:
                            textView = (TextView) view.findViewById(R.id.schizoid_rg_tv_6);
                            QuestionnaireActivity.setBoolTreeMap(41, true);
                            break;
                        case R.id.schizoid_rg_7:
                            textView = (TextView) view.findViewById(R.id.schizoid_rg_tv_7);
                            QuestionnaireActivity.setBoolTreeMap(42, true);
                            break;
                    }
                    textView.setBackgroundColor(Color.parseColor("#04ff00"));
                }

                QuestionnaireActivity.setQuestionAnswer(6, a1 + a2 + a3 + a4 + a5 + a6 + a7);
            }
        });
    }

}
