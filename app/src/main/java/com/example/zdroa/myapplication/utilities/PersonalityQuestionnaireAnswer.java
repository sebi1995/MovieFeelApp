package com.example.zdroa.myapplication.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum PersonalityQuestionnaireAnswer {
    TRUE(2, "True", 1),
    FALSE(2, "False", 2),
    SOMETIMES(1, "Sometimes", 3),
    NOT_ANSWERED(0);

    private final Integer points;
    private final String text;
    private final Integer index;

    PersonalityQuestionnaireAnswer(Integer points, String text, Integer index) {
        this.points = points;
        this.text = text;
        this.index = index;
    }

    PersonalityQuestionnaireAnswer(Integer points) {
        this.points = points;
        this.text = null;
        this.index = null;
    }

    public static List<PersonalityQuestionnaireAnswer> getValidAnswers() {
        return Arrays.stream(values()).filter(PersonalityQuestionnaireAnswer::isValidAnswer).collect(Collectors.toList());
    }

    public boolean isValidAnswer() {
        return this.text != null;
    }

    public Integer getPoints() {
        return points;
    }

    public String getText() {
        return this.text;
    }

    public int getIndex() {
        return this.index == null ? -1 : this.index;
    }

    public static Optional<PersonalityQuestionnaireAnswer> getByIndex(int index) {
        return Arrays.stream(values()).filter(answer -> answer.getIndex() == index).findFirst();
    }
}
