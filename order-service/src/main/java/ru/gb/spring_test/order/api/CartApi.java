package ru.gb.spring_test.order.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.gb.spring_test.order.dto.Cart;

@FeignClient(value = "Carts", url = "http://localhost:8187/web-market-cart/api/v1/carts")
public interface CartApi {

    @RequestMapping(method = RequestMethod.POST, value = "")
    Cart getCurrentCart(@RequestBody String cartName);

    @RequestMapping(method = RequestMethod.POST, value = "/clear")
    void clearCart(@RequestBody String cartName);
}
