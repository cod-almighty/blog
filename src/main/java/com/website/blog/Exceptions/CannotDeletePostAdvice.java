package com.website.blog.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CannotDeletePostAdvice {

    @ResponseBody
    @ExceptionHandler(CannotDeletePostException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String cannotDeletePostHandler(CannotDeletePostException exception) {
        return exception.getMessage();
    }
}
