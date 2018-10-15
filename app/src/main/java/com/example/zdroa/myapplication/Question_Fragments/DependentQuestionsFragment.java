package com.example.zdroa.myapplication.Question_Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zdroa.myapplication.QuestionnaireActivity;
import com.example.zdroa.myapplication.R;

public class DependentQuestionsFragment extends Fragment {

    private static int a1 = 0;
    private static int a2 = 0;
    private static int a3 = 0;
    private static int a4 = 0;
    private static int a5 = 0;
    private static int a6 = 0;
    private static int a7 = 0;

    private TextView textView;

    public DependentQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dependent_questions, container, false);

        RadioGroup radioGroup1 = (RadioGroup) view.findViewById(R.id.dependent_rg_1);
        RadioGroup radioGroup2 = (RadioGroup) view.findViewById(R.id.dependent_rg_2);
        RadioGroup radioGroup3 = (RadioGroup) view.findViewById(R.id.dependent_rg_3);
        RadioGroup radioGroup4 = (RadioGroup) view.findViewById(R.id.dependent_rg_4);
        RadioGroup radioGroup5 = (RadioGroup) view.findViewById(R.id.dependent_rg_5);
        RadioGroup radioGroup6 = (RadioGroup) view.findViewById(R.id.dependent_rg_6);
        RadioGroup radioGroup7 = (RadioGroup) view.findViewById(R.id.dependent_rg_7);

        QuestionnaireActivity.setBoolTreeMap(50, false);
        QuestionnaireActivity.setBoolTreeMap(51, false);
        QuestionnaireActivity.setBoolTreeMap(52, false);
        QuestionnaireActivity.setBoolTreeMap(53, false);
        QuestionnaireActivity.setBoolTreeMap(54, false);
        QuestionnaireActivity.setBoolTreeMap(55, false);
        QuestionnaireActivity.setBoolTreeMap(56, false);

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
                    case 148: a1 = 1; break;//true
                    case 149: a1 = 0; break;//false
                    case 150: a1 = 0; break;//unsure
                    case 151: a2 = 1; break;//true
                    case 152: a2 = 0; break;//false
                    case 153: a2 = 0; break;//unsure
                    case 154: a3 = 1; break;//true
                    case 155: a3 = 0; break;//false
                    case 156: a3 = 1; break;//unsure
                    case 157: a4 = 1; break;//true
                    case 158: a4 = 0; break;//false
                    case 159: a4 = 0; break;//unsure
                    case 160: a5 = 1; break;//true
                    case 161: a5 = 0; break;//false
                    case 162: a5 = 1; break;//unsure
                    case 163: a6 = 1; break;//true
                    case 164: a6 = 0; break;//false
                    case 165: a6 = 1; break;//unsure
                    case 166: a7 = 1; break;//true
                    case 167: a7 = 0; break;//false
                    case 168: a7 = 1; break;//unsure
                }

                if (radioGroup.getId() == R.id.dependent_rg_1 ||
                        radioGroup.getId() == R.id.dependent_rg_2 ||
                        radioGroup.getId() == R.id.dependent_rg_3 ||
                        radioGroup.getId() == R.id.dependent_rg_4 ||
                        radioGroup.getId() == R.id.dependent_rg_5 ||
                        radioGroup.getId() == R.id.dependent_rg_6 ||
                        radioGroup.getId() == R.id.dependent_rg_7) {

                    switch (radioGroup.getId()){
                        case R.id.dependent_rg_1:
                            textView = (TextView) view.findViewById(R.id.dependent_rg_tv_1);
                            QuestionnaireActivity.setBoolTreeMap(50, true);
                            break;
                        case R.id.dependent_rg_2:
                            textView = (TextView) view.findViewById(R.id.dependent_rg_tv_2);
                            QuestionnaireActivity.setBoolTreeMap(51, true);
                            break;
                        case R.id.dependent_rg_3:
                            textView = (TextView) view.findViewById(R.id.dependent_rg_tv_3);
                            QuestionnaireActivity.setBoolTreeMap(52, true);
                            break;
                        case R.id.dependent_rg_4:
                            textView = (TextView) view.findViewById(R.id.dependent_rg_tv_4);
                            QuestionnaireActivity.setBoolTreeMap(53, true);
                            break;
                        case R.id.dependent_rg_5:
                            textView = (TextView) view.findViewById(R.id.dependent_rg_tv_5);
                            QuestionnaireActivity.setBoolTreeMap(54, true);
                            break;
                        case R.id.dependent_rg_6:
                            textView = (TextView) view.findViewById(R.id.dependent_rg_tv_6);
                            QuestionnaireActivity.setBoolTreeMap(55, true);
                            break;
                        case R.id.dependent_rg_7:
                            textView = (TextView) view.findViewById(R.id.dependent_rg_tv_7);
                            QuestionnaireActivity.setBoolTreeMap(56, true);
                            break;
                    }
                    textView.setBackgroundColor(Color.parseColor("#04ff00"));
                }

                QuestionnaireActivity.setQuestionAnswer(8, a1 + a2 + a3 + a4 + a5 + a6 + a7);
            }
        });
    }
}
