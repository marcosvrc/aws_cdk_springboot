package br.com.sbaws.awsprojecta.service;

import br.com.sbaws.awsprojecta.entity.Product;
import br.com.sbaws.awsprojecta.enums.EventType;
import br.com.sbaws.awsprojecta.exception.ResourceNotFoundException;
import br.com.sbaws.awsprojecta.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private ProductPublished productPublished;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductPublished productPublished){
        this.productRepository = productRepository;
        this.productPublished = productPublished;
    }

    public Iterable<Product> findAll(){
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(long id) {
        Optional<Product> product = Optional.ofNullable(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this id!")));
        return product;
    }

    @Override
    public Optional<Product> findByCode(String code) {
        Optional<Product> product = Optional.ofNullable(productRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this code!")));
        return product;
    }

    @Override
    public Product save(Product product) {
        Product productCreated = productRepository.save(product);
        productPublished.publishedProductEvent(productCreated, EventType.PRODUCT_CREATED, "admin");
        return productCreated;

    }

    @Override
    public Product update(Product product) {
        Optional<Product> productOptional = Optional.ofNullable(productRepository.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this id!")));

        Product productUpdated = productRepository.save(product);
        productPublished.publishedProductEvent(productUpdated, EventType.PRODUCT_UPDATED, "root");
        return productUpdated;
    }

    @Override
    public void delete(long id) {
        Optional<Product> productOptional = Optional.ofNullable(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this id!")));
        productRepository.delete(productOptional.get());
        productPublished.publishedProductEvent(productOptional.get(), EventType.PRODUCT_DELETED, "main");
    }
}
