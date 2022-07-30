package com.example.zdroa.myapplication.activities.personalityquestionnaire;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.example.zdroa.myapplication.R;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.models.PQAnswer;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.models.PQQuestion;
import com.example.zdroa.myapplication.utilities.PersonType;

import java.util.List;

public class PQComponentCreator {

    private static CardView getCardView(Context context) {
        CardView cardView = new CardView(context);
        cardView.setRadius(80f);
        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        cardViewParams.setMargins(0, 50, 0, 50);
        cardView.setLayoutParams(cardViewParams);
        return cardView;
    }

    private static LinearLayout getLinearLayout(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return linearLayout;
    }

    private static TextView getTitleTextView(Context context, String text) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextSize(24);
        textView.setTextColor(ContextCompat.getColor(context, R.color.black));
        int padding = 15;
        textView.setPadding(padding, padding, padding, padding);
        textView.setBackgroundColor(ContextCompat.getColor(context, R.color.questionnaireQuestionNotAnsweredColour));
        return textView;
    }

    private static RadioGroup getAnswersRadioGroup(Context context, int offset, RadioGroup.OnCheckedChangeListener onCheckedChangeListener) {
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setId(100 + offset);
        int padding = 20;
        radioGroup.setPadding(padding, padding, padding, padding);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        return radioGroup;
    }

    private static RadioButton getRadioButton(Context context, PQAnswer answer) {
        RadioButton radioButton = new RadioButton(context);
        radioButton.setText(answer.getText());
        radioButton.setId(answer.getIndex());
        return radioButton;
    }

    public static void createFragmentComponent(Context context, PersonType personType, List<PQQuestion> questions, LinearLayout rootLayout) {
        for (int questionIndex = 0; questionIndex < questions.size(); questionIndex++) {
            //get the question
            PQQuestion question = questions.get(questionIndex);

            //create the cardview
            CardView cardView = getCardView(context);

            //create the linear layout to hold the title and the answers
            LinearLayout linearLayout = getLinearLayout(context);

            //create and add the title to the linear layout
            linearLayout.addView(getTitleTextView(context, question.getText()));

            //create radio group and add listener
            RadioGroup radioGroup = getAnswersRadioGroup(
                    context,
                    questionIndex,
                    (group, index) -> {
                        LinearLayout parent = (LinearLayout) group.getParent();
                        TextView titleTextView = (TextView) parent.getChildAt(0);
                        titleTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.questionnaireQuestionAnsweredColour));
                        PQAnswer.getByIndex(index).ifPresent(answer -> {
                            try {
                                PQHandler.setAnswerForQuestion(personType, question, answer);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
            );

            //add radio buttons to group
            PQAnswer.getValidAnswers()
                    .stream()
                    .map(answer -> getRadioButton(context, answer))
                    .forEach(radioGroup::addView);

            //add answers to the linear layout
            linearLayout.addView(radioGroup);

            //add linear layout to the cardview
            cardView.addView(linearLayout);

            //add card view to the root view
            rootLayout.addView(cardView);
        }
    }
}
