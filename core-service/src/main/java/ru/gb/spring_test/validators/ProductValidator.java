package ru.gb.spring_test.validators;

import org.springframework.stereotype.Component;
import ru.gb.spring_test.dto.ProductDto;
import ru.gb.spring_test.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {

    public void validate(ProductDto productDto) {

        List<String> errors = new ArrayList<>();

        if(productDto.getCost() < 1) {
            errors.add("Цена товара не может быть меньше 1");
        }
        if(productDto.getTitle().isBlank()) {
            errors.add("Название товара не может быть пустым");
        }

        if(!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
