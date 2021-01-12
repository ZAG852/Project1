package com.P0.test;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.P0.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ServiceTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ServiceTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( ServiceTest.class );
    }
/*
    public void testServiceDino() throws JsonProcessingException {
    	Service service = new Service();
    	
    	Map<String, String[]> params = new HashMap<>();
    	String[] name = {"Test"};
    	String[] period = {"Test"};
    	params.put("name", name);
    	params.put("period", period);
    	
    	//Read
        assertNotNull("Get all", service.dinoRead(null));
        assertNotNull("Get 1 dino", service.dinoRead("/1"));
        assertNotNull("Get 1 dino", service.dinoRead("/Tyrannosaurus"));
        assertNull("Incorrect Fromat", service.dinoRead("/Tyrannosaurus/"));
        assertNull("Incorrect Fromat", service.dinoRead("/Tyrannosaurus/Tyrannosaurus"));
        //Create
        assertEquals("Create dino", service.dinoCreate(params, "admin"), 1);
        assertEquals("Create existing dino", service.dinoCreate(params, "admin"), -1);
        params.remove("name");
        assertEquals("Incorrect Format", service.dinoCreate(params, "admin"), 0);
        //Update
        int id = service.getDinoID(name[0]);
        name[0] = "Test2";
        params.put("name", name);
        assertEquals("Update dino", service.dinoUpdate(params, "/" + id), 1);
        assertEquals("Incorrect Format", service.dinoUpdate(params, null), 0);
        params.remove("name");
        assertEquals("Incorrect Format", service.dinoUpdate(params, "/" + id), 0);
        //Delete
        assertEquals("No Permissions", service.dinoDelete("/" + id, "user"), 0);
        assertEquals("Deleted dino", service.dinoDelete("/" + id, "admin"), 1);
        assertEquals("Incorrect Format", service.dinoDelete(null, "admin"), 0);
    }*/
    
    public void testLogin() throws JsonProcessingException, NoSuchAlgorithmException {
    	Service service = new Service();
    	Map<String, String[]> params = new HashMap<>();
    	String[] name = {"test2"};
    	params.put("name", name);
    	//Create
        assertEquals("Create User", service.createLogin("test", "test", "test"), 1);
        assertEquals("Create existing User", service.createLogin("test", "test", "test"), 2);
        assertEquals("Incorrect Format", service.createLogin(null, null, null), 2);
    	//Login
        assertEquals("Successful Login", service.login("test", "test"), 0);
        assertEquals("Login, bad password", service.login("test", "test2"), 1);
        assertEquals("Login, bad username", service.login("test2", "test"), 2);
        //Update
        assertEquals("Update user", service.updateUser(params, "test"), 1);
        params.remove("name");
        assertEquals("Incorrect Format", service.updateUser(params, "test"), 0);
        //Delete
        assertEquals("Delete User", service.deleteUser("test"), 1);
    }
}
