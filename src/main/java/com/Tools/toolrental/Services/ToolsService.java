package com.Tools.toolrental.Services;

import com.Tools.toolrental.DTO.ToolDTO;
import com.Tools.toolrental.Model.Brand;
import com.Tools.toolrental.Model.ToolType;
import com.Tools.toolrental.Model.Tool;
import com.Tools.toolrental.Repositories.ToolBrandRepo;
import com.Tools.toolrental.Repositories.ToolTypeRepo;
import com.Tools.toolrental.Repositories.ToolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class ToolsService {

    @Autowired
    private ToolBrandRepo brandRepo;

    @Autowired
    private ToolRepo toolRepo;
    @Autowired
    private ToolTypeRepo toolTypeRepo;

    public String addBrand(Brand brand){
        Brand brand1 = brandRepo.save(brand);
        return "Brand has been added.";
    }

    public String addToolType(ToolType toolType){
        ToolType toolType1 = toolTypeRepo.save(toolType);
        return "ToolTemp has been added.";
    }

    public String addTool(Tool tool){
        Tool tool1 = toolRepo.save(tool);
        return "ToolTemp has been added.";
    }

    public ToolDTO getByCode(String code){

        Optional<Tool> tool =  toolRepo.findByToolCode(code);
        ToolDTO dto = mapToDto.apply(tool.get());
        return dto;
    }

    public static Function<Tool,ToolDTO> mapToDto = (tool)->{
        ToolDTO dto = new ToolDTO();
        dto.setId(tool.getId());
        dto.setToolCode(tool.getToolCode());

        if (tool.getBrand() != null) {
            dto.setBrandId(tool.getBrand().getBrandId());
            dto.setBrandName(tool.getBrand().getBrandName());
        }

        if (tool.getToolType() != null) {
            dto.setToolTypeId(tool.getToolType().getToolTypeId());
            dto.setToolTypeName(tool.getToolType().getToolTypeName());
            dto.setDailyRentalCharge(tool.getToolType().getDailyRentalCharge());
        }
        return dto;
    };

}
