package com.jesper.music.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Autowired;

import com.jesper.music.controller.HTTPClientFactory;

import java.util.*;
import org.json.*;

/**
 * 
 * This class performs our REST queries. In all cases it simply constructs the
 * REST URL based on the instance URL, and the REST action - and then performs
 * the appropriate GET or POST.
 * 
 * @author Jon Mountjoy
 */
public class RESTHelper {

	@Autowired
	HTTPClientFactory httpClientFactory;

	private static final Logger logger = LoggerFactory
			.getLogger(RESTHelper.class);

	// What version of the API should we use?
	private static String VERSION = "v22.0";
	
	public String query(String query) {
		try {
			logger.info("httpClientFactory instance URL = "
					+ httpClientFactory.getInstanceURL());
			logger.info("Excecuting query " + query + "\n");

			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			qparams.add(new BasicNameValuePair("q", query));

			String requestURL = httpClientFactory.getInstanceURL()
					+ "/services/data/" + VERSION + "/query?"
					+ URLEncodedUtils.format(qparams, "UTF-8");
			logger.info(requestURL);

			HttpGet httpget = new HttpGet(requestURL);
			HttpResponse response = httpClientFactory.getHttpClient().execute(
					httpget);

			if (response.getStatusLine().getStatusCode() != 200)
				throw new Exception(EntityUtils.toString(response.getEntity()));

			logger.info(" Got status code "
					+ response.getStatusLine().getStatusCode());

			JSONObject parsedResponse = new JSONObject(
					EntityUtils.toString(response.getEntity()));
			return parsedResponse.toString(2);
		} catch (Exception e) {
			return "Exception: " + e;
		}
	}

	public String create(String resource, String recordDescription) {
		try {
			String createURL = httpClientFactory.getInstanceURL()
					+ "/services/data/" + VERSION + "/sobjects/" + resource + "/";
			logger.info(createURL);					

			HttpPost httppost = new HttpPost(createURL);
			httppost.setEntity(new StringEntity(recordDescription,
					"application/json", null));
			HttpResponse response = httpClientFactory.getHttpClient().execute(
					httppost);

			if (response.getStatusLine().getStatusCode() != 201)
				throw new Exception(EntityUtils.toString(response.getEntity()));

			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			return "Exception: " + e;
		}
	}

}