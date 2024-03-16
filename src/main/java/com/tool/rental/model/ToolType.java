package com.tool.rental.model;

public class ToolType {

    private String toolTypeName;
    private Double dailyRentalCharge;

    private boolean weekDayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public ToolType(String toolTypeName, Double dailyRentalCharge, boolean weekDayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.toolTypeName = toolTypeName;
        this.dailyRentalCharge = dailyRentalCharge;
        this.weekDayCharge = weekDayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public ToolType() {
    }

    public String getToolTypeName() {
        return toolTypeName;
    }

    public Double getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public boolean isWeekDayCharge() {
        return weekDayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }

    public void setWeekDayCharge(boolean weekDayCharge) {
        this.weekDayCharge = weekDayCharge;
    }
}
