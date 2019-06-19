package com.stackroute.favouriteservice.service;

import java.util.List;

import com.stackroute.favouriteservice.exception.ItemAlreadyExistsException;
import com.stackroute.favouriteservice.exception.ItemNotFoundException;
import com.stackroute.favouriteservice.model.FoodApp;





public interface FoodAppService {
	
	boolean saveItem(FoodApp item) throws ItemAlreadyExistsException;
	
	FoodApp updateItem(FoodApp item) throws ItemNotFoundException;
	
	boolean deleteItem(int id) throws ItemNotFoundException;
	
	FoodApp getItemById(int id) throws ItemNotFoundException;
	
	List<FoodApp> getMyItems(String UserId);

}
