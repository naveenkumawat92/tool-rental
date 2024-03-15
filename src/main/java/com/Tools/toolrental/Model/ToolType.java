package com.Tools.toolrental.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "tool_type")
public class ToolType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tool_type_id")
    private Long toolTypeId;

    @Column(name = "tool_type_name")
    private String toolTypeName;

    @Column(name = "rental_charge")
    private Double dailyRentalCharge;

}
