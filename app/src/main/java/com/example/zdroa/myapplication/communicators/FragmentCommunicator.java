package com.example.zdroa.myapplication.communicators;


import static com.example.zdroa.myapplication.utilities.PersonType.ANXIOUS;
import static com.example.zdroa.myapplication.utilities.PersonType.DEPENDANT;
import static com.example.zdroa.myapplication.utilities.PersonType.DEPRESSIVE;
import static com.example.zdroa.myapplication.utilities.PersonType.HISTRIONIC;
import static com.example.zdroa.myapplication.utilities.PersonType.NARCISSIST;
import static com.example.zdroa.myapplication.utilities.PersonType.OBSESSIVE;
import static com.example.zdroa.myapplication.utilities.PersonType.PARANOID;
import static com.example.zdroa.myapplication.utilities.PersonType.SCHIZOID;

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
import com.example.zdroa.myapplication.utilities.PersonType;
import com.example.zdroa.myapplication.utils.Logger;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;


public class FragmentCommunicator extends FragmentStatePagerAdapter {

    private static final Logger LOGGER = new Logger(FragmentCommunicator.class);

    public FragmentCommunicator(FragmentManager fm) {
        super(fm);
    }

    private static final ImmutableMap<Integer, Fragment> fragmentMap = ImmutableMap.<Integer, Fragment>builder()
            .put(InitializerFragment.INDEX, new InitializerFragment())
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
        Fragment fragment = fragmentMap.get(position);
        if (fragment == null) {
            LOGGER.logError("Position: " + position + " doesn't exist.");
            return new InitializerFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
