package edu.com.library.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The persistent subclass for the publication database table, with discriminator value Newspaper.
 * 
 * @author sipost
 * @author kiska
 *
 */
@Entity
@DiscriminatorValue("Newspaper")
public class Newspaper extends Publication {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7358930538078727479L;

}
