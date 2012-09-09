package org.hb.bus.api;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class MakeCallToPollServer {
	private static String HTTP = "http";
	private static String URL = "thearea42.com";
	private static String BASE_URL = "/bahashed/hackerbuddy/";
	
	/*
	 * POST Calls
	 */
	public boolean createHackathon(String hackathonName, String hackathon_description){
		Map<String, String> keyValuePostRequest = new HashMap<String, String>();
		keyValuePostRequest.put("title", hackathonName);
		keyValuePostRequest.put("desc", hackathon_description);

		String json = RESTCall.makePostRequest("http://thearea42.com/bahashed/hackerbuddy/hackathon/create", keyValuePostRequest);
		
		if(json != null){
			return true;
		}
		
		return false;
	}
	
	public boolean createPoll(String hackathon, String pollTitle, List<String> options){
		Map<String, String> keyValuePostRequest = new HashMap<String, String>();
		keyValuePostRequest.put("hackathon_title", hackathon);
		keyValuePostRequest.put("poll_title", pollTitle);
		int count = 0;
		for(String option : options){
			keyValuePostRequest.put("option"+count, option);
			count = count+1;
		}
		
		String json = RESTCall.makePostRequest("http://thearea42.com/bahashed/hackerbuddy/poll/create", keyValuePostRequest);
		
		if(json != null){
			return true;
		}
		
		return false;
	}
	
	public boolean voteOnPoll(String hackathon, String pollTitle, String option){
		Map<String, String> keyValuePostRequest = new HashMap<String, String>();
		keyValuePostRequest.put("hackathon_title", hackathon);
		keyValuePostRequest.put("poll_title", pollTitle);
		keyValuePostRequest.put("option", option);
		
		String json = RESTCall.makePostRequest("http://thearea42.com/bahashed/hackerbuddy/poll/vote", keyValuePostRequest);
		
		if(json != null){
			return true;
		}
		
		return false;
	}
	
	
	
	/*
	 * GET Calls
	 */
	public List<String> getAllPollsForHackathon(String hackathon){
		List<String> polls = new ArrayList<String>();
		URI uri;
		try {
			uri = new URI(
				    HTTP, 
				    URL, 
				    BASE_URL + hackathon,
				    null);

		URL url = uri.toURL();

		String urlToHit = url.toString();
				
		String json = RESTCall.makeGetRequest(urlToHit);
		polls = parseArryJSON(json);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return polls;
	}

	public List<PollOption> getAllOptionsForPoll(String hackathon, String pollTitle){
		URI uri;
		List<PollOption> listOfOptions = new ArrayList<PollOption>();
		try {
			uri = new URI(
				    HTTP, 
				    URL, 
				    BASE_URL + hackathon + "/" + pollTitle,
				    null);
		
		URL url = uri.toURL();
		String urlToHit = url.toString();
		
		String json = RESTCall.makeGetRequest(urlToHit);
		
		listOfOptions = getListOfPollOptionsFromJSON(json);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listOfOptions;
	}
	
	private List<PollOption> getListOfPollOptionsFromJSON(String json) {
		List<PollOption> vals = new ArrayList<PollOption>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject val = jsonArray.getJSONObject(i);
				PollOption po = new PollOption(val.getString("option"), val.getInt("count"));
				vals.add(po);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vals;
	}

	private List<String> parseArryJSON(String json) {
		List<String> vals = new ArrayList<String>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				String val = jsonArray.getString(i);
				vals.add(val);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vals;
	}
}
