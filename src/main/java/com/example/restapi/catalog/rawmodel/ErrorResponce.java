package com.example.restapi.catalog.rawmodel;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Обьект результата работы для методов в которых произошла ошибка
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponce {

        private String error;

    public ErrorResponce() {
            }
    public ErrorResponce(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

