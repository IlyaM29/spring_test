package ru.gb.spring_test.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.spring_test.statistic.AppLoggingAOP;
import ru.gb.spring_test.statistic.Statistic;

@RestController
@RequestMapping("/api/v1/statistic")
@RequiredArgsConstructor
public class AOP_Controller {

    private final AppLoggingAOP appLoggingAOP;

    @GetMapping
    public Statistic getStatistic() {
        return appLoggingAOP.getStatistic();
    }
}
