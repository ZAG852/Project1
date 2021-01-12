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
 * Servlet that handles all dinosaur data
 * 
 * @author Erik Terreri
 *
 */
public class Dinosaur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(Dinosaur.class);

	/**
	 * Get Method to return dinosaur data based on path data
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Service service = new Service();
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    try {
		    String out = service.dinoRead(request.getPathInfo());
		    if(out != null) {
		    	response.getWriter().append(out);
			    log.info("Got dinosaur");
			    response.setStatus(200);
		    } else {
		    	log.info("Failed to get dinosaur");
		    	response.setStatus(400);
		    }
	    } catch (Exception e) {
			log.warn("Exception occured");
			response.setStatus(400);
		}
	}

	/**
	 * Post Method to create dinosaur data if name is unused and user is logged in
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Service service = new Service();
		HttpSession session = request.getSession(false);
		try {
			if(session!=null) {
				int out = service.dinoCreate(request.getParameterMap(), (String)session.getAttribute("username"));
				if(out == 1) {
					log.info("Posted dinosaur");
					response.setStatus(201);
				} else {
					log.info("Failed Posting dinosaur");
					response.setStatus(400);
				}
			} else {
				log.info("Post attempt without login");
				response.setStatus(401);
			}
		} catch (Exception e) {
			log.warn("Exception occured");
			response.setStatus(400);
		}
	}
	/**
	 * Put Method to update dinosaur data given path data and a new dinosaur name if user is logged in
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Service service = new Service();
		HttpSession session = request.getSession(false);
		try {
			if(session!=null) {
				int out = service.dinoUpdate(request.getParameterMap(), request.getPathInfo());
				if(out == 1) {
					log.info("Updated dinosaur");
					response.setStatus(200);
				} else {
					log.info("Failed updating dinosaur");
					response.setStatus(400);
				}
			} else {
				log.info("Update attempt without login");
				response.setStatus(401);
			}
		} catch (Exception e) {
			log.warn("Exception occured");
			response.setStatus(400);
		}
	}
	
	/**
	 * Method to delete dinosaur data given path data if user is logged in
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Service service = new Service();
		HttpSession session = request.getSession(false);
		try {
			if(session!=null) {
				int out = service.dinoDelete(request.getPathInfo(), (String)session.getAttribute("username"));
				if(out == 1) {
					log.info("Deleted dinosaur");
					response.setStatus(200);
				} else {
					log.info("Failed deleting dinosaur");
					response.setStatus(400);
				}
			} else {
				log.info("Delete attempt without login");
				response.setStatus(401);
			}
		} catch (Exception e) {
			log.warn("Exception occured");
			response.setStatus(400);
		}
	}

}
