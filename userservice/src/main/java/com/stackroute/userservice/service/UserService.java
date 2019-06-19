package com.stackroute.userservice.service;

import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.model.User;

public interface UserService {
	boolean saveUser(User user) throws UserAlreadyExistsException;
	public User findByUserIdAndPassword(String userId,String password) throws UserNotFoundException;

}
