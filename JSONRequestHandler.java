package com.example.abgomsale.iot;

import android.util.Log;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;
import com.thetransactioncompany.jsonrpc2.server.RequestHandler;

import java.net.InetAddress;

/**
 * Created by abgomsale on 10/11/15.
 */
public class JSONRequestHandler {

    public static class NotifyHandler implements RequestHandler {
        private static String TAG = "NotifyHandler";

        public String[] handledRequests(){
            return new String[] {"notification"};

        }

        public JSONRPC2Response process(JSONRPC2Request request, MessageContext msgCtx) {
            String hostName = "unknown";
            try{
                hostName = InetAddress.getLocalHost().getHostName();

            }
            catch (Exception e) {
                Log.e(TAG,e.getMessage());
            }
            if(request.getMethod().equals("notification")) {
                String params = request.getPositionalParams().get(0).toString();

                Log.d(TAG,"Received :"+params);

                return new JSONRPC2Response("OK",request.getID());
            }

            return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND,request.getID());

        }


    }
}
