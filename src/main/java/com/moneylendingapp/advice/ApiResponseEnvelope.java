package com.moneylendingapp.advice;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiResponseEnvelope {
    private boolean successStatus;
    private LocalDateTime timeStamp;
    private Object result;
    private List<Object> errorMessage;
}
