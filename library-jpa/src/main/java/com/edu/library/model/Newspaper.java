package com.edu.library.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent subclass for the publication database table, with
 * discriminator value Newspaper.
 *
 * @author sipost
 * @author kiska
 *
 */
@XmlRootElement(name = "Newspaper")
@Entity
@DiscriminatorValue("Newspaper")
public class Newspaper extends Publication {

	private static final long serialVersionUID = -7358930538078727479L;

}
