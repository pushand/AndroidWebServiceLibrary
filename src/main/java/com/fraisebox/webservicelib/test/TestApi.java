package com.fraisebox.webservicelib.test;

import com.fraisebox.webservicelib.core.Service;
import com.fraisebox.webservicelib.core.WebService;
import com.fraisebox.webservicelib.helpers.WebServiceHelper;

import junit.framework.TestCase;

/**
 * Created by pushan on 24/07/15.
 */
public class TestApi extends TestCase  {

    public void testLoginAPI(){
        WebServiceHelper.makeWebServiceCall(new MyApi(), new WebServiceHelper.Callback() {
            @Override
            public void response(Object o) {

            }

            @Override
            public void error(Object o, String error, int statusCode) {

            }
        });
    }

    public void testFirebaseTokeuAPI(){
        Service service = new WebService(new FirebaseTokenApi());
        service.makeCall();
    }



}
