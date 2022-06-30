package com.example.zdroa.myapplication.activities.questionnaire.fragments;

import static com.example.zdroa.myapplication.utilities.PersonType.ANXIOUS;
import static com.example.zdroa.myapplication.utilities.PersonType.DEPENDANT;
import static com.example.zdroa.myapplication.utilities.PersonType.DEPRESSIVE;
import static com.example.zdroa.myapplication.utilities.PersonType.HISTRIONIC;
import static com.example.zdroa.myapplication.utilities.PersonType.NARCISSIST;
import static com.example.zdroa.myapplication.utilities.PersonType.OBSESSIVE;
import static com.example.zdroa.myapplication.utilities.PersonType.PARANOID;
import static com.example.zdroa.myapplication.utilities.PersonType.SCHIZOID;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.example.zdroa.myapplication.ActivityNavigator;
import com.example.zdroa.myapplication.MainActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.questionnaire.QuestionnaireActivity;
import com.example.zdroa.myapplication.handlers.UserSession;
import com.example.zdroa.myapplication.repositories.UserRepository;
import com.example.zdroa.myapplication.services.UserService;
import com.example.zdroa.myapplication.utilities.PersonType;
import com.example.zdroa.myapplication.utils.AppSettings;
import com.example.zdroa.myapplication.utils.Logger;
import com.google.common.collect.ImmutableMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class EndingQuestionsFragment extends Fragment implements ActivityNavigator {

    private TextView textView;

    public EndingQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ending_questions, container, false);
        return view;
    }
}
