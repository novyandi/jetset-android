package xyz.girudo.jetset.helpers;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class DateHelper {
    public static final String DATE_BIRTH_FORMAT = "dd-MMM-yyyy";
    public static final String DATE_POST_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_LEFT_FORMAT = "dd MMM yyyy";
    public static final String API_DATE_FORMAT = DATE_FORMAT + " HH:mm:ss";
    private static final int SECOND_CONVERT = 1000;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int MONTH_MILLIS = 30 * DAY_MILLIS;
    private static DateHelper instance;
    private static int offSet = Calendar.getInstance().getTimeZone().getOffset(System.currentTimeMillis());

    public static DateHelper getInstance() {
        if (instance == null) {
            instance = new DateHelper();
        }
        return instance;
    }

    public String formatDate(String date, String formatDate) {
        return formatDate(DATE_FORMAT, date, formatDate);
    }

    public String formatDate(String dateFormat, String date, String toFormat) {
        return formatDate(dateFormat, date, toFormat, null, null);
    }

    public String formatDate(String dateFormat, String date, String toFormat, Locale fromLocale, Locale toLocale) {
        String formatted = "";
        DateFormat formatter = fromLocale == null ? new SimpleDateFormat(dateFormat) : new SimpleDateFormat(dateFormat, fromLocale);
        try {
            Date dateStr = formatter.parse(date);
            formatted = formatter.format(dateStr);
            Date formatDate = formatter.parse(formatted);
            formatter = toLocale == null ? new SimpleDateFormat(toFormat) : new SimpleDateFormat(toFormat, toLocale);
            formatted = formatter.format(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatted;
    }

    public Date getDate(String dateFormat, String date, String toFormat) {
        String formatted;
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        Date formatDate = new Date();
        try {
            Date dateStr = formatter.parse(date);
            formatted = formatter.format(dateStr);
            formatDate = formatter.parse(formatted);
            formatter = new SimpleDateFormat(toFormat);
            formatted = formatter.format(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate;
    }

    public long formatDateToMillis(String dateFormat, String date) {
        Date dateStr = null;
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            dateStr = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr != null ? dateStr.getTime() : System.currentTimeMillis();
    }

    public String formatMillisToDateGMT(long milliseconds) {
        if (milliseconds < 1000000000000L) {
            milliseconds *= 1000;
        }
        return formatMillisToDate((milliseconds) - offSet, API_DATE_FORMAT);
    }

    public String formatMillisToDate(long milliseconds, String format) {
        Date date = new Date(milliseconds);
        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public String getDateTime() {
        DateFormat df = new SimpleDateFormat(API_DATE_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(new Date());
    }

    public String getDateTimeDevice() {
        return formatMillisToDate(System.currentTimeMillis(), API_DATE_FORMAT);
    }

    public String getDate() {
        return getDate(DATE_FORMAT);
    }

    public String getDate(String format) {
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(new Date());
    }

    public String convertSecondToTime(int sec) {
        return String.format("%02d:%02d",
                TimeUnit.SECONDS.toHours(sec),
                TimeUnit.SECONDS.toMinutes(sec) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(sec)));
    }

    public int calculateTime(long start, long end) {
        long duration = end - start;
        float result = duration / SECOND_CONVERT;
        return Math.round(result);
    }

    public String getDiffDate(String Date, int datediff) {
        String dateInString = Date;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(dateInString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, datediff);
        java.util.Date resultdate = new Date(cal.getTimeInMillis());
        dateInString = sdf.format(resultdate);
        return dateInString;
    }

    public Date parseToDate(String date, String dateFormat) {
        Date _date = null;
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            _date = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return _date;
    }

    public String getTimeAgo(String dateTime) {
        return getTimeAgo(dateTime, API_DATE_FORMAT);
    }

    public String getTimeAgo(String dateTime, String dateFormat) {
        long time = formatDateToMillis(dateFormat, dateTime);
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = formatDateToMillis(API_DATE_FORMAT, getDateTime());
        if (time > now || time <= 0) {
            return "just now";
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "Yesterday";
        } else {
            int dayDiff = (int) (diff / DAY_MILLIS);
            if (dayDiff < 31)
                return dayDiff + (dayDiff > 1 ? " days ago" : " day ago");
            else {
                int monthDiff = dayDiff / 30;
                if (monthDiff < 12) {
                    return monthDiff + (monthDiff > 1 ? " months ago" : " month ago");
                } else {
                    return formatDate(dateFormat, dateTime, DATE_LEFT_FORMAT);
                }
            }

        }
    }

    public String getTimeLeft(String dateTime) {
        return getTimeLeft(dateTime, API_DATE_FORMAT);
    }

    public String getTimeLeft(String dateTime, String dateFormat) {
        long time = formatDateToMillis(dateFormat, dateTime);
        if (time < 1000000000000L) {
            time *= 1000;
        }

        long now = formatDateToMillis(API_DATE_FORMAT, getDateTime());
        if (time < now || time <= 0) {
            return "few minutes left";
        }

        final long diff = time - now;
        if (diff < 5 * MINUTE_MILLIS) {
            return "few minutes left";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes left";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour left";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours left";
        } else {
            int dayDiff = (int) (diff / DAY_MILLIS);
            if (dayDiff < 31)
                return dayDiff + (dayDiff > 1 ? " days left" : " day left");
            else {
                int monthDiff = dayDiff / 30;
                if (monthDiff < 12) {
                    return monthDiff + (monthDiff > 1 ? " months left" : " month left");
                } else {
                    return formatDate(dateFormat, dateTime, DATE_LEFT_FORMAT);
                }
            }

        }
    }

    public void showDatePicker(Context context, long currentTimeMilis, DatePickerDialog.OnDateSetListener datePickerListener) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentTimeMilis);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        DatePickerDialog datePicker = new DatePickerDialog(context, datePickerListener, year, month, day);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

    public void showDatePickerPresent(Context context, long currentTimeMilis, DatePickerDialog.OnDateSetListener datePickerListener) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentTimeMilis);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        DatePickerDialog datePicker = new DatePickerDialog(context, datePickerListener, year, month, day);
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePicker.show();
    }

    public String getCurrentDateInSpecificFormat(Calendar currentCalDate) {
        String dayNumberSuffix = getDayNumberSuffix(currentCalDate.get(Calendar.DAY_OF_MONTH));
        DateFormat dateFormat = new SimpleDateFormat(" d'" + dayNumberSuffix + "' MMMM yyyy");
        return dateFormat.format(currentCalDate.getTime());
    }

    private String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

}