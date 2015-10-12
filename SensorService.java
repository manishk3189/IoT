package com.example.abgomsale.iot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.Dispatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SensorService extends Service {

    private static final String TAG = "SensorService";

    static final int port = 9999;

    boolean isRunning;


    public static class Handler extends Thread {

        private boolean isRunning  = false;
        Socket socket;

        BufferedReader in;
        PrintWriter out;
        Dispatcher dispatcher;

        public Handler(Socket socket) {

            this.socket = socket;

            this.dispatcher = new Dispatcher();
            dispatcher.register(new JSONRequestHandler.NotifyHandler());


        }

        public void run() {

            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                String line = in.readLine();
                StringBuilder sb = new StringBuilder();
                sb.append("" + line);
                boolean isPost = line.startsWith("POST");
                int contentLength = 0;
                while (!(line = in.readLine()).equals("")) {
                    sb.append('\n' + line);
                    if (isPost) {
                        final String contentHeader = "Content Length: ";
                        if (line.startsWith(contentHeader)) {
                            contentLength = Integer.parseInt(line.substring(contentHeader.length()));
                        }
                    }

                }
                StringBuilder body = new StringBuilder();
                if (isPost) {
                    int c = 0;
                    for (int i = 0; i < contentLength; i++) {
                        c = in.read();
                        body.append((char) c);
                    }
                }

                Log.d(TAG, "Body :" + body.toString());
                JSONRPC2Request req = JSONRPC2Request.parse(body.toString());
                JSONRPC2Response resp = dispatcher.process(req, null);

                out.write("HTTP/1.1 200 OK\r\n");
                out.write("Content-Type: application/json\r\n");
                out.write("\r\n");
                out.write(resp.toJSONString());
                out.flush();
                out.close();
                socket.close();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } finally {
                try {

                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }



            }
        }




    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");
        try {

        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
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

                ServerSocket listener = null;
                try {
                    listener = new ServerSocket(port);
                    while(true) {
                        new Handler(listener.accept()).start();
                    }

                } catch (Exception e) {

                }
                finally {
                    try {
                        listener.close();
                    } catch (Exception e) {

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

        Log.d(TAG,"I am dying");
        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }
}
