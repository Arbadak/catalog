package com.example.restapi.catalog.controller;

import com.example.restapi.catalog.rawModel.ResponceWrapper;
import com.example.restapi.catalog.rawModel.ResultResponce;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *  Rest Controller Advice для обработки ошибок возникающих при валидации данных
 */

@RestControllerAdvice
public class MyResponseControllerAdvice implements ResponseBodyAdvice<ResponceWrapper> {


   //TODO Test, удалю в окончательной версии
    @Override
    public ResponceWrapper beforeBodyWrite(ResponceWrapper document, MethodParameter methodParam, MediaType mediaType,
                                           Class<? extends HttpMessageConverter<?>> converter, ServerHttpRequest request, ServerHttpResponse response) {
        //return new ResponceWrapper(new ResultResponce(null, "Hello from AAADVICE!!!"));
        return document;
    }
    @Override
    public boolean supports(MethodParameter methodParam, Class<? extends HttpMessageConverter<?>> converter) {
        return true;
    }

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