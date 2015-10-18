package com.example.abgomsale.iot;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MySensorService extends Service {

    private static final String TAG = "SensorService";

    static final int port = 9999;

    public static boolean isRunning;
    Context ctx;
    static double previous;
    double current;
    public static String MY_ACTION = "MY_ACTION";


    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
        try {
            ctx = this;
            Log.d(TAG, "Sending the request");

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = "";
                String previous_value = "";


                Log.d(TAG,"I am running");

                    while (true) {
                       try{
                           Thread.sleep(2000);

                    } catch (Exception e) {
                           Log.d(TAG,"Exception thrown by service");

                       }
                        response = JSONHandler.testJSONRequest("10.0.1.6:8080", "getNotification");
                        String[] res = response.split(" ");

                        current = Double.parseDouble(res[1]);
                        if(Math.abs(current - CategoryDetails.previousTempValue) > 2) {
                            if(current > 0 && current < 120) {

                                CategoryDetails.sensorResult.add(response);
                                CategoryDetails.previousTempValue = current;
                                Log.d(TAG, "Difference greater than 2");
                            }
                        }

                        if(!CategoryDetails.initial_blindState.equalsIgnoreCase(res[3])) {

                            Intent in = new Intent();
                            Bundle b = new Bundle();

                            in.setAction(MY_ACTION);

                            b.putString("NEW_BLIND", res[3]);
                            b.putString("OLD_BLIND", CategoryDetails.initial_blindState);
                            in.putExtras(b);
                            sendBroadcast(in);
                            CategoryDetails.initial_blindState = res[3];
                        }

                }



            }
        }).start();

        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        Log.d(TAG, "I am dying");
        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }
}
