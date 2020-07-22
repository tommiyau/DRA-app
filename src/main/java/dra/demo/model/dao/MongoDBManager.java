package dra.demo.model.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import dra.demo.model.*;
import java.io.Serializable;
import java.util.ArrayList;
import org.bson.Document;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/*
 * @author George 
 * 
 */
public class MongoDBManager implements Serializable{
    private MongoClientURI mongoURI;
    private MongoClient mongoClient;    
    private CodecRegistry pojoCodecRegistry;
    private String authorization;
    private ArrayList<String> clusters = new ArrayList<>();
    private MongoCredential credential;
    private MongoDatabase database; //MongoDB super-class initializes and shares the MongoDatabase
    private MongoCollection<Document> collection;

 
    public MongoDBManager() { }

    //connect to a collect of the existing db
    public void connect(String owner, String password, String role, String db, String collection) {
        loadClusters(); //Load the database clusters
        this.mongoURI = mongoClientURI(owner, password, role, db); //Specify the mongoURI access rules
        this.mongoClient = new MongoClient(this.mongoURI); //create a mongoClient
        this.database = this.mongoClient.getDatabase(db); //create a database from mongoClient
        this.credential = MongoCredential.createCredential(owner, db, password.toCharArray()); //Get the db credentials
        this.pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));//Register Java object for automatic mapping in Mongo Collection       
        this.database = database.withCodecRegistry(pojoCodecRegistry); //Apply the Java-object mapping to the current database
        this.collection = this.database.getCollection(collection);
    }

    //Specify the database clusters
    private void loadClusters(){
        clusters.add("cluster0-shard-00-00-7c0ng.mongodb.net:27017");
        clusters.add("cluster0-shard-00-01-7c0ng.mongodb.net:27017");
        clusters.add("cluster0-shard-00-02-7c0ng.mongodb.net:27017");
    }
    
    //Specify the connection client URI
    private MongoClientURI mongoClientURI(String owner, String password, String role, String db){
        this.authorization = "ssl=true&replicaSet=Cluster0-shard-0&authSource=" + role + "&retryWrites=true";
        MongoClientURI uri = new MongoClientURI(
                ""
                + "mongodb://" + owner + ":" + password + "@"
                + clusters.get(0)+","
                + clusters.get(1)+","
                + clusters.get(2)+"/" + db + "?"
                + this.authorization
                + ""
        );
        return uri;
    }
    
    //Create operation
    public void create(String name, String email, String password, String phone) {
        Document entity = new Document("_id", email)
                .append("name", name)
                .append("password", password)
                .append("phone", phone);
        this.collection.insertOne(entity);
    }

    //Read Operation
    public User user(String email, String password) {
        Document doc = collection.find(and(eq("_id", email), eq("password", password))).first();
        User user = new User((String) doc.get("name"), (String) doc.get("email"), (String) doc.get("password"), (String) doc.get("phone"));
        return user;
    }    
}
