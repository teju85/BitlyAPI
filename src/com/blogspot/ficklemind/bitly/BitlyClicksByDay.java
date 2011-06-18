package com.blogspot.ficklemind.bitly;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Response for 'clicks_by_day' api
 */
public class BitlyClicksByDay extends BitlyResponse {
    public class Clicks {
        /** number of clicks */
        public int clicks;
        /** day start */
        public int day_start;
    }
    /** Class containing parameters from the 'clicks_by_day' api data */
    public class Params {
        /** shortened url */
        public String short_url;
        /** echo back of the input hash */
        public String hash;
        /** bitly ID for the long_url */
        public String user_hash;
        /** global bitly ID for the long url */
        public String global_hash;
        /** the requested long url */
        public Clicks[] clicks;
    }
    /** parameters */
    public Params[] clicks_by_day;


    /**
     * Constructor
     * @param url url which corresponds to the REST API call
     * @throws BitlyException 
     */
    public BitlyClicksByDay(String url) throws BitlyException {
        super(url);
        try {
            JSONArray arr = m_data.getJSONArray("clicks_by_day");
            int len = arr.length();
            clicks_by_day = new Params[len];
            for(int i=0;i<len;i++) {
                JSONObject obj = (JSONObject) arr.get(i);
                clicks_by_day[i] = new Params();
                clicks_by_day[i].short_url = (obj.has("short_url"))? obj.getString("short_url") : null;
                clicks_by_day[i].hash = (obj.has("hash"))? obj.getString("hash") : null;
                if(obj.has("clicks")) {
                    JSONArray cliks = obj.getJSONArray("clicks");
                    int num = cliks.length();
                    clicks_by_day[i].clicks = new Clicks[num];
                    for(int j=0;j<num;j++) {
                        clicks_by_day[i].clicks[j] = new Clicks();
                        JSONObject clk = (JSONObject) cliks.get(j);
                        clicks_by_day[i].clicks[j].clicks = clk.getInt("clicks");
                        clicks_by_day[i].clicks[j].day_start = clk.getInt("day_start");
                    }
                }
                else {
                    clicks_by_day[i].clicks = null;
                }
                clicks_by_day[i].user_hash = (obj.has("user_hash"))? obj.getString("user_hash") : null;
                clicks_by_day[i].global_hash = (obj.has("global_hash"))? obj.getString("global_hash") : null;
            }
        }
        catch(JSONException e) {
            String msg = "Bad response for url '" + url + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
    }
}
