package com.Tools.toolrental.Services;

import com.Tools.toolrental.CustomException.RequestException;
import com.Tools.toolrental.DTO.CheckoutDto;
import com.Tools.toolrental.DTO.ToolDTO;
import com.Tools.toolrental.Model.*;
import com.Tools.toolrental.Repositories.RentalAgreementRepository;
import com.Tools.toolrental.Repositories.ToolRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.function.Function;

@Service
public class AgreementService {
    @Autowired
    private RentalAgreementRepository agreementRepository;


    @Autowired
    private ToolsService toolsService;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ToolRepo toolRepo;

    public RentalAgreement checkOut(CheckoutDto checkoutDto) {
        RentalAgreement rentalAgreement = new RentalAgreement();

        ToolDTO toolDTO = toolsService.getByCode(checkoutDto.getToolCode());
//        Tool tool = mapper.map(toolDTO, Tool.class);
        Tool tool = mapToEntity.apply(toolDTO);

        if (tool == null) {
            throw new RequestException("tool Not found");
        }

        rentalAgreement.setTool(tool);
        if (checkoutDto.getRentalDay() <= 0) {
            throw new RequestException("Rental days should be 1 or more.");
        }
        rentalAgreement.setRentalDays(checkoutDto.getRentalDay());
        rentalAgreement.setCheckoutDate(checkoutDto.getCheckOutDate());

        LocalDate dueDate = checkoutDto.getCheckOutDate().plusDays(checkoutDto.getRentalDay() - 1);
        rentalAgreement.setDueDate(dueDate);

        int chargeableDays = calculateChargeableDays(checkoutDto.getCheckOutDate(), dueDate, toolDTO.getToolTypeName());
        rentalAgreement.setChargeDays(chargeableDays);

        BigDecimal preDiscountAmount = BigDecimal.valueOf(chargeableDays).multiply(BigDecimal.valueOf(toolDTO.getDailyRentalCharge()));
        rentalAgreement.setPreDiscountCharge(preDiscountAmount);

        rentalAgreement.setDailyRentalCharge(BigDecimal.valueOf(toolDTO.getDailyRentalCharge()));
        if (checkoutDto.getDiscountPer() < 0 || checkoutDto.getDiscountPer() > 100) {
            throw new RequestException("Discount rate is not applicable!");
        }
        rentalAgreement.setDiscountPercent(checkoutDto.getDiscountPer());
        BigDecimal discountAmount = preDiscountAmount.multiply(BigDecimal.valueOf(checkoutDto.getDiscountPer()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        rentalAgreement.setDiscountAmount(discountAmount);

        BigDecimal finalCharge = preDiscountAmount.subtract(discountAmount);
        rentalAgreement.setFinalCharge(finalCharge);

        return agreementRepository.save(rentalAgreement);
    }

    private static int calculateChargeableDays(LocalDate checkoutDate, LocalDate dueDate, String tool) {
        int chargeableDays = 0;
        LocalDate currentDate = checkoutDate;
        if (tool.equalsIgnoreCase("Chainsaw")) {
            while (!currentDate.isAfter(dueDate)) {
                if (isWeekday(currentDate)) {
                    chargeableDays++;
                }
                currentDate = currentDate.plusDays(1);
            }
        } else if (tool.equalsIgnoreCase("Ladder")) {
            while (!currentDate.isAfter(dueDate)) {
                if (!isHoliday(currentDate)) {
                    chargeableDays++;
                }
                currentDate = currentDate.plusDays(1);
            }
        } else if (tool.equalsIgnoreCase("Jackhammer")) {
            while (!currentDate.isAfter(dueDate)) {
                if (!isHoliday(currentDate) && isWeekday(currentDate)) {
                    chargeableDays++;
                }
                currentDate = currentDate.plusDays(1);
            }
        }
        return chargeableDays;
    }

    private static boolean isHoliday(LocalDate date) {
        if (date.getMonth() == Month.JULY && date.getDayOfMonth() == 4) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                return true;
            } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                return true;
            }
        } else if (date.getMonth() == Month.SEPTEMBER && date.getDayOfWeek() == DayOfWeek.MONDAY) {
            TemporalAdjuster firstMondayInMonth = TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY);
            LocalDate firstMondayOfSeptember = date.with(firstMondayInMonth);
            if (date.equals(firstMondayOfSeptember)) {
                return true;
            }
        }
        return false;
    }


    private static boolean isWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    public Function<ToolDTO, Tool> mapToEntity = dto -> {
        Tool tool = new Tool();
        tool.setId(dto.getId());
        tool.setToolCode(dto.getToolCode());

        Brand brand = new Brand();
        brand.setBrandId((dto.getBrandId()));
        brand.setBrandName(dto.getBrandName());
        tool.setBrand(brand);

        ToolType toolType = new ToolType();
        toolType.setToolTypeId(dto.getToolTypeId());
        toolType.setToolTypeName(dto.getToolTypeName());
        toolType.setDailyRentalCharge(dto.getDailyRentalCharge());
        tool.setToolType(toolType);

        return tool;
    };

}

