package com.mawit.dscatalog.repositories;

import com.mawit.dscatalog.entities.Product;
import com.mawit.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.swing.*;
import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long exintingId;
    private long notExintingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        exintingId = 1L;
        notExintingId = 26L;
        countTotalProducts = 25L;
    }

    @Test
    public void findByIdShouldReturnOptionalNotEmptyWhenIdExist(){

        Product product = Factory.createProduct();
        Optional<Product> result = repository.findById(product.getId());
        Assertions.assertTrue(result.isPresent());

    }

    @Test
    public void findByIdShouldReturnOptionalEmptyWhenIdNotExist(){

        Optional<Product> result = repository.findById(notExintingId);
        Assertions.assertTrue(result.isEmpty());

    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);
        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){

        repository.deleteById(exintingId);
        Optional<Product> result = repository.findById(exintingId);
        Assertions.assertFalse(result.isPresent());

    }



}
