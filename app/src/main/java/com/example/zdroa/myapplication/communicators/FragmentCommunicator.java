package com.example.zdroa.myapplication.communicators;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.AnxiousQuestionsFragment;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.DependentQuestionsFragment;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.DepressiveQuestionsFragment;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.EndingQuestionsFragment;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.HistrionicQuestionsFragment;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.InitializerFragment;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.NarcissistQuestionsFragment;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.ObsessiveQuestionsFragment;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.ParanoidQuestionsFragment;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.fragments.SchizoidQuestionsFragment;
import com.example.zdroa.myapplication.utils.Logger;
import com.google.common.collect.ImmutableMap;

public class FragmentCommunicator extends FragmentStatePagerAdapter {

    private static final Logger LOGGER = new Logger(FragmentCommunicator.class);

    public FragmentCommunicator(FragmentManager fm) {
        super(fm);
    }

    private static final ImmutableMap<Integer, Fragment> FRAGMENTS_MAP = ImmutableMap.<Integer, Fragment>builder()
            .put(InitializerFragment.INDEX, new InitializerFragment())
            .put(AnxiousQuestionsFragment.INDEX, new AnxiousQuestionsFragment())
            .put(ParanoidQuestionsFragment.INDEX, new ParanoidQuestionsFragment())
            .put(HistrionicQuestionsFragment.INDEX, new HistrionicQuestionsFragment())
            .put(ObsessiveQuestionsFragment.INDEX, new ObsessiveQuestionsFragment())
            .put(NarcissistQuestionsFragment.INDEX, new NarcissistQuestionsFragment())
            .put(SchizoidQuestionsFragment.INDEX, new SchizoidQuestionsFragment())
            .put(DepressiveQuestionsFragment.INDEX, new DepressiveQuestionsFragment())
            .put(DependentQuestionsFragment.INDEX, new DependentQuestionsFragment())
            .put(EndingQuestionsFragment.INDEX, new EndingQuestionsFragment())
            .build();

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = FRAGMENTS_MAP.get(position);
        if (fragment == null) {
            LOGGER.logError("Fragment with position: " + position + ", doesn't exist.");
            return new InitializerFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return FRAGMENTS_MAP.size();
    }
}
