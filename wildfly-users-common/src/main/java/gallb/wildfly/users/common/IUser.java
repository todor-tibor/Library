package gallb.wildfly.users.common;

import model.User;

<<<<<<< HEAD

public interface IUser extends IEntity<User> {
public User getByUserName(String userName) throws LibraryException;
=======
public interface IUser extends IEntity<User> {	
	public User getByUserName(String userName) throws LibraryException;
>>>>>>> branch 'workingLogin' of https://github.com/todor-tibor/Library.git
}
