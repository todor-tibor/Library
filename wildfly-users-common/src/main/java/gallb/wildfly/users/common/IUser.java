package gallb.wildfly.users.common;

import java.util.List;

import model.User;


public interface IUser extends IEntity<User>{
	public static final String jndiNAME="java:global/wildfly-users-ear-0.0.1-SNAPSHOT/wildfly-users-ejb-0.0.1-SNAPSHOT/UserBean";

	

}
