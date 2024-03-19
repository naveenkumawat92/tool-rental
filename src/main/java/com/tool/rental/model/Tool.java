package com.tool.rental.model;

public class Tool {
    private String toolCode;


    private RentalEnums.Brands brand;

    private ToolType toolType;

    public Tool(String toolCode, ToolType toolType,RentalEnums.Brands brand) {
        this.toolCode = toolCode;
        this.brand = brand;
        this.toolType = toolType;
    }

    public Tool() {
    }


    public String getToolCode() {
        return toolCode;
    }

    public RentalEnums.Brands getBrand() {
        return brand;
    }


    public ToolType getToolType() {
        return toolType;
    }


}