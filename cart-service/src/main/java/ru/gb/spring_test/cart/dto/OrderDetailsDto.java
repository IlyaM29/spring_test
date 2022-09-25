package ru.gb.spring_test.cart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailsDto {
    private String phone;
    private String address;
}
