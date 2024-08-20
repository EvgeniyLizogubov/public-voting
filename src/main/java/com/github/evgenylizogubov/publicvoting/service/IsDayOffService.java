package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.error.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class IsDayOffService {
    private final WebClient webClient;
    private final MessageSource messageSource;
    
    public boolean checkWorkingDay(LocalDate date) {
        log.info("Check date=\"{}\" for weekend or holiday", date);
        String response = webClient.get()
                .uri("/" + date)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        
        if (response == null) {
            log.error("IsDayOff service not available");
            throw new ExternalServiceException("IsDayOff service not available");
        }
        
        int code = Integer.parseInt(response);
        String codeDescription = messageSource.getMessage(
                Integer.toString(code), null, LocaleContextHolder.getLocale()
        );
        
        if (code > 1) {
            throw new ExternalServiceException("IsDayOff error: " + codeDescription);
        }
        
        log.info("Date=\"{}\" - {}", date, codeDescription);
        return code == 0;
    }
}
