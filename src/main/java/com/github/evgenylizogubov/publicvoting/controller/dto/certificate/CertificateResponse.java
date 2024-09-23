package com.github.evgenylizogubov.publicvoting.controller.dto.certificate;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Value
public class CertificateResponse extends BaseDto {
    private final int year;
}
