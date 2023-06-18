 package com.techblog;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.protocol.Message;
import com.techblogdao.UserDao;
import com.techblogentities.User;
import com.techbloghelper.ConnectionProvider;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Fetch user email and password from request
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String userEmail = request.getParameter("email");
		String userPass = request.getParameter("password");
		UserDao dao = new UserDao(ConnectionProvider.getConnection());
		User u = dao.getUserByEmailandPassword(userEmail,userPass);
		if(u==null)
		{ 
			//out.println("Invalid Detaila, try again...");
		com.techblogentities.Message msg = new com.techblogentities.Message("Invalid details ! try with another id", "error","alert-danger");
		HttpSession s = request.getSession();
		s.setAttribute("msg",msg);
;
		response.sendRedirect("loginPage.jsp");
		}
		else
		{
			HttpSession s=  request.getSession();
			s.setAttribute("currentUser",u);
			response.sendRedirect("profile.jsp");
		}
	}

}
