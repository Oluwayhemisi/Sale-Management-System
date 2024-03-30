package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.ProductRequest;
import com.example.salesmanagementsystem.dto.UpdateResponse;
import com.example.salesmanagementsystem.exceptions.ClientException;
import com.example.salesmanagementsystem.exceptions.ProductException;
import com.example.salesmanagementsystem.model.Client;
import com.example.salesmanagementsystem.model.Product;
import com.example.salesmanagementsystem.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    @Override
    public Product createProduct(ProductRequest productRequest) {
        Optional<Product> foundProduct = productRepository.findByProductTag(productRequest.getProductTag());
        if (foundProduct.isPresent()) {
            throw new ClientException("Product already exist.", HttpStatus.CONFLICT);
        }

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setProductTag(productRequest.getProductTag());

        return productRepository.save(product);

    }

    @Override
    public Product updateProduct(ProductRequest productRequest) {

       Product foundProduct= productRepository.findByProductTag(productRequest.getProductTag()).orElseThrow(()-> new ProductException("Client not found",HttpStatus.NOT_FOUND));
       Product mapProduct = modelMapper.map(productRequest,Product.class);

       foundProduct.setName(productRequest.getName());
       foundProduct.setCategory(productRequest.getCategory());
       foundProduct.setDescription(productRequest.getDescription());
       foundProduct.setQuantity(productRequest.getQuantity());
       foundProduct.setPrice(productRequest.getPrice());

       productRepository.save(foundProduct);
       return modelMapper.map(mapProduct, Product.class);



    }

    @Override
    public Product findById(String id) {
        Product foundProduct= productRepository.findById(Long.valueOf(id)).orElseThrow(()-> new ProductException("Client not found",HttpStatus.NOT_FOUND));
        return foundProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public String deleteProduct(String id) {
        Product foundProduct= productRepository.findById(Long.valueOf(id)).orElseThrow(()-> new ProductException("Client not found",HttpStatus.NOT_FOUND));
        productRepository.delete(foundProduct);

        return "This Product has been successfully Deleted";

    }
}
