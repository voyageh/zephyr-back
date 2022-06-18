package com.zephyr.base.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnResultDTO<T> implements java.io.Serializable {
    private String returnCode;

    private T data;
}
