package com.blogspot.ficklemind.bitly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response for 'lookup' api
 */
public class BitlyLookup extends BitlyResponse {
    /** Class containing parameters from the 'lookup' api data */
    public class Params {
        /** shortened url */
        public String short_url;
        /** global bitly ID for the long url */
        public String global_hash;
        /** the requested long url */
        public String url;
    }
    /** parameters */
    public Params[] lookup;


    /**
     * Constructor
     * @param url url which corresponds to the REST API call
     * @throws BitlyException 
     */
    public BitlyLookup(String url) throws BitlyException {
        super(url);
        try {
            JSONArray arr = m_data.getJSONArray("lookup");
            int len = arr.length();
            lookup = new Params[len];
            for(int i=0;i<len;i++) {
                JSONObject obj = (JSONObject) arr.get(i);
                lookup[i] = new Params();
                lookup[i].short_url = (obj.has("short_url"))? obj.getString("short_url") : null;
                lookup[i].global_hash = (obj.has("global_hash"))? obj.getString("global_hash") : null;
                lookup[i].url = (obj.has("url"))? obj.getString("url") : null;
            }
        }
        catch(JSONException e) {
            String msg = "Bad response for url '" + url + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
    }
}
