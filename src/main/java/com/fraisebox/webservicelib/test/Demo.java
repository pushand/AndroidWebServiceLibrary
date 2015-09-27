package com.fraisebox.webservicelib.test;


import com.fraisebox.webservicelib.core.Service;
import com.fraisebox.webservicelib.core.WebService;

/**
 * Created by pushan on 17/07/15.
 */
public class Demo {

    public static void main(String args[]){
        Service service = new WebService(new MyApi());
        service.makeCall();
    }

}
