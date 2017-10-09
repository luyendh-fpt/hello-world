package design.java.rest.entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contain resource object data. "Resource objects" appear in a JSON
 * API document to represent resources.<br>
 * 
 * @see <a href="http://jsonapi.org/format/#document-resource-objects"
 *      >http://jsonapi.org/format/#document-resource-objects</a>
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 16, 2015 10:16:05 AM
 *
 */
public class RESTResourceRelationship {

	private ArrayList<RESTResource> data;

	private HashMap<String, Object> meta;

	private RESTLinks links;

	public ArrayList<RESTResource> getData() {
		return data;
	}

	public void setData(ArrayList<RESTResource> data) {
		this.data = data;
	}

	public void addData(RESTResource data) {
		if (this.data == null) {
			this.data = new ArrayList<RESTResource>();
		}
		this.data.add(data);
	}

	public HashMap<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(HashMap<String, Object> meta) {
		this.meta = meta;
	}

	public RESTLinks getLinks() {
		return links;
	}

	public void setLinks(RESTLinks links) {
		this.links = links;
	}

}
