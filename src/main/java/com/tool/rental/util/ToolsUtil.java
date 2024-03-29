package com.tool.rental.util;

import com.tool.rental.model.ToolType;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * This class contains all the util method
 */
public class ToolsUtil {


    private final static String currency = "$";

    /**
     * this method is used to format LocalDate in MMDDYY format
     * @param date - A date which will be formatted into MMDDYY
     * @return - return the date in MMDDYY formate
     */
    public static String getFormattedDateMMDDYY(LocalDate date) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yy");
        return date.format(formatters);
    }

    /**
     * this method is used to calculate the chargeable days for the given time period,
     * it will calculate days from checkout date to due date.
     * If there is any holiday and weekend during the rental days, then it will check whether tool is chargeable or not for the holiday or weekend
     * If holiday and weekend is chargeable then it will calculate it as chargeable days, else if will consider it as free day
     * @param checkoutDate - checkout date of tool
     * @param dueDate - due date of tool
     * @param toolType - toolType is a object of ToolType class which contains all the information related to tool
     * @return - returns total chargeable days.
     */
    public static int calculateChargeableDays(LocalDate checkoutDate, LocalDate dueDate, ToolType toolType) {
        int chargeableDays = 0;
        LocalDate dayDate = checkoutDate;
        while (!dayDate.isAfter(dueDate)) {
            if (isHoliday(dayDate) && toolType.isHolidayCharge()) {
                chargeableDays++;
            } else if (!isWeekday(dayDate) && toolType.isWeekendCharge()) {
                chargeableDays++;
            } else if (isWeekday(dayDate)) {
                if (isHoliday(dayDate) || !isWeekday(dayDate)) {
                    dayDate = dayDate.plusDays(1);
                    continue;
                }
                chargeableDays++;
            }
            dayDate = dayDate.plusDays(1);
        }
        return chargeableDays;
    }

    /**
     * check, is there any holiday on the given date or not.
     * @param date - date that will be used to check whether it is holiday or not
     * @return - return true if given date is holiday, else return false
     */
    public static boolean isHoliday(LocalDate date) {
        if (date.getMonth() == Month.JULY && date.getDayOfMonth() == 4) {
            return true;
        } else if (date.getMonth() == Month.SEPTEMBER && date.getDayOfWeek() == DayOfWeek.MONDAY) {
            TemporalAdjuster firstMondayInMonth = TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY);
            LocalDate firstMondayOfSeptember = date.with(firstMondayInMonth);
            return date.equals(firstMondayOfSeptember);
        }
        return false;
    }


    /**
     * check whether the date is weekdays or not
     *
     * @param date - date that will be used to check whether it is weekday or not
     * @return - return true if given date is weekday, else return false
     */
    public static boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    public static String getFinalChargesValue(BigDecimal finalCharges) {
//        String number = "1000500000.574";
//        double amount = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return currency+formatter.format(finalCharges);
    }
}

