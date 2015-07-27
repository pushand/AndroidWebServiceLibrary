package com.fraisebox.webservicelib.core;

import android.net.Uri;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by pushan on 17/07/15.
 */
public class WebService extends Service {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private int statusCode;
    private String errorMessage;

    public WebService(WebServiceAPI webServiceAPI) {
        super(webServiceAPI);
    }

    @Override
    public void makeCall() {
       webServiceAPI.response(call(),statusCode,errorMessage);
    }

    private String call() {
        HttpURLConnection urlConnection = null;
        try {
            BaseApi baseApi = (BaseApi) webServiceAPI;
            URL url = getUrl(baseApi);
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(baseApi.getReadTimeoutInSeconds());
                urlConnection.setConnectTimeout(baseApi.getConnectionTimeoutInSeconds());
                urlConnection.setRequestMethod(baseApi.getRequestMethod());
                urlConnection.setDoInput(baseApi.isDoInput());
                urlConnection.setDoOutput(baseApi.isDoOutput());
                urlConnection.setRequestProperty("Accept-Charset", baseApi.getCharset());
                urlConnection.setRequestProperty("Content-Type", baseApi.getContentType() + ";charset=" + baseApi.getCharset());
                if (baseApi.getRequestMethod().equals(BaseApi.REQUEST_TYPE_POST)) {
                    Uri.Builder builder = new Uri.Builder();
                    for (Map.Entry<String, String> entry : baseApi.getParams().entrySet()) {
                        builder.appendQueryParameter(entry.getKey(), entry.getValue());
                    }
                    String query = builder.build().getEncodedQuery();
                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, baseApi.getCharset()));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();
                    urlConnection.connect();
                }
                statusCode = urlConnection.getResponseCode();
                return readStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                errorMessage = e.getMessage();
                return readStream(urlConnection.getErrorStream());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }catch(MalformedURLException e){
            errorMessage = "MalformedURLException "+" "+e.getMessage();
            return null;
        }

    }

    private URL getUrl(BaseApi baseApi) throws MalformedURLException {
        if (baseApi.getRequestMethod().equals(BaseApi.REQUEST_TYPE_GET)) {
            Uri.Builder builder = Uri.parse(baseApi.getUrl()).buildUpon();
            for (Map.Entry<String, String> entry : baseApi.getParams().entrySet()) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            return new URL(builder.build().toString());
        } else return new URL(baseApi.getUrl());
    }

    private String readStream(InputStream inputStream) {
        if(inputStream!=null) {
            StringWriter writer = new StringWriter();
            try {
                copy(inputStream, writer);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return writer.toString();
        } else{
            return null;
        }
    }

    private void copy(InputStream input, Writer output) throws IOException {
        InputStreamReader in = new InputStreamReader(input);
        copy(in, output);
    }

    private int copy(Reader input, Writer output) throws IOException {
        return (int) copyLarge(input, output);
    }

    private long copyLarge(Reader input, Writer output) throws IOException {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }


}
