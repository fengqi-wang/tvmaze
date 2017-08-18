package com.fengqi.tvmaze.model.util;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by fengqi on 2017-08-17.
 */

public class DateFormatHelper {
    private static final String AGO = " ago";
    private static final String LATER = " later";
    private static final String SECOND = " Second";
    private static final String MINUTE = " Minute";
    private static final String HOUR = " Hour";
    private static final String DAY = " Day";
    private static final String MONTH = " Month";
    private static final String YEAR = " Year";

    public static String format(Date date) {
        DateTime nowDateTime = DateTime.now();
        DateTime dateTime = new DateTime(date);
        return formatDate(dateTime,nowDateTime);
    }

    private static String formatDate(final DateTime dateTime, final DateTime nowDateTime){
        if(dateTime.isEqualNow())
            return "Now";

        String diff = "";
        long unitizedValue = Math.abs(nowDateTime.getMillis() - dateTime.getMillis()) / 1000;

        if(unitizedValue < 60)
            diff = addUnit(unitizedValue, SECOND);
        else if((unitizedValue=unitizedValue/60) < 60)
            diff = addUnit(unitizedValue, MINUTE);
        else if((unitizedValue=unitizedValue/60) < 24)
            diff = addUnit(unitizedValue, HOUR);
        else if((unitizedValue=unitizedValue/24) < 28)
            diff = addUnit(unitizedValue, DAY);
        else {
            int years = nowDateTime.getYear() - dateTime.getYear();
            if(years != 0)
                diff = addUnit(years, YEAR);
            else {
                int months = nowDateTime.getMonthOfYear() - dateTime.getMonthOfYear();
                if(months != 0)
                    diff =  addUnit(months, MONTH);
                else {
                    int day = nowDateTime.getDayOfMonth() - dateTime.getDayOfMonth();
                    if(day != 0)
                        diff = addUnit(day, DAY);
                }
            }
        }

        String postfix = dateTime.isBeforeNow()? AGO : LATER;
        return diff + postfix;
    }

    private static String addUnit(long value, String unit) {
        return Math.abs(value) + unit + addS(Math.abs(value));
    }

    private static String addS(long number) {
        return number > 1? "s" : "";
    }

}