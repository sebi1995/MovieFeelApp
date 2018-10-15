package com.example.zdroa.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.zdroa.myapplication.communicators.FragmentCommunicator;

import java.util.Collections;
import java.util.TreeMap;

public class QuestionnaireActivity extends AppCompatActivity {

    ViewPager viewPager;

    public static FragmentCommunicator communicator;

    private static int QUESTION_1_ANSWER;
    private static int QUESTION_2_ANSWER;
    private static int QUESTION_3_ANSWER;
    private static int QUESTION_4_ANSWER;
    private static int QUESTION_5_ANSWER;
    private static int QUESTION_6_ANSWER;
    private static int QUESTION_7_ANSWER;
    private static int QUESTION_8_ANSWER;

    private static TreeMap<Integer, Boolean> boolTreeMap = new TreeMap<>(/*Collections.<Integer>reverseOrder()*/);

    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        fa = this;

        viewPager = (ViewPager) findViewById(R.id.vpQuestionsActivity);

        communicator = new FragmentCommunicator(getSupportFragmentManager());
        viewPager.setAdapter(communicator);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setCurrentItem(0);
    }

    public void setPagerFragment(int a) {
        viewPager.setCurrentItem(a);
    }

    public static void setQuestionAnswer(int question, int questionAnswer) {
        switch (question) {
            case 1:
                QUESTION_1_ANSWER = questionAnswer;
                break;
            case 2:
                QUESTION_2_ANSWER = questionAnswer;
                break;
            case 3:
                QUESTION_3_ANSWER = questionAnswer;
                break;
            case 4:
                QUESTION_4_ANSWER = questionAnswer;
                break;
            case 5:
                QUESTION_5_ANSWER = questionAnswer;
                break;
            case 6:
                QUESTION_6_ANSWER = questionAnswer;
                break;
            case 7:
                QUESTION_7_ANSWER = questionAnswer;
                break;
            case 8:
                QUESTION_8_ANSWER = questionAnswer;
                break;
        }
    }

    public static TreeMap<Integer, Integer> getQuestionAnswer() {
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.<Integer>reverseOrder());

        map.put(1, QUESTION_1_ANSWER);
        map.put(2, QUESTION_2_ANSWER);
        map.put(3, QUESTION_3_ANSWER);
        map.put(4, QUESTION_4_ANSWER);
        map.put(5, QUESTION_5_ANSWER);
        map.put(6, QUESTION_6_ANSWER);
        map.put(7, QUESTION_7_ANSWER);
        map.put(8, QUESTION_8_ANSWER);

        return map;
    }

    public static void setBoolTreeMap(int n, Boolean b) {
        boolTreeMap.put(n, b);
    }

    public static boolean getBoolTreeMapItem(int position) {
        return boolTreeMap.get(position);
    }

    public static TreeMap<Integer, Boolean> getBoolTreeMap() {
        return boolTreeMap;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(
                fa,
                "Please go back to the first screen to cancel.",
                Toast.LENGTH_LONG).show();
    }

    /*
    28 - Action
    12 - Adventure
    16 - Animation
    35 - Comedy
    80 - Crime
    99 - Documentary
    18 - Drama
    10751 - Family
    14 - Fantasy
    36 - History
    27 - Horror
    10402 - Music
    9648 - Mystery
    10749 - Romance
    878 - Science Fiction
    10770 - TV Movie
    53 - Thriller
    10752 - War
    37 - Western
    */

}
