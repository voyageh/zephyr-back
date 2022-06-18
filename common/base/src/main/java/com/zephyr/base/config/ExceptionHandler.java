package com.zephyr.base.config;

import com.zephyr.base.constant.ReturnCode;
import com.zephyr.base.constant.ReturnResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zephyr.base.constant.ReturnCode.RUNTIME_EXCEPTION;
import static com.zephyr.base.constant.ReturnCode.VALIDATION_ERROR;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
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

        return new ResponseEntity<>(new ReturnResultDTO<>(VALIDATION_ERROR.getCode(), mapList), HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = BindException.class)
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
        return new ResponseEntity<>(new ReturnResultDTO<>(VALIDATION_ERROR.getCode(), mapList), HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ConstraintViolationException.class)
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
        return new ResponseEntity<>(new ReturnResultDTO<>(VALIDATION_ERROR.getCode(), mapList), HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        ReturnResultDTO returnResultDTO = new ReturnResultDTO();
        String errorName = exception.getClass().getName();
        errorName = errorName.substring(errorName.lastIndexOf(".") + 1);
        if (ReturnCode.contains(errorName)) {
            returnResultDTO.setReturnCode(ReturnCode.valueOf(errorName).getCode());
            String errorMessage = messageSource.getMessage(ReturnCode.valueOf(errorName).getMsg(), null,
                    ReturnCode.valueOf(errorName).getMsg(), LocaleContextHolder.getLocale());
            returnResultDTO.setData(errorMessage);
        } else {
            returnResultDTO.setReturnCode(RUNTIME_EXCEPTION.getCode());
        }
        return new ResponseEntity<>(returnResultDTO, HttpStatus.OK);
    }
}
