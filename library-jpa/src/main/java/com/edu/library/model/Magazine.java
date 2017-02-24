package com.edu.library.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * The persistent subclass for the publication database table, with
 * discriminator value Magazine.
 * 
 * @author sipost
 * @author kiska
 *
 */
@Entity
@DiscriminatorValue("Magazine")
public class Magazine extends Publication {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7358930538078727479L;

	// uni-directional many-to-many association to Author
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "publication_authors", joinColumns = @JoinColumn(name = "publication_id", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "uuid"))
	private List<Author> authors;

	public List<Author> getAuthors() {
		return this.authors;
	}

	public void setAuthors(final List<Author> authors) {
		this.authors = authors;
	}
}
