package design.java.rest.entity;

import java.util.HashMap;

/**
 * Error objects provide additional information about problems encountered while
 * performing an operation.<br>
 * 
 * @see <a
 *      href="http://jsonapi.org/format/#errors">http://jsonapi.org/format/#errors</a>
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 16, 2015 10:16:05 AM
 *
 */
public class RESTError {
	/**
	 * A unique identifier for this particular occurrence of the problem.
	 */
	private String id;
	/**
	 * A link that leads to further details about this particular occurrence of
	 * the problem.
	 */
	private HashMap<String, Object> links;
	private String status;
	private String code;
	/**
	 * A short, human-readable summary of the problem that SHOULD NOT change
	 * from occurrence to occurrence of the problem, except for purposes of
	 * localization.
	 */
	private String title;
	/**
	 * A human-readable explanation specific to this occurrence of the problem.
	 */
	private String detail;
	/**
	 * An object containing references to the source of the error.
	 */
	private RESTSource source;
	/**
	 * A meta object containing non-standard meta-information about the error.
	 */
	private HashMap<String, Object> meta;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, Object> getLinks() {
		return links;
	}

	public void setLinks(HashMap<String, Object> links) {
		this.links = links;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public RESTSource getSource() {
		return source;
	}

	public void setSource(RESTSource source) {
		this.source = source;
	}

	public HashMap<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(HashMap<String, Object> meta) {
		this.meta = meta;
	}

	protected RESTError(Builder builder) {
		super();
		this.id = builder.id;
		this.links = builder.links;
		this.status = builder.status;
		this.code = builder.code;
		this.title = builder.title;
		this.detail = builder.detail;
		this.source = builder.source;
		this.meta = builder.meta;
	}

	public static class Builder {

		String id;
		HashMap<String, Object> links;
		String status;
		String code;
		String title;
		String detail;
		RESTSource source;
		HashMap<String, Object> meta;

		public Builder setId(String id) {
			this.id = id;
			return this;
		}

		public Builder addLinks(String key, Object value) {
			if (this.links == null) {
				this.links = new HashMap<String, Object>();
			}
			this.links.put(key, value);
			return this;
		}

		public Builder setLinks(HashMap<String, Object> links) {
			this.links = links;
			return this;
		}

		public Builder setStatus(String status) {
			this.status = status;
			return this;
		}

		public Builder setCode(String code) {
			this.code = code;
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setDetail(String detail) {
			this.detail = detail;
			return this;
		}

		public Builder setSource(RESTSource source) {
			this.source = source;
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

		public RESTError build() {
			return new RESTError(this);
		}

	}

}
