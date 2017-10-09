package design.java.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import design.java.rest.entity.RESTDocumentSingle;
import design.java.rest.entity.RESTError;
import design.java.rest.entity.RESTErrorInterface;
import design.java.rest.entity.RESTJsonApi;
import design.java.rest.util.RESTJsonUtil;

/**
 * This class used to build response's error before sending to REST Api client.
 * Content is formatted following jsonapi format below.<br>
 * 
 * @see <a href="http://jsonapi.org/format/">http://jsonapi.org/format/</a>
 * 
 * @author xuanhung2401@gmail.com
 * 
 * @doc Sep 16, 2015 9:40:25 AM
 *
 */
public class RESTResponseError extends RESTResponse {

	public RESTResponseError(RESTErrorInterface define) {
		this.code = define.code();
		if (this.errors == null) {
			this.errors = new ArrayList<RESTError>();
		}
		this.errors.add(new RESTError.Builder().setCode(String.valueOf(define.code())).setTitle(define.description())
				.setDetail(define.description()).build());
	}

	private ArrayList<RESTError> errors;

	private RESTJsonApi jsonapi;

	@Override
	void buildResponseDocument() {
		if (this.errors != null && this.errors.size() > 0) {
			this.document = new RESTDocumentSingle.Builder().setErrors(this.errors).build();
		} else {
			this.document = new RESTDocumentSingle();
		}
	}

	@Override
	public String toJsonString() {
		buildResponseDocument();
		return RESTJsonUtil.GSON.toJson(this.document);
	}

	@Override
	public void doResponse(HttpServletResponse resp) throws IOException {
		buildResponseDocument();
		build(resp);
		resp.getWriter().println(RESTJsonUtil.GSON.toJson(this.document));
	}

	public RESTResponseError putErrors(RESTGeneralError define) {
		if (this.errors == null) {
			this.errors = new ArrayList<RESTError>();
		}
		this.errors.add(new RESTError.Builder().setCode(String.valueOf(this.code)).setTitle(define.description())
				.setDetail(define.description()).build());
		return this;
	}

	public RESTResponseError putErrors(RESTErrorInterface define) {
		if (this.errors == null) {
			this.errors = new ArrayList<RESTError>();
		}
		this.errors.add(new RESTError.Builder().setCode(String.valueOf(define.code())).setTitle(define.description())
				.setDetail(define.description()).build());
		return this;
	}

	/**
	 * Put error to response object.
	 */
	public RESTResponseError putErrors(RESTError value) {
		if (this.errors == null) {
			this.errors = new ArrayList<RESTError>();
		}
		this.errors.add(value);
		return this;
	}

	/**
	 * Put errors to response object.
	 */
	public RESTResponseError putErrors(Collection<RESTError> listValue) {
		if (this.errors == null) {
			this.errors = new ArrayList<RESTError>();
		}
		for (RESTError value : listValue) {
			this.errors.add(value);
		}
		return this;
	}

	/**
	 * Put many error to response object.
	 */
	public RESTResponseError putErrors(RESTError... listValue) {
		if (this.errors == null) {
			this.errors = new ArrayList<RESTError>();
		}
		for (RESTError value : listValue) {
			this.errors.add(value);
		}
		return this;
	}

	/**
	 * Put error manually to response object.
	 */
	public RESTResponseError putErrors(int code, String title, String description, boolean multiError) {
		if (this.errors == null || !multiError) {
			this.errors = new ArrayList<RESTError>();
		}
		this.errors.add(
				new RESTError.Builder().setCode(String.valueOf(code)).setTitle(title).setDetail(description).build());
		return this;
	}

	public RESTResponseError putJsonapi(String key, Object value) {
		System.out.println(this.jsonapi);
		return this;
	}

}
