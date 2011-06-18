package com.blogspot.ficklemind.bitly;

import org.json.JSONException;

/**
 * Response for 'validate' api
 */
public class BitlyValidate extends BitlyResponse {
    /** whether the input user login/api-key are valid */
    public boolean valid;


    /**
     * Constructor
     * @param url url which corresponds to the REST API call
     * @throws BitlyException 
     */
    public BitlyValidate(String url) throws BitlyException {
        super(url);
        try {
            valid = (m_data.getInt("valid") == 1);
        }
        catch(JSONException e) {
            String msg = "Bad response for url '" + url + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
    }
}
