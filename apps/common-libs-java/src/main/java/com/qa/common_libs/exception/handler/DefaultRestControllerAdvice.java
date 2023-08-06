package com.qa.common_libs.exception.handler;

import com.qa.common_libs.exception.AbstractUserErrorExceptionBase;
import com.qa.common_libs.exception.ErrorData;
import com.qa.common_libs.exception.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class DefaultRestControllerAdvice {

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorData handleException(Exception e) {
        log.error("Unhandled Error", e);
        return new InternalServerErrorException("Unexpected error happened.", e).makeErrorData();
    }

    @ExceptionHandler({AbstractUserErrorExceptionBase.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorData handleUserErrorException(AbstractUserErrorExceptionBase e) {
        log.info("User Error", e);
        return e.makeErrorData();
    }
}
