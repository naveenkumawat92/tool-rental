package com.Tools.toolrental.DTO;

import com.Tools.toolrental.Model.Brand;
import com.Tools.toolrental.Model.ToolType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolDTO {
    private Long id;
    private String toolCode;
    private Long brandId;
    private String brandName;
    private String toolTypeName;
    private Double dailyRentalCharge;
    private Long toolTypeId;
}