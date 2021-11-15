package br.com.sbaws.awsprojecta.service;

import br.com.sbaws.awsprojecta.entity.Product;

import java.util.Optional;

public interface ProductService {

    Iterable<Product> findAll();

    Optional<Product> findById(long id);

    Optional<Product> findByCode(String code);

    Product save(Product product);

    Product update(Product product);

    void delete(long id);


}
