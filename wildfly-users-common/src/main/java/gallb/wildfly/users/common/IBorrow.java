/**
 * 
 */
package gallb.wildfly.users.common;

import model.Borrow;

/**
 * @author nagys
 *
 */
public interface IBorrow extends IEntity<Borrow>{
	public static final String jndiNAME = "java:global/wildfly-users-ear-0.0.1-SNAPSHOT/wildfly-users-ejb-0.0.1-SNAPSHOT/BorrowManager";

}
