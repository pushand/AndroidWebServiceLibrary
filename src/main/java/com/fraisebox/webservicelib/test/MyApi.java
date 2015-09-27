package com.fraisebox.webservicelib.test;


import com.fraisebox.webservicelib.core.BaseApi;

/**
 * Created by pushan on 17/07/15.
 */
public class MyApi extends BaseApi {

    public MyApi() {
        super(BaseApi.REQUEST_TYPE_POST,"http://res-produssct.tinyowl.com/user/login.json");
        addParams("email", "9987915009");
        addParams("password", "pushan123");
        addParams("agent", "ANDROID");
    }

}
