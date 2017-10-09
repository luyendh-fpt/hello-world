package design.java.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import design.java.rest.entity.RESTDocumentMulti;
import design.java.rest.entity.RESTDocumentSingle;
import design.java.rest.entity.RESTJsonApi;
import design.java.rest.entity.RESTLinks;
import design.java.rest.entity.RESTResource;
import design.java.rest.util.RESTJsonUtil;

/**
 * This class used to build response's content before sending to REST Api
 * client. Content is formatted following jsonapi format below.<br>
 * 
 * @see <a href="http://jsonapi.org/format/">http://jsonapi.org/format/</a>
 * 
 * @author xuanhung2401@gmail.com
 * 
 * @doc Sep 16, 2015 9:40:25 AM
 *
 */
public class RESTResponseSuccess extends RESTResponse {

	public RESTResponseSuccess(RESTGeneralSuccess define) {
		this.code = define.code();
	}

	private ArrayList<RESTResource> data;

	private HashMap<String, Object> meta;

	private RESTLinks links;

	private RESTJsonApi jsonapi;

	private ArrayList<RESTResource> included;

	private boolean isMulti = false;

	void buildResponseDocument() {
		if (this.data != null && this.data.size() > 0) {
			if (this.data.size() == 1 && !isMulti) {
				this.document = new RESTDocumentSingle.Builder().setData(this.data.get(0)).setMeta(this.meta)
						.setLinks(this.links).setJsonApi(this.jsonapi).setIncluded(this.included).build();
			} else {
				this.document = new RESTDocumentMulti.Builder().setData(this.data).setMeta(this.meta)
						.setLinks(this.links).setJsonApi(this.jsonapi).setIncluded(this.included).build();
			}
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
		LOGGER.info(String.format("-----------------------%s---------------------", "Response Data"));
		LOGGER.info(RESTJsonUtil.GSON.toJson(this.document));
		resp.getWriter().println(RESTJsonUtil.GSON.toJson(this.document));
	}

	/**
	 * Put a single data to response document.
	 * 
	 * @param value
	 *            all kind of classes. But MUST be a single object.
	 */
	public <T> RESTResponseSuccess putData(T value) {
		if (this.data == null) {
			this.data = new ArrayList<RESTResource>();
		}
		if (value != null) {
			this.data.add(RESTResource.getInstance(value));
		}
		return this;
	}

	public <T> RESTResponseSuccess putData(RESTResource value) {
		if (this.data == null) {
			this.data = new ArrayList<RESTResource>();
		}
		if (value != null) {
			this.data.add(value);
		}
		return this;
	}

	public <T> RESTResponseSuccess putData(T value, double version) {
		if (this.data == null) {
			this.data = new ArrayList<RESTResource>();
		}
		if (value != null) {
			this.data.add(RESTResource.getInstanceByVersion(value, version));
		}
		return this;
	}

	public <T> RESTResponseSuccess putExposeData(T value) {
		if (this.data == null) {
			this.data = new ArrayList<RESTResource>();
		}
		if (value != null) {
			this.data.add(RESTResource.getExposeInstance(value));
		}
		return this;
	}

	/**
	 * Put many data to response document.
	 * 
	 * @param listValue
	 *            MUST be a collection.
	 */
	public <T> RESTResponseSuccess putData(Collection<T> listValue) {
		this.isMulti = true;
		if (this.data == null) {
			this.data = new ArrayList<RESTResource>();
		}
		if (listValue != null && !listValue.isEmpty()) {
			for (T value : listValue) {
				this.data.add(RESTResource.getInstance(value));
			}
		}
		return this;
	}

	/**
	 * Put many data to response document. Use this way to put data if you don't
	 * know exactly how many object will be put. This function use
	 * "varargs parameter".
	 * 
	 * @param values
	 *            a collection that can't estimate size.
	 */
	@SuppressWarnings("unchecked")
	public <T> RESTResponseSuccess putData(T... values) {
		this.isMulti = true;
		if (this.data == null) {
			this.data = new ArrayList<RESTResource>();
		}
		for (T value : values) {
			this.data.add(RESTResource.getInstance(value));
		}
		return this;
	}

	/**
	 * Put a single include to response document.
	 * 
	 * @param value
	 *            all kind of classes. But MUST be a single object.
	 */
	public <T> RESTResponseSuccess putIncluded(T value) {
		if (this.included == null) {
			this.included = new ArrayList<RESTResource>();
		}
		if (value != null) {
			this.included.add(RESTResource.getInstance(value));
		}
		return this;
	}

	public <T> RESTResponseSuccess putIncluded(RESTResource value) {
		if (this.included == null) {
			this.included = new ArrayList<RESTResource>();
		}
		if (value != null) {
			this.included.add(value);
		}
		return this;
	}

	public <T> RESTResponseSuccess putIncluded(Collection<T> listValue) {
		if (this.included == null) {
			this.included = new ArrayList<RESTResource>();
		}
		if (listValue != null && !listValue.isEmpty()) {
			for (T value : listValue) {
				this.included.add(RESTResource.getInstance(value));
			}
		}
		return this;
	}

	public RESTResponseSuccess putMeta(String key, Object value) {
		if (this.meta == null) {
			this.meta = new HashMap<String, Object>();
		}
		this.meta.put(key, value);
		return this;
	}

	public RESTResponseSuccess putLinks(RESTLinks links) {
		this.links = links;
		return this;
	}

	public RESTResponseSuccess putJsonapi(String key, Object value) {
		return this;
	}

	public RESTResponseSuccess putIncluded(String key, Object value) {
		return this;
	}

}
