package design.java.rest.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;

import design.java.rest.RESTHandle;
import design.java.rest.util.RESTJsonUtil;

/**
 * This class contain resource object data. "Resource objects" appear in a JSON
 * API document to represent resources.<br>
 * 
 * @see <a href="http://jsonapi.org/format/#document-resource-objects" >http://
 *      jsonapi.org/format/#document-resource-objects</a>
 *
 * @author xuanhung2401@gmail.com
 *
 * @doc Sep 16, 2015 10:16:05 AM
 *
 */
public class RESTResource {

	private static final Logger LOGGER = Logger.getLogger(RESTHandle.class.getName());

	private String type;
	private String id;
	private HashMap<String, Object> meta;
	private String links;
	private HashMap<String, Object> attributes;
	private ArrayList<RESTResourceRelationship> relationships;

	public void addAttribute(String name, Object value) {
		if (this.attributes == null) {
			this.attributes = new HashMap<String, Object>();
		}
		this.attributes.put(name, value);
	}

	public ArrayList<RESTResourceRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(ArrayList<RESTResourceRelationship> relationships) {
		this.relationships = relationships;
	}

	public void addRelationships(RESTResourceRelationship relationship) {
		if (this.relationships == null) {
			this.relationships = new ArrayList<RESTResourceRelationship>();
		}
		this.relationships.add(relationship);
	}

	public void addRelationships(RESTResource resourceRelationship) {
		if (this.relationships == null) {
			this.relationships = new ArrayList<RESTResourceRelationship>();
		}
		RESTResourceRelationship rel = new RESTResourceRelationship();
		rel.addData(resourceRelationship);
		this.relationships.add(rel);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(HashMap<String, Object> meta) {
		this.meta = meta;
	}

	public String getLinks() {
		return links;
	}

	public void setLinks(String links) {
		this.links = links;
	}

	public HashMap<String, Object> getAttributes() {
		return attributes;
	}

	public String getAttributesString() {
		return RESTJsonUtil.GSON.toJson(this.attributes);
	}

	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String toJsonString() {
		return RESTJsonUtil.GSON.toJson(this);
	}

	/**
	 * This function transform an Object class to RESTResource Object. All of
	 * class fields will be added to Resource attributes HashMap.
	 * 
	 * @param <T>
	 *            type of object.
	 * 
	 * @param object
	 *            Object to transform.
	 */
	public static <T> RESTResource getInstance(T object) {
		RESTResource resource = new RESTResource();
		resource.setType(object.getClass().getSimpleName());
		try {
			for (Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				/**
				 * Check if field is marked as Annotation.
				 */
				if (field.isAnnotationPresent(com.googlecode.objectify.annotation.Id.class)
						|| field.isAnnotationPresent(RESTAnnotation.Id.class)) {
					resource.setId(String.valueOf(field.get(object)));
				} else {
					if (!field.isAnnotationPresent(RESTAnnotation.NotResponse.class)) {
						resource.addAttribute(field.getName(), field.get(object));
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOGGER.severe(e.getMessage());
		}
		return resource;
	}

	public static <T> RESTResource getInstanceByVersion(T object, double version) {
		RESTResource resource = new RESTResource();
		resource.setType(object.getClass().getSimpleName());
		try {
			for (Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				/**
				 * Check if field is marked as Annotation.
				 */
				if (field.isAnnotationPresent(com.googlecode.objectify.annotation.Id.class)
						|| field.isAnnotationPresent(RESTAnnotation.Id.class)) {
					resource.setId(String.valueOf(field.get(object)));
				} else {
					if (!field.isAnnotationPresent(RESTAnnotation.NotResponse.class)) {
						if (field.isAnnotationPresent(Since.class)) {
							if (field.getAnnotation(Since.class).value() > version) {
								continue;
							}
						}
						resource.addAttribute(field.getName(), field.get(object));
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOGGER.severe(e.getMessage());
		}
		return resource;
	}

	public static <T> RESTResource getExposeInstance(T object) {
		RESTResource resource = new RESTResource();
		resource.setType(object.getClass().getSimpleName());
		try {
			for (Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				/**
				 * Check if field is marked as Annotation.
				 */
				if (field.isAnnotationPresent(com.googlecode.objectify.annotation.Id.class)
						|| field.isAnnotationPresent(RESTAnnotation.Id.class)) {
					resource.setId(String.valueOf(field.get(object)));
				} else {
					if (field.isAnnotationPresent(Expose.class)) {
						resource.addAttribute(field.getName(), field.get(object));
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOGGER.severe(e.getMessage());
		}
		return resource;
	}

	/**
	 * This function transform an existing RESTResource Object to Object class
	 * by type.
	 * 
	 * @param type
	 *            Type of class will be return.
	 * @throws JsonSyntaxException
	 */
	public <T> T getInstance(Class<T> type) throws JsonSyntaxException {
		if (this.id != null && this.id.length() > 0) {
			String fieldName = null;
			for (Field field : type.getDeclaredFields()) {
				field.setAccessible(true);
				/**
				 * Check if field is marked as Annotation.
				 */
				if (field.isAnnotationPresent(com.googlecode.objectify.annotation.Id.class)
						|| field.isAnnotationPresent(RESTAnnotation.Id.class)) {
					fieldName = field.getName();
				}
			}
			if (fieldName != null) {
				this.addAttribute(fieldName, this.id);
			}

		}
		return RESTJsonUtil.GSON.fromJson(this.getAttributesString(), type);
	}

	public <T> T getInstanceWithLongPolicy(Class<T> type) throws JsonSyntaxException {
		if (this.id != null && this.id.length() > 0) {
			String fieldName = null;
			for (Field field : type.getDeclaredFields()) {
				field.setAccessible(true);
				/**
				 * Check if field is marked as Annotation.
				 */
				if (field.isAnnotationPresent(com.googlecode.objectify.annotation.Id.class)
						|| field.isAnnotationPresent(RESTAnnotation.Id.class)) {
					fieldName = field.getName();
				}
			}
			if (fieldName != null) {
				this.addAttribute(fieldName, this.id);
			}

		}
		return RESTJsonUtil.GSON2.fromJson(this.getAttributesString(), type);
	}

	public static RESTResource getInstance(HashMap<String, Object> object) {
		RESTResource resource = new RESTResource();
		resource.setType(object.getClass().getSimpleName());
		for (Entry<String, Object> entry : object.entrySet()) {
			resource.addAttribute(entry.getKey(), entry.getValue());
		}
		return resource;
	}

}