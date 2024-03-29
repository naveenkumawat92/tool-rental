package com.tool.rental.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This class is used to generate Rent agreement, this class contains all the required
 * information which is needed to generate rent agreement like rental date, discount etc.
 */
public class RentalAgreement {

    private String toolCode;
    private Integer rentalDays;
    private Integer discountPercent;

    private String brandName;
    private String checkoutDate;
    private String dueDate;
    private String dailyRentalCharge;
    private Integer chargeDays;
    private String preDiscountCharge;
    private String discountAmount;
    private String finalCharge;

    private String toolType;

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public Integer getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(Integer rentalDays) {
        this.rentalDays = rentalDays;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public void setDailyRentalCharge(String dailyRentalCharge) {
        this.dailyRentalCharge = dailyRentalCharge;
    }

    public Integer getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(Integer chargeDays) {
        this.chargeDays = chargeDays;
    }

    public String getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(String  preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(String finalCharge) {
        this.finalCharge = finalCharge;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    @Override
    public String toString() {
        return "RentalAgreement{" +
                "toolCode=" + toolCode +
                "toolType=" + toolType +
                "checkoutDate=" + checkoutDate +
                ", discountAmount=" + discountAmount +
                ", discountPercent=" + discountPercent +
                ", finalCharge=" + finalCharge +
                '}';
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * this method is used to print rent agreement information on console
     *
     */
    public void printCheckOutAgreement() {
        System.out.println("Tool code: "+this.getToolCode());
        System.out.println("Tool type: "+this.getToolType());
        System.out.println("Tool Brand: "+this.getBrandName());
        System.out.println("Rental Days: "+this.getRentalDays());
        System.out.println("Checkout date: "+this.getCheckoutDate());
        System.out.println("Due date: "+this.getDueDate());
        System.out.println("Daily Rental Charges: "+this.getDailyRentalCharge());
        System.out.println("Charge Days: "+this.getChargeDays());
        System.out.println("Pre Discount charge: "+this.getPreDiscountCharge());
        System.out.println("Discount Percent: "+this.getDiscountPercent()+"%");
        System.out.println("Discount Amount: "+this.getDiscountAmount());
        System.out.println("Final Charge: "+this.getFinalCharge());
    }

}
