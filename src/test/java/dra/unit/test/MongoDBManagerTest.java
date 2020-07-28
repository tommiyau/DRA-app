package dra.unit.test;
import dra.demo.model.User;
import dra.demo.model.dao.MongoDBManager;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
/**
 *
 * @author George
 */
@FixMethodOrder(MethodSorters.JVM)
public class MongoDBManagerTest extends TestCase{
    
    private MongoDBManager manager;
    
    /**
     * create and instance of MongoDBManager
     */
    @Before
    @Override
    public void setUp() {
        System.out.println(" >> Setting up MongoDB Manager Test variables: ");
        manager = new MongoDBManager("Georges034302","darkside666","admin","dra-db","users");
    }      
    
    /**
     * test if the create and add methods work by randomly adding 
     * a user to dra-db.users then reading this user by email-password
     */
    @Test
    public void testCreateRead() {
        System.out.println(" >> Testing Create/Read operations with Mongo DB: ");
        String name = RandomStringUtils.randomAlphabetic(8);
        String password = RandomStringUtils.randomAlphabetic(8);
        String email = RandomStringUtils.randomAlphabetic(8);
        String phone = RandomStringUtils.randomNumeric(8);
        manager.create(name, email, password, phone);
        User user = manager.user(email, password);
        assertNotNull(name+"is not added to dra-db.users",user);
    }  
   
    /**
     * Print the dra-db.users contents to verify that new user has been added
     */
    @Test
    public void testCollection() {
        System.out.println(" >> Verifying and printing the updated Mongo DB database: ");
        manager.showCollection();        
    }  
    
}
