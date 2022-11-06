package ru.gb.spring_test.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring_test.dto.ProductDto;
import ru.gb.spring_test.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> getAllProducts(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                        @RequestParam(name = "min_cost", required = false) Integer minCost,
                                        @RequestParam(name = "max_cost", required = false) Integer maxCost,
                                        @RequestParam(name = "title", required = false) String title) {
        if (page < 1) page = 1;
        return productService.find(page, minCost, maxCost, title);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return productService.update(productDto);
    }

    @PostMapping
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PatchMapping("/{id}/title")
    public void patchTitle(@PathVariable Long id, @RequestBody ProductDto productDto) {
        productService.updateTitle(id, productDto);
    }
}
