package com.edu.library.model.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.edu.library.model.BaseEntity;

/**
 * Wrapper class for list of entities using for Marshall and unmarshall.
 *
 * @author sipost
 *
 * @param <T>
 *            - Type of entities
 */
@XmlRootElement(name = "EntityList")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityList<T extends BaseEntity> {

	@XmlAnyElement
	private List<T> entities = new ArrayList<T>();

	public EntityList() {
	}

	public EntityList(final List<T> entities) {
		this.entities = entities;
	}

	public List<T> getEntities() {
		return this.entities;
	}

	public void setEntities(final List<T> entities) {
		this.entities = entities;
	}

}
