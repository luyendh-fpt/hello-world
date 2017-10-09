package api.youtube.utility;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by daolinh on 10/9/17.
 */
public class Utils {

    private static RandomString session = new RandomString(64, ThreadLocalRandom.current());

    // Add number of day to current time, return new day widh long format.
    public static long addDays(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTimeInMillis();
    }

    public static String generateToken(){
        return session.nextString();
    }

}
