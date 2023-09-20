package com.zzb.shopping.config;


import com.zzb.shopping.common.util.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * validation
 */

@RestControllerAdvice(basePackages = {"com.zzb"})
@Slf4j
public class ValidationExceptionHandler {
    public ValidationExceptionHandler() {
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResultModel<?> validationErrorHandler(MethodArgumentNotValidException e) {
        List<String> errorInfo = e
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        log.error(errorInfo.toString());
        return new ResultModel(401, errorInfo.toString());
    }


    @ExceptionHandler({ConstraintViolationException.class})
    public ResultModel validationParamErrorHandler(ConstraintViolationException e) {
        List<String> errorInfo = e
                .getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        log.error(errorInfo.toString());
        return new ResultModel(402, errorInfo.toString());
    }

}