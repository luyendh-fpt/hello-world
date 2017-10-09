package design.java.rest.entity;

/**
 * Farther of all kind of errors. Every child need code() and description()
 * method.
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 21, 2015 5:02:38 PM
 *
 */
public interface RESTErrorInterface {

	int code();

	String description();
}
