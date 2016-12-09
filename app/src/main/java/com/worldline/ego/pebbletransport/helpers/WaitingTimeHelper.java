package com.worldline.ego.pebbletransport.helpers;

import android.util.Log;

import com.worldline.ego.pebbletransport.pojo.ItiStop;
import com.worldline.ego.pebbletransport.pojo.WaitingTime;
import com.worldline.ego.pebbletransport.pojo.WaitingTimeStop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

import static com.worldline.ego.pebbletransport.helpers.Constants.URI_STREAM_GET_WAITING_TIMES;
import static com.worldline.ego.pebbletransport.helpers.Constants.URL_STREAM_GET_CLOSE_STOPS;

/**
 * Created by a143210 on 9/12/2016.
 */

public class WaitingTimeHelper {
    // Proxy stuff
    private static final boolean USE_PROXY = false;
    private static final String PROXY_HOST = "bcproxynew.extsec.banksys.be";
    private static final int PROXY_PORT = 8080;
    private static final boolean USE_PROXY_AUTHENTICATION = false;
    private static final String PROXY_USERNAME = "training10";
    private static final String PROXY_PASSWORD = "Student10/";
    private static final String msg = "WaitingTimeHelper";

    private List nbStops=null;
    private float latitude = 0;
    private float longitude = 0;

    public static WaitingTimeStop getWaitingTimes(String lineNumber, String mode, String iti,
                                              int haltId) {

        int rnd = 768258427;

        //TODO. Get this from settings
        String lang="fr";

        Log.d(msg, "Getting nearby stops list");

        String httpUrlString = URI_STREAM_GET_WAITING_TIMES+"&line="+lineNumber+"&mode="+mode+"&iti="+iti+"&halt="+haltId+"&lang="+lang+"&rnd="+rnd;
        try {
            final HttpURLConnection connection = getHTTPUrlConnection(httpUrlString);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Host", "m.stib.be");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "okhttp/3.4.1");
            connection.setRequestProperty("Content-Type", "application/json");

            final int responseCode = connection.getResponseCode();

            if(responseCode == 200) { //OK
                String type = connection.getContentType();
                Log.d(msg, "Content Type = "+type);
                String encoding = connection.getContentEncoding();
                Log.d(msg, "Content Encoding = "+encoding);
                if (encoding.equals("gzip")) {
                    //dumpContent(connection);
                    return WaitingTimeXmlParser.parse(new GZIPInputStream(connection.getInputStream()));
                } else {
                    return WaitingTimeXmlParser.parse(connection.getInputStream());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(msg, "Failed to get nearby stops list");
        }
        return null;
    }

    private static HttpURLConnection getHTTPUrlConnection(String url) throws Exception {
        if (USE_PROXY) {
            final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, PROXY_PORT));

            if (USE_PROXY_AUTHENTICATION){
                Authenticator authenticator = new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return (new PasswordAuthentication(PROXY_USERNAME, PROXY_PASSWORD.toCharArray()));
                    }
                };
                Authenticator.setDefault(authenticator);
            }
            return (HttpURLConnection) new URL(url).openConnection(proxy);
        }
        else {
            return(HttpURLConnection) new URL(url).openConnection();
        }
    }

    private static void dumpContent(final HttpURLConnection connection) {
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream()), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String inline;
            while ((inline = inputReader.readLine()) != null) {
                sb.append(inline);
            }
            Log.d(msg, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
