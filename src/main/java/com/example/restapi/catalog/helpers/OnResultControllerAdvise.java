package com.example.restapi.catalog.helpers;

import com.example.restapi.catalog.rawmodel.ResponceWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *  Rest Controller Advice для обработки ошибок возникающих при валидации данных
 */

@RestControllerAdvice
public class OnResultControllerAdvise implements ResponseBodyAdvice<Object> {


   //TODO Test, удалю в окончательной версии
    @Override
    public ResponceWrapper beforeBodyWrite(Object document, MethodParameter methodParam, MediaType mediaType,
                                           Class<? extends HttpMessageConverter<?>> converter, ServerHttpRequest request, ServerHttpResponse response) {
                return new ResponceWrapper (document);
    }
    /*
    public ResponceWrapper beforeBodyWrite(List document, MethodParameter methodParam, MediaType mediaType,
                                           Class<? extends HttpMessageConverter<?>> converter, ServerHttpRequest request, ServerHttpResponse response) {
     return new ResponceWrapper(document);
    }

     public ResponceWrapper beforeBodyWrite(RawOffice document, MethodParameter methodParam, MediaType mediaType,
                                            Class<? extends HttpMessageConverter<?>> converter, ServerHttpRequest request, ServerHttpResponse response) {
         return new ResponceWrapper(document);
     }
    public ResponceWrapper beforeBodyWrite(RawUser document, MethodParameter methodParam, MediaType mediaType,
                                           Class<? extends HttpMessageConverter<?>> converter, ServerHttpRequest request, ServerHttpResponse response) {
        return new ResponceWrapper(document);
    }
        public ResponceWrapper beforeBodyWrite(RawOrganization document, MethodParameter methodParam, MediaType mediaType,
                Class<? extends HttpMessageConverter<?>> converter, ServerHttpRequest request, ServerHttpResponse response) {
            return new ResponceWrapper(document);
    }
    */
    @Override
    public boolean supports(MethodParameter methodParam, Class<? extends HttpMessageConverter<?>> converter) {
        return true;
    }
}