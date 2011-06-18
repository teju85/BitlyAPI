package com.blogspot.ficklemind.bitly;

import org.json.JSONException;

/**
 * Response for 'shorten' api
 */
public class BitlyShorten extends BitlyResponse {
    /** shortened url */
    public String url;
    /** bitly ID for the long_url */
    public String hash;
    /** global bitly ID for the long url */
    public String global_hash;
    /** the requested long url */
    public String long_url;
    /** whether this url was shortened first time */
    public boolean new_hash;


    /**
     * Constructor
     * @param url url which corresponds to the REST API call
     * @throws BitlyException 
     */
    public BitlyShorten(String url) throws BitlyException {
        super(url);
        try {
            this.url = m_data.getString("url");
            hash = m_data.getString("hash");
            global_hash = m_data.getString("global_hash");
            long_url = m_data.getString("long_url");
            new_hash = (m_data.getInt("new_hash") == 1);
        }
        catch(JSONException e) {
            String msg = "Bad response for url '" + url + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
    }
}
