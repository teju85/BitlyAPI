package com.blogspot.ficklemind.bitly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Response for 'countries' api
 */
public class BitlyCountries extends BitlyResponse {
    /** class for country */
    public class Country {
        /** country name */
        public String country;
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
    /** country */
    public Country[] countries;


    /**
     * Constructor
     * @param url url which corresponds to the REST API call
     * @throws BitlyException 
     */
    public BitlyCountries(String url) throws BitlyException {
        super(url);
        try {
            short_url = (m_data.has("short_url"))? m_data.getString("short_url") : null;
            hash = (m_data.has("hash"))? m_data.getString("hash") : null;
            user_hash = (m_data.has("user_hash"))? m_data.getString("user_hash") : null;
            global_hash = (m_data.has("global_hash"))? m_data.getString("global_hash") : null;
            created_by = (m_data.has("created_by"))? m_data.getString("created_by") : null;
            JSONArray arr = m_data.getJSONArray("countries");
            int len = arr.length();
            countries = new Country[len];
            for(int i=0;i<len;i++) {
                JSONObject obj = (JSONObject) arr.get(i);
                countries[i] = new Country();
                countries[i].country = (obj.has("country"))? obj.getString("country") : null;
                countries[i].clicks = (obj.has("clicks"))? obj.getInt("clicks") : -1;
            }
        }
        catch(JSONException e) {
            String msg = "Bad response for url '" + url + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
    }
}
