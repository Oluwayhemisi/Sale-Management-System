package com.example.salesmanagementsystem.controller;


import com.example.salesmanagementsystem.dto.ProductRequest;
import com.example.salesmanagementsystem.dto.SaleRequest;
import com.example.salesmanagementsystem.dto.SaleUpdateRequest;
import com.example.salesmanagementsystem.model.Sale;
import com.example.salesmanagementsystem.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;


    @PostMapping("/create-sale")
    public ResponseEntity<Sale> createSale(@Valid @RequestBody SaleRequest saleRequest){
        Sale savedSale=  saleService.createSale(saleRequest);
        return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
    }

    @PostMapping("/update-sale/{id}")
    public ResponseEntity<Sale> updateSale(@Valid @PathVariable("id") Long id,@RequestBody SaleUpdateRequest updateRequest){
        Sale savedSale=  saleService.updateSale(id, updateRequest);
        return new ResponseEntity<>(savedSale, HttpStatus.OK);
    }



    @GetMapping("/find-all-sales")
    public ResponseEntity<?> getAllSales(){
        List<Sale> savedSales =  saleService.findAllSales();
        return new ResponseEntity<>(savedSales, HttpStatus.CREATED);
    }


}
