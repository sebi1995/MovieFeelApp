package com.example.zdroa.myapplication.utilities;

import com.example.zdroa.myapplication.utils.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieApiDateFormatter {

    private static Logger LOGGER = new Logger(MovieApiDateFormatter.class);

    public static String format(Date date) {
        try {
            String pattern = "yyyy/MM/dd";
            Locale uk = Locale.UK;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, uk);
            return simpleDateFormat.format(date.getTime());
        }catch (Exception e){
            LOGGER.logError(e);
            return "un-parsable";
        }
    }
}
