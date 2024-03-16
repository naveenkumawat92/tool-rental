package com.tool.rental.model;

import java.time.LocalDate;


public class Checkout {

    public String toolCode;
    public int rentalDay;
    public int discountPer;
    public LocalDate checkOutDate;

    public Checkout() {
    }

    public Checkout(String toolCode, int rentalDay, int discountPer, LocalDate checkOutDate) {
        this.toolCode = toolCode;
        this.rentalDay = rentalDay;
        this.discountPer = discountPer;
        this.checkOutDate = checkOutDate;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public int getRentalDay() {
        return rentalDay;
    }

    public void setRentalDay(int rentalDay) {
        this.rentalDay = rentalDay;
    }

    public int getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(int discountPer) {
        this.discountPer = discountPer;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
