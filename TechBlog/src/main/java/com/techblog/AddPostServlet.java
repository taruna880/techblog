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

import com.techblogdao.PostDao;
import com.techblogentities.Post;
import com.techblogentities.User;
import com.techbloghelper.ConnectionProvider;
import com.techbloghelper.Helper;

/**
 * Servlet implementation class AddPostServlet
 */
@MultipartConfig
@WebServlet("/AddPostServlet")
public class AddPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int cid =Integer.parseInt(request.getParameter("cid"));
		
		String pTitle = request.getParameter("pTitle");
		String pContent = request.getParameter("pContent");
		String pCode = request.getParameter("pCode");
		Part part = request.getPart("pic");
		
		HttpSession s = request.getSession();
		User user = (User)s.getAttribute("currentUser");
		
		//out.println("post title is" + pTitle);
	//	out.println();
		Post p = new Post(pTitle,pContent,pCode,part.getSubmittedFileName(),null,cid,user.getId());
		PostDao pd = new PostDao(ConnectionProvider.getConnection());
		if(pd.savePost(p))
		{
			
			String path = request.getSession().getServletContext().getRealPath("/") +"Post_pics"+File.separator +part.getSubmittedFileName();
	     
			Helper.saveFile(part.getInputStream(), path);	
			out.println("DONE");
		}
		else
		{
			out.println("error");
		}
	}

}
