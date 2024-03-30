package com.example.salesmanagementsystem.service;

import com.example.salesmanagementsystem.dto.SaleItemUpdateRequest;
import com.example.salesmanagementsystem.dto.SaleRequest;
import com.example.salesmanagementsystem.dto.SaleUpdateRequest;
import com.example.salesmanagementsystem.exceptions.ClientException;
import com.example.salesmanagementsystem.exceptions.InsufficientProductStockException;
import com.example.salesmanagementsystem.exceptions.ProductException;
import com.example.salesmanagementsystem.exceptions.SaleException;
import com.example.salesmanagementsystem.model.*;
import com.example.salesmanagementsystem.repo.ProductRepository;
import com.example.salesmanagementsystem.repo.SaleItemRepository;
import com.example.salesmanagementsystem.repo.SaleRepository;
import com.example.salesmanagementsystem.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class SaleServiceImpl implements SaleService{
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;


    @Transactional
    @Override
    public Sale createSale(SaleRequest saleRequest) {
        Client client = userRepository.findById(saleRequest.getClientId()).orElseThrow(()-> new ClientException("Client Not Found", HttpStatus.NOT_FOUND));
        Client seller = userRepository.findById(saleRequest.getSellerId()).orElseThrow(()-> new ClientException("Seller ID not Found",HttpStatus.NOT_FOUND));


        Sale sale = new Sale();
        sale.setClient(client);
        if(seller.getRoles().equals(Role.ROLE_ADMIN)){
            sale.setSeller(seller);}

        List<SaleItem> saleItems = new ArrayList<>();


        for(SaleItem saleItem : saleRequest.getSaleItems()){
            Product product = productRepository.findByProductTag(saleItem.getProduct().getProductTag()).orElseThrow(()-> new ProductException("Product not found", HttpStatus.NOT_FOUND));
            if (product.getQuantity() < saleItem.getQuantity()) {
                throw new InsufficientProductStockException("Insufficient stock for product: " + product.getName(),HttpStatus.FORBIDDEN);

            }
            saleItems.add(new SaleItem( product, saleItem.getQuantity(), saleItem.getPrice()));



            product.setQuantity(product.getQuantity() - saleItem.getQuantity());
        }
        sale.setSaleItems(saleItems);
        System.out.println(sale.getSaleItems());
        sale.setTotal(saleItems.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum());
        saleRepository.save(sale);
        saleItems.stream()
                .map(saleItemRepository::save)  // Map each item to its saved version
                .collect(Collectors.toList());
        productRepository.saveAll(saleItems.stream().map(SaleItem::getProduct).toList());

        client.setTotalSpending(client.getTotalSpending() + sale.getTotal());
        userRepository.save(client);
        return sale;

    }




    @Override
    public List<Sale> findAllSales() {
        if(saleRepository.count() == 0){
            throw new SaleException("No sale has been made", HttpStatus.NOT_FOUND);

        }
        return saleRepository.findAll();
    }

    @Override
    public Sale updateSale(Long id, SaleUpdateRequest updateRequest) {
        Sale sale = saleRepository.findById(id).orElseThrow(()-> new SaleException("Sale does not exist", HttpStatus.NOT_FOUND));

        // Update existing SaleItems
        for (SaleItemUpdateRequest updateItem : updateRequest.getSaleItems()) {
            SaleItem existingItem = sale.getSaleItems().stream()
                    .filter(item -> item.getId().equals(updateItem.getId()))
                    .findFirst().orElseThrow();
            Product product = productRepository.findById(existingItem.getProduct().getId()).orElseThrow();

            // Calculate quantity change and validate stock
            int quantityChange = updateItem.getQuantity() - existingItem.getQuantity();
            if (product.getQuantity() < quantityChange) {
                throw new InsufficientProductStockException("Insufficient stock for product: " + product.getName(), HttpStatus.FORBIDDEN);

            }
            // Update SaleItem details and recalculate total for the sale
            existingItem.setQuantity(updateItem.getQuantity());
            existingItem.setPrice(updateItem.getPrice());
        }
        sale.setTotal(sale.getSaleItems().stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum());
        saleRepository.save(sale);
        return sale;
    }


}
