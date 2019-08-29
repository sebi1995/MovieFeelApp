package com.example.zdroa.myapplication.Question_Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.zdroa.myapplication.LoginAreaActivity;
import com.example.zdroa.myapplication.QuestionnaireActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.requests.Questionnaire_Request;
import com.example.zdroa.myapplication.session.SessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ending_questions, container, false);

        SECTIONS_COMPLETE.put(1, false);
        SECTIONS_COMPLETE.put(2, false);
        SECTIONS_COMPLETE.put(3, false);
        SECTIONS_COMPLETE.put(4, false);
        SECTIONS_COMPLETE.put(5, false);
        SECTIONS_COMPLETE.put(6, false);
        SECTIONS_COMPLETE.put(7, false);
        SECTIONS_COMPLETE.put(8, false);

        mChronometer = (Chronometer) view.findViewById(R.id.ending_chronometer);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();

        final Button button1 = (Button) view.findViewById(R.id.bGotoMainAreaActivity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                if (SECTIONS_COMPLETE.get(1) &&
                        SECTIONS_COMPLETE.get(2) &&
                        SECTIONS_COMPLETE.get(3) &&
                        SECTIONS_COMPLETE.get(4) &&
                        SECTIONS_COMPLETE.get(5) &&
                        SECTIONS_COMPLETE.get(6) &&
                        SECTIONS_COMPLETE.get(7) &&
                        SECTIONS_COMPLETE.get(8)) {
                    complete = true;
                }

                if (complete) {
                    mChronometer.stop();

                    String timer_time = String.valueOf(SystemClock.elapsedRealtime() - mChronometer.getBase());
                    timer_time = timer_time.substring(0, timer_time.length() - 3);


                    TreeMap<Integer, Integer> resultsMap;
                    resultsMap = QuestionnaireActivity.getQuestionAnswer();

                    String PERSON_TYPE = null;

                    int PERSON_TYPE_INT = entriesSortedByValues(resultsMap).last().getKey();

                    switch (PERSON_TYPE_INT) {
                        case 1:
                            PERSON_TYPE = "anxious";
                            break;
                        case 2:
                            PERSON_TYPE = "paranoid";
                            break;
                        case 3:
                            PERSON_TYPE = "histrionic";
                            break;
                        case 4:
                            PERSON_TYPE = "obsessive";
                            break;
                        case 5:
                            PERSON_TYPE = "narcissist";
                            break;
                        case 6:
                            PERSON_TYPE = "schizoid";
                            break;
                        case 7:
                            PERSON_TYPE = "depressive";
                            break;
                        case 8:
                            PERSON_TYPE = "dependent";
                            break;
                    }

                    final SessionHandler sessionHandler = new SessionHandler(getContext());

                    String user_id = sessionHandler.getID();
                    final String finalTimer_time = timer_time;
                    final String finalPERSON_TYPE = PERSON_TYPE;

                    // Response received from the server
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
//                                    sessionHandler.setVar("user_type_bool",null, true);
//                                    sessionHandler.setVar("user_type", finalPERSON_TYPE, null);

                                    QuestionnaireActivity.fa.finish();
                                    startActivity(new Intent(getActivity(), LoginAreaActivity.class));


                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setMessage("fail")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    Questionnaire_Request QRT = new Questionnaire_Request(user_id, finalTimer_time, finalPERSON_TYPE, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(QRT);
                }
            }
        });
        return view;
    }

    static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {
                    @Override
                    public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? res : 1; // Special fix to preserve items with equal values
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
}
