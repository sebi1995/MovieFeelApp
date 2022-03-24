package com.example.zdroa.myapplication.communicators;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.zdroa.myapplication.activities.questionnaire.fragments.AnxiousQuestionsFragment;
import com.example.zdroa.myapplication.activities.questionnaire.fragments.DependentQuestionsFragment;
import com.example.zdroa.myapplication.activities.questionnaire.fragments.DepressiveQuestionsFragment;
import com.example.zdroa.myapplication.activities.questionnaire.fragments.EndingQuestionsFragment;
import com.example.zdroa.myapplication.activities.questionnaire.fragments.HistrionicQuestionsFragment;
import com.example.zdroa.myapplication.activities.questionnaire.fragments.InitializerFragment;
import com.example.zdroa.myapplication.activities.questionnaire.fragments.NarcissistQuestionsFragment;
import com.example.zdroa.myapplication.activities.questionnaire.fragments.ObsessiveQuestionsFragment;
import com.example.zdroa.myapplication.activities.questionnaire.fragments.ParanoidQuestionsFragment;
import com.example.zdroa.myapplication.activities.questionnaire.fragments.SchizoidQuestionsFragment;


public class FragmentCommunicator extends FragmentStatePagerAdapter {

    public FragmentCommunicator(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new InitializerFragment();
            case 1:
                return new AnxiousQuestionsFragment();
            case 2:
                return new ParanoidQuestionsFragment();
            case 3:
                return new HistrionicQuestionsFragment();
            case 4:
                return new ObsessiveQuestionsFragment();
            case 5:
                return new NarcissistQuestionsFragment();
            case 6:
                return new SchizoidQuestionsFragment();
            case 7:
                return new DepressiveQuestionsFragment();
            case 8:
                return new DependentQuestionsFragment();
            case 9:
                return new EndingQuestionsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 10;
    }


}
