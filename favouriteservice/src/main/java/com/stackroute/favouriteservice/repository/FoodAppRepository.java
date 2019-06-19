package com.stackroute.favouriteservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stackroute.favouriteservice.model.FoodApp;

public interface FoodAppRepository extends JpaRepository<FoodApp, Integer>{
	
	List<FoodApp> findByUserId(String userId);

}
