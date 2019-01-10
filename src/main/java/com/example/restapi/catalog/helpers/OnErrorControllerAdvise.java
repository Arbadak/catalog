package com.example.restapi.catalog.helpers;

import com.example.restapi.catalog.exceptions.NotFoundException;
import com.example.restapi.catalog.rawmodel.ErrorResponce;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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
    ErrorResponce handleNotFoundException(NotFoundException e) {

        return new ErrorResponce (e.getMessage());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public @ResponseBody
    ErrorResponce handleValidationException(MethodArgumentNotValidException e) {

        return new ErrorResponce(e.getBindingResult()
                .getAllErrors()
                .listIterator()
                .next()
                .getDefaultMessage());
    }

    ///TODO Надо проверить кажется это больше ненужно
    /// При неверноем типе поля будет выдаваться ошибка
    @ExceptionHandler(com.fasterxml.jackson.databind.exc.InvalidFormatException.class)
    public @ResponseBody
    ErrorResponce handleDeserializeException(InvalidFormatException e) {

        return new ErrorResponce("неверный тип данных");
    }

}