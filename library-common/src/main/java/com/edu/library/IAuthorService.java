package com.edu.library;

import com.edu.library.model.Author;

/**
 * Defines persistence operations, for Author entities.
 *
 * @author sipost
 *
 */
public interface IAuthorService extends IService<Author> {
	public static final String jndiNAME = "java:global/library-ear-0.0.1-SNAPSHOT/library-ejb-0.0.1-SNAPSHOT/AuthorBean";
}
