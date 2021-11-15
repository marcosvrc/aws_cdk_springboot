package br.com.sbaws.awsprojecta.controller;

import br.com.sbaws.awsprojecta.entity.Product;
import br.com.sbaws.awsprojecta.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/v1/products")
    public Iterable<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/v1/product/{id}")
    public ResponseEntity<Product> findById(@PathVariable long id) {
        Optional<Product> productOptional = productService.findById(id);
            return ResponseEntity.ok().body(productOptional.get());
    }

    @PostMapping("/v1/product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product productCreated = productService.save(product);

        return new ResponseEntity<Product>(productCreated,HttpStatus.CREATED);
    }

    @PutMapping("/v1/product")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        Product productUpdated = productService.update(product);
        return ResponseEntity.ok().body(productUpdated);
    }

    @DeleteMapping("/v1/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id){
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/product")
    public ResponseEntity<Product> findByCode(@RequestParam String code) {
        Optional<Product> productOptional = productService.findByCode(code);
        return ResponseEntity.ok().body(productOptional.get());
    }
}
