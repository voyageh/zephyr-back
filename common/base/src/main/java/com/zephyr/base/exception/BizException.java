package com.zephyr.base.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BizException extends RuntimeException {
    @NonNull
    private String errorMessage;
}
