package com.tool.rental.service;

import com.tool.rental.exception.RequestException;
import com.tool.rental.model.Checkout;
import com.tool.rental.model.RentalAgreement;
import com.tool.rental.model.Tool;
import com.tool.rental.model.ToolType;
import com.tool.rental.util.ToolsUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

public class AgreementService {

    public RentalAgreement checkOut(Checkout checkout) {

        if (Objects.nonNull(checkout)) {
            RentalAgreement rentalAgreement = new RentalAgreement();
            //validate checkout details
            preCheckoutValidation(checkout);
            Tool tool = ToolsUtil.getToolByToolCode(checkout.getToolCode());

            rentalAgreement.setToolCode(tool.getToolCode());
            rentalAgreement.setRentalDays(checkout.getRentalDay());
            rentalAgreement.setCheckoutDate(ToolsUtil.getFormattedDateDDMMYY(checkout.getCheckOutDate()));

            LocalDate dueDate = checkout.getCheckOutDate().plusDays(checkout.getRentalDay() - 1);
            rentalAgreement.setDueDate(dueDate);

            int chargeableDays = calculateChargeableDays(checkout.getCheckOutDate(), dueDate, tool.getToolType());
            rentalAgreement.setChargeDays(chargeableDays);

            BigDecimal preDiscountAmount = BigDecimal.valueOf(chargeableDays).multiply(BigDecimal.valueOf(tool.getToolType().getDailyRentalCharge()));
            rentalAgreement.setPreDiscountCharge(preDiscountAmount);

            rentalAgreement.setDailyRentalCharge(BigDecimal.valueOf(tool.getToolType().getDailyRentalCharge()));
            rentalAgreement.setDiscountPercent(checkout.getDiscountPer());
            BigDecimal discountAmount = preDiscountAmount.multiply(BigDecimal.valueOf(checkout.getDiscountPer()))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            rentalAgreement.setDiscountAmount(discountAmount);

            BigDecimal finalCharge = preDiscountAmount.subtract(discountAmount);
            rentalAgreement.setFinalCharge(finalCharge.toPlainString() + "$");
            System.out.println(rentalAgreement);
            return rentalAgreement;
        } else {
            throw new RequestException("Bad Request, Checkout details are missing");
        }
    }

    private static void preCheckoutValidation(Checkout checkout) {

        if (checkout.getToolCode()== null || checkout.getToolCode().equals("")) {
            throw new RequestException("Invalid Request,Tool code is missing in request");
        }

        if (checkout.checkOutDate == null ) {
            throw new RequestException("Invalid Request,CheckOut date is missing in request");
        }

        if (checkout.getRentalDay() <= 0) {
            throw new RequestException("Rental days should be 1 or more.");
        }

        if (checkout.getDiscountPer() < 0 || checkout.getDiscountPer() > 100) {
            throw new RequestException("Discount percent is not in the range, (0-100)");
        }
    }

    private static int calculateChargeableDays(LocalDate checkoutDate, LocalDate dueDate, ToolType toolType) {
        int chargeableDays = 0;
        LocalDate dayDate = checkoutDate;
            while (!dayDate.isAfter(dueDate)) {
                if (isHoliday(dayDate) && toolType.isHolidayCharge()) {
                    chargeableDays++;
                }  else if (!isWeekday(dayDate) && toolType.isWeekendCharge()) {
                    chargeableDays++;
                } else if (isWeekday(dayDate) ) {
                    if (isHoliday(dayDate) || !isWeekday(dayDate)) {
                        dayDate = dayDate.plusDays(1);
                        continue;
                    }
                    chargeableDays++;
                }
                dayDate = dayDate.plusDays(1);
            }
//        }
        return chargeableDays;
    }

    private static boolean isHoliday(LocalDate date) {
        if (date.getMonth() == Month.JULY && date.getDayOfMonth() == 4) {
            return true;//date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        } else if (date.getMonth() == Month.SEPTEMBER && date.getDayOfWeek() == DayOfWeek.MONDAY) {
            TemporalAdjuster firstMondayInMonth = TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY);
            LocalDate firstMondayOfSeptember = date.with(firstMondayInMonth);
            return date.equals(firstMondayOfSeptember);
        }
        return false;
    }


    private static boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

//    public Function<ToolDTO, Tool> mapToEntity = dto -> {
//        Tool tool = new Tool();
//        tool.setId(dto.getId());
//        tool.setToolCode(dto.getToolCode());
//
//        Brand brand = new Brand();
//        brand.setBrandId((dto.getBrandId()));
//        brand.setBrandName(dto.getBrandName());
//        tool.setBrand(brand);
//
//        ToolType toolType = new ToolType();
//        toolType.setToolTypeId(dto.getToolTypeId());
//        toolType.setToolTypeName(dto.getToolTypeName());
//        toolType.setDailyRentalCharge(dto.getDailyRentalCharge());
//        tool.setToolType(toolType);
//
//        return tool;
//    };

}

