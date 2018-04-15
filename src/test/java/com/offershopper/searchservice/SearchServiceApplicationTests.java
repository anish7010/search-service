package com.offershopper.searchservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.TreeMap;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.offershopper.searchservice.controller.SearchController;
import com.offershopper.searchservice.service.SearchService;
import org.springframework.http.MediaType;



@RunWith(SpringRunner.class)
@WebMvcTest(value=SearchController.class ,secure = false)
public class SearchServiceApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MongoClient mongoClient;
	
	@MockBean
	private SearchService searchService;
	@MockBean
	TreeMap<Double, Document> hm;
	@MockBean
	MongoDatabase database;
	@MockBean
	MongoCollection<Document> collection;
	@MockBean
	List<Document> searchResults;
	@MockBean
	List<Document> finalSearchResults;
	
	@InjectMocks
	private SearchController searchController;

	Integer mockTotal;
	@Before
	public void setUp() throws Exception {
		// System.out.println(jsonContent.toString());
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
		mockTotal=5;
		
	}
	
	@Test
	public void testSearch() throws Exception {
		
		Mockito.when(hm.size()).thenReturn(mockTotal);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/search/totalResultsFound")
		        .accept(MediaType.APPLICATION_JSON);
		
		
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		
		String expected = "5";
		
		assertEquals(expected, result.getResponse().getContentAsString());
		
		
	}
	
	
	
	@Test
	public void testGetTotalResults() throws Exception {
		
		Mockito.when(hm.size()).thenReturn(mockTotal);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/search/totalResultsFound")
		        .accept(MediaType.APPLICATION_JSON);
		
		
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		
		String expected = "5";
		
		assertEquals(expected, result.getResponse().getContentAsString());
		
		
	}
	
	@Test
	public void negativeTestGetTotalResults() throws Exception {
		
		Mockito.when(hm.size()).thenReturn(mockTotal);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/search/totalResultsFound")
		        .accept(MediaType.APPLICATION_JSON);
		
		
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		
		String expected = "6";
		
		assertNotEquals(expected, result.getResponse().getContentAsString());
		
		
	}
	
	

	/*
	 * @Test public void testBag() throws Exception {
	 * 
	 * mockMvc.perform(get("/bag1")) .andExpect(status().isNotFound())
	 * .andExpect(content().string("Hello")); }
	 */
	// Positive test for getAllByUserId
	/*@Test
	public void search() throws Exception {


		// carryBags.add(new CarryBagBean("101"));
	
		Mockito.when(searchService.findSimilarity(Mockito.anyString(),Mockito.anyString())).thenReturn(1.0);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bag/userId/rakesh_005")
				.accept(MediaType.APPLICATION_JSON);
		try {

			MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isAccepted()).andReturn();
			System.out.println("\n" + result.getResponse().getContentAsString() + "\nHello\n");

			 //tring expected = ;

			// String expected =
			//JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
			// JSONAssert.assertNotEquals(expectedNegative,
			// result.getResponse().getContentAsString(), false);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		
		 * Mockito.when(carryBagRepository.findAll()).thenReturn(carryBags);
		 * 
		 * MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bag")
		 * .accept(MediaType.APPLICATION_JSON)) .andExpect(status().isOk()) //
		 * .andExpect(model().attribute("carryBags", Matchers.hasSize(2))) .andReturn()
		 * ;
		 * 
		 * System.out.println(mvcResult+"=============================================="
		 * ); verify(carryBagRepository).findAll();
		 
	}
*/
	
	

}
