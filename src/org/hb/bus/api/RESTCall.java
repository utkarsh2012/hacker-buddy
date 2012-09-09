package org.hb.bus.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class RESTCall {

	public static String makeGetRequest(String getURL) {
		String json = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(getURL);
			HttpResponse responseGet = client.execute(get);
			HttpEntity resEntityGet = responseGet.getEntity();
			if (resEntityGet != null) {
				json = EntityUtils.toString(resEntityGet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}

	public static String makePostRequest(String postURL, Map<String, String> keyValuePostRequest) {
		String json = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(postURL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (String key : keyValuePostRequest.keySet()) {
				//Dirty hack, see createPoll operation to see why.
				if(key.contains("option")){
					params.add(new BasicNameValuePair("option", keyValuePostRequest.get(key)));
				} else {
					params.add(new BasicNameValuePair(key, keyValuePostRequest.get(key)));
				}
			}
			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);
			HttpEntity resEntity = responsePOST.getEntity();
			if (resEntity != null) {
				json = EntityUtils.toString(resEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}
}
