package com.edu.library.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The persistent subclass for the publication database table, with
 * discriminator value Magazine.
 *
 * @author sipost
 * @author kiska
 *
 */

@XmlRootElement(name = "Magazine")
@Entity
@DiscriminatorValue("Magazine")
public class Magazine extends Publication {

	private static final long serialVersionUID = -7358930538078727479L;

	// uni-directional many-to-many association to Author
	@XmlElement(name = "Author")
	@XmlElementWrapper(name = "Authors")
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "publication_authors", joinColumns = @JoinColumn(name = "publication_id", referencedColumnName = "uuid"), inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "uuid"))
	private List<Author> authors;

	public List<Author> getAuthors() {
		return this.authors;
	}

	public void setAuthors(final List<Author> authors) {
		this.authors = authors;
	}
}
