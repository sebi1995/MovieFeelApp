package com.example.zdroa.myapplication.utilities;

import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireAnswer.NOT_ANSWERED;

public class PersonalityQuestionnaireQuestion {

    private String question;
    private PersonalityQuestionnaireAnswer answer;

    public PersonalityQuestionnaireQuestion(String question) {
        this.question = question;
        this.answer = NOT_ANSWERED;
    }

    public void setAnswer(PersonalityQuestionnaireAnswer answer) {
        this.answer = answer;
    }

    public PersonalityQuestionnaireAnswer getAnswer() {
        return answer;
    }

    public boolean isAnswered() {
        return this.answer != NOT_ANSWERED;
    }

    public String getText() {
        return question;
    }
}
