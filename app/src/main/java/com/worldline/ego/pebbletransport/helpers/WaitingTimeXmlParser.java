package com.worldline.ego.pebbletransport.helpers;

import android.util.Log;
import android.util.Xml;

import com.worldline.ego.pebbletransport.pojo.Position;
import com.worldline.ego.pebbletransport.pojo.WaitingTime;
import com.worldline.ego.pebbletransport.pojo.WaitingTimeStop;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a143210 on 9/12/2016.
 */

public class WaitingTimeXmlParser {
    private static final String ns = null;
    private final static String msg = "WTimeXmlParser";

    public static WaitingTimeStop parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            while(parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                parser.nextTag();
                String name = parser.getName();
                if (name.equals("waitingtimes")) {
                    Log.d(msg, "waitingtimes tag found");
                    return readWaitingTimes(parser);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static WaitingTimeStop readWaitingTimes(XmlPullParser parser) throws XmlPullParserException, IOException {
        String stopName = "";
        Position position = null;
        List <WaitingTime> wTimes = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, "waitingtimes");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("stopname")) {
                stopName = readTextVal(parser, "stopname");
            } else if (name.equals("position")){
                position = readPostion(parser);
            } else if (name.equals("waitingtime")) {
                wTimes.add(readWaitingTime(parser));
            } else {
                skip(parser);
            }
        }
        Log.d(msg, "There are "+wTimes.size()+" entries");
        return (new WaitingTimeStop(stopName, position, wTimes));
    }

    private static Position readPostion(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "position");
        double latitude = 0;
        double longitude = 0;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("latitude")) {
                latitude = readFloatVal(parser, "latitude");
            } else if (name.equals("longitude")) {
                latitude = readFloatVal(parser, "longitude");
            } else {
                skip(parser);
            }
        }
        return new Position(latitude, longitude);
    }

    private static WaitingTime readWaitingTime(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "waitingtime");
        String line = "";
        String mode = "";
        int minutes = 0;
        String destination = "";
        String message="";

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("line")) {
                line = readTextVal(parser, "line");
            } else if (name.equals("mode")) {
                mode = readTextVal(parser, "mode");
            } else if (name.equals("minutes")) {
                minutes = readIntVal(parser, "minutes");
            } else if (name.equals("destination")) {
                destination = readTextVal(parser, "destination");
            } else if (name.equals("message")) {
                message = readTextVal(parser, "message");
            } else {
                skip(parser);
            }
        }
        return new WaitingTime(line,mode,minutes, destination, message);
    }

    private static int readIntVal(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        int result = 0;
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String strResult = readText(parser);
        result = Integer.parseInt(strResult);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private static String readTextVal(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private static double readFloatVal(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
        double result = 0;
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String strResult = readText(parser);
        result = Float.parseFloat(strResult);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
