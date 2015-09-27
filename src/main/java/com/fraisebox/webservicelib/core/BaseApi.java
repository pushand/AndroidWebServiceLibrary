package com.fraisebox.webservicelib.core;


import android.os.Handler;
import android.os.Looper;

import com.fraisebox.webservicelib.helpers.WebServiceHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by pushan on 23/07/15.
 */
public class BaseApi implements WebServiceAPI{

    public static final String REQUEST_TYPE_GET = "GET";
    public static final String REQUEST_TYPE_POST = "POST";
    private static final int CONNECTION_TIMEOUT_IN__TEN_SEC = 10;
    private static final int READ_TIMEOUT_IN_FIFTEEN_SEC = 15;
    private static final int SEC_TO_MILLSEC = 1000;
    public static final String APP_URL_ENCODED = "application/x-www-form-urlencoded";
    public static final String APP_JSON = "application/json";
    private final Gson gson;

    private String requestMethod;
    private String url;
    private HashMap<String,String> params = new HashMap<>();
    private HashMap<String, String> urlParams = new HashMap<>();
    private int connectionTimeoutInSeconds=CONNECTION_TIMEOUT_IN__TEN_SEC * SEC_TO_MILLSEC;
    private int readTimeoutInSeconds=READ_TIMEOUT_IN_FIFTEEN_SEC * SEC_TO_MILLSEC;
    private boolean doInput=true;
    private boolean doOutput=false;
    private String charset = "UTF-8";
    private String contentType = APP_URL_ENCODED;
    private String jsonPostBody;

    private WebServiceHelper.Callback callback;

    public BaseApi(String requestMethod, String url){
        this.setRequestMethod(requestMethod);
        this.setUrl(url);
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("MM/dd/yy HH:mm:ss");
        gson = builder.create();

    }

    public void addCallback(WebServiceHelper.Callback callback){
        this.callback = callback;
    }

    public void setConnectionTimeoutInSeconds(int connectionTimeoutInSeconds) {
        this.connectionTimeoutInSeconds = connectionTimeoutInSeconds*SEC_TO_MILLSEC;
    }

    public void setReadTimeoutInSeconds(int readTimeoutInSeconds) {
        this.readTimeoutInSeconds = readTimeoutInSeconds*SEC_TO_MILLSEC;
    }

    public void setDoInput(boolean doInput) {
        this.doInput = doInput;
    }


    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        doOutput = requestMethod.equals(REQUEST_TYPE_POST);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getConnectionTimeoutInSeconds() {
        return connectionTimeoutInSeconds;
    }

    public int getReadTimeoutInSeconds() {
        return readTimeoutInSeconds;
    }

    public boolean isDoInput() {
        return doInput;
    }

    public boolean isDoOutput() {
        return doOutput;
    }

    public void setDoOutput(boolean doOutput) {
        this.doOutput = doOutput;
    }

    public void addParams(String key, String value) {
        params.put(key, value);
    }

    public void addUrlParams(String key, String value) {
        urlParams.put(key, value);
    }

    public HashMap<String, String> getUrlParams() {
        return urlParams;
    }

    public HashMap<String, String> getParams(){
        return params;
    }


    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getJsonPostBody() {
        return jsonPostBody;
    }

    public void setJsonPostBody(Object jsonPostBody) {
        this.jsonPostBody = new Gson().toJson(jsonPostBody);
    }

    @Override
    public void response(final String jsonString, final int statusCode, final String errorMessage) {
        Type[] genericInterfaces = callback.getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                final Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(statusCode == 200 ){
                            String value = genericTypes[0].toString().split(" ")[1];
                            if(value.substring(value.lastIndexOf(".")+1).equals(" ")) {
                                callback.response(jsonString);
                            }else{
                                callback.response(gson.fromJson(jsonString, genericTypes[0]));
                            }
                        } else {
                            String value = genericTypes[1].toString().split(" ")[1];
                            if(value.substring(value.lastIndexOf(".")+1).equals("String")) {
                                callback.error(jsonString, errorMessage, statusCode);
                            }else {
                                callback.error(gson.fromJson(jsonString, genericTypes[1]), errorMessage, statusCode);
                            }
                        }
                    }
                });
            }
        }
    }


}
