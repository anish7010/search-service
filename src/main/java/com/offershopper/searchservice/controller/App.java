package com.offershopper.searchservice.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
 
 
public class App {
	
    public static void setup() {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
 
        App app = new App();
        app.insert();
        /*System.out.println("1] query = coffee , caseSensitive = false, diacriticSensitive = false");
        app.fullTextSearch("coffee", false, false);
        System.out.println("-------------------------------------------------------------------------");
 
        System.out.println("2] query = bake coffee cake , caseSensitive = false, diacriticSensitive = false");
        app.fullTextSearch("bake coffee cake", false, false);
        System.out.println("-------------------------------------------------------------------------");
 
 
        System.out.println("3] query = \"coffee shop\" , caseSensitive = false, diacriticSensitive = false");
        app.fullTextSearch("\"coffee shop\"", false, false);
        System.out.println("-------------------------------------------------------------------------");
 
 
        System.out.println("4] query = coffee -shop , caseSensitive = false, diacriticSensitive = false");
        app.fullTextSearch("coffee -shop", false, false);
        System.out.println("-------------------------------------------------------------------------");
 
 
        System.out.println("5] query = Coffee , caseSensitive = true , diacriticSensitive = false");
        app.fullTextSearch("Coffee", true, false);
        System.out.println("-------------------------------------------------------------------------");
 */
 
        System.out.println("6] query = CAFÉ , caseSensitive = false , diacriticSensitive = false");
        app.fullTextSearch("bake", false, false);
        System.out.println("-------------------------------------------------------------------------");
 
    }
 
    public void insert() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongoClient.getDatabase("technicalkeeda");
        MongoCollection <Document> collection = database.getCollection("articles");
        collection.drop();
        collection.createIndex(new Document("subject", "text"), new IndexOptions());
        try {
            collection.insertOne(new Document("_id", 1).append("subject", "coffee").append("category", "coffee"));
            collection.insertOne(new Document("_id", 2).append("subject", "Popular Coffee Shopping").append("category", "coffee"));
            collection.insertOne(new Document("_id", 3).append("subject", "Baking a cake").append("category", "coffee"));
            collection.insertOne(new Document("_id", 4).append("subject", "baking").append("category", "tea"));
            collection.insertOne(new Document("_id", 5).append("subject", "Café Con Leche").append("category", "tea"));
            collection.insertOne(new Document("_id", 6).append("subject", "???????").append("category", "tea"));
            collection.insertOne(new Document("_id", 7).append("subject", "coffee and cream").append("category", "tea"));
            collection.insertOne(new Document("_id", 9).append("subject", "Cafe con Leche").append("category", "tea"));
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }
 
    public static void fullTextSearch(String query, boolean caseSensitive, boolean diacriticSensitive) {
 
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongoClient.getDatabase("technicalkeeda");
        MongoCollection<Document> collection = database.getCollection("articles");
 
        try {
            MongoCursor<Document> cursor = null;
            cursor = collection.find(new Document("$text", 
            		new Document("$search", query)
            		.append("$caseSensitive",
            				new Boolean(caseSensitive))
            		.append("$diacriticSensitive"
            				,new Boolean(diacriticSensitive))))
            		.sort(new BasicDBObject("_id", 1)).iterator();
            
            while (cursor.hasNext()) {
                Document article = cursor.next();
                System.out.println(article);
            }
 
            cursor.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
 
    }
 
}