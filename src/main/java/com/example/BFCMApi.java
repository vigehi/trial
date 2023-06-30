package com.example;
import java.util.concurrent.TimeUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONObject;
import org.json.JSONException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BFCMApi {
	
	public static void main(String[] args) throws JSONException {
		System.out.println("Starting Auth test");
		BFCMApi bfcmApi = new BFCMApi();

		//String sURL = "https://demo.dewcis.com/aua/dataserver";
		//String sURL = "https://portal.dewcis.com/hr/dataserver";
		String sURL = "http://localhost:9090/hr/dataserver";
		String userName = "root";
		String password = "baraza";

		JSONObject jData = new JSONObject();
		jData.put("STR_USER_NAME", userName);
		jData.put("STR_PASSWORD", password);
		jData.put("STR_PARTNER_TYPE", "Customer");
		jData.put("STR_PARTNER_CATEGORY", "");
		jData.put("STR_PARTNER_GROUP", "");


		JSONObject jResp = bfcmApi.makeCall(sURL, jData);
		System.out.println("Response: " + jResp.toString());
		
	}

	
	public JSONObject makeCall(String url, JSONObject jData) throws JSONException {
    JSONObject jResp = new JSONObject();

    try {
        System.out.println("BASE 2010 : " + url);
        System.out.println("BASE 2020 : " + jData);

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jData.toString(), mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String resp = response.body().string();
        System.out.println(resp);

       
    } catch(IOException ex) {
        System.out.println("IO Error : " + ex);
    }

    return jResp;
}
}