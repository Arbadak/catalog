package com.example.restapi.catalog.rawmodel;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Обьект результата работы для методов которые не возвращают данные (add, update) в случае успеха заполняется поля result.
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse {

    private String result;

    public ResultResponse() {
    }

    public ResultResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

