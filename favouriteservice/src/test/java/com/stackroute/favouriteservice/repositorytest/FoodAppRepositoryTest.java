package com.stackroute.favouriteservice.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.favouriteservice.model.FoodApp;
import com.stackroute.favouriteservice.repository.FoodAppRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FoodAppRepositoryTest {

	@Autowired
	private transient FoodAppRepository itemRepo;

	public void setItemRepo(FoodAppRepository itemRepo) {
		this.itemRepo = itemRepo;
	}

	public List<FoodApp> items;

	@Test
	public void testSaveItem() throws Exception {
		itemRepo.save(new FoodApp(1, "Bread", "1234", "vinod", "Average"));
		final FoodApp item = itemRepo.getOne(1);
		assertThat(item.getItemId()).isEqualTo(1);
	}

	@Test
	public void testUpdateItem() throws Exception {
		FoodApp savedItem = itemRepo.save(new FoodApp(1, "Bread", "1234", "vinod", "Average"));
		final FoodApp foodItem = itemRepo.getOne(savedItem.getItemId());
		assertEquals(foodItem.getItemId(), savedItem.getItemId());
		foodItem.setComments("Very Good");
		itemRepo.save(foodItem);
		final FoodApp updatedItem = itemRepo.getOne(savedItem.getItemId());
		assertThat(updatedItem.getComments()).isEqualTo("Very Good");
	}

	@Test
	public void testDeleteItem() throws Exception {
		FoodApp savedItem = new FoodApp(1, "Bread", "1234", "vinod", "Average");
		FoodApp food = itemRepo.save(savedItem);
		itemRepo.deleteById(food.getItemId());
		assertEquals(Optional.empty(), itemRepo.findById(savedItem.getItemId()));

	}

	@Test
	public void testGetFoodItem() throws Exception {
		itemRepo.save(new FoodApp(1, "Bread", "1234", "vinod", "Average"));
		final FoodApp item1 = itemRepo.getOne(1);

		assertEquals(item1.getItemId(), 1);
	}

	@Test
	public void testGetAllFoodItem() throws Exception {
		itemRepo.save(new FoodApp(1, "Bread", "1234", "vinod", "Average"));
		itemRepo.save(new FoodApp(2, "Corn", "1235", "vinod", "Above Average"));
		List<FoodApp> items1 = itemRepo.findAll();
		assertEquals(items1.get(0).getName(), "Bread");
	}

}
