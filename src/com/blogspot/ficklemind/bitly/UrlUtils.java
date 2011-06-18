package com.blogspot.ficklemind.bitly;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



/**
 * Helper class for accessing internet and reading HTML pages.
 */
public class UrlUtils {

    /**
     * Helper function to download a URI from the given url and save it to a string.
     * Uses URLConnection in order to set up a connection with the server and download
     * the URI requested.
     * @param url The url of interest.
     * @return the contents of the url in a string
     * @throws IOException 
     * @throws URISyntaxException 
     * @throws ClientProtocolException
     * @throws MalformedURLException
     */
    public static String downloadToString(String url) throws ClientProtocolException,
							     URISyntaxException, IOException,
							     MalformedURLException {
	URL link = new URL(url);
	BufferedReader br = openConnection(link);
	StringBuilder sb = new StringBuilder(GlobalParams.SB_CAPACITY);
	char[] buff = new char[GlobalParams.SB_CAPACITY];
	int len;
	while((len = br.read(buff)) > 0) {
	    sb.append(buff, 0, len);
	}
	br.close();
	return sb.toString();
    }

    /**
     * Open a http connection with the given url
     * @param url desired url
     * @return buffered reader for this url connection
     * @throws URISyntaxException 
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static BufferedReader openConnection(URL url) throws URISyntaxException, ClientProtocolException, IOException {
	HttpGet http = new HttpGet(url.toURI());
	HttpClient client = new DefaultHttpClient();
	HttpResponse resp = (HttpResponse) client.execute(http);
	HttpEntity entity = resp.getEntity();
	InputStreamReader isr = new InputStreamReader(entity.getContent());
	BufferedReader br = new BufferedReader(isr, GlobalParams.DNLD_BUFF_SIZE);
	return br;
    }

    /**
     * Open a http connection with the given url
     * @param url desired url
     * @return buffered reader stream for this url connection
     * @throws URISyntaxException 
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public static BufferedInputStream openConnectionStream(URL url) throws URISyntaxException, ClientProtocolException, IOException {
	HttpGet http = new HttpGet(url.toURI());
	HttpClient client = new DefaultHttpClient();
	HttpResponse resp = (HttpResponse) client.execute(http);
	InputStream is = resp.getEntity().getContent();
	BufferedInputStream bis = new BufferedInputStream(is);
	return bis;
    }

    /**
     * Encode url using application/x-www-form-urlencoded and UTF-8 scheme.
     * @param url desired url to be encoded
     * @return encoded url
     * @throws UnsupportedEncodingException
     */
    public static String urlEncode(String url) throws UnsupportedEncodingException {
	return URLEncoder.encode(url, "UTF-8");
    }
}
