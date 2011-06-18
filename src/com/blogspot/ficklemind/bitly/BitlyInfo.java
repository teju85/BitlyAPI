package com.blogspot.ficklemind.bitly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response for 'info' api
 */
public class BitlyInfo extends BitlyResponse {
    /** Class containing parameters from the 'info' api data */
    public class Params {
        /** shortened url */
        public String short_url;
        /** echo back of the input hash */
        public String hash;
        /** bitly ID for the long_url */
        public String user_hash;
        /** global bitly ID for the long url */
        public String global_hash;
        /** error (if any) */
        public String error;
        /** title of the page */
        public String title;
        /** created by */
        public String created_by;
    }
    /** parameters */
    public Params[] info;


    /**
     * Constructor
     * @param url url which corresponds to the REST API call
     * @throws BitlyException 
     */
    public BitlyInfo(String url) throws BitlyException {
        super(url);
        try {
            JSONArray arr = m_data.getJSONArray("info");
            int len = arr.length();
            info = new Params[len];
            for(int i=0;i<len;i++) {
                JSONObject obj = (JSONObject) arr.get(i);
                info[i] = new Params();
                info[i].short_url = (obj.has("short_url"))? obj.getString("short_url") : null;
                info[i].hash = (obj.has("hash"))? obj.getString("hash") : null;
                info[i].error = (obj.has("error"))? obj.getString("error") : null;
                info[i].user_hash = (obj.has("user_hash"))? obj.getString("user_hash") : null;
                info[i].global_hash = (obj.has("global_hash"))? obj.getString("global_hash") : null;
                if(obj.has("title") && !obj.isNull("title")) {
                    info[i].title = obj.getString("title");
                }
                else {
                    info[i].title = null;
                }
                info[i].created_by = (obj.has("created_by"))? obj.getString("created_by") : null;
            }
        }
        catch(JSONException e) {
            String msg = "Bad response for url '" + url + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
    }
}
