package com.caliq.api_conection_service.controller;


import com.caliq.api_conection_service.model.FoodDataResponse;
import com.caliq.api_conection_service.model.FoodInfoDto;
import com.caliq.api_conection_service.service.OpenFoodFactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class OpenFoodFactController {

    private final OpenFoodFactService openFoodFactService;

    public OpenFoodFactController(OpenFoodFactService openFoodFactService) {
        this.openFoodFactService = openFoodFactService;
    }


    @GetMapping("/findbybarcode/{barcode}")
    public ResponseEntity<FoodDataResponse> getInfoByBarcode(@PathVariable("barcode") String barcode){

        FoodDataResponse response = openFoodFactService.getFoodInfo(barcode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
