package com.example.restapi.catalog.helpers;

public class LoggingController implements LoggingControllerMBean {

    private boolean EnableLogging;
    private boolean ShowReturnObjects;

    public boolean isEnableLogging() {
        return EnableLogging;
    }

    public void setEnableLogging(boolean enableLogging) {
        EnableLogging = enableLogging;
    }

    public boolean isShowReturnObjects() {
        return ShowReturnObjects;
    }

    public void setShowReturnObjects(boolean showReturnObjects) {
        ShowReturnObjects = showReturnObjects;
    }
}
