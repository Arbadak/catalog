package com.example.restapi.catalog.helpers;

import com.example.restapi.catalog.exceptions.NotFoundException;
import com.example.restapi.catalog.rawmodel.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *  Rest Controller Advice для обработки ошибок возникающих при валидации данных
 */

@RestControllerAdvice
public class OnErrorControllerAdvise {

    @ExceptionHandler(com.example.restapi.catalog.exceptions.NotFoundException.class)
    public @ResponseBody
    ErrorResponse handleNotFoundException(NotFoundException e) {

        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public @ResponseBody
    ErrorResponse handleValidationException(MethodArgumentNotValidException e) {

        return new ErrorResponse(e.getBindingResult()
                .getAllErrors()
                .listIterator()
                .next()
                .getDefaultMessage());
    }


    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public @ResponseBody
    ErrorResponse handleDeserializeException(InvalidFormatException e) {

        return new ErrorResponse("неверный тип данных");
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public @ResponseBody
    ErrorResponse handleHtpMessageNotReadableException(HttpMessageNotReadableException e) {

        return new ErrorResponse("ошибка в формате запроса");
    }
}