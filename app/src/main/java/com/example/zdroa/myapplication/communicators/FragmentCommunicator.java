package com.example.zdroa.myapplication.communicators;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.zdroa.myapplication.Question_Fragments.AnxiousQuestionsFragment;
import com.example.zdroa.myapplication.Question_Fragments.DependentQuestionsFragment;
import com.example.zdroa.myapplication.Question_Fragments.DepressiveQuestionsFragment;
import com.example.zdroa.myapplication.Question_Fragments.EndingQuestionsFragment;
import com.example.zdroa.myapplication.Question_Fragments.HistrionicQuestionsFragment;
import com.example.zdroa.myapplication.Question_Fragments.InitializerFragment;
import com.example.zdroa.myapplication.Question_Fragments.NarcissistQuestionsFragment;
import com.example.zdroa.myapplication.Question_Fragments.ObsessiveQuestionsFragment;
import com.example.zdroa.myapplication.Question_Fragments.ParanoidQuestionsFragment;
import com.example.zdroa.myapplication.Question_Fragments.SchizoidQuestionsFragment;


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
