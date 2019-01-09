package com.example.restapi.catalog.helpers;

import com.example.restapi.catalog.rawmodel.ResponceWrapper;
import com.example.restapi.catalog.rawmodel.ResultResponce;
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
    ResponceWrapper handleNotFoundException(com.example.restapi.catalog.exceptions.NotFoundException e) {

        return new ResponceWrapper(new ResultResponce(null, (e.getMessage())));
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public @ResponseBody
    ResponceWrapper handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException e) {

        return new ResponceWrapper(new ResultResponce(null, (e.getBindingResult()
                .getAllErrors()
                .listIterator()
                .next()
                .getDefaultMessage())));
    }

    ///TODO Надо проверить кажется это больше ненужно
    /// При неверноем типе поля будет выдаваться ошибка
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public @ResponseBody
    ResponceWrapper handleDeserializeException(com.fasterxml.jackson.databind.exc.InvalidFormatException e) {

        return new ResponceWrapper(new ResultResponce(null, "неверный тип данных"));
    }

}