package design.java.rest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

/**
 * This class contains all json function handle.
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 16, 2015 9:34:46 AM
 *
 */
public class RESTJsonUtil {

	private static final Logger LOGGER = Logger.getLogger(RESTJsonUtil.class.getName());

	public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

	public static final Gson GSON2 = new GsonBuilder().setLongSerializationPolicy(LongSerializationPolicy.STRING)
			.disableHtmlEscaping().create();

	/**
	 * This function parse json object from client request.
	 * 
	 * @param in
	 *            HttpServlet request input stream.
	 * 
	 * @throws IOException
	 * 
	 * @throws JSONException
	 */
	public static JSONObject parseJsonFromInputStream(InputStream in) throws IOException, JSONException {
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
		JSONObject json = new JSONObject();
		while ((line = br.readLine()) != null) {
			json = new JSONObject(line);
		}
		return json;
	}

	/**
	 * Parse input stream to string, UTF-8.
	 */
	public static String parseStringInputStream(InputStream in) throws IOException, JSONException {
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
		while ((line = br.readLine()) != null) {
			stringBuilder.append(line);
		}
		LOGGER.info("Input String: " + stringBuilder.toString());
		return stringBuilder.toString();
	}
}
