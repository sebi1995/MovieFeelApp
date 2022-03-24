package com.example.zdroa.myapplication.activities.questionnaire.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.MainActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.questionnaire.QuestionnaireActivity;
import com.example.zdroa.myapplication.handlers.HttpRequestHandler;
import com.example.zdroa.myapplication.handlers.UserSessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class EndingQuestionsFragment extends Fragment {

    private Chronometer mChronometer;
    private TextView textView;
    private static Boolean complete = false;
    private static TreeMap<Integer, Boolean> SECTIONS_COMPLETE = new TreeMap<>();

    public EndingQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ending_questions, container, false);

        SECTIONS_COMPLETE.put(1, false);
        SECTIONS_COMPLETE.put(2, false);
        SECTIONS_COMPLETE.put(3, false);
        SECTIONS_COMPLETE.put(4, false);
        SECTIONS_COMPLETE.put(5, false);
        SECTIONS_COMPLETE.put(6, false);
        SECTIONS_COMPLETE.put(7, false);
        SECTIONS_COMPLETE.put(8, false);

        mChronometer = view.findViewById(R.id.ending_chronometer);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();

        view.findViewById(R.id.bGotoMainAreaActivity).setOnClickListener(v -> {

            QuestionnaireActivity parent = (QuestionnaireActivity) getActivity();

            TreeMap<Integer, Boolean> map = QuestionnaireActivity.getBoolTreeMap();

            for (int i = 50; i <= 56; i++) {
                if (!map.get(i)) {
                    parent.setPagerFragment(8);
                } else SECTIONS_COMPLETE.put(8, true);
            }
            for (int i = 43; i <= 49; i++) {
                if (!map.get(i)) {
                    parent.setPagerFragment(7);
                } else SECTIONS_COMPLETE.put(7, true);
            }
            for (int i = 36; i <= 42; i++) {
                if (!map.get(i)) {
                    parent.setPagerFragment(6);
                } else SECTIONS_COMPLETE.put(6, true);
            }
            for (int i = 29; i <= 35; i++) {
                if (!map.get(i)) {
                    parent.setPagerFragment(5);
                } else SECTIONS_COMPLETE.put(5, true);
            }
            for (int i = 22; i <= 28; i++) {
                if (!map.get(i)) {
                    parent.setPagerFragment(4);
                } else SECTIONS_COMPLETE.put(4, true);
            }
            for (int i = 15; i <= 21; i++) {
                if (!map.get(i)) {
                    parent.setPagerFragment(3);
                } else SECTIONS_COMPLETE.put(3, true);
            }
            for (int i = 8; i <= 14; i++) {
                if (!map.get(i)) {
                    parent.setPagerFragment(2);
                } else SECTIONS_COMPLETE.put(2, true);
            }
            for (int i = 1; i <= 7; i++) {
                if (!map.get(i)) {
                    parent.setPagerFragment(1);
                } else SECTIONS_COMPLETE.put(1, true);
            }

            if (SECTIONS_COMPLETE.get(1) && SECTIONS_COMPLETE.get(2) && SECTIONS_COMPLETE.get(3) && SECTIONS_COMPLETE.get(4) && SECTIONS_COMPLETE.get(5) && SECTIONS_COMPLETE.get(6) && SECTIONS_COMPLETE.get(7) && SECTIONS_COMPLETE.get(8)) {
                complete = true;
            }

            String timerDuration = null;
            String personType = null;

            if (complete) {
                mChronometer.stop();
                timerDuration = String.valueOf(SystemClock.elapsedRealtime() - mChronometer.getBase());
                timerDuration = timerDuration.substring(0, timerDuration.length() - 3);
                personType = Map.of(
                        1, "anxious",
                        2, "paranoid",
                        3, "histrionic",
                        4, "obsessive",
                        5, "narcissist",
                        6, "schizoid",
                        7, "depressive",
                        8, "dependent"
                ).get(entriesSortedByValues(QuestionnaireActivity.getQuestionAnswer()).last().getKey());
            }

            UserSessionHandler session = new UserSessionHandler(getContext());

            String finalPersonType = personType;
            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        session.setPersonType(finalPersonType);

                        QuestionnaireActivity.fa.finish();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    } else {
                        new AlertDialog.Builder(getActivity()).setMessage("fail")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };

            Volley.newRequestQueue(getContext())
                    .add(HttpRequestHandler
                            .registerQuestionnaire(
                                    responseListener,
                                    session.getId(),
                                    timerDuration,
                                    personType)
                    );

        });
        return view;
    }

    static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1; // Special fix to preserve items with equal values
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
