package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path = "")
    public List<Product> index() {
        return productRepository.findAll();
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // BEGIN
    @GetMapping(path = "/{id}")
    public ResponseEntity show(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Product with id "+id+" not found")));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody Product product) {
        var currentProduct = productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Product with id "+id+" not found"));
        currentProduct.setTitle(product.getTitle());
        currentProduct.setPrice(product.getPrice());
        productRepository.save(currentProduct);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    // END

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        productRepository.deleteById(id);
    }
}
