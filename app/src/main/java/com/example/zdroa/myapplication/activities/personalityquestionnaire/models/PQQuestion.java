package com.example.zdroa.myapplication.activities.personalityquestionnaire.models;

import static com.example.zdroa.myapplication.activities.personalityquestionnaire.models.PQAnswer.NOT_ANSWERED;

public class PQQuestion {

    private String question;
    private PQAnswer answer;

    public PQQuestion(String question) {
        this.question = question;
        this.answer = NOT_ANSWERED;
    }

    public void setAnswer(PQAnswer answer) {
        this.answer = answer;
    }

    public PQAnswer getAnswer() {
        return answer;
    }

    public boolean isAnswered() {
        return this.answer != NOT_ANSWERED;
    }

    public String getText() {
        return question;
    }
}
