package com.worldline.ego.pebbletransport.helpers;

import com.worldline.ego.pebbletransport.dummy.DummyContent;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.worldline.ego.pebbletransport.pojo.TranspLine;

/**
 * Created by a143210 on 2/12/2016.
 */

public class LinesHelper {

    //Proxy stuff
    private static final boolean USE_PROXY = false;
    private static final String PROXY_HOST = "bcproxynew.extsec.banksys.be";
    private static final int PROXY_PORT = 8080;
    private static final boolean USE_PROXY_AUTHENTICATION = true;
    private static final String PROXY_USERNAME = "training10";
    private static final String PROXY_PASSWORD = "Student10/";

    public static List<TranspLine> getLinesList() throws Exception {
        final HttpURLConnection connection = getHttpUrlConnection(Constants.URI_DIGITALIA_LINES_FR);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Content-Type", "application/json");

        final int responseCode = connection.getResponseCode();

        // If success
        if (responseCode == 200) {
            // Build Destinations list
            final Type type = new TypeToken<ArrayList<TranspLine>>() {}.getType();
            return new Gson().fromJson(new JsonReader(new InputStreamReader(connection.getInputStream(), "UTF-8")), type);
        }

        return getDummyLines();
    }

    private static HttpURLConnection getHttpUrlConnection(String url) throws Exception {
        if (USE_PROXY) {
            final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, PROXY_PORT));

            if (USE_PROXY_AUTHENTICATION) {
                Authenticator authenticator = new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return (new PasswordAuthentication(PROXY_USERNAME, PROXY_PASSWORD.toCharArray()));
                    }
                };
                Authenticator.setDefault(authenticator);
            }
            return (HttpURLConnection) new URL(url).openConnection(proxy);
        } else {
            return (HttpURLConnection) new URL(url).openConnection();
        }
    }

    private static List<TranspLine> getDummyLines() {
        List <TranspLine> destList = new ArrayList<>();
        List <DummyContent.DummyItem> dummyItems = DummyContent.ITEMS;
        Iterator<DummyContent.DummyItem> iterator = dummyItems.iterator();
        while (iterator.hasNext() ){
            DummyContent.DummyItem item = iterator.next();
            destList.add(new TranspLine(item.lineid, item.destfrom, item.destto, ""));
        }

        return destList;
    }
}
