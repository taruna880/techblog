package com.techblog;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techbloghelper.ConnectionProvider;

/**
 * Servlet implementation class RegisterServlet
 */
@MultipartConfig
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String chk = request.getParameter("check");
		//out.println(chk);
		if(chk==null)
		{
			out.print("Box not checked");
		}
		else
		{
			String name = request.getParameter("user_name");
			String email = request.getParameter("user_email");
			String password = request.getParameter("user_password");
			String gender = request.getParameter("gender");
			String about = request.getParameter("about");
			
			com.techblogentities.User user = new com.techblogentities.User(name,email,password,gender,about);
			//CREate a userDao object
			com.techblogdao.UserDao dao = new com.techblogdao.UserDao(ConnectionProvider.getConnection());
			if(dao.saveUser(user))
			{
				out.print("done");
			}
			else
			{
				out.print("Errror detected");
			}
		}
	}

}
