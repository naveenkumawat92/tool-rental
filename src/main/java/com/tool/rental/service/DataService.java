package com.tool.rental.service;

import com.tool.rental.exception.RequestException;
import com.tool.rental.model.RentalEnums;
import com.tool.rental.model.Tool;
import com.tool.rental.model.ToolType;

import java.util.HashMap;
import java.util.Map;

/**
 * DataService class contains all the method related to tool data, where we can add more tools and tools type
 * and also we can fetch data based on specific identifier.
 */
public class DataService {

    /**
     * this method contains all the tools data, Data is prepared using map, where key is unique ToolCode
     * and value will contain a Tool object which contains the information of tool
     * @return - returning a map of tools
     */
    public Map<String, Tool> getAllTools() {
        Map<String, Tool> data = new HashMap<>();
        data.put(RentalEnums.ToolCode.CHNS.name(), new Tool(RentalEnums.ToolCode.CHNS.name(), getToolTypeByName(RentalEnums.ToolTypes.CHAINSAW.name()), RentalEnums.Brands.STIHL));
        data.put(RentalEnums.ToolCode.LADW.name(), new Tool(RentalEnums.ToolCode.LADW.name(), getToolTypeByName(RentalEnums.ToolTypes.LADDER.name()), RentalEnums.Brands.WERNER));
        data.put(RentalEnums.ToolCode.JAKD.name(), new Tool(RentalEnums.ToolCode.JAKD.name(), getToolTypeByName(RentalEnums.ToolTypes.JACKHAMMER.name()), RentalEnums.Brands.RIDGID));
        data.put(RentalEnums.ToolCode.JAKR.name(), new Tool(RentalEnums.ToolCode.JAKR.name(), getToolTypeByName(RentalEnums.ToolTypes.JACKHAMMER.name()),  RentalEnums.Brands.RIDGID));
        return data;
    }

    /**
     * getAllToolTypes method returns map of ToolType object which is containing information about the tool like dailyCharges,
     * weekend charges and other charges relation information.
     * Map contains key as ToolType name which is unique, and value is ToolType Object
     * @return - returning a map of all the toolTypes
     */
    public Map<String, ToolType> getAllToolTypes() {
        Map<String, ToolType> data = new HashMap<>();
        data.put(RentalEnums.ToolTypes.CHAINSAW.name(), new ToolType(RentalEnums.ToolTypes.CHAINSAW.name(), 2d, true,false,true));
        data.put(RentalEnums.ToolTypes.LADDER.name(), new ToolType(RentalEnums.ToolTypes.LADDER.name(), 4d, true,true,false));
        data.put(RentalEnums.ToolTypes.JACKHAMMER.name(), new ToolType(RentalEnums.ToolTypes.JACKHAMMER.name(), 3d, true,false,false));
        return data;
    }


    /**
     * This method return the Tool object based on the given toolCode
     * if given toolCode is not found in the Map, then this method will throw the exception for the invalid toolCode
     * @param toolCode - A unique identifier for each tool
     * @return - returns the Tool class object for the given toolCode.
     */
    public Tool getToolByToolCode(String toolCode) {
        if (getAllTools().containsKey(toolCode)) {
            return getAllTools().get(toolCode);
        } else {
            throw new RequestException("Provided ToolCode does not exist, ToolCode: "+toolCode);
        }
    }

    /**
     * This method return the ToolType object based on the given toolType
     * if given toolType is not found in the Map, then this method will throw the exception for the invalid toolType
     * @param toolType - A unique identifier for each toolType
     * @return - returns the ToolType class object for the given toolType.
     */
    public ToolType getToolTypeByName(String toolType) {
        if (getAllToolTypes().containsKey(toolType)) {
            return getAllToolTypes().get(toolType);
        } else {
            throw new RequestException("Invalid tool type");
        }
    }

}
