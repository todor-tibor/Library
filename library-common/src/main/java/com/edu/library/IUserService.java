package com.edu.library;

import edu.com.library.model.User;


public interface IUserService extends IService<User> {
public User getByUserName(String userName) throws LibraryException;
}
