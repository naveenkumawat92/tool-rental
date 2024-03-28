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
    private String checkoutDate;
    private LocalDate dueDate;
    private Double dailyRentalCharge;
    private Integer chargeDays;
    private BigDecimal preDiscountCharge;
    private BigDecimal discountAmount;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Double getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public void setDailyRentalCharge(Double dailyRentalCharge) {
        this.dailyRentalCharge = dailyRentalCharge;
    }

    public Integer getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(Integer chargeDays) {
        this.chargeDays = chargeDays;
    }

    public BigDecimal getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(BigDecimal preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
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


    /**
     * this method is used to print rent agreement information on console
     *
     */
    public void printCheckOutAgreement() {
        System.out.println("Tool code: "+this.getToolCode());
        System.out.println("Tool type: "+this.getToolType());
        System.out.println("Checkout date: "+this.getCheckoutDate());
        System.out.println("Discount Percent: "+this.getDiscountPercent()+"%");
        System.out.println("Final Charge: "+this.getFinalCharge());
        System.out.println("Discount Amount: "+this.getDiscountAmount());
        System.out.println("Daily Rental Charges: "+this.getDailyRentalCharge());
        System.out.println("Pre Discount charge: "+this.getPreDiscountCharge());
        System.out.println("Rental Days: "+this.getRentalDays());
    }

}
