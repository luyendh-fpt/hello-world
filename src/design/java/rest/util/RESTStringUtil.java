package design.java.rest.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * String util functions.
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 15, 2015 4:35:48 PM
 *
 */
public class RESTStringUtil {

	/**
	 * This fucntion join all collection string item to a string seperate by a
	 * character that developer defined. This function will not append null or
	 * empty element.
	 * 
	 * @param collection
	 *            collection string to join.
	 * @param seperator
	 *            seperate character.
	 * 
	 * */
	public static String joinCollectionString(Collection<String> collection, String seperator) {
		if (collection.isEmpty())
			return "";

		Iterator<String> iter = collection.iterator();
		StringBuilder sb = new StringBuilder(iter.next());
		while (iter.hasNext()) {
			String next = iter.next();
			if (null != next && !next.isEmpty()) {
				sb.append(seperator);
				sb.append(next);
			}
		}
		return sb.toString();
	}

}
