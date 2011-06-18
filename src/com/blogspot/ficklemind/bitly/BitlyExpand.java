package com.blogspot.ficklemind.bitly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response for 'expand' api
 */
public class BitlyExpand extends BitlyResponse {
    /** Class containing parameters from the 'expand' api data */
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
        /** the requested long url */
        public String long_url;
    }
    /** parameters */
    public Params[] expand;


    /**
     * Constructor
     * @param url url which corresponds to the REST API call
     * @throws BitlyException 
     */
    public BitlyExpand(String url) throws BitlyException {
        super(url);
        try {
            JSONArray arr = m_data.getJSONArray("expand");
            int len = arr.length();
            expand = new Params[len];
            for(int i=0;i<len;i++) {
                JSONObject obj = (JSONObject) arr.get(i);
                expand[i] = new Params();
                expand[i].short_url = (obj.has("short_url"))? obj.getString("short_url") : null;
                expand[i].hash = (obj.has("hash"))? obj.getString("hash") : null;
                expand[i].error = (obj.has("error"))? obj.getString("error") : null;
                expand[i].user_hash = (obj.has("user_hash"))? obj.getString("user_hash") : null;
                expand[i].global_hash = (obj.has("global_hash"))? obj.getString("global_hash") : null;
                expand[i].long_url = (obj.has("long_url"))? obj.getString("long_url") : null;
            }
        }
        catch(JSONException e) {
            String msg = "Bad response for url '" + url + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
    }
}
