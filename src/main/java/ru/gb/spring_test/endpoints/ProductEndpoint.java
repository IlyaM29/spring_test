package ru.gb.spring_test.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.gb.spring_test.servise.ProductService;
import ru.gb.spring_test.soapProduct.GetAllProductsRequest;
import ru.gb.spring_test.soapProduct.GetAllProductsResponse;
import ru.gb.spring_test.soapProduct.Product;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://www.mvg.com/spring/ws/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request){
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.findAllProducts().forEach(p -> {
            Product product = new Product();
            product.setTitle(p.getTitle());
            product.setCost(p.getCost());
            response.getProducts().add(product);
        });
        return response;
    }
}
