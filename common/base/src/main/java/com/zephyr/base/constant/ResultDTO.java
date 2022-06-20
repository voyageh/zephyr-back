package com.zephyr.base.constant;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO<T> implements java.io.Serializable {
    private String code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private String message;

    public ResultDTO(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
