package ru.gb.spring_test.servise;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.gb.spring_test.converters.ProductMapper;
import ru.gb.spring_test.dto.ProductDto;
import ru.gb.spring_test.entities.Product;
import ru.gb.spring_test.exceptions.ValidateException;
import ru.gb.spring_test.repositories.ProductRepository;
import ru.gb.spring_test.repositories.specification.ProductSpecification;
import ru.gb.spring_test.validators.ProductValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collector;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    public Page<ProductDto> find(Integer page, Integer minCost, Integer maxCost, String title) {
        Specification<Product> specification = Specification.where(null);
        if(minCost != null) {
            specification = specification.and(ProductSpecification.costGreaterOrElseThan(minCost));
        }
        if(maxCost != null) {
            specification = specification.and(ProductSpecification.costLessOrElseThan(maxCost));
        }
        if(title != null) {
            specification = specification.and(ProductSpecification.titleEquals(title));
        }

        Page<ProductDto> productDtos = productRepository.findAll(specification, PageRequest.of(page-1, 5)).map(ProductMapper.MAPPER::fromProduct);
        Page<Product> products = productRepository.findAll(specification, PageRequest.of(page-1, 5));
        return productDtos;
    }

    public ProductDto findById(Long id) {
        return productRepository.findById(id).map(ProductMapper.MAPPER::fromProduct).orElseThrow();
    }

    public ProductDto addProduct(ProductDto productDto) {
        productValidator.validate(productDto);
        productRepository.save(ProductMapper.MAPPER.toProduct(productDto));
        return productDto;
    }

    public void deleteProductById(Long id) {
        log.info("Product {} is delete", id);
        productRepository.deleteById(id);
    }

    @Transactional
    public ProductDto update(ProductDto productDto) {
        if(!productRepository.existsProductById(productDto.getId())) {
            throw new ValidateException(List.of("Продукта с таким id не существует"));
        }
        Product product = productRepository.findById(productDto.getId()).orElseThrow();
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
        return productDto;
    }

    @Transactional
    public void updateTitle(Long id, ProductDto productDto) {
        if(!productRepository.existsProductById(productDto.getId())) {
            throw new ValidateException(List.of("Продукта с таким id не существует"));
        }
        Product product = productRepository.findById(id).orElseThrow();
        product.setTitle(productDto.getTitle());
    }
}
