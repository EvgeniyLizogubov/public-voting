package com.github.evgenylizogubov.publicvoting.controller;

import com.github.evgenylizogubov.publicvoting.controller.dto.certificate.CertificateDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.certificate.CertificateResponse;
import com.github.evgenylizogubov.publicvoting.mapper.certificate.CertificateDtoToCertificateResponseMapper;
import com.github.evgenylizogubov.publicvoting.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = CertificateController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class CertificateController {
    static final String REST_URL = "/api/certificates";
    
    private final CertificateService certificateService;
    private final CertificateDtoToCertificateResponseMapper certificateDtoToCertificateResponseMapper;
    
    @GetMapping
    public List<CertificateResponse> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll by user {}", authUser.getUserDto());
        List<CertificateDto> certificates = certificateService.getAllByOwner(authUser.getUserDto());
        return certificateDtoToCertificateResponseMapper.toResponseList(certificates);
    }
    
    @GetMapping(value = "/by-year", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getCertificateByYear(@RequestParam int year, @AuthenticationPrincipal AuthUser authUser) {
        log.info("get certificate with year={} by user {}", year, authUser.getUserDto());
        return certificateService.getCertificate(authUser.getUserDto(), year);
    }
}

