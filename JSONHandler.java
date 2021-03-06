package com.example.abgomsale.iot;


import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

public class JSONHandler {

	public static String testJSONRequest(String server_URL_text, String method){
		// Creating a new session to a JSON-RPC 2.0 web service at a specified URL

		Log.d("Debug serverURL", server_URL_text);

		// The JSON-RPC 2.0 server URL
		URL serverURL = null;

		try {
			serverURL = new URL("http://"+server_URL_text);

		} catch (MalformedURLException e) {
		// handle exception...
			Log.d("Exception",e.getMessage());
		}

		// Create new JSON-RPC 2.0 client session
		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);

		Log.d("JSONHandler",mySession.toString());

		// Once the client session object is created, you can use to send a series
		// of JSON-RPC 2.0 requests and notifications to it.

		// Sending an example "getTime" request:
		// Construct new request

		int requestID = 0;
		JSONRPC2Request request = new JSONRPC2Request(method, requestID);

		// Send request
		JSONRPC2Response response = null;

		try {
			response = mySession.send(request);

		} catch (JSONRPC2SessionException e) {

		Log.e("error", e.getMessage().toString());
		// handle exception...
		}

		if(response!=null)
		Log.d("JSONHandler",response.toJSONString());
		else
		Log.d("JSONHandler","response is null");
		// Print response result / error
		if (response.indicatesSuccess())
			Log.d("debug", response.getResult().toString());
		else
			Log.e("error", response.getError().getMessage().toString());


		return response.getResult().toString();
	}

}
