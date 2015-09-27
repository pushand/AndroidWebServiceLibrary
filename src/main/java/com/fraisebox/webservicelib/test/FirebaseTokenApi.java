package com.fraisebox.webservicelib.test;

import com.fraisebox.webservicelib.core.BaseApi;
import com.fraisebox.webservicelib.core.WebServiceAPI;

import org.json.JSONObject;

/**
 * Created by pushan on 24/07/15.
 */
public class FirebaseTokenApi extends BaseApi{

    public FirebaseTokenApi() {
        super(BaseApi.REQUEST_TYPE_GET, "http://res-product.tinyowl.com/restaurant/restaurant_product/fetch_orders");
        addParams("session_token", "219582vjEek3zpFKQW6dokLzpeM6j7WMGW89EWDaQeChPEtpymxXNscF01gGGj8Wnw07D3nvpBVQoeDY9HSk6uDEYThgGG");
        addParams("type","NEW");
    }


}
