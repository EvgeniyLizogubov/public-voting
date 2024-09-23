package com.github.evgenylizogubov.publicvoting.mapper.certificate;

import com.github.evgenylizogubov.publicvoting.controller.dto.certificate.CertificateDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.certificate.CertificateResponse;
import com.github.evgenylizogubov.publicvoting.mapper.BaseDtoToResponseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CertificateDtoToCertificateResponseMapper extends BaseDtoToResponseMapper<CertificateDto, CertificateResponse> {
}
