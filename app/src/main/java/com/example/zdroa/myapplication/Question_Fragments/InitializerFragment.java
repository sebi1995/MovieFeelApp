package com.example.zdroa.myapplication.Question_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.zdroa.myapplication.QuestionnaireActivity;
import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.session.Session_Class;

public class InitializerFragment extends Fragment {

    private String uFirstName;
    private String uSurname;

    public InitializerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initializer_questions, container, false);

        Session_Class session = new Session_Class(getContext());

        uFirstName = session.getFirstname();
        uSurname = session.getSurname();

        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_initializer_iv_cancel);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionnaireActivity.fa.finish();
            }
        });

        TextView page_title = (TextView) view.findViewById(R.id.fragment_initializer_tv_title);
        page_title.setText("Welcome!");
        page_title.append("\n" + uFirstName + " " + uSurname);

        TextView textView = (TextView) view.findViewById(R.id.initializer_tv_welcome_msg);

        textView.setText("This will be the hardest part of using MovieFeel!");
        textView.append("\n\nPlease respond to the following statements by pressing the true, false or unsure options.");
        textView.append("\n\nTaking your time to accurately answer the following question will help MovieFeel determine what type of movies to suggest to help you in your needs.");
        textView.append("\n\nSo please take your time and answer as accurate as possible! All your data is kept safe and confidential.");
        textView.append("\n\nSwipe from right to left to start. You may go back to previous questions at any time. To do so just swipe backwards.");

        return view;
    }

}
