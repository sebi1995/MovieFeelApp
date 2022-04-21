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

// TODO: 22/04/2022 refactor everything
public class AnxiousQuestionsFragment extends Fragment {

    private static int a1 = 0;
    private static int a2 = 0;
    private static int a3 = 0;
    private static int a4 = 0;
    private static int a5 = 0;
    private static int a6 = 0;
    private static int a7 = 0;

    private TextView textView;

    public AnxiousQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anxious_questions, container, false);

        RadioGroup radioGroup1 = view.findViewById(R.id.anxious_rg_1);
        RadioGroup radioGroup2 = view.findViewById(R.id.anxious_rg_2);
        RadioGroup radioGroup3 = view.findViewById(R.id.anxious_rg_3);
        RadioGroup radioGroup4 = view.findViewById(R.id.anxious_rg_4);
        RadioGroup radioGroup5 = view.findViewById(R.id.anxious_rg_5);
        RadioGroup radioGroup6 = view.findViewById(R.id.anxious_rg_6);
        RadioGroup radioGroup7 = view.findViewById(R.id.anxious_rg_7);

        QuestionnaireActivity.setBoolTreeMap(1, false);
        QuestionnaireActivity.setBoolTreeMap(2, false);
        QuestionnaireActivity.setBoolTreeMap(3, false);
        QuestionnaireActivity.setBoolTreeMap(4, false);
        QuestionnaireActivity.setBoolTreeMap(5, false);
        QuestionnaireActivity.setBoolTreeMap(6, false);
        QuestionnaireActivity.setBoolTreeMap(7, false);

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
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case 1:
                    a1 = 1;
                    break;//true
                case 2:
                    a1 = 0;
                    break;//false
                case 3:
                    a1 = 0;
                    break;//unsure
                case 4:
                    a2 = 1;
                    break;//true
                case 5:
                    a2 = 0;
                    break;//false
                case 6:
                    a2 = 0;
                    break;//unsure
                case 7:
                    a3 = 1;
                    break;//true
                case 8:
                    a3 = 0;
                    break;//false
                case 9:
                    a3 = 1;
                    break;//unsure
                case 10:
                    a4 = 1;
                    break;//true
                case 11:
                    a4 = 0;
                    break;//false
                case 12:
                    a4 = 0;
                    break;//unsure
                case 13:
                    a5 = 1;
                    break;//true
                case 14:
                    a5 = 0;
                    break;//false
                case 15:
                    a5 = 1;
                    break;//unsure
                case 16:
                    a6 = 1;
                    break;//true
                case 17:
                    a6 = 0;
                    break;//false
                case 18:
                    a6 = 0;
                    break;//unsure
                case 19:
                    a7 = 1;
                    break;//true
                case 20:
                    a7 = 0;
                    break;//false
                case 21:
                    a7 = 1;
                    break;//unsure
            }

            if (radioGroup.getId() == R.id.anxious_rg_1 ||
                    radioGroup.getId() == R.id.anxious_rg_2 ||
                    radioGroup.getId() == R.id.anxious_rg_3 ||
                    radioGroup.getId() == R.id.anxious_rg_4 ||
                    radioGroup.getId() == R.id.anxious_rg_5 ||
                    radioGroup.getId() == R.id.anxious_rg_6 ||
                    radioGroup.getId() == R.id.anxious_rg_7) {

                switch (radioGroup.getId()) {
                    case R.id.anxious_rg_1:
                        textView = (TextView) view.findViewById(R.id.anxious_rg_tv_1);
                        QuestionnaireActivity.setBoolTreeMap(1, true);
                        break;
                    case R.id.anxious_rg_2:
                        textView = (TextView) view.findViewById(R.id.anxious_rg_tv_2);
                        QuestionnaireActivity.setBoolTreeMap(2, true);
                        break;
                    case R.id.anxious_rg_3:
                        textView = (TextView) view.findViewById(R.id.anxious_rg_tv_3);
                        QuestionnaireActivity.setBoolTreeMap(3, true);
                        break;
                    case R.id.anxious_rg_4:
                        textView = (TextView) view.findViewById(R.id.anxious_rg_tv_4);
                        QuestionnaireActivity.setBoolTreeMap(4, true);
                        break;
                    case R.id.anxious_rg_5:
                        textView = (TextView) view.findViewById(R.id.anxious_rg_tv_5);
                        QuestionnaireActivity.setBoolTreeMap(5, true);
                        break;
                    case R.id.anxious_rg_6:
                        textView = (TextView) view.findViewById(R.id.anxious_rg_tv_6);
                        QuestionnaireActivity.setBoolTreeMap(6, true);
                        break;
                    case R.id.anxious_rg_7:
                        textView = (TextView) view.findViewById(R.id.anxious_rg_tv_7);
                        QuestionnaireActivity.setBoolTreeMap(7, true);
                        break;
                }
                textView.setBackgroundColor(Color.parseColor("#04ff00"));
            }

            QuestionnaireActivity.setQuestionAnswer(1, a1 + a2 + a3 + a4 + a5 + a6 + a7);
        });
    }
}
