package com.github.evgenylizogubov.publicvoting.mapper.certificate;

import com.github.evgenylizogubov.publicvoting.controller.dto.certificate.CertificateDto;
import com.github.evgenylizogubov.publicvoting.mapper.BaseDtoToEntityMapper;
import com.github.evgenylizogubov.publicvoting.model.Certificate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CertificateDtoToCertificateMapper extends BaseDtoToEntityMapper<CertificateDto, Certificate> {
}
