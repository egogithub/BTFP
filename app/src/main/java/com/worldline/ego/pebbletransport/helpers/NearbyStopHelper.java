package com.worldline.ego.pebbletransport.helpers;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

import com.worldline.ego.pebbletransport.pojo.ItiStop;
import com.worldline.ego.pebbletransport.helpers.Constants;
import java.util.List;
import java.util.zip.GZIPInputStream;

import static com.worldline.ego.pebbletransport.helpers.Constants.*;

/**
 * Created by a143210 on 21/11/2016.
 */

public class NearbyStopHelper {
    // Proxy stuff
    private static final boolean USE_PROXY = false;
    private static final String PROXY_HOST = "bcproxynew.extsec.banksys.be";
    private static final int PROXY_PORT = 8080;
    private static final boolean USE_PROXY_AUTHENTICATION = true;
    private static final String PROXY_USERNAME = "training10";
    private static final String PROXY_PASSWORD = "Student10/";

    private List nbStops=null;
    private float latitude = 0;
    private float longitude = 0;

    public static List<ItiStop> getNearbyStops() {

        // TODO. Get the values from current location
        float latitude=50.789339f;
        float longitude=4.34064f;

        //TODO. Get this from settings
        String lang="fr";

        String httpUrlString = URL_STREAM_GET_CLOSE_STOPS+"&latitude="+latitude+"&longitude="+longitude+"&lang="+lang;
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
                return NbStopsXmlParser.parse(new GZIPInputStream(connection.getInputStream()));
            }
        } catch (Exception e) {
            e.printStackTrace();
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

}
