package com.tool.rental.service;

import com.tool.rental.exception.RequestException;
import com.tool.rental.model.Checkout;
import com.tool.rental.model.RentalAgreement;
import com.tool.rental.model.Tool;
import com.tool.rental.util.ToolsUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * AgreementService is used to generate the agreement of Tool,
 * this service contains one method which prepare the agreement based on the checkout details
 *
 */
public class AgreementService {


    /**
     * checkout method is used to prepare checkout details and before prepare the checkout agreement
     * it validates the checkout request, if required parameters are not present in the checkout
     * object then it will throw the exception.
     * It calculates the chargeable days based on the chargeable criteria of tool
     * It checks weekends and holidays are chargeable or
     *
     * @param checkout - Checkout class object contains all the required checkout details
     * @return - it returns the RentalAgreement obect, which contains all the rent agreement details
     */
    public RentalAgreement checkOut(Checkout checkout) {

        //Validate checkout request before generating checkout agreement
            preCheckoutValidation(checkout);

            RentalAgreement rentalAgreement = new RentalAgreement();
            DataService dataService = new DataService();

            Tool tool = dataService.getToolByToolCode(checkout.getToolCode());

            rentalAgreement.setToolCode(tool.getToolCode());
            rentalAgreement.setRentalDays(checkout.getRentalDay());
            // convert checkout date in MMDDYY
            rentalAgreement.setCheckoutDate(ToolsUtil.getFormattedDateMMDDYY(checkout.getCheckOutDate()));

            LocalDate dueDate = checkout.getCheckOutDate().plusDays(checkout.getRentalDay() - 1);
            rentalAgreement.setDueDate(ToolsUtil.getFormattedDateMMDDYY(dueDate));

            // calculate chargeable days
            int chargeableDays = ToolsUtil.calculateChargeableDays(checkout.getCheckOutDate(), dueDate, tool.getToolType());
            rentalAgreement.setChargeDays(chargeableDays);
            rentalAgreement.setBrandName(tool.getBrand().name());
            rentalAgreement.setDailyRentalCharge(ToolsUtil.getFinalChargesValue(new BigDecimal(tool.getToolType().getDailyRentalCharge())));

            BigDecimal preDiscountAmount = BigDecimal.valueOf(chargeableDays).multiply(BigDecimal.valueOf(tool.getToolType().getDailyRentalCharge()));
            rentalAgreement.setPreDiscountCharge(ToolsUtil.getFinalChargesValue(preDiscountAmount));

//            rentalAgreement.setDailyRentalCharge(BigDecimal.valueOf(tool.getToolType().getDailyRentalCharge()));
            rentalAgreement.setDiscountPercent(checkout.getDiscountPer());
            BigDecimal discountAmount = preDiscountAmount.multiply(BigDecimal.valueOf(checkout.getDiscountPer()))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            rentalAgreement.setDiscountAmount(ToolsUtil.getFinalChargesValue(discountAmount));

            BigDecimal finalCharge = preDiscountAmount.subtract(discountAmount);
            rentalAgreement.setFinalCharge(ToolsUtil.getFinalChargesValue(finalCharge));
            rentalAgreement.setToolType(tool.getToolType().getToolTypeName());
//            System.out.println(rentalAgreement);
            return rentalAgreement;
    }

    /**
     * validate checkout details that whether all the required properties are available or not
     * if required details is missing in the Checkout details object, then it will throw RequestException
     *
     * @param checkout - A object of Checkout class, which contains checkout relation information like checkout date and days.
     */
    private static void preCheckoutValidation(Checkout checkout) {

        if (Objects.isNull(checkout)) {
            throw new RequestException("Bad Request, Checkout details is missing");
        }

        if (checkout.getToolCode() == null || checkout.getToolCode().equals("")) {
            throw new RequestException("Invalid Request,Tool code is missing in request");
        }

        if (checkout.checkOutDate == null) {
            throw new RequestException("Invalid Request,CheckOut date is missing in request");
        }

        if (checkout.getRentalDay() <= 0) {
            throw new RequestException("Rental days should be 1 or more.");
        }

        if (checkout.getDiscountPer() < 0 || checkout.getDiscountPer() > 100) {
            throw new RequestException("Discount percent is not in the range, (0-100)");
        }
    }




}

