package com.tool.rental.util;

import com.tool.rental.exception.RequestException;
import com.tool.rental.model.Tool;
import com.tool.rental.model.ToolType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ToolsUtil {

    public static Map<String, Tool> getAllTools() {
        Map<String, Tool> data = new HashMap<>();
        data.put("CHNS", new Tool("CHNA", getToolTypeByName("Chainsaw"), "Stihl"));
        data.put("LADW", new Tool("LADW", getToolTypeByName("Ladder"), "Werner"));
        data.put("JAKD", new Tool("JAKD", getToolTypeByName("Jackhammer"), "Ridgid"));
        data.put("JAKR", new Tool("JAKR", getToolTypeByName("Jackhammer"), "Ridgid"));
        return data;
    }

    public static Map<String, ToolType> getAllToolTypes() {
        Map<String, ToolType> data = new HashMap<>();
        data.put("Chainsaw", new ToolType("Chainsaw", 2d, true,false,true));
        data.put("Ladder", new ToolType("Ladder", 4d, true,true,false));
        data.put("Jackhammer", new ToolType("Jackhammer", 3d, true,false,false));
        return data;
    }


    public static Tool getToolByToolCode(String toolCode) {
        if (getAllTools().containsKey(toolCode)) {
            return getAllTools().get(toolCode);
        } else {
            throw new RequestException("Provided ToolCode does not exist, ToolCode: "+toolCode);
        }
    }

    public static ToolType getToolTypeByName(String toolTypeName) {
        if (getAllToolTypes().containsKey(toolTypeName)) {
            return getAllToolTypes().get(toolTypeName);
        } else {
            throw new RequestException("Invalid tool type name");
        }
    }


    public static String getFormattedDateDDMMYY(LocalDate date) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yy");
        return date.format(formatters);
    }
}

