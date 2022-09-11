package ru.gb.spring_test.converters;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.gb.spring_test.dto.ProductDto;
import ru.gb.spring_test.entities.Product;

@Mapper(componentModel = "spring")
//@Component
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDto productDto);

    @InheritConfiguration
    ProductDto fromProduct(Product product);
}
