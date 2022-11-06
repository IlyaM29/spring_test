package ru.gb.spring_test.cart.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.gb.spring_test.cart.dto.ProductDto;

@FeignClient(name = "Products", url = "http://localhost:8189/web-market-core/api/v1/products")
public interface ProductApi {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ProductDto getById(@PathVariable Long id);
}
