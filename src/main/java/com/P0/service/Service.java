package com.P0.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.P0.controller.Dinosaur;
import com.P0.model.Dino;
import com.P0.model.User;
import com.P0.repository.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * Class that contains all service logic for the Server
 * It contains methods called by servlets and accesses methods in the repository
 * 
 * @author Erik Terreri
 *
 */
public class Service{
	
	private ObjectMapper objectMapper;
	private Repository repo;
	private static final Logger log = LogManager.getLogger(Dinosaur.class);
	
	public Service() {
		objectMapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		objectMapper.setDateFormat(df);
		repo = new Repository();
	}
	
	/**
	 * Simple hashing method to hash passwords using SHA-512
	 * 
	 * @param password
	 * @param salt
	 * @return hashed password
	 * @throws NoSuchAlgorithmException
	 */
	public String hashingMethod(String password, byte[] salt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
	}
	/**
	 * Method to get a Dinosaur ID given a name
	 * 
	 * @param name
	 * @return dinoID
	 */
	public int getDinoID(String name) {
		Dino dino = repo.getDinos(name);
		log.debug("Dinosaur ID retrieved");
		return dino.getDinoID();
	}
	
	/**
	 * Method to process path data and send either dinosaur id or name to the DB
	 * Can also get dino's by period
	 * 
	 * @param path
	 * @return dinosaur data as string
	 * @throws JsonProcessingException
	 */
	public String dinoRead(String path) throws JsonProcessingException {
		String out = null;
		if(path == null) {
			log.debug("All Dinosaurs request");
			out = objectMapper.writeValueAsString(repo.getDinos());
		} else if(path.substring(1).indexOf('/') == -1) {
			log.debug("Dinosaur " + path.substring(1) + " request");
			try {
				out = objectMapper.writeValueAsString(repo.getDinos(Integer.parseInt(path.substring(1))));
			} catch (NumberFormatException ex){
				out = objectMapper.writeValueAsString(repo.getDinos(path.substring(1)));
			}
		} else if(path.substring(1, path.substring(1).indexOf('/') + 1).equals("period")) {
			log.debug("Dinosaur " + path.substring(1) + " request");
			out = objectMapper.writeValueAsString(repo.getDinosPeriod(path.substring(path.substring(1).indexOf('/') + 2))); 
		} else if(path.substring(1, path.substring(1).indexOf('/') + 1).equals("user")) {
			log.debug("Dinosaur " + path.substring(1) + " request");
			try {
				out = objectMapper.writeValueAsString(repo.getDinosUser(Integer.parseInt(path.substring(path.substring(1).indexOf('/') + 2)))); 
			} catch (NumberFormatException ex){
				out = objectMapper.writeValueAsString(repo.getDinosUser(path.substring(path.substring(1).indexOf('/') + 2))); 
			}
		}
		return out;
	}
	
	/**
	 * Method to process HTTP parameters and username to create dinosaur data in DB, 
	 * returns 1 if successful, 0 if not
	 * 
	 * @param params
	 * @param username
	 * @return 1 | 0
	 */
	public int dinoCreate(Map<String, String[]> params, String username) {
		String name = null;
		String period = null;
		for(Map.Entry<String, String[]> entry : params.entrySet()) {
			if(entry.getKey().equals("name")) {
				name = entry.getValue()[0];
			}
			if(entry.getKey().equals("period")) {
				period = entry.getValue()[0];
			}
		}
		if(name != null && period != null) {
			log.debug("Dinosaur creation request");
			return repo.addDino(new Dino(0, name, period, repo.getUID(username)));
		} else {
			log.debug("Dinosaur creation incorrect format");
			return 0;
		}
	}
	
	/**
	 * Method to process HTTP parameters and path data to update dinosaur data in DB,
	 * returns 1 if successful, 0 if not
	 * 
	 * @param params
	 * @param path
	 * @return 1 | 0
	 */
	public int dinoUpdate(Map<String, String[]> params, String path) {
		int out = 0;
		if((path != null) && (path.substring(1).indexOf('/') == -1)) {
			int id = Integer.parseInt(path.substring(1));
			String name = null;
			for(Map.Entry<String, String[]> entry : params.entrySet()) {
				if(entry.getKey().equals("name")) {
					name = entry.getValue()[0];
				}
			}
			if( id!=-1 && name != null) {
				log.debug("Dinosaur Update request");
				out = repo.updateDino(id, name);
			}
		}
		return out;
	}
	
	/**
	 * Method to delete dinosaur data given path data and username,
	 * returns 1 is successful, 0 is not
	 * 
	 * @param path
	 * @param username
	 * @return 1 | 0
	 */
	public int dinoDelete(String path, String username) {
		int out = 0;
		if((path != null) && (path.substring(1).indexOf('/') == -1)) {
			log.debug("Dinosaur deletion request");
			out = repo.deleteDino(Integer.parseInt(path.substring(1)), username);
		}
		return out;
	}
	
	/**
	 * Method to login given password and username,
	 * returns 0 if successful, 1 if password is incorrect, or 2 if the username isn't found
	 * 
	 * @param username
	 * @param password
	 * @return 0 | 1 | 2
	 * @throws NoSuchAlgorithmException 
	 */
	public int login(String username, String password) throws NoSuchAlgorithmException  {
		log.debug("User data request");
		User user = repo.getUser(username);
		if(user == null) {
			return 2;
		} else {
			return (user.checkPassword(hashingMethod(password, user.getSalt())) ? 0 : 1);
		}
	}
	
	/**
	 * Method to create login information in the DB given username, password, and name
	 * returns 0 if unsuccessful, 1 if successful, or 2 if username is already in use
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @return 0 | 1 | 2
	 * @throws NoSuchAlgorithmException 
	 */
	public int createLogin(String username, String password, String name) throws NoSuchAlgorithmException {
		int out = 2;
		System.out.println("in user creation");
		log.debug("User data request");
		if(username != null && repo.getUser(username) == null) {
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			String hashedPassword = hashingMethod(password, salt);
			log.debug("User creation request");
			out = repo.createUser(username, hashedPassword, salt, name);
		}
		return out;
	}
	
	/**
	 * Method for processing HTTP parameters and username and updating user's data in DB,
	 * returns 1 if successful, 0 if not
	 * 
	 * @param params
	 * @param username
	 * @return 0 | 1
	 */
	public int updateUser(Map<String, String[]> params, String username) {
		int out = 0;
		String name = null;
		for(Map.Entry<String, String[]> entry : params.entrySet()) {
			if(entry.getKey().equals("name")) {
				name = entry.getValue()[0];
			}
		}
		if(name != null) {
			log.debug("User update request");
			out = repo.updateUser(username, name);
		}
		return out;
	}
	
	/**
	 * Method for deleting user data from DB,
	 * returns 1 if successful, 0 if not
	 * 
	 * @param username
	 * @return 0 | 1
	 */
	public int deleteUser(String username) {
		log.debug("User deletion request");
		return repo.deleteUser(username);
	}
	
	public String userList() throws JsonProcessingException {
		log.debug("User List request");
		return objectMapper.writeValueAsString(repo.listUsers());
	}
}
