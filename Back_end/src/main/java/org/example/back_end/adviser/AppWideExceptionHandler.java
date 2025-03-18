package org.example.back_end.adviser;

import org.example.back_end.util.ResponseUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseUtil exceptionHandler(Exception ex){
        return new ResponseUtil(
                500,"Internal server error",ex.getMessage()
        );
    }
}
