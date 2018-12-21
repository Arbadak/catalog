package com.example.restapi.catalog.rawModel;

/**
 * Обертка для отдаваемых данных, data{XXXXX} крокодил должен быть красиво упакован
 */
public class ResponceWrapper {

    public Object data;

    public ResponceWrapper(Object data) {
        this.data = data;
    }

    public ResponceWrapper() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
