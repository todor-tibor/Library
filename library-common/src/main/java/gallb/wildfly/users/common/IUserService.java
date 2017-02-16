package gallb.wildfly.users.common;

import model.User;


public interface IUserService extends IService<User> {
public User getByUserName(String userName) throws LibraryException;
}
