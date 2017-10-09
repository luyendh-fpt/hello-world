package design.java.rest.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.labs.repackaged.org.json.JSONException;

import design.java.rest.util.RESTJsonUtil;

/**
 * Root of every JSON API request and response containing data. This object
 * defines a document's "top level". This class included data as an array of
 * Resource Object.<br>
 * 
 * @see <a
 *      href="http://jsonapi.org/format/#document-top-level">http://jsonapi.org/format/#document-top-level</a>
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 16, 2015 10:16:05 AM
 *
 */
public class RESTDocumentMulti implements RESTDocument {

	public RESTDocumentMulti() {
		super();
	}

	/*
	 * MUST
	 */
	private ArrayList<RESTResource> data;
	private HashMap<String, Object> meta;
	/*
	 * One live, One death with field "data".
	 */
	private ArrayList<RESTError> errors;
	/*
	 * MAY
	 */
	private RESTLinks links;
	private RESTJsonApi jsonapi;
	private ArrayList<RESTResource> included;

	/**
	 * This function used to read all data from request input stream and cast to
	 * RESTDocument as an array of RESTResource object.
	 * */
	public static RESTDocumentMulti getInstanceFromRequest(HttpServletRequest req) throws IOException, JSONException {
		return RESTJsonUtil.GSON.fromJson(RESTJsonUtil.parseStringInputStream(req.getInputStream()), RESTDocumentMulti.class);

	}

	public HashMap<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(HashMap<String, Object> meta) {
		this.meta = meta;
	}

	public ArrayList<RESTError> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<RESTError> errors) {
		this.errors = errors;
	}

	public RESTLinks getLinks() {
		return links;
	}

	public void setLinks(RESTLinks links) {
		this.links = links;
	}

	public RESTJsonApi getJsonapi() {
		return jsonapi;
	}

	public void setJsonapi(RESTJsonApi jsonapi) {
		this.jsonapi = jsonapi;
	}

	public ArrayList<RESTResource> getIncluded() {
		return included;
	}

	public void setIncluded(ArrayList<RESTResource> included) {
		this.included = included;
	}

	public void addIncluded(RESTResource included) {
		if (this.included == null) {
			this.included = new ArrayList<RESTResource>();
		}
		this.included.add(included);

	}

	public ArrayList<RESTResource> getData() {
		return data;
	}

	public void setData(ArrayList<RESTResource> data) {
		this.data = data;
	}

	protected RESTDocumentMulti(Builder builder) {
		super();
		this.data = builder.data;
		this.meta = builder.meta;
		this.errors = builder.errors;
		this.links = builder.links;
		this.jsonapi = builder.jsonapi;
		this.included = builder.included;

	}

	public static class Builder {

		ArrayList<RESTResource> data;
		HashMap<String, Object> meta;
		ArrayList<RESTError> errors;
		RESTLinks links;
		RESTJsonApi jsonapi;
		ArrayList<RESTResource> included;

		public Builder setData(ArrayList<RESTResource> data) {
			this.data = data;
			return this;
		}

		public Builder setMeta(HashMap<String, Object> meta) {
			this.meta = meta;
			return this;
		}

		public Builder addMeta(String name, Object value) {
			if (this.meta == null) {
				this.meta = new HashMap<String, Object>();
			}
			this.meta.put(name, value);
			return this;
		}

		public Builder setErrors(ArrayList<RESTError> errors) {
			this.errors = errors;
			return this;
		}

		public Builder addError(RESTError error) {
			if (this.errors == null) {
				this.errors = new ArrayList<RESTError>();
			}
			this.errors.add(error);
			return this;
		}

		public Builder setLinks(RESTLinks links) {
			this.links = links;
			return this;
		}

		public Builder setJsonApi(RESTJsonApi jsonapi) {
			this.jsonapi = jsonapi;
			return this;
		}

		public Builder setIncluded(ArrayList<RESTResource> included) {
			this.included = included;
			return this;
		}

		public Builder addIncluded(RESTResource included) {
			if (this.included == null) {
				this.included = new ArrayList<RESTResource>();
			}
			this.included.add(included);
			return this;
		}

		public RESTDocumentMulti build() {
			return new RESTDocumentMulti(this);
		}

	}

}
