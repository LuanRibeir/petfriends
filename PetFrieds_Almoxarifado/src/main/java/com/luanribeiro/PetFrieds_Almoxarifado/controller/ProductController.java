package com.luanribeiro.PetFrieds_Almoxarifado.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luanribeiro.PetFrieds_Almoxarifado.domain.Product;
import com.luanribeiro.PetFrieds_Almoxarifado.infra.service.ProductService;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @GetMapping("/{id}")
    public Product getById(@PathVariable(value = "id") long id) {
        return service.getById(id);
    }

    @PatchMapping("/prepare/{id}")
    public Product prepareProductById(@PathVariable(value = "id") long id) {
        return service.prepareProduct(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Product product) {
        LOG.info("Raw JSON Request: {}", product);

        try {

            return new ResponseEntity<>(service.createProduct(product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
