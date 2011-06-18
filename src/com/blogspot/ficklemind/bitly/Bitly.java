package com.blogspot.ficklemind.bitly;

import java.io.UnsupportedEncodingException;



/**
 * Class responsible for providing access to the bitly services.
 */
public class Bitly {
    /** authentication string required by the REST API */
    private String m_auth_str;
    /** API server */
    private static final String m_bitly_server = "http://api.bitly.com/v3";
    /** maximum number of requests in the 'expand' api */
    private static int MAX_EXPAND = 15;
    /** maximum number of days in the 'clicks_by_days' api */
    private static int MAX_DAYS = 30;
    /** api key */
    private String m_apiKey;
    /** login */
    private String m_login;


    /**
     * Constructor
     * @param login login ID in order to use bitly webservices
     * @param apikey key used in all webservice queries
     */
    public Bitly(String login, String apikey) {
        m_auth_str = "login="+login+"&apiKey="+apikey+"&format=json";
        m_login = login;
        m_apiKey = apikey;
    }

    /**
     * Returns the qrcode link for the shortened url.
     * @param bitlyUrl the bitly shortened url
     * @return the qrcode link for the above url
     */
    public String getQRLink(String bitlyUrl) {
    	return bitlyUrl + ".qrcode";
    }

    /**
     * 'shorten' API call
     * @param longUrl url which needs to be shortened. (this will be url-encoded)
     * @param domain domain for shortening [bit.ly]
     * @return response object
     * @throws BitlyException 
     * Available options for 'domain': "bit.ly" and "j.mp"
     */
    public BitlyShorten shorten(String longUrl, String domain) throws BitlyException {
        String encode;
        try {
            encode = UrlUtils.urlEncode(longUrl);
        }
        catch (UnsupportedEncodingException e) {
            String msg = "Bad long url '" + longUrl + "' Reason: " + e;
            BitlyException be = new BitlyException(msg);
            throw be;
        }
        String api_url = String.format("%s/shorten?%s&longUrl=%s&domain=%s", m_bitly_server,
                m_auth_str, encode, domain);
        return new BitlyShorten(api_url);
    }
    public BitlyShorten shorten(String longUrl) throws BitlyException {
        return shorten(longUrl, "bit.ly");
    }

    /**
     * 'expand' API call
     * @param shortUrls list of short urls to be expanded
     * @param hashes list of hashes of short urls to be expanded
     * @return response object
     * @throws BitlyException 
     */
    public BitlyExpand expand(String[] shortUrls, String[] hashes) throws BitlyException {
        checkInputVectors(shortUrls, hashes);
        StringBuilder sb = new StringBuilder(GlobalParams.SB_CAPACITY);
        sb.append(m_bitly_server);
        sb.append("/expand?");
        sb.append(m_auth_str);
        if(shortUrls != null) {
            for(String s : shortUrls) {
                sb.append("&shortUrl=");
                try {
                    sb.append(UrlUtils.urlEncode(s));
                }
                catch (UnsupportedEncodingException e) {
                    String msg = "Bad short url '" + s + "' Reason: " + e;
                    BitlyException be = new BitlyException(msg);
                    throw be;
                }
            }
        }
        if(hashes != null) {
            for(String s : hashes) {
                sb.append("&hash=");
                sb.append(s);
            }
        }
        String api_url = sb.toString();
        return new BitlyExpand(api_url);
    }

    /**
     * 'validate' API call
     * @param login login which needs to be validated [m_login]
     * @param apikey api-key which needs to be validated [m_apiKey]
     * @return response object
     * @throws BitlyException 
     */
    public BitlyValidate validate(String login, String apikey) throws BitlyException {
        String api_url = String.format("%s/validate?%s&x_login=%s&x_apiKey=%s", m_bitly_server,
                m_auth_str, login, apikey);
        return new BitlyValidate(api_url);
    }
    public BitlyValidate validate() throws BitlyException {
        return validate(m_login, m_apiKey);
    }

