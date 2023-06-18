package com.techblog;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.techblogdao.UserDao;
import com.techblogentities.User;
import com.techbloghelper.ConnectionProvider;
import com.techbloghelper.Helper;

/**
 * Servlet implementation class EditServlet
 */
@MultipartConfig
@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Fetch all data
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String userEmail = request.getParameter("user_email");
		String userName = request.getParameter("user_name");
		String userPassword = request.getParameter("user_password");
		String userAbout = request.getParameter("user_about");
		Part part = request.getPart("image");
		String imgName =part.getSubmittedFileName();
		HttpSession s= request.getSession();
		User user = (User)s.getAttribute("currentUser");
		user.setEmail(userEmail);
		out.print(userEmail);
		user.setName(userName);
		user.setPassword(userPassword);
		user.setAbout(userAbout);
		String oldProfile = user.getProfile();
		//System.out.print("Old"+oldProfile);
		user.setProfile(imgName);
		//update in db
		UserDao userDao = new UserDao(ConnectionProvider.getConnection());
		boolean ans = userDao.updateUser(user);
		System.out.print(ans);
		if(ans)
			{
			
				
				String path = request.getSession().getServletContext().getRealPath("/") +"pics"+File.separator+user.getProfile();
				//System.out.print(path);
				
				String pathOld =request.getSession().getServletContext().getRealPath("/")+"pics"+File.separator+ oldProfile;
				//System.out.print(path+"  "+pathOld);
				if(!oldProfile.equals("def.jpeg"))
				{
					Helper.deleteFile(pathOld);
					
				}
					if(Helper.saveFile(part.getInputStream(), path))
					{
						//out.println("PROFILE   updated to db");
						com.techblogentities.Message msg = new com.techblogentities.Message("Profile details Updated...!", "success","alert-success");
						//HttpSession s1 = request.getSession();
						s.setAttribute("msg",msg);
					}
					else
					{
						com.techblogentities.Message msg = new com.techblogentities.Message("Something went wrong..", "error","alert-danger");
						//HttpSession s = request.getSession();
						s.setAttribute("msg",msg);
					}
				
			}
		else
			{
			com.techblogentities.Message msg = new com.techblogentities.Message("Something went wrong..", "error","alert-danger");
			//HttpSession s = request.getSession();
			s.setAttribute("msg",msg);
			
			}
		response.sendRedirect("profile.jsp");
		//out.println(" NOT....updated to db");
	}

}
