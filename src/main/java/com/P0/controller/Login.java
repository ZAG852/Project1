package com.P0.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.P0.service.Service;

/**
 * Servlet that handles all user/login data
 * 
 * @author Erik Terreri
 *
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(Login.class);  
	
	/**
	 * Logout Method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Service service = new Service();
		HttpSession session = request.getSession(false);
		if(session != null) {
			if(request.getPathInfo() == null) {
				response.getWriter().append((String)session.getAttribute("username"));
				response.setStatus(200);
			} else if(request.getPathInfo().equals("/list")){
				log.info("Getting user list");
				response.getWriter().append(service.userList());
				response.setStatus(200);
			} else if(request.getPathInfo().equals("/logout")){
				log.info((String)session.getAttribute("username") + " logged out");
				session.invalidate();
				response.setStatus(200);
			} else {
				response.setStatus(400);
			}
		}
	}

	/**
	 * Post Method to either create a login if given a name parameter, or login if not
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		Service service = new Service();
		try {
			if(name != null) {
				switch(service.createLogin(username, password, name)) {
				case 0:
					response.getWriter().append("Login creation failed.");
					log.info("Failed creating Login");
					break;
				case 1:
					response.getWriter().append("Login created successfully.");
					log.info("Login Created");
					break;
				case 2:
					response.getWriter().append("Username already in use.");
					log.info("Failed creaing Login, Username in use");
					break;
				}
			} else {
				switch(service.login(username, password)) {
				case 0:
					System.out.println(0);
					HttpSession session = request.getSession();
					session.setAttribute("username", username);
					response.getWriter().append("Login successful.");
					log.info(username + " logged in");
					break;
				case 1:
					System.out.println(1);
					response.getWriter().append("Password Incorrect.");
					log.info("Login failed, incorrect password");
					break;
				case 2:
					System.out.println(2);
					response.getWriter().append("Username not found.");
					log.info("Login failed, username not found");
					break;
				}
			}
		} catch (Exception e) {
			log.info("Exception occured");
			e.printStackTrace();
			response.setStatus(400);
		}
	}
	
	/**
	 * Put Method to update user data
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Service service = new Service();
		HttpSession session = request.getSession(false);
		try {
			if(session!=null) {
				int out = service.updateUser(request.getParameterMap(), (String)session.getAttribute("username"));
				if(out == 1) {
					log.info("Updated user");
					response.setStatus(200);
				} else {
					log.info("Failed updating user");
					response.setStatus(400);
				}
			} else {
				log.info("Update user attempt without login");
				response.setStatus(401);
			}
		} catch (Exception e) {
			log.info("Exception occured");
			response.setStatus(400);
		}
	}
	
	/**
	 * Delete method to delete user data
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Service service = new Service();
		HttpSession session = request.getSession(false);
		try {
			if(session!=null) {
				int out = service.deleteUser((String)session.getAttribute("username"));
				if(out == 1) {
					log.info("Deleted user");
					doGet(request, response);
				} else {
					log.info("Failed deleting user");
					response.setStatus(400);
				}
			} else {
				log.info("Delete user attempt without login");
				response.setStatus(401);
			}
		} catch (Exception e) {
			log.info("Exception occured");
			response.setStatus(400);
		}
	}

}
