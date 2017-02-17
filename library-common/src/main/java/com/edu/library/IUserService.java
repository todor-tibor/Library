package com.edu.library;

import com.edu.library.model.User;


public interface IUserService extends IService<User> {
public User getByUserName(String userName) throws LibraryException;
}
