package com.P0.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StaticResourceServlet
 */
@WebServlet("/StaticResourceServlet")
public class StaticResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StaticResourceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getRequestURI());
		String resource = request.getRequestURI().replace("/P0Server", "");
		if (resource != null) {
			switch(resource) {
				case "/":
					System.out.println("invoked forwarding of index.html");
					request.getRequestDispatcher("index.html").include(request, response);
					
					response.setContentType("text/html");
					response.setStatus(200);
					
//					Hey tomcat, forward our request and response object to this other endpoint 
//					http://localhost:8080/PirateServer/index.html
//					In this case, we're forwarding this endpoint to the default servlet, which will then process our .html file
//					and send the response contents to our browser
					break;
				case "/loginPage":
					System.out.println("invoked forwarding of login.html");
					request.getRequestDispatcher("login.html").include(request, response);
					
					response.setContentType("text/html");
					response.setStatus(200);
					break;
					
				case "/js/log":
					
					request.getRequestDispatcher("/js/loginSend.js").include(request, response);
					
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
				case "/js/dino-list":
					
					request.getRequestDispatcher("/js/dino-list.js").include(request, response);
					
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
				case "/js/createDino":
	
					request.getRequestDispatcher("/js/createDino.js").include(request, response);
	
					response.setContentType("text/javascript");
					response.setStatus(200);
	break;
				case "/createUser":
					System.out.println("invoked forwarding of createUser.html");
					request.getRequestDispatcher("createUser.html").include(request, response);
					
					response.setContentType("text/html");
					response.setStatus(200);
					break;
					
				case "/js/createUser":
					
					request.getRequestDispatcher("/js/createUser.js").include(request, response);
					
					response.setContentType("text/javascript");
					response.setStatus(200);
					break;
					
				case "/dino-list":
					System.out.println("invoked forwarding of createUser.html");
					request.getRequestDispatcher("dino-list.html").include(request, response);
					
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				case "/Profile":
					System.out.println("invoked forwarding of createUser.html");
					request.getRequestDispatcher("Profile.html").include(request, response);
					
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				case "/createDino":
					System.out.println("invoked forwarding of createUser.html");
					request.getRequestDispatcher("createDino.html").include(request, response);
					
					response.setContentType("text/html");
					response.setStatus(200);
					break;
				default:
					response.setStatus(404);
			}
		} else {
			response.setStatus(404);
		}
	}
}
