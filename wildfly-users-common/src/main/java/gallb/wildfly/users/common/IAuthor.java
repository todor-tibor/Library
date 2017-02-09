package gallb.wildfly.users.common;

import model.Author;

public interface IAuthor extends IEntity<Author> {
	public static final String jndiNAME = "java:global/wildfly-users-ear-0.0.1-SNAPSHOT/wildfly-users-ejb-0.0.1-SNAPSHOT/UserBean";

}
