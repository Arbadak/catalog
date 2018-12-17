package com.example.restapi.catalog.rawModel;

import com.fasterxml.jackson.annotation.JsonInclude;

/** В случае удачного реультата конструктор с result, при ошибке игнорим result в null, пишем в error
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class resultResponce {

    private String result;
    private String error;

    public resultResponce(String result) {
        this.result = result;
    }

    public resultResponce(String result, String error) {
        this.result = result;
        this.error = error;
    }

    public resultResponce() {
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

