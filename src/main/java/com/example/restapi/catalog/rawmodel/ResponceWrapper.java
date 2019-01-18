package com.example.restapi.catalog.rawmodel;

/**
 * Обертка для отдаваемых данных, data{XXXXX}
 */
public class ResponceWrapper {

    public Object data;

    public ResponceWrapper(Object data) {
        this.data = data;
    }

    /*public ResponceWrapper() {
    }*/

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
