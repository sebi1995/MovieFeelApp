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

public class ParanoidQuestionsFragment extends Fragment {

    private static int a1 = 0;
    private static int a2 = 0;
    private static int a3 = 0;
    private static int a4 = 0;
    private static int a5 = 0;
    private static int a6 = 0;
    private static int a7 = 0;

    private TextView textView;

    public ParanoidQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_paranoid_questions, container, false);

        RadioGroup radioGroup1 = (RadioGroup) view.findViewById(R.id.paranoid_rg_1);
        RadioGroup radioGroup2 = (RadioGroup) view.findViewById(R.id.paranoid_rg_2);
        RadioGroup radioGroup3 = (RadioGroup) view.findViewById(R.id.paranoid_rg_3);
        RadioGroup radioGroup4 = (RadioGroup) view.findViewById(R.id.paranoid_rg_4);
        RadioGroup radioGroup5 = (RadioGroup) view.findViewById(R.id.paranoid_rg_5);
        RadioGroup radioGroup6 = (RadioGroup) view.findViewById(R.id.paranoid_rg_6);
        RadioGroup radioGroup7 = (RadioGroup) view.findViewById(R.id.paranoid_rg_7);

        QuestionnaireActivity.setBoolTreeMap(8, false);
        QuestionnaireActivity.setBoolTreeMap(9, false);
        QuestionnaireActivity.setBoolTreeMap(10, false);
        QuestionnaireActivity.setBoolTreeMap(11, false);
        QuestionnaireActivity.setBoolTreeMap(12, false);
        QuestionnaireActivity.setBoolTreeMap(13, false);
        QuestionnaireActivity.setBoolTreeMap(14, false);

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
                    case 22: a1 = 1; break;//true
                    case 23: a1 = 0; break;//false
                    case 24: a1 = 1; break;//unsure
                    case 25: a2 = 1; break;//true
                    case 26: a2 = 0; break;//false
                    case 27: a2 = 0; break;//unsure
                    case 28: a3 = 1; break;//true
                    case 29: a3 = 0; break;//false
                    case 30: a3 = 0; break;//unsure
                    case 31: a4 = 1; break;//true
                    case 32: a4 = 0; break;//false
                    case 33: a4 = 0; break;//unsure
                    case 34: a5 = 1; break;//true
                    case 35: a5 = 0; break;//false
                    case 36: a5 = 0; break;//unsure
                    case 37: a6 = 1; break;//true
                    case 38: a6 = 0; break;//false
                    case 39: a6 = 0; break;//unsure
                    case 40: a7 = 1; break;//true
                    case 41: a7 = 0; break;//false
                    case 42: a7 = 1; break;//unsure
                }

                if (radioGroup.getId() == R.id.paranoid_rg_1 ||
                        radioGroup.getId() == R.id.paranoid_rg_2 ||
                        radioGroup.getId() == R.id.paranoid_rg_3 ||
                        radioGroup.getId() == R.id.paranoid_rg_4 ||
                        radioGroup.getId() == R.id.paranoid_rg_5 ||
                        radioGroup.getId() == R.id.paranoid_rg_6 ||
                        radioGroup.getId() == R.id.paranoid_rg_7) {
                    switch (radioGroup.getId()){
                        case R.id.paranoid_rg_1:
                            textView = (TextView) view.findViewById(R.id.paranoid_rg_tv_1);
                            QuestionnaireActivity.setBoolTreeMap(8, true);
                            break;
                        case R.id.paranoid_rg_2:
                            textView = (TextView) view.findViewById(R.id.paranoid_rg_tv_2);
                            QuestionnaireActivity.setBoolTreeMap(9, true);
                            break;
                        case R.id.paranoid_rg_3:
                            textView = (TextView) view.findViewById(R.id.paranoid_rg_tv_3);
                            QuestionnaireActivity.setBoolTreeMap(10, true);
                            break;
                        case R.id.paranoid_rg_4:
                            textView = (TextView) view.findViewById(R.id.paranoid_rg_tv_4);
                            QuestionnaireActivity.setBoolTreeMap(11, true);
                            break;
                        case R.id.paranoid_rg_5:
                            textView = (TextView) view.findViewById(R.id.paranoid_rg_tv_5);
                            QuestionnaireActivity.setBoolTreeMap(12, true);
                            break;
                        case R.id.paranoid_rg_6:
                            textView = (TextView) view.findViewById(R.id.paranoid_rg_tv_6);
                            QuestionnaireActivity.setBoolTreeMap(13, true);
                            break;
                        case R.id.paranoid_rg_7:
                            textView = (TextView) view.findViewById(R.id.paranoid_rg_tv_7);
                            QuestionnaireActivity.setBoolTreeMap(14, true);
                            break;
                    }
                    textView.setBackgroundColor(Color.parseColor("#04ff00"));
                }

                QuestionnaireActivity.setQuestionAnswer(2, a1 + a2 + a3 + a4 + a5 + a6 + a7);
            }
        });
    }
}