    /**
     * 'clicks' API call
     * @param shortUrls list of short urls
     * @param hashes list of hashes
     * @return response object
     * @throws BitlyException
     */
    public BitlyClicks clicks(String[] shortUrls, String[] hashes) throws BitlyException {
        checkInputVectors(shortUrls, hashes);
        StringBuilder sb = new StringBuilder(GlobalParams.SB_CAPACITY);
        sb.append(m_bitly_server);
        sb.append("/clicks?");
        sb.append(m_auth_str);
        if(shortUrls != null) {
            for(String s : shortUrls) {
                sb.append("&shortUrl=");
                try {
                    sb.append(UrlUtils.urlEncode(s));
                }
                catch (UnsupportedEncodingException e) {
                    String msg = "Bad short url '" + s + "' Reason: " + e;
                    BitlyException be = new BitlyException(msg);
                    throw be;
                }
            }
        }
        if(hashes != null) {
            for(String s : hashes) {
                sb.append("&hash=");
                sb.append(s);
            }
        }
        String api_url = sb.toString();
        return new BitlyClicks(api_url);
    }

    /**
     * 'referrers' API call
     * @param shortUrlOrHash shortUrl or hash
     * @param shortUrl tells whether the above input is shortUrl or hash
     * @return response object
     * @throws BitlyException 
     */
    public BitlyReferrers referrers(String shortUrlOrHash, boolean shortUrl) throws BitlyException {
        String type = shortUrl? "shortUrl" : "hash";
        String api_url = String.format("%s/referrers?%s&%s=%s", m_bitly_server,
                m_auth_str, type, shortUrlOrHash);
        return new BitlyReferrers(api_url);
    }

    /**
     * 'countries' API call
     * @param shortUrlOrHash shortUrl or hash
     * @param shortUrl tells whether the above input is shortUrl or hash
     * @return response object
     * @throws BitlyException 
     */
    public BitlyCountries countries(String shortUrlOrHash, boolean shortUrl) throws BitlyException {
        String type = shortUrl? "shortUrl" : "hash";
        String api_url = String.format("%s/countries?%s&%s=%s", m_bitly_server,
                m_auth_str, type, shortUrlOrHash);
        return new BitlyCountries(api_url);
    }

    /**
     * 'clicks_by_minute' API call
     * @param shortUrls list of short urls to be expanded
     * @param hashes list of hashes of short urls to be expanded
     * @return response object
     * @throws BitlyException 
     */
    public BitlyClicksByMinute clicks_by_minute(String[] shortUrls, String[] hashes) throws BitlyException {
        checkInputVectors(shortUrls, hashes);
        StringBuilder sb = new StringBuilder(GlobalParams.SB_CAPACITY);
        sb.append(m_bitly_server);
        sb.append("/clicks_by_minute?");
        sb.append(m_auth_str);
        if(shortUrls != null) {
            for(String s : shortUrls) {
                sb.append("&shortUrl=");
                try {
                    sb.append(UrlUtils.urlEncode(s));
                }
                catch (UnsupportedEncodingException e) {
                    String msg = "Bad short url '" + s + "' Reason: " + e;
                    BitlyException be = new BitlyException(msg);
                    throw be;
                }
            }
        }
        if(hashes != null) {
            for(String s : hashes) {
                sb.append("&hash=");
                sb.append(s);
            }
        }
        String api_url = sb.toString();
        return new BitlyClicksByMinute(api_url);
    }

    /**
     * 'clicks_by_day' API call
     * @param shortUrls list of short urls to be expanded
     * @param hashes list of hashes of short urls to be expanded
     * @param days number of days for which to retrieve the data [7]
     * @return response object
     * @throws BitlyException 
     * 'days' must be between 1 and 30 (both inclusive)
     */
    public BitlyClicksByDay clicks_by_day(String[] shortUrls, String[] hashes, int days) throws BitlyException {
        checkInputVectors(shortUrls, hashes);
        if((days <= 0) || (days >= MAX_DAYS)) {
            BitlyException be = new BitlyException("Days must be between 1 and "+MAX_DAYS+"! You've passed "+days);
            throw be;
        }
        StringBuilder sb = new StringBuilder(GlobalParams.SB_CAPACITY);
        sb.append(m_bitly_server);
        sb.append("/clicks_by_day?");
        sb.append(m_auth_str);
        if(shortUrls != null) {
            for(String s : shortUrls) {
                sb.append("&shortUrl=");
                try {
                    sb.append(UrlUtils.urlEncode(s));
                }
                catch (UnsupportedEncodingException e) {
                    String msg = "Bad short url '" + s + "' Reason: " + e;
                    BitlyException be = new BitlyException(msg);
                    throw be;
                }
            }
        }
        if(hashes != null) {
            for(String s : hashes) {
                sb.append("&hash=");
                sb.append(s);
            }
        }
        sb.append("&days=");
        sb.append(days);
        String api_url = sb.toString();
        return new BitlyClicksByDay(api_url);
    }
    public BitlyClicksByDay clicks_by_day(String[] shortUrls, String[] hashes) throws BitlyException {
        return clicks_by_day(shortUrls, hashes, 7);
    }

