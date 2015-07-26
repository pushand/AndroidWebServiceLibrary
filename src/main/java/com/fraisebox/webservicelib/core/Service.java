package com.fraisebox.webservicelib.core;

/**
 * Created by pushan on 17/07/15.
 */
public abstract class Service {

    protected WebServiceAPI webServiceAPI;

    protected Service(WebServiceAPI webServiceAPI){
        this.webServiceAPI = webServiceAPI;
    }

    public abstract void makeCall();

}
