package design.java.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import design.java.rest.entity.RESTError2;
import design.java.rest.entity.RESTError2Detail;
import design.java.rest.entity.RESTErrorInterface;
import design.java.rest.util.RESTJsonUtil;

public class RESTResponseError2 {

	private RESTError2 error;

	public RESTResponseError2(RESTGeneralError general) {
		if (this.error == null) {
			this.error = new RESTError2();
		}
		this.error.setCode(general.code());
		this.error.setMessage(general.description());
		this.error.setStatus(general.name());
	}

	public RESTResponseError2(RESTGeneralError basic, RESTErrorInterface advance) {
		if (this.error == null) {
			this.error = new RESTError2();
		}
		this.error.setCode(basic.code());
		this.error.setMessage(basic.description());
		this.error.setStatus(basic.name());
		if (advance.code() > 0) {
			this.error.addErrors(new RESTError2Detail(advance.code(), advance.description()));
		}
	}

	public RESTResponseError2 putErrors(RESTErrorInterface define) {
		this.error.addErrors(new RESTError2Detail(define.code(), define.description()));
		return this;
	}

	public RESTResponseError2 putErrors(RESTError2Detail define) {
		this.error.addErrors(define);
		return this;
	}

	public String toJsonString() {
		return RESTJsonUtil.GSON.toJson(this);
	}

	public void doResponse(HttpServletResponse resp) throws IOException {
		resp.setStatus(this.error.getCode());
		resp.getWriter().println(RESTJsonUtil.GSON.toJson(this));
	}

}
