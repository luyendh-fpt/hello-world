package design.java.rest;

/**
 * This class contains some general success status from REST Api.
 * 
 * @author xuanhung2401@gmail.com
 * 
 * @doc Sep 22, 2015 10:42:35 AM
 * */
public enum RESTGeneralSuccess {

	OK(200, "Ok"), CREATED(201, "Created"), ACCEPTED(202, "Accepted"), NO_CONTENT(
			204, "No Content");

	private final int code;
	private final String description;

	private RESTGeneralSuccess(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String description() {
		return description;
	}

	public int code() {
		return code;
	}
}
