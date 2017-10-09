package design.java.rest.entity;

import java.util.HashMap;

/**
 * A JSON API document MAY include information about its implementation under a
 * top level jsonapi member.<br>
 * 
 * @see <a
 *      href="http://jsonapi.org/format/#document-jsonapi-object">http://jsonapi.org/format/#document-jsonapi-object</a>
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 16, 2015 11:30:41 AM
 *
 */
public class RESTJsonApi {

	private String version;
	private HashMap<String, Object> meta;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public HashMap<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(HashMap<String, Object> meta) {
		this.meta = meta;
	}

}
