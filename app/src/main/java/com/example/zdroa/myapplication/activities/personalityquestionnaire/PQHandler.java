package com.example.zdroa.myapplication.activities.personalityquestionnaire;

import static com.example.zdroa.myapplication.activities.personalityquestionnaire.models.PQAnswer.NOT_ANSWERED;

import android.util.Pair;

import com.example.zdroa.myapplication.activities.personalityquestionnaire.models.PQAnswer;
import com.example.zdroa.myapplication.activities.personalityquestionnaire.models.PQQuestion;
import com.example.zdroa.myapplication.utilities.PersonType;
import com.example.zdroa.myapplication.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class PQHandler {

    private PQHandler() {

    }

    public static boolean allQuestionsAreAnswered() throws Exception {
        for (PersonType personType : PersonType.values()) {
            List<PQQuestion> questions = PQQuestions.getQuestions(personType);
            for (PQQuestion question : questions) {
                if (!question.isAnswered()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int getIndexForFirstFragmentWithUnAnsweredQuestion() throws Exception {
        for (PersonType personType : PersonType.values()) {
            boolean personTypeHasUnAnsweredQuestion = false;
            List<PQQuestion> personTypeQuestions = PQQuestions.getQuestions(personType);
            for (PQQuestion question : personTypeQuestions) {
                if (!question.isAnswered()) {
                    personTypeHasUnAnsweredQuestion = true;
                    break;
                }
            }
            if (personTypeHasUnAnsweredQuestion) {
                return personType.getIndex();
            }
        }
        return 0;
    }

    public static void setAnswerForQuestion(PersonType personType, PQQuestion questionToUpdate, PQAnswer answer) throws Exception {
        PQQuestions.getQuestions(personType).stream()
                .filter(question -> question.getText().equals(questionToUpdate.getText()))
                .findFirst()
                .ifPresent(question -> question.setAnswer(answer));
    }

    public static List<PersonType> getMatchingPersonTypes(List<PersonType> personTypes) throws Exception {
        List<PersonType> result = new ArrayList<>();
        List<Pair<PersonType, Integer>> allPersonTypesResults = new ArrayList<>();
        //calculate points for all person types
        for (PersonType personType : personTypes) {
            allPersonTypesResults.add(getPersonTypeTotalPoints(personType));
        }
        //get person type with highest points
        //if multiple person types, get all
        int highestPoints = 0;
        for (Pair<PersonType, Integer> personTypePointsPair : allPersonTypesResults) {
            PersonType personType = personTypePointsPair.first;
            Integer points = personTypePointsPair.second;
            if (points > highestPoints) {
                highestPoints = points;
                result.clear();
                result.add(personType);
            } else if (points == highestPoints) {
                result.add(personType);
            }
        }
        return result;
    }

    private static Pair<PersonType, Integer> getPersonTypeTotalPoints(PersonType personType) throws Exception {
        List<PQQuestion> questions = PQQuestions.getQuestions(personType);
        int points = 0;
        for (PQQuestion question : questions) {
            if (question.getAnswer() == NOT_ANSWERED) {
                throw new Exception(personType.name() + " has an unanswered question: " + question.getText() + ".");
            }
            points += question.getAnswer().getPoints();
        }
        return new Pair<>(personType, points);
    }
}
