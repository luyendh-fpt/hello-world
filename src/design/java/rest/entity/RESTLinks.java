package design.java.rest.entity;

import java.util.HashMap;

/**
 * A links member can be used to represent links.<br>
 * 
 * @see <a
 *      href="http://jsonapi.org/format/#document-links">http://jsonapi.org/format/#document-links</a>
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 16, 2015 10:49:09 AM
 *
 */
public class RESTLinks {
	/*
	 * Must contain this field.
	 */
	private String self;
	private String first;
	private String prev;
	private String next;
	private String last;

	/*
	 * Or 2 fields below, not all at once.
	 */
	private String href;
	private HashMap<String, Object> meta;

	public RESTLinks() {

	}

	/**
	 * @param builder
	 */
	RESTLinks(Builder builder) {
		super();
		this.self = builder.self;
		this.first = builder.first;
		this.prev = builder.prev;
		this.next = builder.next;
		this.last = builder.last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getPrev() {
		return prev;
	}

	public void setPrev(String prev) {
		this.prev = prev;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public HashMap<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(HashMap<String, Object> meta) {
		this.meta = meta;
	}

	public static class Builder {
		String self;
		String first;
		String prev;
		String next;
		String last;

		String href;
		HashMap<String, Object> meta;

		public Builder setSelf(String self) {
			this.self = self;
			return this;
		}

		public Builder setFirst(String first) {
			this.first = first;
			return this;
		}

		public Builder setPrev(String prev) {
			this.prev = prev;
			return this;
		}

		public Builder setNext(String next) {
			this.next = next;
			return this;
		}

		public Builder setLast(String last) {
			this.last = last;
			return this;
		}

		public Builder setHref(String href) {
			this.href = href;
			return this;
		}

		public Builder addMeta(String key, Object value) {
			if (this.meta == null) {
				this.meta = new HashMap<String, Object>();
			}
			this.meta.put(key, value);
			return this;
		}

		public Builder setMeta(HashMap<String, Object> meta) {
			this.meta = meta;
			return this;
		}

		public RESTLinks build() {
			return new RESTLinks(this);
		}

	}

}