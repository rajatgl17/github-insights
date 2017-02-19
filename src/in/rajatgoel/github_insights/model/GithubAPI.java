package in.rajatgoel.github_insights.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GithubAPI {

	public Long count(String urlString) throws IOException {
		URL url = null;
		HttpURLConnection uc = null;
		BufferedReader bd;
		JSONObject jo = null;

		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			System.out.println(urlString + "is incorrect");
		}

		try {
			uc = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			System.out.println(urlString + " Error in opening connection");
			e.printStackTrace();
		}

		bd = new BufferedReader(new InputStreamReader(uc.getInputStream()));

		String inputString;
		inputString = bd.readLine();
		bd.close();

		JSONParser jp = new JSONParser();
		try {
			jo = (JSONObject) jp.parse(inputString);
		} catch (ParseException e) {
			System.out.println(inputString + "Invalid json object");
			e.printStackTrace();
		}

		return (long) jo.get("total_count");

	}
}
