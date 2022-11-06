package ru.gb.spring_test.order.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring_test.order.dto.ProductDto;

@FeignClient(value = "Product", url = "http://localhost:8189/web-market-core/api/v1/products")
public interface ProductApi {

    @RequestMapping(method = RequestMethod.GET, value = "")
    Page<ProductDto> getAllProducts(@RequestParam(name = "page", defaultValue = "1") Integer page);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ProductDto findById(@PathVariable Long id);
}
