package com.github.evgenylizogubov.publicvoting.service;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IsDayOffServiceResponse {
    private String message;
    private int code;
    private boolean isWorkingDay;
    private LocalDate date;
}
