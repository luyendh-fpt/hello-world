package design.java.rest.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RESTAnnotation {
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Id {

	}

	@Retention(RetentionPolicy.RUNTIME)
	public @interface NotResponse {

	}
}
