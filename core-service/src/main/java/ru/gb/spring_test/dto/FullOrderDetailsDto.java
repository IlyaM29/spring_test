package ru.gb.spring_test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullOrderDetailsDto {

    private String username;
    private OrderDetailsDto orderDetailsDto;
    private String cartName;
}
