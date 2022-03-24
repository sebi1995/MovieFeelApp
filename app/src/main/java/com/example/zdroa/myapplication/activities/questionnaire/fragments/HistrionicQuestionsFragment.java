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

public class HistrionicQuestionsFragment extends Fragment {

    private static int a1 = 0;
    private static int a2 = 0;
    private static int a3 = 0;
    private static int a4 = 0;
    private static int a5 = 0;
    private static int a6 = 0;
    private static int a7 = 0;

    private TextView textView;

    public HistrionicQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_histrionic_questions, container, false);

        RadioGroup radioGroup1 = (RadioGroup) view.findViewById(R.id.histrionic_rg_1);
        RadioGroup radioGroup2 = (RadioGroup) view.findViewById(R.id.histrionic_rg_2);
        RadioGroup radioGroup3 = (RadioGroup) view.findViewById(R.id.histrionic_rg_3);
        RadioGroup radioGroup4 = (RadioGroup) view.findViewById(R.id.histrionic_rg_4);
        RadioGroup radioGroup5 = (RadioGroup) view.findViewById(R.id.histrionic_rg_5);
        RadioGroup radioGroup6 = (RadioGroup) view.findViewById(R.id.histrionic_rg_6);
        RadioGroup radioGroup7 = (RadioGroup) view.findViewById(R.id.histrionic_rg_7);

        QuestionnaireActivity.setBoolTreeMap(15, false);
        QuestionnaireActivity.setBoolTreeMap(16, false);
        QuestionnaireActivity.setBoolTreeMap(17, false);
        QuestionnaireActivity.setBoolTreeMap(18, false);
        QuestionnaireActivity.setBoolTreeMap(19, false);
        QuestionnaireActivity.setBoolTreeMap(20, false);
        QuestionnaireActivity.setBoolTreeMap(21, false);

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
                    case 43:
                        a1 = 1;
                        break;//true
                    case 44:
                        a1 = 0;
                        break;//false
                    case 45:
                        a1 = 0;
                        break;//unsure
                    case 46:
                        a2 = 1;
                        break;//true
                    case 47:
                        a2 = 0;
                        break;//false
                    case 48:
                        a2 = 0;
                        break;//unsure
                    case 49:
                        a3 = 1;
                        break;//true
                    case 50:
                        a3 = 0;
                        break;//false
                    case 51:
                        a3 = 1;
                        break;//unsure
                    case 52:
                        a4 = 1;
                        break;//true
                    case 53:
                        a4 = 0;
                        break;//false
                    case 54:
                        a4 = 0;
                        break;//unsure
                    case 55:
                        a5 = 1;
                        break;//true
                    case 56:
                        a5 = 0;
                        break;//false
                    case 57:
                        a5 = 1;
                        break;//unsure
                    case 58:
                        a6 = 1;
                        break;//true
                    case 59:
                        a6 = 0;
                        break;//false
                    case 60:
                        a6 = 1;
                        break;//unsure
                    case 61:
                        a7 = 1;
                        break;//true
                    case 62:
                        a7 = 0;
                        break;//false
                    case 63:
                        a7 = 0;
                        break;//unsure
                }

                if (radioGroup.getId() == R.id.histrionic_rg_1 ||
                        radioGroup.getId() == R.id.histrionic_rg_2 ||
                        radioGroup.getId() == R.id.histrionic_rg_3 ||
                        radioGroup.getId() == R.id.histrionic_rg_4 ||
                        radioGroup.getId() == R.id.histrionic_rg_5 ||
                        radioGroup.getId() == R.id.histrionic_rg_6 ||
                        radioGroup.getId() == R.id.histrionic_rg_7) {

                    switch (radioGroup.getId()) {
                        case R.id.histrionic_rg_1:
                            textView = (TextView) view.findViewById(R.id.histrionic_rg_tv_1);
                            QuestionnaireActivity.setBoolTreeMap(15, true);
                            break;
                        case R.id.histrionic_rg_2:
                            textView = (TextView) view.findViewById(R.id.histrionic_rg_tv_2);
                            QuestionnaireActivity.setBoolTreeMap(16, true);
                            break;
                        case R.id.histrionic_rg_3:
                            textView = (TextView) view.findViewById(R.id.histrionic_rg_tv_3);
                            QuestionnaireActivity.setBoolTreeMap(17, true);
                            break;
                        case R.id.histrionic_rg_4:
                            textView = (TextView) view.findViewById(R.id.histrionic_rg_tv_4);
                            QuestionnaireActivity.setBoolTreeMap(18, true);
                            break;
                        case R.id.histrionic_rg_5:
                            textView = (TextView) view.findViewById(R.id.histrionic_rg_tv_5);
                            QuestionnaireActivity.setBoolTreeMap(19, true);
                            break;
                        case R.id.histrionic_rg_6:
                            textView = (TextView) view.findViewById(R.id.histrionic_rg_tv_6);
                            QuestionnaireActivity.setBoolTreeMap(20, true);
                            break;
                        case R.id.histrionic_rg_7:
                            textView = (TextView) view.findViewById(R.id.histrionic_rg_tv_7);
                            QuestionnaireActivity.setBoolTreeMap(21, true);
                            break;
                    }
                    textView.setBackgroundColor(Color.parseColor("#04ff00"));
                }

                QuestionnaireActivity.setQuestionAnswer(3, a1 + a2 + a3 + a4 + a5 + a6 + a7);
            }
        });
    }

}
