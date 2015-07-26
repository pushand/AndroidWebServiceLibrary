package com.fraisebox.webservicelib.helpers;

import android.content.Context;

import com.fraisebox.webservicelib.core.BaseApi;
import com.fraisebox.webservicelib.core.Service;
import com.fraisebox.webservicelib.core.WebService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pushan on 24/07/15.
 */
public class WebServiceHelper {

    private static WebServiceHelper webServiceHelper;
    private ExecutorService executorService;

    private WebServiceHelper(){

    }

    public interface Callback<R,E>{

        /** Called only when response status code is 200
         * @param r Response bean to be mapper with JSON response */
        void response(R r);

        /** Called when response status code is not 200
         * @param e Error bean to be mapped with JSON respons
         * @param statusCode status code received in web service call
         * @param error error trace */
        void error(E e, String error, int statusCode);


    }

    public static void makeWebServiceCall(BaseApi baseApi, Callback callback){
        baseApi.addCallback(callback);
        if(isAvailable()) {
            webServiceHelper.executorService.execute(new ApiTask(baseApi));
        }else baseApi.response(null,0,"Internet not available");
    }

    public static boolean isAvailable(){
        if(webServiceHelper == null){
            webServiceHelper = new WebServiceHelper();
            webServiceHelper.executorService = Executors.newCachedThreadPool();
        }
        return webServiceHelper != null;
    }

    private static class ApiTask implements Runnable{

        private BaseApi baseApi;

        public ApiTask(BaseApi baseApi){
            this.baseApi = baseApi;
        }

        @Override
        public void run() {
            Service service = new WebService(baseApi);
            service.makeCall();
        }
    }




}
