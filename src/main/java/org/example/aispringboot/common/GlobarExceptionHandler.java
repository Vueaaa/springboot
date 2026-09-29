package org.example.aispringboot.common;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobarExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleValidationException(
            MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("参数校验失败");

        return Result.error(
                ResultCode.PARAM_INVALID.getCode(),
                message,
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception exception) {
        return Result.error(
                ResultCode.SYSTEM_ERROR.getCode(),
                ResultCode.SYSTEM_ERROR.getMsg(),
                null
        );
    }

    @ExceptionHandler(BusinessException.class)
    public Result<String> handleBusinessException(
            BusinessException exception) {

        ResultCode resultCode = exception.getResultCode();

        return Result.error(
                resultCode.getCode(),
                resultCode.getMsg(),
                null
        );
    }

}
