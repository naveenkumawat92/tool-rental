package com.tool.rental.model;

public class Tool {


    public Tool(String toolCode, ToolType toolType,String brand) {
        this.toolCode = toolCode;
        this.brand = brand;
        this.toolType = toolType;
    }

    public Tool() {
    }

    private String toolCode;


    private String brand;

    private ToolType toolType;


    public String getToolCode() {
        return toolCode;
    }

    public String getBrand() {
        return brand;
    }

    public ToolType getToolType() {
        return toolType;
    }


}