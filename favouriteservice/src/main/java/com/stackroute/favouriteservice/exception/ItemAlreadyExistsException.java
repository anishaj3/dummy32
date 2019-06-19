package com.stackroute.favouriteservice.exception;

@SuppressWarnings("serial")
public class ItemAlreadyExistsException extends Exception{
	
	public ItemAlreadyExistsException(String message) {
		super(message);
	}

}
