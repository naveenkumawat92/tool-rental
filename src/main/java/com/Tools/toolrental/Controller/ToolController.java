package com.Tools.toolrental.Controller;

import com.Tools.toolrental.CustomException.RequestException;
import com.Tools.toolrental.DTO.CheckoutDto;
import com.Tools.toolrental.DTO.ToolDTO;
import com.Tools.toolrental.Model.*;
import com.Tools.toolrental.Services.AgreementService;
import com.Tools.toolrental.Services.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tool")
public class ToolController {

    @Autowired
    private ToolsService toolsService;

    @Autowired
    private AgreementService agreementService;


    @PostMapping("/brand")
    public String addBrand(@RequestBody Brand brand){
        return toolsService.addBrand(brand);
    }

    @PostMapping("/type")
    public String addType(@RequestBody ToolType type){
        return toolsService.addToolType(type);
    }

    @PostMapping("/add/tool")
    public String addType(@RequestBody Tool tool){
        return toolsService.addTool(tool);
    }

    @GetMapping("/get/{toolCode}")
    public ToolDTO getTool(@PathVariable("toolCode") String code){
        return toolsService.getByCode(code);
    }


    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutTool(@RequestBody CheckoutDto checkoutDto){
        try {
            RentalAgreement rentalAgreement = agreementService.checkOut(checkoutDto);
            return new ResponseEntity<>(rentalAgreement, HttpStatus.CREATED);
        } catch (RequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
