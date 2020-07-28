/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dra.unit.test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

/**
 *
 * @author George
 */

public class MongoTestSuite extends TestSuite {

    public static void main(String[] a) {        
        TestSuite suite = new TestSuite(MongoDBConnectionTest.class, MongoDBManagerTest.class);
        TestResult result = new TestResult();
        suite.run(result);
        System.out.println("Number of test cases = " + result.runCount() + "\n"
                + "Number of failed cases = " + result.failureCount() + "\n");
    }
}
