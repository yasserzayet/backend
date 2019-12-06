package com.Ecommerce.controller;

import com.Ecommerce.exception.NotFoundException;
import com.Ecommerce.model.OrderProduct;
import com.Ecommerce.repository.OrderProductRepository;
import com.Ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderProductController {
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product/{productId}/cart")
    public List<OrderProduct> getCartByProductId(@PathVariable long productId){
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProductRepository.findByProductId(productId).forEach(orderProducts::add);
        return orderProducts;
    }
}
