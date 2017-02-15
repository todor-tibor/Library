package gallb.wildfly.users.common;

import model.User;

public interface IUser extends IEntity<User> {	
	public User getByUserName(String userName) throws LibraryException;
}
