package com.tool.rental;


import com.tool.rental.exception.RequestException;
import com.tool.rental.model.Checkout;
import com.tool.rental.model.RentalAgreement;
import com.tool.rental.model.RentalEnums;
import com.tool.rental.service.AgreementService;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ToolRentalApplicationTests {

    String currency = "$";

    @Test
    public void testWeekDayIsChargeable() {
        AgreementService agreementService = new AgreementService();
        Checkout jakr = new Checkout(RentalEnums.ToolCode.JAKR.name(),  1000, 25, LocalDate.of(2015, 3, 9));
        RentalAgreement rentalAgreement = agreementService.checkOut(jakr);
        rentalAgreement.printCheckOutAgreement();
        assertNotNull(rentalAgreement);
    }


    @Test
    public void test2() {
        AgreementService agreementService = new AgreementService();
        Checkout ladw = new Checkout(RentalEnums.ToolCode.LADW.name(), 3, 10, LocalDate.of(2020, 7, 2));
        RentalAgreement rentalAgreement = agreementService.checkOut(ladw);
        rentalAgreement.printCheckOutAgreement();
        assertEquals(getFinalValue("10.80"), rentalAgreement.getFinalCharge());
    }

    @Test
    public void test3() {
        AgreementService agreementService = new AgreementService();
        Checkout chns = new Checkout(RentalEnums.ToolCode.CHNS.name(), 5, 25, LocalDate.of(2015, 7, 2));
        RentalAgreement rentalAgreement = agreementService.checkOut(chns);
        rentalAgreement.printCheckOutAgreement();
        assertEquals(getFinalValue("6.00"), rentalAgreement.getFinalCharge());
    }

    @Test
    public void test4() {
        AgreementService agreementService = new AgreementService();
        Checkout jakd = new Checkout(RentalEnums.ToolCode.JAKD.name(), 6, 0, LocalDate.of(2015, 9, 3));
        RentalAgreement rentalAgreement = agreementService.checkOut(jakd);
        rentalAgreement.printCheckOutAgreement();
        assertEquals(getFinalValue("9.00"), rentalAgreement.getFinalCharge());
        assertEquals(Integer.valueOf(0), rentalAgreement.getDiscountPercent());
    }

    @Test
    public void test5() {
        AgreementService agreementService = new AgreementService();
        Checkout jakr = new Checkout(RentalEnums.ToolCode.JAKR.name(), 9, 0, LocalDate.of(2015, 7, 2));
        RentalAgreement rentalAgreement = agreementService.checkOut(jakr);
        rentalAgreement.printCheckOutAgreement();
        assertEquals(getFinalValue("21.00"), rentalAgreement.getFinalCharge());
        assertEquals(Integer.valueOf(0), rentalAgreement.getDiscountPercent());
    }

    @Test
    public void test6() {
        AgreementService agreementService = new AgreementService();
        Checkout jakr = new Checkout(RentalEnums.ToolCode.JAKR.name(), 4, 50, LocalDate.of(2020, 7, 2));
        RentalAgreement rentalAgreement = agreementService.checkOut(jakr);
        rentalAgreement.printCheckOutAgreement();
        assertEquals(getFinalValue("3.00"), rentalAgreement.getFinalCharge());
        assertEquals(Integer.valueOf(50), rentalAgreement.getDiscountPercent());
    }

    @Test
    public void testHolidayChargesIsApplicable() {
        AgreementService agreementService = new AgreementService();
        Checkout chns = new Checkout(RentalEnums.ToolCode.CHNS.name(), 3, 10, LocalDate.of(2023, 7, 4));
        RentalAgreement rentalAgreement = agreementService.checkOut(chns);
        rentalAgreement.printCheckOutAgreement();
        assertEquals(getFinalValue("5.40"), rentalAgreement.getFinalCharge());
        assertEquals(new BigDecimal("0.60"), rentalAgreement.getDiscountAmount());
    }


    @Test
    public void testHolidayChargesIsNotApplicable() {
        AgreementService agreementService = new AgreementService();
        Checkout ladw = new Checkout(RentalEnums.ToolCode.LADW.name(), 5, 10, LocalDate.of(2023, 7, 4));
        RentalAgreement rentalAgreement = agreementService.checkOut(ladw);
        rentalAgreement.printCheckOutAgreement();
        assertEquals(getFinalValue("14.40"), rentalAgreement.getFinalCharge());
    }

    @Test
    public void testThrowErrorIfInvalidDaysPassed() {
        AgreementService agreementService = new AgreementService();
        Checkout ladw = new Checkout(RentalEnums.ToolCode.LADW.name(), 0, 10, LocalDate.of(2023, 7, 4));
        Throwable exception = assertThrows(RequestException.class, () -> agreementService.checkOut(ladw));
        assertEquals("Rental days should be 1 or more.", exception.getMessage());
    }


    @Test
    public void test1() {
        AgreementService agreementService = new AgreementService();
        Checkout jakr = new Checkout(RentalEnums.ToolCode.JAKR.name(), 5, 101, LocalDate.of(2015, 9, 3));
        Throwable exception = assertThrows(RequestException.class, () -> agreementService.checkOut(jakr));
        assertEquals("Discount percent is not in the range, (0-100)", exception.getMessage());
    }
    private String getFinalValue(String value) {
        String amount = new BigDecimal(value).toPlainString();
        return currency+amount ;
    }

}
