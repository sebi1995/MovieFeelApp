package com.example.zdroa.myapplication.utils;

public class RequestUrls {

    private static final String PORT = "8080";
    private static final String DEVELOPMENT_DNS = "10.0.2.2" + ":" + PORT;
    private static final String PRODUCTION_DNS = "164.92.210.138" + ":" + PORT;
    private static final String WEB_APP_BASE_URL;
    static {
        String build;
        if (AppSettings.DEVELOPMENT_MODE_ACTIVE) {
            build = DEVELOPMENT_DNS;
        } else {
            build = PRODUCTION_DNS;
        }
        WEB_APP_BASE_URL = "http://" + build + "/";
    }

    public static final String WEB_APP_USER_REGISTER_URL = WEB_APP_BASE_URL + "user/register";
    public static final String WEB_APP_USER_RESET_PASSWORD_URL = WEB_APP_BASE_URL + "user/reset";
    public static final String WEB_APP_USER_FORGOT_PASSWORD_URL = WEB_APP_BASE_URL + "user/forgot/password";
    public static final String WEB_APP_USER_FETCH_BY_EMAIL_AND_PASSWORD_URL = WEB_APP_BASE_URL + "user/login";
    public static final String WEB_APP_USER_REMOVE_MOVIE_FROM_WATCHED_BY_USER_UID_AND_MOVIE_ID = WEB_APP_BASE_URL + "user/remove/movie/watched";
    public static final String WEB_APP_USER_ADD_MOVIE_FROM_WATCHED_BY_USER_UID_AND_MOVIE_ID = WEB_APP_BASE_URL + "user/add/movie/watched";
    public static final String WEB_APP_USER_REGISTER_QUESTIONNAIRE = WEB_APP_BASE_URL + "user/register/questionnaire";

    public static final String WEB_APP_FETCH_MOVIE_BY_ID_URL = WEB_APP_BASE_URL + "movies/id";
    public static final String WEB_APP_FETCH_MOVIES_BY_IDS_URL = WEB_APP_BASE_URL + "movies/id/multiple";
    public static final String WEB_APP_FETCH_MOVIES_STARTING_WITH_ID_AND_LIMIT_URL = "movies/id/multiple/limit";
    public static final String WEB_APP_FETCH_WATCHED_MOVIES_BY_USER_UID = WEB_APP_BASE_URL + "movies/watched/user";


    public static final String CHECK_USERNAME_EXISTS_URL = "http://www.movie-feel.com/php_scripts/usernameExists.php";
    public static final String REGISTER_REQUEST_URL = "http://www.movie-feel.com/php_scripts/register_request.php";
    public static final String REGISTER_QUESTIONNAIRE_REQUEST_URL = "http://www.movie-feel.com/php_scripts/Questionnaire_Request.php";
    public static final String INSERT_IDS_INTO_DB_REQUEST_URL = "http://www.movie-feel.com/php_scripts/insertIDSIntoDB.php";
    public static final String GET_MOVIE_IDS_REQUEST_URL = "http://www.movie-feel.com/php_scripts/ids_request.php";
    public static final String GET_WATCHED_LIST_REQUEST_URL = "http://www.movie-feel.com/php_scripts/watchedlist_request.php";
    public static final String ADD_TO_WATCHED_LIST_REQUEST_URL = "http://www.movie-feel.com/php_scripts/addToWatchedList_request.php";


    private static final String MOVIE_DB_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY_PARAM = "?api_key=" + AppSettings.API_KEY;
    private static final String APPEND_TRAILERS = "&append_to_response=videos";

    public static String generateMovieDatabaseAPIUrl(int movieId) {
        return MOVIE_DB_URL + movieId + API_KEY_PARAM + APPEND_TRAILERS;
    }
}
