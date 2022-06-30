package com.example.zdroa.myapplication.utils;

public class AppSettings {

    public static final String API_KEY = "b692b9da86f1cf0c1b623ea6e2770101";


    public static final String NO_INFO_AVAILABLE = "not available";

    public static final int MOVIES_TO_DISPLAY_LIMIT_PER_PAGE = 20;

    public static final String USER_SESSION_SHARED_PREFERENCES = "user_session_shared_preferences";

    public static final int DEFAULT_PROGRESS_BAR_HEIGHT = 400;
    public static final int DEFAULT_PROGRESS_BAR_WIDTH = 400;

    public static final boolean LOGIN_SYSTEM_ENABLED = true;
    public static final boolean LOGGING_ENABLED = true;
    public static final boolean DEVELOPMENT_MODE_ACTIVE = false;

    public static final int API_HTTP_REQUEST_MAX_NUMBER_OF_RETRIES = 0;
    public static final int API_HTTP_REQUEST_TIMEOUT_AFTER_SECONDS = 20;
    public static final String SYSTEM_LINE_SEPARATOR = System.getProperty("line.separator");
    public static final int REST_REQUEST_MS_DELAY = 200;
    public static final int MAX_REGISTRATION_PASSWORD_LENGTH = 20;
    public static final int MIN_REGISTRATION_PASSWORD_LENGTH = 8;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * MINUTES_IN_HOUR;
    public static final int SESSION_TIMEOUT_DURATION = SECONDS_IN_HOUR * 6;
}