    /**
     * 'bitly_pro_domain' API call
     * @param domain domain which needs to be validated
     * @return response object
     * @throws BitlyException 
     */
    public BitlyBitlyProDomain bitly_pro_domain(String domain) throws BitlyException {
        String api_url = String.format("%s/bitly_pro_domain?%s&domain=%s", m_bitly_server,
                m_auth_str, domain);
        return new BitlyBitlyProDomain(api_url);
    }

    /**
     * 'lookup' API call
     * @param longUrls list of long urls to be looked up
     * @return response object
     * @throws BitlyException
     */
    public BitlyLookup lookup(String[] longUrls) throws BitlyException {
        checkInputVector(longUrls);
        StringBuilder sb = new StringBuilder(GlobalParams.SB_CAPACITY);
        sb.append(m_bitly_server);
        sb.append("/lookup?");
        sb.append(m_auth_str);
        for(String s : longUrls) {
            sb.append("&url=");
            try {
                sb.append(UrlUtils.urlEncode(s));
            }
            catch (UnsupportedEncodingException e) {
                String msg = "Bad long url '" + s + "' Reason: " + e;
                BitlyException be = new BitlyException(msg);
                throw be;
            }
        }
        String api_url = sb.toString();
        return new BitlyLookup(api_url);
    }

    /**
     * 'info' API call
     * @param shortUrls list of short urls to be expanded
     * @param hashes list of hashes of short urls to be expanded
     * @return response object
     * @throws BitlyException 
     * 'days' must be between 1 and 30 (both inclusive)
     */
    public BitlyInfo info(String[] shortUrls, String[] hashes) throws BitlyException {
        checkInputVectors(shortUrls, hashes);
        StringBuilder sb = new StringBuilder(GlobalParams.SB_CAPACITY);
        sb.append(m_bitly_server);
        sb.append("/info?");
        sb.append(m_auth_str);
        if(shortUrls != null) {
            for(String s : shortUrls) {
                sb.append("&shortUrl=");
                try {
                    sb.append(UrlUtils.urlEncode(s));
                }
                catch (UnsupportedEncodingException e) {
                    String msg = "Bad short url '" + s + "' Reason: " + e;
                    BitlyException be = new BitlyException(msg);
                    throw be;
                }
            }
        }
        if(hashes != null) {
            for(String s : hashes) {
                sb.append("&hash=");
                sb.append(s);
            }
        }
        String api_url = sb.toString();
        return new BitlyInfo(api_url);
    }

    /**
     * Utility function to check the inputs for API calls expecting multiple inputs
     * @param shortUrls list of short urls
     * @param hashes list of hashes
     * @throws BitlyException 
     */
    private void checkInputVectors(String[] shortUrls, String[] hashes) throws BitlyException {
        if((shortUrls == null) && (hashes == null)) {
            BitlyException be = new BitlyException("Atleast one of 'shorturl' or 'hash' is mandatory!");
            throw be;
        }
        int total = 0;
        if(shortUrls != null) {
            total += shortUrls.length;
        }
        if(hashes != null) {
            total += hashes.length;
        }
        if(total <= 0) {
            BitlyException be = new BitlyException("Atleast one of 'shorturl' or 'hash' is mandatory!");
            throw be;
        }
        if(total > MAX_EXPAND) {
            BitlyException be = new BitlyException("Max of "+MAX_EXPAND+" entries is allowed! You've passed "+total);
            throw be;
        }
    }

    /**
     * Utility function to check the inputs for API calls expecting multiple inputs of same kind
     * @param longUrls list of long urls
     * @throws BitlyException 
     */
    private void checkInputVector(String[] longUrls) throws BitlyException {
        if(longUrls == null) {
            BitlyException be = new BitlyException("Atleast one of 'longurl' is mandatory!");
            throw be;
        }
        int total = longUrls.length;
        if(total <= 0) {
            BitlyException be = new BitlyException("Atleast one of 'longurl' is mandatory! You've passed 0");
            throw be;
        }
        if(total > MAX_EXPAND) {
            BitlyException be = new BitlyException("Max of "+MAX_EXPAND+" entries is allowed! You've passed "+total);
            throw be;
        }
    }
}

