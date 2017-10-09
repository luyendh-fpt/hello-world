package design.java.rest.entity;

import java.util.ArrayList;
import java.util.List;

public class RESTError2 {

	private List<RESTError2Detail> errors;
	private int code;
	private String message;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RESTError2Detail> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<RESTError2Detail>();
		}
		return errors;
	}

	public void addErrors(RESTError2Detail e) {
		if (this.errors == null) {
			this.errors = new ArrayList<RESTError2Detail>();
		}
		this.errors.add(e);
	}

	public void setErrors(List<RESTError2Detail> errors) {
		this.errors = errors;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
