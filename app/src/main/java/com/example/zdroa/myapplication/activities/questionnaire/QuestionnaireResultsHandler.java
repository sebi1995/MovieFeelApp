package com.example.zdroa.myapplication.activities.questionnaire;

import static com.example.zdroa.myapplication.utilities.PersonType.ANXIOUS;
import static com.example.zdroa.myapplication.utilities.PersonType.DEPENDANT;
import static com.example.zdroa.myapplication.utilities.PersonType.DEPRESSIVE;
import static com.example.zdroa.myapplication.utilities.PersonType.HISTRIONIC;
import static com.example.zdroa.myapplication.utilities.PersonType.NARCISSIST;
import static com.example.zdroa.myapplication.utilities.PersonType.OBSESSIVE;
import static com.example.zdroa.myapplication.utilities.PersonType.PARANOID;
import static com.example.zdroa.myapplication.utilities.PersonType.SCHIZOID;
import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireAnswer.NOT_ANSWERED;
import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestions.ANXIOUS_PERSON_TYPE_QUESTIONS;
import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestions.DEPENDANT_PERSON_TYPE_QUESTIONS;
import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestions.DEPRESSIVE_PERSON_TYPE_QUESTIONS;
import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestions.HISTRIONIC_PERSON_TYPE_QUESTIONS;
import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestions.NARCISSIST_PERSON_TYPE_QUESTIONS;
import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestions.OBSESSIVE_PERSON_TYPE_QUESTIONS;
import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestions.PARANOID_PERSON_TYPE_QUESTIONS;
import static com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestions.SCHIZOID_PERSON_TYPE_QUESTIONS;

import com.example.zdroa.myapplication.utilities.PersonType;
import com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireAnswer;
import com.example.zdroa.myapplication.utilities.PersonalityQuestionnaireQuestion;
import com.example.zdroa.myapplication.utils.Logger;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QuestionnaireResultsHandler {

    private static final ImmutableMap<PersonType, List<PersonalityQuestionnaireQuestion>> QUESTIONS =
            ImmutableMap.<PersonType, List<PersonalityQuestionnaireQuestion>>builder()
                    .put(ANXIOUS, ANXIOUS_PERSON_TYPE_QUESTIONS)
                    .put(PARANOID, PARANOID_PERSON_TYPE_QUESTIONS)
                    .put(HISTRIONIC, HISTRIONIC_PERSON_TYPE_QUESTIONS)
                    .put(NARCISSIST, NARCISSIST_PERSON_TYPE_QUESTIONS)
                    .put(SCHIZOID, SCHIZOID_PERSON_TYPE_QUESTIONS)
                    .put(DEPRESSIVE, DEPRESSIVE_PERSON_TYPE_QUESTIONS)
                    .put(DEPENDANT, DEPENDANT_PERSON_TYPE_QUESTIONS)
                    .put(OBSESSIVE, OBSESSIVE_PERSON_TYPE_QUESTIONS)
                    .build();

    private static final Logger LOGGER = new Logger(QuestionnaireResultsHandler.class);

    public static List<PersonalityQuestionnaireQuestion> getQuestions(PersonType personType) {
        List<PersonalityQuestionnaireQuestion> questions = QuestionnaireResultsHandler.QUESTIONS.get(personType);
        return questions == null ? Collections.emptyList() : questions;
    }

    public static boolean allQuestionsAreAnswered() {
        for (PersonType personType : PersonType.values()) {
            List<PersonalityQuestionnaireQuestion> questions = QUESTIONS.get(personType);
            if (questions == null) {
                LOGGER.logError(personType.toString() + " questions are null.");
                return false;
            } else {
                for (PersonalityQuestionnaireQuestion question : questions) {
                    if (!question.isAnswered()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int getIndexForFirstFragmentWithUnAnsweredQuestion() {
        for (int index = 0; index < PersonType.numberOfTypes(); index++) {
            PersonType personType = PersonType.getByIndex(index);
            if (personType == null) {
                LOGGER.logError("No person type with index " + index + " found.");
                continue;
            }
            boolean personTypeHasUnAnsweredQuestion = false;
            List<PersonalityQuestionnaireQuestion> personTypeQuestions = QUESTIONS.get(personType);
            if (personTypeQuestions == null) {
                LOGGER.logError("No questions found for person type " + personType.toString());
                continue;
            }
            for (PersonalityQuestionnaireQuestion question : personTypeQuestions) {
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

    public static void setAnswerForQuestion(PersonType personType, PersonalityQuestionnaireQuestion questionToUpdate, PersonalityQuestionnaireAnswer answer) {
        Optional.ofNullable(QuestionnaireResultsHandler.QUESTIONS.get(personType)).ifPresent(questions -> {
            questions.forEach(question -> {
                if (question.equals(questionToUpdate)) {
                    question.setAnswer(answer);
                }
            });
        });
    }

    public static List<PersonType> calculateResultPersonType() {
        Map<PersonType, Integer> results = new HashMap<>();
        for (Map.Entry<PersonType, List<PersonalityQuestionnaireQuestion>> personTypeQuestions : QUESTIONS.entrySet()) {
            int points = 0;
            List<PersonalityQuestionnaireQuestion> questions = personTypeQuestions.getValue();
            for (PersonalityQuestionnaireQuestion question : questions) {
                if (question.getAnswer() == NOT_ANSWERED) {
                    return null;
                }
                points += question.getAnswer().getPoints();
            }
            results.put(personTypeQuestions.getKey(), points);
        }
        List<PersonType> result = new ArrayList<>();
        int highestPoints = 0;
        for (PersonType personType : PersonType.values()) {
            Integer points = results.get(personType);
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
}
