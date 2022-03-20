package com.example.zdroa.myapplication.Question_Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zdroa.myapplication.QuestionnaireActivity;
import com.example.zdroa.myapplication.R;

public class ObsessiveQuestionsFragment extends Fragment {

    private static int a1 = 0;
    private static int a2 = 0;
    private static int a3 = 0;
    private static int a4 = 0;
    private static int a5 = 0;
    private static int a6 = 0;
    private static int a7 = 0;

    private TextView textView;

    public ObsessiveQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_obsessive_questions, container, false);

        RadioGroup radioGroup1 = (RadioGroup) view.findViewById(R.id.obsessive_rg_1);
        RadioGroup radioGroup2 = (RadioGroup) view.findViewById(R.id.obsessive_rg_2);
        RadioGroup radioGroup3 = (RadioGroup) view.findViewById(R.id.obsessive_rg_3);
        RadioGroup radioGroup4 = (RadioGroup) view.findViewById(R.id.obsessive_rg_4);
        RadioGroup radioGroup5 = (RadioGroup) view.findViewById(R.id.obsessive_rg_5);
        RadioGroup radioGroup6 = (RadioGroup) view.findViewById(R.id.obsessive_rg_6);
        RadioGroup radioGroup7 = (RadioGroup) view.findViewById(R.id.obsessive_rg_7);

        QuestionnaireActivity.setBoolTreeMap(22, false);
        QuestionnaireActivity.setBoolTreeMap(23, false);
        QuestionnaireActivity.setBoolTreeMap(24, false);
        QuestionnaireActivity.setBoolTreeMap(25, false);
        QuestionnaireActivity.setBoolTreeMap(26, false);
        QuestionnaireActivity.setBoolTreeMap(27, false);
        QuestionnaireActivity.setBoolTreeMap(28, false);

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
                    case 64: a1 = 1; break;//true
                    case 65: a1 = 0; break;//false
                    case 66: a1 = 0; break;//unsure
                    case 67: a2 = 1; break;//true
                    case 68: a2 = 0; break;//false
                    case 69: a2 = 1; break;//unsure
                    case 70: a3 = 1; break;//true
                    case 71: a3 = 0; break;//false
                    case 72: a3 = 1; break;//unsure
                    case 73: a4 = 1; break;//true
                    case 74: a4 = 0; break;//false
                    case 75: a4 = 0; break;//unsure
                    case 76: a5 = 1; break;//true
                    case 77: a5 = 0; break;//false
                    case 78: a5 = 0; break;//unsure
                    case 79: a6 = 1; break;//true
                    case 80: a6 = 0; break;//false
                    case 81: a6 = 0; break;//unsure
                    case 82: a7 = 1; break;//true
                    case 83: a7 = 0; break;//false
                    case 84: a7 = 0; break;//unsure
                }

                if (radioGroup.getId() == R.id.obsessive_rg_1 ||
                        radioGroup.getId() == R.id.obsessive_rg_2 ||
                        radioGroup.getId() == R.id.obsessive_rg_3 ||
                        radioGroup.getId() == R.id.obsessive_rg_4 ||
                        radioGroup.getId() == R.id.obsessive_rg_5 ||
                        radioGroup.getId() == R.id.obsessive_rg_6 ||
                        radioGroup.getId() == R.id.obsessive_rg_7) {

                    switch (radioGroup.getId()) {
                        case R.id.obsessive_rg_1:
                            textView = (TextView) view.findViewById(R.id.obsessive_rg_tv_1);
                            QuestionnaireActivity.setBoolTreeMap(22, true);
                            break;
                        case R.id.obsessive_rg_2:
                            textView = (TextView) view.findViewById(R.id.obsessive_rg_tv_2);
                            QuestionnaireActivity.setBoolTreeMap(23, true);
                            break;
                        case R.id.obsessive_rg_3:
                            textView = (TextView) view.findViewById(R.id.obsessive_rg_tv_3);
                            QuestionnaireActivity.setBoolTreeMap(24, true);
                            break;
                        case R.id.obsessive_rg_4:
                            textView = (TextView) view.findViewById(R.id.obsessive_rg_tv_4);
                            QuestionnaireActivity.setBoolTreeMap(25, true);
                            break;
                        case R.id.obsessive_rg_5:
                            textView = (TextView) view.findViewById(R.id.obsessive_rg_tv_5);
                            QuestionnaireActivity.setBoolTreeMap(26, true);
                            break;
                        case R.id.obsessive_rg_6:
                            textView = (TextView) view.findViewById(R.id.obsessive_rg_tv_6);
                            QuestionnaireActivity.setBoolTreeMap(27, true);
                            break;
                        case R.id.obsessive_rg_7:
                            textView = (TextView) view.findViewById(R.id.obsessive_rg_tv_7);
                            QuestionnaireActivity.setBoolTreeMap(28, true);
                            break;
                    }
                    textView.setBackgroundColor(Color.parseColor("#04ff00"));
                }

                QuestionnaireActivity.setQuestionAnswer(4, a1 + a2 + a3 + a4 + a5 + a6 + a7);
            }
        });
    }
}
