package com.stackroute.favouriteservice.controllertest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.favouriteservice.controller.FoodAppController;
import com.stackroute.favouriteservice.model.FoodApp;
import com.stackroute.favouriteservice.service.FoodAppService;

@RunWith(SpringRunner.class)
@WebMvcTest(FoodAppController.class)
public class FoodAppControllerTest {

	@Autowired
	private transient MockMvc mvc;

	@MockBean
	private transient FoodAppService itemservice;

	@InjectMocks
	private FoodAppController itemController;

	private transient FoodApp item;

	static List<FoodApp> items;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		items = new ArrayList<>();
		item = (new FoodApp(1, "Bread", "1234", "vinod", "Average"));
		items.add(item);
		item = (new FoodApp(2, "Corn", "1235", "vinod", "Above Average"));
		items.add(item);

	}

	@Test
	public void testsaveFavoriteFoodSuccess() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5vZCIsImlhdCI6MTU1NjI1MjIwNX0.oNUOIqtbV4iM8nUA7MzNXPtLPkvcfS2qn7mfrfCcOWM";

		when(itemservice.saveItem(item)).thenReturn(true);
		mvc.perform(post("/api/foodApp").header("authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(jsonToString(item))).andExpect(status().isCreated());
		verify(itemservice, times(1)).saveItem(Mockito.any(FoodApp.class));
		verifyNoMoreInteractions(itemservice);
	}

	@Test
	public void testUpdateItemSuccess() throws Exception {
		item.setComments("Above Average");

		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5vZCIsImlhdCI6MTU1NjI1MjIwNX0.oNUOIqtbV4iM8nUA7MzNXPtLPkvcfS2qn7mfrfCcOWM";
		when(itemservice.updateItem(item)).thenReturn(items.get(0));
		mvc.perform(put("/api/foodApp/{id}", 1).header("authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(jsonToString(item))).andExpect(status().isOk());

		verify(itemservice, times(1)).updateItem(Mockito.any(FoodApp.class));
		verifyNoMoreInteractions(itemservice);
	}

	@Test
	public void testDeleteItemById() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5vZCIsImlhdCI6MTU1NjI1MjIwNX0.oNUOIqtbV4iM8nUA7MzNXPtLPkvcfS2qn7mfrfCcOWM";
		when(itemservice.deleteItem(1)).thenReturn(true);
		mvc.perform(delete("/api/foodApp/{id}", 1).header("authorization", "Bearer " + token))
				.andExpect(status().isOk());

		verify(itemservice, times(1)).deleteItem(1);
		verifyNoMoreInteractions(itemservice);

	}

	@Test
	public void testFetchItemById() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5vZCIsImlhdCI6MTU1NjI1MjIwNX0.oNUOIqtbV4iM8nUA7MzNXPtLPkvcfS2qn7mfrfCcOWM";
		when(itemservice.getItemById(1)).thenReturn(items.get(0));
		mvc.perform(get("/api/foodApp/{id}", 1).header("authorization", "Bearer " + token)).andExpect(status().isOk());
		verify(itemservice, times(1)).getItemById(1);
		verifyNoMoreInteractions(itemservice);
	}

	@Test
	public void testGetAllItems() throws Exception {

		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW5vZCIsImlhdCI6MTU1NjI1MjIwNX0.oNUOIqtbV4iM8nUA7MzNXPtLPkvcfS2qn7mfrfCcOWM";

		when(itemservice.getMyItems("vinod")).thenReturn(items);

		mvc.perform(get("/api/foodApp").header("authorization", "Bearer " + token)).andExpect(status().isOk())
				.andDo(print());

		verify(itemservice, times(1)).getMyItems("vinod");

		verifyNoMoreInteractions(itemservice);

	}

	private String jsonToString(final Object object) {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			result = "Json processing error";
		}
		return result;
	}

}
