package com.example.abgomsale.iot;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by abgomsale on 10/8/15.
 */
public class SendJSONRequest extends AsyncTask<String, String, String> {
    String response_txt;
    String callType;
    String TAG = "SendJSONRequest";

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        Log.d(TAG, type);
        String serverURL_text = "10.0.1.6:8080";
        String request_method = type;//getResources().getString(R.string.getTemp);
/*            String serverURL_text = et_server_url.getText().toString();
            String request_method = et_requst_method.getText().toString();*/
        Log.d(TAG + " URL", serverURL_text);
        Log.d(TAG + " method", request_method);

        callType = type;


        response_txt = JSONHandler.testJSONRequest(serverURL_text, request_method);

        return response_txt;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(String result) {
        Log.d("debug", result);
        Log.d("debug", response_txt);
        Log.d(TAG, "Temp : " + response_txt);

        if(callType.contains("getTemp"))
            MainActivity.temperature = response_txt;
        else
            MainActivity.ambient = response_txt;

    }

}