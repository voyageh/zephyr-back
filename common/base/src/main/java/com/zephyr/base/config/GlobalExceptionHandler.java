package com.zephyr.base.config;

import com.zephyr.base.constant.ResultDTO;
import com.zephyr.base.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zephyr.base.constant.ResultCode.RUNTIME_EXCEPTION;
import static com.zephyr.base.constant.ResultCode.VALIDATION_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMedhodArgsValidata(MethodArgumentNotValidException exception) {
        List<Map<String, String>> mapList = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            Map<String, String> errorMap = new HashMap<>();
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put("fieldName", fieldName);
            errorMap.put("errorMessage", errorMessage);
            mapList.add(errorMap);
        });

        return new ResponseEntity<>(new ResultDTO<>(VALIDATION_ERROR.getCode(), mapList), HttpStatus.OK);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<?> handleValidationException(BindException exception) {
        List<Map<String, String>> mapList = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            Map<String, String> errorMap = new HashMap<>();
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put("fieldName", fieldName);
            errorMap.put("errorMessage", errorMessage);
            mapList.add(errorMap);
        });
        return new ResponseEntity<>(new ResultDTO<>(VALIDATION_ERROR.getCode(), mapList), HttpStatus.OK);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationException(ConstraintViolationException exception) {
        List<Map<String, String>> mapList = new ArrayList<>();
        exception.getConstraintViolations().forEach((error) -> {
            Map<String, String> errorMap = new HashMap<>();
            String errorMessage = error.getMessage();
            String path = error.getPropertyPath().toString();
            String fieldName = path.substring(path.lastIndexOf(".") + 1);
            errorMap.put("fieldName", fieldName);
            errorMap.put("errorMessage", errorMessage);
            mapList.add(errorMap);
        });
        return new ResponseEntity<>(new ResultDTO<>(VALIDATION_ERROR.getCode(), mapList), HttpStatus.OK);
    }

    @ExceptionHandler(value = BizException.class)
    public ResponseEntity<?> handleValidationException(BizException e) {
        log.error("ValidationException：", e);
        String errorMessage = e.getErrorMessage();
        return new ResponseEntity<>(new ResultDTO<>(VALIDATION_ERROR.getCode(), errorMessage), HttpStatus.OK);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Exception：", e);
        return new ResponseEntity<>(new ResultDTO<>(RUNTIME_EXCEPTION.getCode(), e.getMessage()), HttpStatus.OK);
    }
}
