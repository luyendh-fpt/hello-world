package design.java.rest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import design.java.rest.entity.RESTConstantHttp;
import design.java.rest.entity.RESTDocumentSingle;
import design.java.rest.entity.RESTResource;
import design.java.rest.util.RESTStringUtil;

/**
 * This class used to handle REST Api request.
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 16, 2015 9:14:33 AM
 *
 */
public class RESTHandle {

	/**
	 * This function is a beginner of REST Api, all of request must be handled
	 * by this function. It checks header parameters that required, be sure that
	 * request are made from real client.
	 * 
	 * @param resp
	 *            HttpServletResponse response, to make a response to client.
	 * @param acceptMethods
	 *            list of methods that api accept from client.
	 */
	public static void doOption(HttpServletResponse resp, ArrayList<String> acceptMethods) {
		resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_METHODS,
				RESTStringUtil.joinCollectionString(acceptMethods, ","));
		resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_HEADERS,
				RESTConstantHttp.HEADER_CONTENT_TYPE + ", " + RESTConstantHttp.HEADER_AUTHORIZATION);
		resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_MAX_AGE, RESTConstantHttp.ACCESS_CONTROL_MAX_AGE_TIME);
		resp.setHeader(RESTConstantHttp.DO_ALLOW, RESTStringUtil.joinCollectionString(acceptMethods, ","));
	}

	/**
	 * After doOption() method, developer must call this function if client is
	 * identified.
	 * 
	 * @param resp
	 *            HttpServletResponse response, to client.
	 */
	public static void passRequest(HttpServletResponse resp, ArrayList<String> acceptMethods) {
		resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		resp.setHeader(RESTConstantHttp.ACCESS_CONTROL_ALLOW_METHODS,
				RESTStringUtil.joinCollectionString(acceptMethods, ","));
	}

	public static <T> String objectToSingleDocument(T object) {
		RESTDocumentSingle singleDoc = new RESTDocumentSingle();
		singleDoc.setData(RESTResource.getInstance(object));
		return singleDoc.toJsonString();
	}

}
