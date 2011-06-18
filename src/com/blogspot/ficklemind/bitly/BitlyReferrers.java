package com.blogspot.ficklemind.bitly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Response for 'referrers' api
 */
public class BitlyReferrers extends BitlyResponse {
    /** class for referrer */
    public class Referrer {
        /** referrer site */
        public String referrer;
        /** referrer app */
        public String referrer_app;
        /** url of referring application */
        public String url;
        /** number of clicks */
        public int clicks;
    }
    /** shortened url */
    public String short_url;
    /** requested hash */
    public String hash;
    /** bitly ID for the long_url */
    public String user_hash;
    /** global bitly ID for the long url */
    public String global_hash;
    /** creator */
    public String created_by;
    /** referrers */
    public Referrer[] referrers;


    /**
     * Constructor
     * @param url url which corresponds to the REST API call
     * @throws BitlyException 
     */
    public BitlyReferrers(String url) throws BitlyException {
        super(url);
        try {
            short_url = (m_data.has("short_url"))? m_data.getString("short_url") : null;
            hash = (m_data.has("hash"))? m_data.getString("hash") : null;
            user_hash = (m_data.has("user_hash"))? m_data.getString("user_hash") : null;
            global_hash = (m_data.has("global_hash"))? m_data.getString("global_hash") : null;
            created_by = (m_data.has("created_by"))? m_data.getString("created_by") : null;
            JSONArray arr = m_data.getJSONArray("referrers");
            int len = arr.length();
            referrers = new Referrer[len];
            for(int i=0;i<len;i++) {
                JSONObject obj = (JSONObject) arr.get(i);
                referrers[i] = new Referrer();
                referrers[i].referrer = (obj.has("referrer"))? obj.getString("referrer") : null;
                referrers[i].referrer_app = (obj.has("referrer_app"))? obj.getString("referrer_app") : null;
                referrers[i].url = (obj.has("url"))? obj.getString("url") : null;
                referrers[i].clicks = (obj.has("clicks"))? obj.getInt("clicks") : -1;
            }
        }
        catch(JSONException e) {
            String msg = "Bad response for url '" + url + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
    }
}
