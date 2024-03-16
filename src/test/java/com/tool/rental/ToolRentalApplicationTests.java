package com.tool.rental;


import com.tool.rental.exception.RequestException;
import com.tool.rental.model.Checkout;
import com.tool.rental.model.RentalAgreement;
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
        Checkout jakr = new Checkout("JAKR", 5, 25, LocalDate.of(2015, 3, 9));
        Checkout ladw = new Checkout("LADW", 5, 25, LocalDate.now());
//        Checkout chns = new Checkout("CHNS", 5, 25, );
        RentalAgreement rentalAgreement = agreementService.checkOut(jakr);
        assertNotNull(rentalAgreement);
    }


    @Test
    public void testFinalPriceAfterDiscount() {
        AgreementService agreementService = new AgreementService();
        Checkout ladw = new Checkout("LADW", 3, 10, LocalDate.of(2020, 2, 7));
        RentalAgreement rentalAgreement = agreementService.checkOut(ladw);
        assertEquals(getFinalValue("10.80"), rentalAgreement.getFinalCharge());
    }

    @Test
    public void testCHNS() {
        AgreementService agreementService = new AgreementService();
        Checkout chns = new Checkout("CHNS", 5, 25, LocalDate.of(2015, 2, 7));
        RentalAgreement rentalAgreement = agreementService.checkOut(chns);
        assertEquals(getFinalValue("4.50"), rentalAgreement.getFinalCharge());
    }

    @Test
    public void testJAKDWithoutDiscount() {
        AgreementService agreementService = new AgreementService();
        Checkout jakd = new Checkout("JAKD", 6, 0, LocalDate.of(2015, 3, 9));
        RentalAgreement rentalAgreement = agreementService.checkOut(jakd);
        assertEquals(getFinalValue("15.00"), rentalAgreement.getFinalCharge());
        assertEquals(0, rentalAgreement.getDiscountPercent());
    }

    @Test
    public void testJAKRWithoutDiscount() {
        AgreementService agreementService = new AgreementService();
        Checkout jakd = new Checkout("JAKD", 9, 0, LocalDate.of(2015, 2, 7));
        RentalAgreement rentalAgreement = agreementService.checkOut(jakd);
        assertEquals(getFinalValue("15.00"), rentalAgreement.getFinalCharge());
        assertEquals(0, rentalAgreement.getDiscountPercent());
    }

    @Test
    public void testHolidayChargesIsApplicable() {
        AgreementService agreementService = new AgreementService();
        Checkout chns = new Checkout("CHNS", 3, 10, LocalDate.of(2023, 7, 4));
        RentalAgreement rentalAgreement = agreementService.checkOut(chns);
        assertEquals(getFinalValue("5.40"), rentalAgreement.getFinalCharge());
        assertEquals(new BigDecimal("0.60"), rentalAgreement.getDiscountAmount());
    }


    @Test
    public void testHolidayChargesIsNotApplicable() {
        AgreementService agreementService = new AgreementService();
        Checkout ladw = new Checkout("LADW", 5, 10, LocalDate.of(2023, 7, 4));
        RentalAgreement rentalAgreement = agreementService.checkOut(ladw);
        assertEquals(getFinalValue("14.40"), rentalAgreement.getFinalCharge());
    }

    @Test
    public void testThrowErrorIfInvalidDaysPassed() {
        AgreementService agreementService = new AgreementService();
        Checkout ladw = new Checkout("LADW", 0, 10, LocalDate.of(2023, 7, 4));
        Throwable exception = assertThrows(RequestException.class, () -> agreementService.checkOut(ladw));
        assertEquals("Rental days should be 1 or more.", exception.getMessage());
    }


    @Test
    public void testThrowErrorIfInvalidDiscountPassed() {
        AgreementService agreementService = new AgreementService();
        Checkout ladw = new Checkout("LADW", 5, 101, LocalDate.of(2023, 7, 4));
        Throwable exception = assertThrows(RequestException.class, () -> agreementService.checkOut(ladw));
        assertEquals("Discount percent is not in the range, (0-100)", exception.getMessage());
    }
    private String getFinalValue(String value) {
        return new BigDecimal(value).toPlainString() + currency;
    }

}
