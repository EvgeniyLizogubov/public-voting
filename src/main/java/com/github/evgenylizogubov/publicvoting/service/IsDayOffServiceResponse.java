package com.github.evgenylizogubov.publicvoting.service;

import lombok.Data;

@Data
public class IsDayOffServiceResponse {
    private String message;
    private int code;
    private boolean isWorkingDay;
}
