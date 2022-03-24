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

public class DepressiveQuestionsFragment extends Fragment {

    private static int a1 = 0;
    private static int a2 = 0;
    private static int a3 = 0;
    private static int a4 = 0;
    private static int a5 = 0;
    private static int a6 = 0;
    private static int a7 = 0;

    private TextView textView;

    public DepressiveQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_depressive_questions, container, false);

        RadioGroup radioGroup1 = (RadioGroup) view.findViewById(R.id.depressive_rg_1);
        RadioGroup radioGroup2 = (RadioGroup) view.findViewById(R.id.depressive_rg_2);
        RadioGroup radioGroup3 = (RadioGroup) view.findViewById(R.id.depressive_rg_3);
        RadioGroup radioGroup4 = (RadioGroup) view.findViewById(R.id.depressive_rg_4);
        RadioGroup radioGroup5 = (RadioGroup) view.findViewById(R.id.depressive_rg_5);
        RadioGroup radioGroup6 = (RadioGroup) view.findViewById(R.id.depressive_rg_6);
        RadioGroup radioGroup7 = (RadioGroup) view.findViewById(R.id.depressive_rg_7);

        QuestionnaireActivity.setBoolTreeMap(43, false);
        QuestionnaireActivity.setBoolTreeMap(44, false);
        QuestionnaireActivity.setBoolTreeMap(45, false);
        QuestionnaireActivity.setBoolTreeMap(46, false);
        QuestionnaireActivity.setBoolTreeMap(47, false);
        QuestionnaireActivity.setBoolTreeMap(48, false);
        QuestionnaireActivity.setBoolTreeMap(49, false);

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
                    case 127:
                        a1 = 1;
                        break;//true
                    case 128:
                        a1 = 0;
                        break;//false
                    case 129:
                        a1 = 1;
                        break;//unsure
                    case 130:
                        a2 = 1;
                        break;//true
                    case 131:
                        a2 = 0;
                        break;//false
                    case 132:
                        a2 = 1;
                        break;//unsure
                    case 133:
                        a3 = 1;
                        break;//true
                    case 134:
                        a3 = 0;
                        break;//false
                    case 135:
                        a3 = 1;
                        break;//unsure
                    case 136:
                        a4 = 1;
                        break;//true
                    case 137:
                        a4 = 0;
                        break;//false
                    case 138:
                        a4 = 0;
                        break;//unsure
                    case 139:
                        a5 = 1;
                        break;//true
                    case 140:
                        a5 = 0;
                        break;//false
                    case 141:
                        a5 = 0;
                        break;//unsure
                    case 142:
                        a6 = 1;
                        break;//true
                    case 143:
                        a6 = 0;
                        break;//false
                    case 144:
                        a6 = 0;
                        break;//unsure
                    case 145:
                        a7 = 1;
                        break;//true
                    case 146:
                        a7 = 0;
                        break;//false
                    case 147:
                        a7 = 0;
                        break;//unsure
                }

                if (radioGroup.getId() == R.id.depressive_rg_1 ||
                        radioGroup.getId() == R.id.depressive_rg_2 ||
                        radioGroup.getId() == R.id.depressive_rg_3 ||
                        radioGroup.getId() == R.id.depressive_rg_4 ||
                        radioGroup.getId() == R.id.depressive_rg_5 ||
                        radioGroup.getId() == R.id.depressive_rg_6 ||
                        radioGroup.getId() == R.id.depressive_rg_7) {

                    switch (radioGroup.getId()) {
                        case R.id.depressive_rg_1:
                            textView = (TextView) view.findViewById(R.id.depressive_rg_tv_1);
                            QuestionnaireActivity.setBoolTreeMap(43, true);
                            break;
                        case R.id.depressive_rg_2:
                            textView = (TextView) view.findViewById(R.id.depressive_rg_tv_2);
                            QuestionnaireActivity.setBoolTreeMap(44, true);
                            break;
                        case R.id.depressive_rg_3:
                            textView = (TextView) view.findViewById(R.id.depressive_rg_tv_3);
                            QuestionnaireActivity.setBoolTreeMap(45, true);
                            break;
                        case R.id.depressive_rg_4:
                            textView = (TextView) view.findViewById(R.id.depressive_rg_tv_4);
                            QuestionnaireActivity.setBoolTreeMap(46, true);
                            break;
                        case R.id.depressive_rg_5:
                            textView = (TextView) view.findViewById(R.id.depressive_rg_tv_5);
                            QuestionnaireActivity.setBoolTreeMap(47, true);
                            break;
                        case R.id.depressive_rg_6:
                            textView = (TextView) view.findViewById(R.id.depressive_rg_tv_6);
                            QuestionnaireActivity.setBoolTreeMap(48, true);
                            break;
                        case R.id.depressive_rg_7:
                            textView = (TextView) view.findViewById(R.id.depressive_rg_tv_7);
                            QuestionnaireActivity.setBoolTreeMap(49, true);
                            break;
                    }
                    textView.setBackgroundColor(Color.parseColor("#04ff00"));
                }

                QuestionnaireActivity.setQuestionAnswer(7, a1 + a2 + a3 + a4 + a5 + a6 + a7);
            }
        });
    }

}
