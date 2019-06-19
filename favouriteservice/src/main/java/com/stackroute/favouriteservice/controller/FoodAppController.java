package com.stackroute.favouriteservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.favouriteservice.exception.ItemAlreadyExistsException;
import com.stackroute.favouriteservice.exception.ItemNotFoundException;
import com.stackroute.favouriteservice.model.FoodApp;
import com.stackroute.favouriteservice.service.FoodAppService;

import io.jsonwebtoken.Jwts;

@RestController
@CrossOrigin
@RequestMapping("/api/foodApp")
public class FoodAppController {

	@Autowired
	private FoodAppService foodAppService;
	
	/*
	 * //@Autowired public FoodAppController(final FoodAppService foodAppService) {
	 * this.foodAppService = foodAppService; }
	 */

	@PostMapping
	public ResponseEntity<?> saveNewItem(@RequestBody final FoodApp item, HttpServletRequest request) {
		final String authHeader = request.getHeader("authorization");
		System.out.println(authHeader);
		final String token = authHeader.substring(7);
		System.out.println(token);
		String userId = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody().getSubject();
		System.out.println(item);
		System.out.println(userId);
		try {
			item.setUserId(userId);
			foodAppService.saveItem(item);
			return new ResponseEntity<FoodApp>(item, HttpStatus.CREATED);
		} catch (ItemAlreadyExistsException e) {
			return new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}

	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateItem(@PathVariable("id") final Integer id, @RequestBody FoodApp item){
		try {
			FoodApp foodItem = foodAppService.updateItem(item);
			return new ResponseEntity<FoodApp>(foodItem, HttpStatus.OK);
		}catch(ItemNotFoundException e) {
			return new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		
	}
	
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable("id") final Integer id){
		try {
			foodAppService.deleteItem(id);
			return new ResponseEntity<String>("Item Deleted Sucessfully", HttpStatus.OK);
		}catch(ItemNotFoundException e) {
			return new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> fetchItemById(@PathVariable("id") final Integer id) {
		try {
			FoodApp foodItem = foodAppService.getItemById(id);
			return new ResponseEntity<FoodApp>(foodItem, HttpStatus.OK);
		}catch(ItemNotFoundException e) {
			return new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<FoodApp>> fetchAllMovies(HttpServletRequest request) throws ItemNotFoundException {
		final String authHeader = request.getHeader("authorization");
		final String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody().getSubject();
		return new ResponseEntity<List<FoodApp>>(foodAppService.getMyItems(userId), HttpStatus.OK);
	}
	

}
