package com.edu.library.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sipost
 * @author gallb
 * @author kiska
 */
@XmlRootElement(name = "BaseEntity")
@XmlAccessorType(XmlAccessType.FIELD)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 3333408811149331428L;

	@XmlElement
	@Id
	/**
	 * @param uuid
	 *            - A unique identification string for the object
	 */
	private String uuid;

	public String getUuid() {
		ensureUuid();
		return this.uuid;
	}

	public void setUuid(final String uuid) {
		this.uuid = uuid;
	}

	@PrePersist
	public void onPrePersist() {
		ensureUuid();
	}

	/**
	 * Generate a random and unique value for the {@code uuid}
	 */
	private void ensureUuid() {
		if (this.uuid == null) {
			setUuid(UUID.randomUUID().toString());
		}
	}
}
