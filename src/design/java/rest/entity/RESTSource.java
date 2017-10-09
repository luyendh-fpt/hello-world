package design.java.rest.entity;

/**
 * An object containing references to the source of the error, optionally
 * including any of the following members:
 * 
 * @pointer a JSON Pointer [RFC6901] to the associated entity in the request
 *          document [e.g. "/data" for a primary data object, or
 *          "/data/attributes/title" for a specific attribute].
 * @parameter a string indicating which URI query parameter caused the error.
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 16, 2015 11:04:24 AM
 *
 */
public class RESTSource {

	private String pointer;
	private String parameter;

	public String getPointer() {
		return pointer;
	}

	public void setPointer(String pointer) {
		this.pointer = pointer;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

}
