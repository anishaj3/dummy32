package com.stackroute.favouriteservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.favouriteservice.exception.ItemAlreadyExistsException;
import com.stackroute.favouriteservice.exception.ItemNotFoundException;
import com.stackroute.favouriteservice.model.FoodApp;
import com.stackroute.favouriteservice.repository.FoodAppRepository;

@Service
public class FoodAppServiceImpl implements FoodAppService {
	
	@Autowired
	public final FoodAppRepository repo;
	
	

	public FoodAppServiceImpl(FoodAppRepository repo) {
		super();
		this.repo = repo;
	}

	@Override
	public boolean saveItem(FoodApp item) throws ItemAlreadyExistsException {
		final Optional<FoodApp> foodItem = repo.findById(item.getItemId());  
		if(foodItem.isPresent()) {
			throw new ItemAlreadyExistsException("Item Already Exits in List");
		}
		repo.save(item);
		return true;
	}

	@Override
	public FoodApp updateItem(FoodApp item) throws ItemNotFoundException {
		
		final FoodApp foodItem = repo.findById(item.getItemId()).orElse(null);
		if(foodItem == null) {
			throw new ItemNotFoundException("Update failed,Item not found");
		}
		foodItem.setComments(item.getComments());
		repo.save(foodItem);
		return foodItem;
	}

	@Override
	public boolean deleteItem(int id) throws ItemNotFoundException {
		final FoodApp foodItem = repo.findById(id).orElse(null);
		if(foodItem == null) {
		throw new ItemNotFoundException("Deletion failed,Item not found");
		}
		repo.delete(foodItem);
		return true;
	}

	@Override
	public FoodApp getItemById(int id) throws ItemNotFoundException {
		final FoodApp item = repo.findById(id).orElse(null);
		if(item == null) {
			throw new ItemNotFoundException("Cannot Found Item");
		}
		return item;
	}

	@Override
	public List<FoodApp> getMyItems(String userId) {
		
		return repo.findByUserId(userId);
	}

}
