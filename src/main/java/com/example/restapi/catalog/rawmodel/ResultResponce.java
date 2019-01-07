package com.example.restapi.catalog.rawmodel;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Обьект результата работы для методов которые не возвращают данные (add, update) в случае успеха заполняется поля result, в случае ошибки заполняется поле error
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponce {

    private String result;
    private String error;

    public ResultResponce(String result) {
        this.result = result;
    }

    public ResultResponce(String result, String error) {
        this.result = result;
        this.error = error;
    }

    public ResultResponce() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

