package com.harshalbenake.apiencryptionaes;

import android.util.ArrayMap;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * This class is used fto get and post service response to server using okhttp.
 */
public class OKHTTPService {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String mStrUrl="Url";
    public static final String mStrRequestJson="RequestJson";
    public static final String mStrRequestEncoded="RequestEncoded";
    public static final String mStrResponseEncoded="ResponseEncoded";
    public static final String mStrResponseJson="ResponseJson";


    public OKHTTPService() {
    }

    /**
     * gets Ok Http Service
     *
     * @param strUrl
     * @param strJson
     * @return
     */
    public static HashMap<String,String> requestACallToServer(String strUrl, String strJson) {
        HashMap<String,String> hashMap=new HashMap<String, String>();
        try {
            System.out.println("OkHttp get Request: " + strJson);
            String strEncodedRequest = EncryptionUtility.encrypt("YourKey",strJson, "YourKey");
            System.out.println("OkHttp get EncodedRequest: " + strEncodedRequest);
            RequestBody body = RequestBody.create(JSON, strEncodedRequest);
            Request request = new Request.Builder()
                    .url(strUrl)
                    .post(body)
                    .build();
            OkHttpClient.Builder builder = new OkHttpClient.Builder().protocols(Arrays.asList(Protocol.HTTP_1_1));
            builder.connectTimeout(3, TimeUnit.MINUTES)
                    .writeTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES);
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("HttpLogging", message);
                }
            });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .protocols(Arrays.asList(Protocol.HTTP_1_1))
                    .addInterceptor(logging)
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .writeTimeout(3, TimeUnit.MINUTES)
                    .readTimeout(3, TimeUnit.MINUTES)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            String strEncodedResponse = response.body().string();
            System.out.println("OkHttp get EncodedResponse: " + strEncodedResponse);
            String strResponse = EncryptionUtility.decrypt("YourKey", strEncodedResponse, "YourKey");
            System.out.println("OkHttp get Response: " + strResponse);
            hashMap.put(mStrUrl,strUrl);
            hashMap.put(mStrRequestJson,strJson);
            hashMap.put(mStrRequestEncoded,strEncodedRequest);
            hashMap.put(mStrResponseEncoded,strEncodedResponse);
            hashMap.put(mStrResponseJson,strResponse.replaceAll("\\r\\n", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }
}
