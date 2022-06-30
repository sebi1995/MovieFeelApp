package com.example.zdroa.myapplication.utilities;


import static com.example.zdroa.myapplication.utilities.PersonType.ANXIOUS;
import static com.example.zdroa.myapplication.utilities.PersonType.DEPENDANT;
import static com.example.zdroa.myapplication.utilities.PersonType.DEPRESSIVE;
import static com.example.zdroa.myapplication.utilities.PersonType.HISTRIONIC;
import static com.example.zdroa.myapplication.utilities.PersonType.NARCISSIST;
import static com.example.zdroa.myapplication.utilities.PersonType.OBSESSIVE;
import static com.example.zdroa.myapplication.utilities.PersonType.PARANOID;
import static com.example.zdroa.myapplication.utilities.PersonType.SCHIZOID;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PersonalityQuestionnaireQuestions {

    private static final String ANXIOUS_QUESTION_ONE = "Worries keep me up at night";
    private static final String ANXIOUS_QUESTION_TWO = "The risk of missing my transport mean makes me feel awfully uncomfortable";
    private static final String ANXIOUS_QUESTION_THREE = "I get told that I worry too much";
    private static final String ANXIOUS_QUESTION_FOUR = "I pay my debts as soon as possible";
    private static final String ANXIOUS_QUESTION_FIVE = "When I am waiting for someone and they are late, I can’t resist thinking that something bad has happened to them";
    private static final String ANXIOUS_QUESTION_SIX = "I often realise too late that I worried about something I shouldn’t have";
    private static final String ANXIOUS_QUESTION_SEVEN = "I sometimes feel tensioned without knowing the particular reason";

    private static final String PARANOID_QUESTION_ONE = "I have ended a lot of relationships/friendships because those in question were not treating me as they should have";
    private static final String PARANOID_QUESTION_TWO = "I am sceptic about those I have just met";
    private static final String PARANOID_QUESTION_THREE = "When I entrust someone with a secret I am afraid they might use that secret against me";
    private static final String PARANOID_QUESTION_FOUR = "I get accused of having trust issues";
    private static final String PARANOID_QUESTION_FIVE = "To get through in life you have to be harsh and inflexible";
    private static final String PARANOID_QUESTION_SIX = "When someone shows me kindness, I think they want something from me";
    private static final String PARANOID_QUESTION_SEVEN = "This questionnaire is making me feel uncomfortable";

    private static final String HISTRIONIC_QUESTION_ONE = "The way other people look at me excites me";
    private static final String HISTRIONIC_QUESTION_TWO = "I get excited easily";
    private static final String HISTRIONIC_QUESTION_THREE = "I like to seduce others, even if I don’t plan on taking it any further";
    private static final String HISTRIONIC_QUESTION_FOUR = "For others to help me, I must charm them";
    private static final String HISTRIONIC_QUESTION_FIVE = "If I am not given attention by everyone in a group, I can’t be myself";
    private static final String HISTRIONIC_QUESTION_SIX = "I find myself to fall in love with people who are too far from me or are inaccessible";
    private static final String HISTRIONIC_QUESTION_SEVEN = "I get told that I dress to provocative";

    private static final String OBSESSIVE_QUESTION_ONE = "In a discussion, I like to present my ideas in a certain order";
    private static final String OBSESSIVE_QUESTION_TWO = "I get told that I’m a perfectionist";
    private static final String OBSESSIVE_QUESTION_THREE = "I have missed things in the past for concentrating too much on the detail";
    private static final String OBSESSIVE_QUESTION_FOUR = "When working in a team, I feel responsible for the result of the work";
    private static final String OBSESSIVE_QUESTION_FIVE = "I don’t feel comfortable when I receive a gift, I feel like I owe them something back";
    private static final String OBSESSIVE_QUESTION_SIX = "I get told that I’m stingy";
    private static final String OBSESSIVE_QUESTION_SEVEN = "I don’t like to throw things away";

    private static final String NARCISSIST_QUESTION_ONE = "I am more charming than other people";
    private static final String NARCISSIST_QUESTION_TWO = "Everything I have is due to my own work";
    private static final String NARCISSIST_QUESTION_THREE = "I am jealous of other people’s success";
    private static final String NARCISSIST_QUESTION_FOUR = "I have cheated in the past without feeling remorse/regret";
    private static final String NARCISSIST_QUESTION_FIVE = "When I don’t get attention, I immediately get angry";
    private static final String NARCISSIST_QUESTION_SIX = "I love using the privileges and advantages that are available to me";
    private static final String NARCISSIST_QUESTION_SEVEN = "I don’t like to follow rules";

    private static final String SCHIZOID_QUESTION_ONE = "After a long day spent around other people, I feel the need to be alone";
    private static final String SCHIZOID_QUESTION_TWO = "I am not really keen on meeting new people";
    private static final String SCHIZOID_QUESTION_THREE = "If my friends get together to celebrate my anniversary, this tires me more than it excites me";
    private static final String SCHIZOID_QUESTION_FOUR = "I get told that I daydream too much";
    private static final String SCHIZOID_QUESTION_FIVE = "Besides my family, I only have 1 to 3 friends";
    private static final String SCHIZOID_QUESTION_SIX = "I don’t really care what others think about me";
    private static final String SCHIZOID_QUESTION_SEVEN = "I don’t like group activities";

    private static final String DEPRESSIVE_QUESTION_ONE = "I love life less than others";
    private static final String DEPRESSIVE_QUESTION_TWO = "I get told that I think too negative";
    private static final String DEPRESSIVE_QUESTION_THREE = "Sometimes I feel like a burden to others";
    private static final String DEPRESSIVE_QUESTION_FOUR = "I easily feel guilty for anything";
    private static final String DEPRESSIVE_QUESTION_FIVE = "I have the tendency to think of past mistakes";
    private static final String DEPRESSIVE_QUESTION_SIX = "I often feel of less importance than others";
    private static final String DEPRESSIVE_QUESTION_SEVEN = "I often feel tired and without motivation";

    private static final String DEPENDANT_QUESTION_ONE = "I ask others for advice before making an important decision";
    private static final String DEPENDANT_QUESTION_TWO = "It’s hard for me to end a conversation or say goodbye to someone";
    private static final String DEPENDANT_QUESTION_THREE = "I often doubt my own self-worth";
    private static final String DEPENDANT_QUESTION_FOUR = "I am willing to sacrifice my needs for others";
    private static final String DEPENDANT_QUESTION_FIVE = "I often don’t express my feelings because I don’t want to argue with others";
    private static final String DEPENDANT_QUESTION_SIX = "I never forget people and I don’t like to break away from anyone";
    private static final String DEPENDANT_QUESTION_SEVEN = "I often get told that I deserve more than I have";

    public static final List<PersonalityQuestionnaireQuestion> ANXIOUS_PERSON_TYPE_QUESTIONS = getPersonalityQuestionnaireQuestionsList(ANXIOUS_QUESTION_ONE, ANXIOUS_QUESTION_TWO, ANXIOUS_QUESTION_THREE, ANXIOUS_QUESTION_FOUR, ANXIOUS_QUESTION_FIVE, ANXIOUS_QUESTION_SIX, ANXIOUS_QUESTION_SEVEN);
    public static final List<PersonalityQuestionnaireQuestion> PARANOID_PERSON_TYPE_QUESTIONS = getPersonalityQuestionnaireQuestionsList(PARANOID_QUESTION_ONE, PARANOID_QUESTION_TWO, PARANOID_QUESTION_THREE, PARANOID_QUESTION_FOUR, PARANOID_QUESTION_FIVE, PARANOID_QUESTION_SIX, PARANOID_QUESTION_SEVEN);
    public static final List<PersonalityQuestionnaireQuestion> HISTRIONIC_PERSON_TYPE_QUESTIONS = getPersonalityQuestionnaireQuestionsList(HISTRIONIC_QUESTION_ONE, HISTRIONIC_QUESTION_TWO, HISTRIONIC_QUESTION_THREE, HISTRIONIC_QUESTION_FOUR, HISTRIONIC_QUESTION_FIVE, HISTRIONIC_QUESTION_SIX, HISTRIONIC_QUESTION_SEVEN);
    public static final List<PersonalityQuestionnaireQuestion> OBSESSIVE_PERSON_TYPE_QUESTIONS = getPersonalityQuestionnaireQuestionsList(OBSESSIVE_QUESTION_ONE, OBSESSIVE_QUESTION_TWO, OBSESSIVE_QUESTION_THREE, OBSESSIVE_QUESTION_FOUR, OBSESSIVE_QUESTION_FIVE, OBSESSIVE_QUESTION_SIX, OBSESSIVE_QUESTION_SEVEN);
    public static final List<PersonalityQuestionnaireQuestion> NARCISSIST_PERSON_TYPE_QUESTIONS = getPersonalityQuestionnaireQuestionsList(NARCISSIST_QUESTION_ONE, NARCISSIST_QUESTION_TWO, NARCISSIST_QUESTION_THREE, NARCISSIST_QUESTION_FOUR, NARCISSIST_QUESTION_FIVE, NARCISSIST_QUESTION_SIX, NARCISSIST_QUESTION_SEVEN);
    public static final List<PersonalityQuestionnaireQuestion> SCHIZOID_PERSON_TYPE_QUESTIONS = getPersonalityQuestionnaireQuestionsList(SCHIZOID_QUESTION_ONE, SCHIZOID_QUESTION_TWO, SCHIZOID_QUESTION_THREE, SCHIZOID_QUESTION_FOUR, SCHIZOID_QUESTION_FIVE, SCHIZOID_QUESTION_SIX, SCHIZOID_QUESTION_SEVEN);
    public static final List<PersonalityQuestionnaireQuestion> DEPRESSIVE_PERSON_TYPE_QUESTIONS = getPersonalityQuestionnaireQuestionsList(DEPRESSIVE_QUESTION_ONE, DEPRESSIVE_QUESTION_TWO, DEPRESSIVE_QUESTION_THREE, DEPRESSIVE_QUESTION_FOUR, DEPRESSIVE_QUESTION_FIVE, DEPRESSIVE_QUESTION_SIX, DEPRESSIVE_QUESTION_SEVEN);
    public static final List<PersonalityQuestionnaireQuestion> DEPENDANT_PERSON_TYPE_QUESTIONS = getPersonalityQuestionnaireQuestionsList(DEPENDANT_QUESTION_ONE, DEPENDANT_QUESTION_TWO, DEPENDANT_QUESTION_THREE, DEPENDANT_QUESTION_FOUR, DEPENDANT_QUESTION_FIVE, DEPENDANT_QUESTION_SIX, DEPENDANT_QUESTION_SEVEN);

    private static List<PersonalityQuestionnaireQuestion> getPersonalityQuestionnaireQuestionsList(String... questions) {
        return Arrays.stream(questions).map(PersonalityQuestionnaireQuestion::new).collect(Collectors.toList());
    }
}
