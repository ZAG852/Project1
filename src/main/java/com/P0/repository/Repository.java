package com.P0.repository;

import java.io.File;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.postgresql.Driver;

import com.P0.controller.Dinosaur;
import com.P0.model.Dino;
import com.P0.model.User;

/**
 * Class that contains all methods used to connect to the Database
 * 
 * @author Erik Terreri
 *
 */
public class Repository {
	private static final Logger log = LogManager.getLogger(Dinosaur.class);
	
	/**
	 * Method that generates a user object given username
	 * 
	 * @param username
	 * @return User Object
	 */
	public User getUser(String username) {
		User user = null;
		try(Connection conn = getConnection()){
			log.trace("User DB access");
			String query = "SELECT * "
					+ "FROM Users "
					+ "WHERE Username = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBytes(4), rs.getString(5), rs.getBoolean(6));
			}
		} catch (SQLException e) {
			log.trace("User DB access failed");
		}
		return user;
	}
	
	/**
	 * Method that returns the UID of a user, returns -1 if no user is found
	 * 
	 * @param username
	 * @return UID | -1
	 */
	public int getUID(String username) {
		int UID = -1;
		try(Connection conn = getConnection()){
			log.trace("User DB access");
			String query = "SELECT USER_ID "
					+ "FROM Users "
					+ "WHERE Username = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				UID = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.trace("User DB access failed");
		}
		return UID;
	}
	
	/**
	 * Method for getting all the Dinosaurs from the DB
	 * 
	 * @return ArrayList of all Dinos in DB
	 */
	public ArrayList<Dino> getDinos(){
		ArrayList<Dino> dinos = new ArrayList<Dino>();
		try(Connection conn = getConnection()){
			log.trace("Dino DB access");
			String query = "SELECT * "
					+ "FROM Dinosaurs";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				dinos.add(new Dino(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5)));
			}
		} catch (SQLException e) {
			log.trace("Dino DB access failed");
		}
		return dinos;
	}
	
	public ArrayList<Dino> getDinosPeriod(String period){
		ArrayList<Dino> dinos = new ArrayList<Dino>();
		try(Connection conn = getConnection()){
			log.trace("Dino DB access");
			String query = "SELECT * "
					+ "FROM Dinosaurs "
					+ "Where Period = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, period);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				dinos.add(new Dino(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5)));
			}
		} catch (SQLException e) {
			log.trace("Dino DB access failed");
		}
		return dinos;
	}
	
	public ArrayList<Dino> getDinosUser(int UID){
		ArrayList<Dino> dinos = new ArrayList<Dino>();
		try(Connection conn = getConnection()){
			log.trace("Dino DB access");
			String query = "SELECT * "
					+ "FROM Dinosaurs "
					+ "Where User_ID = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, UID);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				dinos.add(new Dino(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5)));
			}
		} catch (SQLException e) {
			log.trace("Dino DB access failed");
		}
		return dinos;
	}
	
	public ArrayList<Dino> getDinosUser(String username){
		System.out.println(username);
		ArrayList<Dino> dinos = new ArrayList<Dino>();
		try(Connection conn = getConnection()){
			log.trace("Dino DB access");
			String query = "SELECT * "
					+ "FROM Dinosaurs "
					+ "INNER JOIN Users "
					+ "ON Dinosaurs.User_ID = Users.User_ID "
					+ "Where Username = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				dinos.add(new Dino(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5)));
			}
		} catch (SQLException e) {
			log.trace("Dino DB access failed");
		}
		return dinos;
	}
	
	/**
	 * Method to return a single dinosaur from the DB given it's name
	 * 
	 * @param name
	 * @return dino | null
	 */
	public Dino getDinos(String name){
		Dino dino = null;
		try(Connection conn = getConnection()){
			log.trace("Dino DB access");
			String query = "SELECT * "
					+ "FROM Dinosaurs "
					+ "WHERE Dinosaur_Name = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, name);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				dino = new Dino(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5));
			}
		} catch (SQLException e) {
			log.trace("Dino DB access failed");
		}
		return dino;
	}
	
	/**
	 * Method to return a single dinosaur from the DB given it's id
	 * 
	 * @param id
	 * @return dino | null
	 */
	public Dino getDinos(int id){
		Dino dino = null;
		try(Connection conn = getConnection()){
			log.trace("Dino DB access");
			String query = "SELECT * "
					+ "FROM Dinosaurs "
					+ "WHERE Dinosaur_ID = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				dino = new Dino(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDate(5));
			}
		} catch (SQLException e) {
			log.trace("Dino DB access failed");
		}
		return dino;
	}

	/**
	 * Method to add a dinosaur object's data to the DB
	 * 
	 * @param dino
	 * @return -1 | 0 | 1
	 */
	public int addDino(Dino dino) {
		int out = -1;
		try(Connection conn = getConnection()){
			log.trace("Dino DB access");
			String query = "INSERT INTO Dinosaurs (Dinosaur_Name, Period, User_id, DateAdded)"
					+ "VALUES (?, ?, ?, ?)";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, dino.getName());
			pst.setString(2, dino.getPeriod());
			pst.setInt(3, dino.getUserID());
			pst.setDate(4, dino.getDate());
			out = pst.executeUpdate();
		} catch (SQLException e) {
			log.trace("Dino DB access failed");
		}
		return out;
	}
	
	/**
	 * Updates a dinosaur with a given id to the given name
	 * 
	 * @param id
	 * @param name
	 * @return -1 | 0 | 1
	 */
	public int updateDino(int id, String name) {
		int out = -1;
		try(Connection conn = getConnection()){
			log.trace("Dino DB access");
			String query = "Update Dinosaurs "
					+ "SET Dinosaur_Name = ? "
					+ "WHERE Dinosaur_ID = ? ";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, name);
			pst.setInt(2, id);
			out = pst.executeUpdate();
		} catch (SQLException e) {
			log.trace("Dino DB access failed");
		}
		return out;
	}
	
	/**
	 * Method to delte a dinosaur given an id, and the user's username,
	 * will delete from the DB if the user originally submitted the dino or if the user is an admin
	 * 
	 * @param id
	 * @param username
	 * @return -1 | 0 | 1
	 */
	public int deleteDino(int id, String username) {
		int out = -1;
		try(Connection conn = getConnection()){
			log.trace("Dino DB access");
			String query = "DELETE FROM  Dinosaurs "
					+ "WHERE Dinosaur_ID = "
					+ "(SELECT Dinosaur_ID "
					+ "FROM Dinosaurs "
					+ "INNER JOIN Users "
					+ "ON Dinosaurs.User_ID = Users.User_ID "
					+ "WHERE Dinosaurs.Dinosaur_ID = ? AND (Users.Username = ? OR ? in ( "
					+ "SELECT username "
					+ "FROM Users "
					+ "WHERE permissions = true)))";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setInt(1, id);
			pst.setString(2, username);
			pst.setString(3, username);
			out = pst.executeUpdate();
		} catch (SQLException e) {
			log.trace("Dino DB access failed");
		}
		return out;
	}
	
	/**
	 * Method to update a user's name given their username
	 * 
	 * @param username
	 * @param name
	 * @return -1 | 0 | 1
	 */
	public int updateUser(String username, String name) {
		int out = -1;
		try(Connection conn = getConnection()){
			log.trace("User DB access");
			String query = "Update Users "
					+ "SET User_Name = ? "
					+ "WHERE Username = ? ";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, name);
			pst.setString(2, username);
			out = pst.executeUpdate();
		} catch (SQLException e) {
			log.trace("User DB access failed");
		}
		return out;
	}
	
	/**
	 * Method to delete a user from the DB
	 * 
	 * @param username
	 * @return -1 | 0 | 1
	 */
	public int deleteUser(String username) {
		int out = -1;
		try(Connection conn = getConnection()){
			log.trace("User DB access");
			String query = "DELETE FROM  Users "
					+ "WHERE username = ?";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, username);
			out = pst.executeUpdate();
		} catch (SQLException e) {
			log.trace("User DB access failed");
		}
		return out;
	}
	
	/**
	 * Method to create a user entry in the DB
	 * 
	 * @param username
	 * @param hashedPassword
	 * @param salt
	 * @param name
	 * @return -1 | 0 | 1
	 */
	public int createUser(String username, String hashedPassword, byte[] salt, String name) {
		int out = -1;
		try(Connection conn = getConnection()){
			log.trace("User DB access");
			String query = "INSERT INTO Users (username, Password, Salt, User_name, Permissions)"
					+ "VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setString(1, username);
			pst.setString(2, hashedPassword);
			pst.setBytes(3, salt);
			pst.setString(4, name);
			pst.setBoolean(5, false);
			out = pst.executeUpdate();
		} catch (SQLException e) {
			log.trace("User DB access failed");
		}
		return out;
	}
	
	public ArrayList<User> listUsers(){
		ArrayList<User> users = new ArrayList<User>();
		try(Connection conn = getConnection()){
			log.trace("User DB access");
			String query = "SELECT * "
					+ "FROM Users";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				users.add(new User(rs.getInt(1), rs.getString(2), null, null, rs.getString(5), rs.getBoolean(6)));
			}
		} catch (SQLException e) {
			log.trace("User DB access failed");
		}
		return users;
	}
	/*
	public static void main(String args[]) {
		Repository r = new Repository();
		Connection c = r.getConnection();
		System.out.println(c);
	}
	*/
	/**
	 * Method to establish the DB connection
	 * 
	 * @return Connection
	 */
	private Connection getConnection() {
		/*
		Properties prop = new Properties();
		String fileName = "Properties/settings.cfg.properties";
		try (InputStream ip = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);){
			prop.load(ip);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		String url = "jdbc:postgresql://dinodb.c4k7ynynbkpn.us-east-2.rds.amazonaws.com/postgres";//prop.getProperty("DB_URL");//
		String username = "postgres"; //prop.getProperty("DB_USERNAME"); //
		String password ="admin123admin";// prop.getProperty("DB_PASSWORD");//prop.getProperty("DB_PASSWORD");//
		Connection conn = null;
		try {
			log.trace("DB connection attempt");
			DriverManager.registerDriver(new Driver());
			conn = DriverManager.getConnection(url, username, password);
			log.trace("DB connection success");
		} catch (SQLException e) {
			log.trace("DB connection failure");
		}
		return conn;
	}

}
