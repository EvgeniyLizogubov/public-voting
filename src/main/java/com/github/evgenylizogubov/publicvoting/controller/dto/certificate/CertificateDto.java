package com.github.evgenylizogubov.publicvoting.controller.dto.certificate;

import com.github.evgenylizogubov.publicvoting.controller.dto.BaseDto;
import com.github.evgenylizogubov.publicvoting.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateDto extends BaseDto {
    private int year;
    private User owner;
}
