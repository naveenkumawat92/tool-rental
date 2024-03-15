package com.Tools.toolrental.DTO;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CheckoutDto {

    public String toolCode;
    public int rentalDay;
    public int discountPer;
    public LocalDate checkOutDate;

}
