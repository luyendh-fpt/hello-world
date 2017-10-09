package design.java.rest.entity;

public class RESTError2Detail {

	private int applicationCode;
	private String reason;
	private String message;
	private String locationType;
	private String location;

	public RESTError2Detail(int applicationCode, String reason) {
		super();
		if (applicationCode > 0) {
			this.applicationCode = applicationCode;
		}
		this.reason = reason;
	}

	public RESTError2Detail(String reason) {
		super();
		this.reason = reason;
	}

	public RESTError2Detail() {
		super();
	}

	public int getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(int applicationCode) {
		this.applicationCode = applicationCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	protected RESTError2Detail(Builder builder) {
		super();
		this.applicationCode = builder.applicationCode;
		this.reason = builder.reason;
		this.message = builder.message;
		this.locationType = builder.locationType;
		this.location = builder.location;
	}

	public static class Builder {
		private int applicationCode;
		private String reason;
		private String message;
		private String locationType;
		private String location;

		public int getApplicationCode() {
			return applicationCode;
		}

		public Builder setApplicationCode(int applicationCode) {
			this.applicationCode = applicationCode;
			return this;
		}

		public String getReason() {
			return reason;
		}

		public Builder setReason(String reason) {
			this.reason = reason;
			return this;
		}

		public String getMessage() {
			return message;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public String getLocationType() {
			return locationType;
		}

		public Builder setLocationType(String locationType) {
			this.locationType = locationType;
			return this;
		}

		public String getLocation() {
			return location;
		}

		public Builder setLocation(String location) {
			this.location = location;
			return this;
		}

		public RESTError2Detail build() {
			return new RESTError2Detail(this);
		}

	}

}
