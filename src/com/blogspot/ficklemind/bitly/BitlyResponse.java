package com.blogspot.ficklemind.bitly;

import org.json.JSONObject;

/**
 * Class containing the response from a bitly query using the REST API.
 */
public class BitlyResponse {
    /** data part of the json response */
    protected JSONObject m_data;
    /** status code */
    public int status_code;
    /** status text */
    public String status_txt;


    /**
     * Constructor. All child classes should call a 'super' first
     * and then proceed to interpret the data part of the response!
     * @param url url which corresponds to the REST API call
     * @throws BitlyException 
     */
    public BitlyResponse(String url) throws BitlyException {
        String response;
        try {
            response = UrlUtils.downloadToString(url);
            JSONObject root = new JSONObject(response);
            status_code = root.getInt("status_code");
            status_txt = root.getString("status_txt");
            m_data = (JSONObject) root.get("data");
        }
        catch(Exception e) {
            String msg = "Couldn't get response for '" + url + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
        if(status_code != 200) {
            String msg = "Bad response for url'" + url + "' Response string=>> " + response;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
    }
}
