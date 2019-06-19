package com.stackroute.userservice.service;

import java.util.Map;

import com.stackroute.userservice.model.User;

public interface SecurityTokenGenerator {
	
	Map<String,String> generateToken(User user);

}
