package com.offershopper.searchservice.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@RestController
@RequestMapping("/search")
public class Search {
  
  @GetMapping("/search-key/{value}")
  public void search(@PathVariable String value) {
    MongoClient mongoClient = new MongoClient("localhost",27017); //with default server and port adress
    MongoDatabase database = mongoClient.getDatabase("mongo");
    
    MongoCollection<Document> collection = database.getCollection("offer");
    FindIterable<Document> allOffers = collection.find();
    
    
    
    ArrayList<Document> searchResults = new ArrayList<Document>();
    
    for(Document document : allOffers) {
      if(value.equals(document.get("offerTitle"))) {
        searchResults.add(document);
      }
    }
    System.out.println(searchResults.size());
    mongoClient.close();
  }
}
