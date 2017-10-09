package design.java.rest;

import design.java.rest.entity.RESTErrorInterface;

/**
 * This class used to make API's response kind. There are 2: success or error.
 * 
 * @author xuanhung2401@gmail.com
 * 
 * @doc Sep 16, 2015 9:13:47 AM
 * 
 */

public class RESTFactory {

	public static RESTResponseError make(RESTErrorInterface define) {
		return new RESTResponseError(define);
	}

	public static RESTResponseError2 buildError(RESTGeneralError basic, RESTErrorInterface advance) {
		return new RESTResponseError2(basic, advance);
	}

	public static RESTResponseError2 buildError(RESTGeneralError general) {
		return new RESTResponseError2(general);
	}

	public static RESTResponseSuccess make(RESTGeneralSuccess define) {
		return new RESTResponseSuccess(define);
	}

}
