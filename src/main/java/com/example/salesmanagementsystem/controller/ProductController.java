package com.example.salesmanagementsystem.controller;

import com.example.salesmanagementsystem.dto.ProductRequest;
import com.example.salesmanagementsystem.model.Product;
import com.example.salesmanagementsystem.service.ProductService;
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
public class ProductController {

    private final ProductService productService;


    @PostMapping("/createproduct")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest productRequest){
        Product savedProduct=  productService.createProduct(productRequest);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PostMapping("/update-product")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductRequest productRequest){
        Product savedProduct=  productService.updateProduct(productRequest);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @GetMapping("/find-product/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") String id){
        Product savedProduct=  productService.findById(id);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }


    @GetMapping("/find-all-products")
    public ResponseEntity<?> getAllProduct(){
        List<Product> savedProducts=  productService.getAllProducts();
        return new ResponseEntity<>(savedProducts, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable ("id") String id){
        String message = productService.deleteProduct(id);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }





}
