package com.github.evgenylizogubov.publicvoting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class IsDayOffService {
    private final WebClient webClient;
    
    @Value("${a}")
    public String s;
    
    public String checkDateForWeekendOrHoliday(LocalDate date) {
        return webClient.get()
                .uri("/" + date)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
