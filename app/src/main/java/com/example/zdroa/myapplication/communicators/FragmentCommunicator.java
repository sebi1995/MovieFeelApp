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
import com.google.common.collect.ImmutableMap;


public class FragmentCommunicator extends FragmentStatePagerAdapter {

    public FragmentCommunicator(FragmentManager fm) {
        super(fm);
    }

    private static final ImmutableMap<Integer, Fragment> FRAGMENT_MAP = ImmutableMap.<Integer, Fragment>builder()
            .put(0, new InitializerFragment())
            .put(1, new AnxiousQuestionsFragment())
            .put(2, new ParanoidQuestionsFragment())
            .put(3, new HistrionicQuestionsFragment())
            .put(4, new ObsessiveQuestionsFragment())
            .put(5, new NarcissistQuestionsFragment())
            .put(6, new SchizoidQuestionsFragment())
            .put(7, new DepressiveQuestionsFragment())
            .put(8, new DependentQuestionsFragment())
            .put(9, new EndingQuestionsFragment())
            .build();

    @Override
    public Fragment getItem(int position) {
        // TODO: 21/04/2022 investigate
        return FRAGMENT_MAP.get(position);
    }

    @Override
    public int getCount() {
        return 10;
    }
}
