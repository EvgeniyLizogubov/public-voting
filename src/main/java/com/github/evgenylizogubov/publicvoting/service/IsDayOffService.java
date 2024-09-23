package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.error.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IsDayOffService {
    private final WebClient webClient;
    private final MessageSource messageSource;
    
    public IsDayOffServiceResponse getDateInfo(LocalDate date) {
        log.info("Check date=\"{}\" for weekend or holiday", date);
        Integer response = webClient.get()
                .uri("/" + date)
                .retrieve()
                .bodyToMono(String.class)
                .mapNotNull(Integer::parseInt)
                .block();
        
        if (response == null) {
            log.error("IsDayOff service not available");
            throw new ExternalServiceException("IsDayOff service not available");
        }
        
        int code = response;
        String codeDescription = messageSource.getMessage(
                Integer.toString(code), null, LocaleContextHolder.getLocale()
        );
        
        if (!List.of(0, 1).contains(code)) {
            throw new ExternalServiceException("IsDayOff error: " + codeDescription);
        }
        
        log.info("Date=\"{}\" - {}", date, codeDescription);
        
        IsDayOffServiceResponse isDayOffServiceResponse = new IsDayOffServiceResponse();
        isDayOffServiceResponse.setDate(date);
        isDayOffServiceResponse.setWorkingDay(code == 0);
        isDayOffServiceResponse.setCode(code);
        isDayOffServiceResponse.setMessage(codeDescription);
        return isDayOffServiceResponse;
    }
}
