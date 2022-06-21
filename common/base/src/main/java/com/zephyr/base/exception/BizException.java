package com.zephyr.base.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class BizException extends RuntimeException {
    @NonNull
    private String errorMessage;
}
