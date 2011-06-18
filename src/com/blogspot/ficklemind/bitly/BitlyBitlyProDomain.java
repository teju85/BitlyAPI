package com.blogspot.ficklemind.bitly;

import org.json.JSONException;

/**
 * Response for 'bitly_pro_domain' api
 */
public class BitlyBitlyProDomain extends BitlyResponse {
    /** domain */
    public String domain;
    /** whether the domain is pro */
    public boolean bitly_pro_domain;


    /**
     * Constructor
     * @param url url which corresponds to the REST API call
     * @throws BitlyException 
     */
    public BitlyBitlyProDomain(String url) throws BitlyException {
        super(url);
        try {
            domain = m_data.getString("domain");
            bitly_pro_domain = m_data.getBoolean("bitly_pro_domain");
        }
        catch(JSONException e) {
            String msg = "Bad response for url '" + url + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
    }
}
