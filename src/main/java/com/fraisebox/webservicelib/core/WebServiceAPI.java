package com.fraisebox.webservicelib.core;

import org.json.JSONObject;

/**
 * Created by pushan on 17/07/15.
 */
public interface WebServiceAPI {

     void response(String jsonString, int statusCode, String errorMessage);

}
