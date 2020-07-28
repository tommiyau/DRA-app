package dra.unit.test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import dra.demo.model.dao.MongoDB;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author George
 */
@FixMethodOrder(MethodSorters.JVM)
public class MongoDBConnectionTest extends TestCase{
       
    private MongoDB mongo;
    private MongoClientURI clientURI;
    private MongoClient client;
    
    @Before
    @Override
    public void setUp() throws Exception {
        System.out.println(" >> Setting up MongoDB Test variables: ");
        mongo = new MongoDB("Georges034302","darkside666","admin","dra-db");
        clientURI = mongo.mongoClientURI("Georges034302","darkside666","admin","dra-db");
        this.client = new MongoClient(this.clientURI);
    }

    /**
     * test if the connection is built with mongo db
     */
    @Test
    public void testConnection() {  
        System.out.println(" >> Testing Mongo DB connection instance: ");
        assertNotNull(" >> Connection established with dra-db is refused",mongo);
    }
     
    /**
     * test if the URI is created and authorized using the clusters
     */
    @Test 
    public void testMongoClientURI(){  
        System.out.println(" >> Testing Mongo DB URI autorization: ");
        assertNotNull(" >> MongoClientURI is not created",clientURI);
    }
    
    /**
     * test if mongo db client is connect using the URI
     */
    @Test 
    public void testMongoClient(){      
        System.out.println(" >> Testing client access to Mongo DB: ");
        assertNotNull(" >> MongoClient link with dra-db is not established",client);
    }
    
    /**
     * terminate the connection after the test
     */
    @After
    @Override
    public void tearDown() {
        System.out.println(" >> Closing connection with Mongo DB: ");
        client.close();
    }
}
