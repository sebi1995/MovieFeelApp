package com.example.zdroa.myapplication.Question_Fragments;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zdroa.myapplication.QuestionnaireActivity;
import com.example.zdroa.myapplication.R;

public class NarcissistQuestionsFragment extends Fragment {

    private static int a1 = 0;
    private static int a2 = 0;
    private static int a3 = 0;
    private static int a4 = 0;
    private static int a5 = 0;
    private static int a6 = 0;
    private static int a7 = 0;

    private TextView textView;

    public NarcissistQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_narcissist_questions, container, false);

        RadioGroup radioGroup1 = (RadioGroup) view.findViewById(R.id.narcissist_rg_1);
        RadioGroup radioGroup2 = (RadioGroup) view.findViewById(R.id.narcissist_rg_2);
        RadioGroup radioGroup3 = (RadioGroup) view.findViewById(R.id.narcissist_rg_3);
        RadioGroup radioGroup4 = (RadioGroup) view.findViewById(R.id.narcissist_rg_4);
        RadioGroup radioGroup5 = (RadioGroup) view.findViewById(R.id.narcissist_rg_5);
        RadioGroup radioGroup6 = (RadioGroup) view.findViewById(R.id.narcissist_rg_6);
        RadioGroup radioGroup7 = (RadioGroup) view.findViewById(R.id.narcissist_rg_7);

        QuestionnaireActivity.setBoolTreeMap(29, false);
        QuestionnaireActivity.setBoolTreeMap(30, false);
        QuestionnaireActivity.setBoolTreeMap(31, false);
        QuestionnaireActivity.setBoolTreeMap(32, false);
        QuestionnaireActivity.setBoolTreeMap(33, false);
        QuestionnaireActivity.setBoolTreeMap(34, false);
        QuestionnaireActivity.setBoolTreeMap(35, false);

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
                    case 85: a1 = 1; break;//true
                    case 86: a1 = 0; break;//false
                    case 87: a1 = 0; break;//unsure
                    case 88: a2 = 1; break;//true
                    case 89: a2 = 0; break;//false
                    case 90: a2 = 1; break;//unsure
                    case 91: a3 = 1; break;//true
                    case 92: a3 = 0; break;//false
                    case 93: a3 = 1; break;//unsure
                    case 94: a4 = 1; break;//true
                    case 95: a4 = 0; break;//false
                    case 96: a4 = 0; break;//unsure
                    case 97: a5 = 1; break;//true
                    case 98: a5 = 0; break;//false
                    case 99: a5 = 0; break;//unsure
                    case 100: a6 = 1; break;//true
                    case 101: a6 = 0; break;//false
                    case 102: a6 = 0; break;//unsure
                    case 103: a7 = 1; break;//true
                    case 104: a7 = 0; break;//false
                    case 105: a7 = 0; break;//unsure
                }

                if (radioGroup.getId() == R.id.narcissist_rg_1 ||
                        radioGroup.getId() == R.id.narcissist_rg_2 ||
                        radioGroup.getId() == R.id.narcissist_rg_3 ||
                        radioGroup.getId() == R.id.narcissist_rg_4 ||
                        radioGroup.getId() == R.id.narcissist_rg_5 ||
                        radioGroup.getId() == R.id.narcissist_rg_6 ||
                        radioGroup.getId() == R.id.narcissist_rg_7) {

                    switch (radioGroup.getId()){
                        case R.id.narcissist_rg_1:
                            textView = (TextView) view.findViewById(R.id.narcissist_rg_tv_1);
                            QuestionnaireActivity.setBoolTreeMap(29, true);
                            break;
                        case R.id.narcissist_rg_2:
                            textView = (TextView) view.findViewById(R.id.narcissist_rg_tv_2);
                            QuestionnaireActivity.setBoolTreeMap(30, true);
                            break;
                        case R.id.narcissist_rg_3:
                            textView = (TextView) view.findViewById(R.id.narcissist_rg_tv_3);
                            QuestionnaireActivity.setBoolTreeMap(31, true);
                            break;
                        case R.id.narcissist_rg_4:
                            textView = (TextView) view.findViewById(R.id.narcissist_rg_tv_4);
                            QuestionnaireActivity.setBoolTreeMap(32, true);
                            break;
                        case R.id.narcissist_rg_5:
                            textView = (TextView) view.findViewById(R.id.narcissist_rg_tv_5);
                            QuestionnaireActivity.setBoolTreeMap(33, true);
                            break;
                        case R.id.narcissist_rg_6:
                            textView = (TextView) view.findViewById(R.id.narcissist_rg_tv_6);
                            QuestionnaireActivity.setBoolTreeMap(34, true);
                            break;
                        case R.id.narcissist_rg_7:
                            textView = (TextView) view.findViewById(R.id.narcissist_rg_tv_7);
                            QuestionnaireActivity.setBoolTreeMap(35, true);
                            break;
                    }
                    textView.setBackgroundColor(Color.parseColor("#04ff00"));
                }

                QuestionnaireActivity.setQuestionAnswer(5, a1 + a2 + a3 + a4 + a5 + a6 + a7);
            }
        });
    }

}
