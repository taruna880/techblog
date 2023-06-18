package com.techblogdao;
import java.sql.*;
import java.util.*;
import java.util.ArrayList;

import com.techblogentities.Category;
import com.techblogentities.Post;
public class PostDao {
	Connection con;
	public PostDao(Connection con)
	{
		this.con=con;
	}
	public ArrayList<Category> getAllCategories(){
		ArrayList<Category> list = new ArrayList<>();
		try {
			String q= "select*from category";
			Statement st = this.con.createStatement();
			ResultSet rs = st.executeQuery(q);
			while(rs.next())
			{
				int cid= rs.getInt("cid");
				String name= rs.getString("name");
				String description = rs.getString("description");
				Category c = new Category(cid,name,description);
				list.add(c);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	public boolean savePost(Post p)
	{
		boolean f = false;
		try {
			String q = "insert into post(pTitle,pContent,pCode,pPic,catId,uid) values(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(q);
			ps.setString(1,p.getpTitle() );
			ps.setString(2, p.getpContent());
			ps.setString(3, p.getpCode());
			ps.setString(4, p.getpPic());
			ps.setInt(5, p.getCatId());
			ps.setInt(6, p.getUid());
			
			ps.executeUpdate();
			f= true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return f;
	}
	public List<Post> getAllPost()
	{
		List<Post> list = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement("select*from post order by pid desc");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				int pid= rs.getInt("pid");
				String pTitle = rs.getString("pTitle");
				String pContent = rs.getString("pContent");
				String pCode = rs.getString("pCode");
				String pPic = rs.getString("pPic");
				Timestamp pDate = rs.getTimestamp("pDate");
				int catId = rs.getInt("catId");
				int uid = rs.getInt("uid");
				Post p = new Post(pid,pTitle,pContent,pCode,pPic,pDate,catId,uid );
				list.add(p);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	public List<Post> getPostByCatID(int catId)
	{
		List<Post> list = new ArrayList<>();
		try {
			PreparedStatement ps = con.prepareStatement("select*from post where catId = ?");
			ps.setInt(1,catId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				int pid= rs.getInt("pid");
				String pTitle = rs.getString("pTitle");
				String pContent = rs.getString("pContent");
				String pCode = rs.getString("pCode");
				String pPic = rs.getString("pPic");
				Timestamp pDate = rs.getTimestamp("pDate");
				
				int uid = rs.getInt("uid");
				Post p = new Post(pid,pTitle,pContent,pCode,pPic,pDate,catId,uid );
				list.add(p);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	public Post getPostByPostId(int post_id)
	{
		Post p=null;
		try {
		String q= "select*from post where pid=?";
		PreparedStatement ps = this.con.prepareStatement(q);
		ps.setInt(1,post_id);
		ResultSet rs= ps.executeQuery();
			if(rs.next())
			{
				int pid= rs.getInt("pid");
				String pTitle = rs.getString("pTitle");
				String pContent = rs.getString("pContent");
				String pCode = rs.getString("pCode");
				String pPic = rs.getString("pPic");
				Timestamp pDate = rs.getTimestamp("pDate");
				int cid = rs.getInt("catId");
				int uid = rs.getInt("uid");
			 p = new Post(pid,pTitle,pContent,pCode,pPic,pDate,cid,uid );
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return p;
	}
}
