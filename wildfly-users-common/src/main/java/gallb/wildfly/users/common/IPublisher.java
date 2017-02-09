/**
 * 
 */
package gallb.wildfly.users.common;

import model.Publisher;

/**
 * @author nagys
 *
 */
public interface IPublisher extends IEntity<Publisher> {
	public static final String jndiNAME = "java:global/wildfly-users-ear-0.0.1-SNAPSHOT/wildfly-users-ejb-0.0.1-SNAPSHOT/PublisherBean";
}
