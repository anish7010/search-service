package com.offershopper.searchservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offershopper.searchservice.service.searchService;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	@GetMapping("/searchKey/{searchTerm}")
	@HystrixCommand(fallbackMethod="searchFallBack")
	public ArrayList<Document> search(@PathVariable String searchTerm) {

		TreeMap<Double, Document> hm = new TreeMap<Double, Document>();

		MongoClient mongoClient = new MongoClient("10.151.60.204", 27017); // with default server and port adress
		// MongoDatabase database = mongoClient.getDatabase("OfferShopperDB");
		// MongoDatabase database = mongoClient.getDatabase("OfferShopperDB");
		MongoDatabase database = mongoClient.getDatabase("OfferShopperDB");

		MongoCollection<Document> collection = database.getCollection("wishlistBean");
		FindIterable<Document> allOffers = collection.find();

		ArrayList<Document> searchResults = new ArrayList<Document>();
		ArrayList<Document> finalSearchResults = new ArrayList<Document>();

		for (Document document : allOffers) {
			if (searchService.findSimilarity(searchTerm, document.get("offerTitle").toString()) > .25) {
				hm.put(1-searchService.findSimilarity(searchTerm, document.get("offerTitle").toString()),
						document);
				searchResults.add(document);
			}
		}
		BasicDBObject inQuery = new BasicDBObject();
		for (Map.Entry m : hm.entrySet()) {
			System.out.println(m.getKey() + " " + m.getValue());
			finalSearchResults.add((Document) m.getValue());
		}
		// System.out.println(searchResults.size());
		System.out.println(searchResults.toString());
		mongoClient.close();
		return finalSearchResults;

	}
	
	public ArrayList<Document> searchFallBack(@PathVariable String searchTerm){
		
		return new ArrayList<Document>();
	}
}
