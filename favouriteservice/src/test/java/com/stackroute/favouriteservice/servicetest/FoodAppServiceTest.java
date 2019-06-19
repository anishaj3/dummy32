package com.stackroute.favouriteservice.servicetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stackroute.favouriteservice.exception.ItemAlreadyExistsException;
import com.stackroute.favouriteservice.exception.ItemNotFoundException;
import com.stackroute.favouriteservice.model.FoodApp;
import com.stackroute.favouriteservice.repository.FoodAppRepository;
import com.stackroute.favouriteservice.service.FoodAppServiceImpl;

public class FoodAppServiceTest {
	
	@Mock
	private transient FoodAppRepository itemRepo;
	

	private transient FoodApp item;
	
	@InjectMocks
	private transient FoodAppServiceImpl itemServiceImpl;

	transient Optional<FoodApp> items;

	@Before
	public void setUpMock() 
	{
		MockitoAnnotations.initMocks(this);
		item = new FoodApp(1, "Bread", "1234", "vinod", "Average");
		items = Optional.of(item);
	}

	@Test
	public void testMockCreation() 
	{
		assertNotNull("JPa repository creation fails, use imnject mocks over movieServiceImpl", item);
	}

	@Test
	public void testSaveItemSuccess() throws ItemAlreadyExistsException{
		when(itemRepo.save(item)).thenReturn(item);
		final boolean flag = itemServiceImpl.saveItem(item);
		assertTrue("Saving Item failed, the call to FoodAppServiceImpl failed, please check", flag);
		verify(itemRepo, times(1)).save(item);
		verify(itemRepo, times(1)).findById(item.getItemId());

	}

	@Test(expected = ItemAlreadyExistsException.class)
	public void testSaveItemFailure() throws ItemAlreadyExistsException{
		when(itemRepo.findById(1)).thenReturn(items);
		when(itemRepo.save(item)).thenReturn(item);
		final boolean flag = itemServiceImpl.saveItem(item);
		assertFalse("Saving Item failed", flag);
		verify(itemRepo, times(1)).findById(item.getItemId());
		


	}

	public void testupdatFoodItem() throws ItemNotFoundException{
		
		when(itemRepo.findById(1)).thenReturn(items);
		when(itemRepo.save(item)).thenReturn(item);
		item.setComments("good");
		final FoodApp foodItem1 = itemServiceImpl.updateItem(item);
		assertEquals("updating movie failed", "not so good movie", foodItem1.getComments());
		verify(itemRepo, times(1)).save(item);
		verify(itemRepo, times(1)).findById(item.getItemId());
		

	}

	@Test
	public void testDeleteFoodItemById() throws ItemAlreadyExistsException, ItemNotFoundException
	{
		when(itemRepo.findById(1)).thenReturn(items);
		doNothing().when(itemRepo).delete(item);
		final boolean flag = itemServiceImpl.deleteItem(1);
		assertTrue("Deleting Movie failed",flag);
		verify(itemRepo, times(1)).delete(item);
		verify(itemRepo, times(1)).findById(item.getItemId());
		

	}

}
