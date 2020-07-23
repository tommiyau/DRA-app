package dra.demo.model.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.*;
import dra.demo.model.*;
import org.bson.Document;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/*
 * @author George 
 * 
 */
public class MongoDBManager {
    private MongoClientURI mongoURI;
    private MongoClient mongoClient;    
    private CodecRegistry pojoCodecRegistry;
    private String authorization;
    private MongoDatabase database; 
    private MongoCollection<Document> users;
 
    public MongoDBManager(String owner, String password, String role, String db, String collection) { 
        connect(owner, password,role,db,collection);
    }

    //connect to a collection of the existing db
    public void connect(String owner, String password, String role, String db, String collection) {        
        this.mongoURI = mongoClientURI(owner, password, role, db); 
        this.mongoClient = new MongoClient(this.mongoURI); 
        this.database = this.mongoClient.getDatabase(db); 
        this.pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        this.database = database.withCodecRegistry(pojoCodecRegistry);  
        users = this.database.getCollection(collection);        
    }

    //Specify the connection client URI
    private MongoClientURI mongoClientURI(String owner, String password, String role, String db){
        this.authorization = "ssl=true&replicaSet=Cluster0-shard-0&authSource=" + role + "&retryWrites=true";
        MongoClientURI uri = new MongoClientURI(
                ""
                + "mongodb://" + owner + ":" + password + "@"
                + "cluster0-shard-00-00-7c0ng.mongodb.net:27017"+","
                + "cluster0-shard-00-01-7c0ng.mongodb.net:27017"+","
                + "cluster0-shard-00-02-7c0ng.mongodb.net:27017"+"/" + db + "?"
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
        users.insertOne(entity);
    }

    //Read Operation
    public User user(String email, String password) {      
        Document doc = users.find(and(eq("_id", email), eq("password", password))).first();
        User user = new User((String) doc.get("name"), (String) doc.get("email"), (String) doc.get("password"), (String) doc.get("phone"));
        return user;
    }    
}
