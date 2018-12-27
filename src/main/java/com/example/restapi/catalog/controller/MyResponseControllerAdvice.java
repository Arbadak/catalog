package com.example.restapi.catalog.controller;

/*
import com.example.restapi.catalog.rawModel.RawOffice;
import com.example.restapi.catalog.rawModel.ResponceWrapper;
import com.example.restapi.catalog.rawModel.resultResponce;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

//@RestControllerAdvice(assignableTypes = OrganizationController.class)
@RestControllerAdvice
public class MyResponseControllerAdvice implements ResponseBodyAdvice<ResponceWrapper>{

    @Override
    public ResponceWrapper beforeBodyWrite(ResponceWrapper document, MethodParameter methodParam, MediaType mediaType,
                                                      Class<? extends HttpMessageConverter<?>> converter, ServerHttpRequest request, ServerHttpResponse response) {

        //modify your document here


        //return new ResponceWrapper(new resultResponce(null, "Hello from AAADVICE!!!"));
        return document;
    }








    @Override
    public boolean supports(MethodParameter methodParam, Class<? extends HttpMessageConverter<?>> converter) {


            return true;
        }


}*/