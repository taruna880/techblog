package com.techblogdao;
import java.sql.*;

import com.techblogentities.User;


public class UserDao {
	private Connection con;
	public UserDao(Connection con)
	{
		this.con=con;
	}
	public boolean saveUser(com.techblogentities.User user)
	{
		boolean f=false;
		try {
			String q = "insert into users(name,email,password,gender,about) values(?,?,?,?,?)";
			PreparedStatement ps = this.con.prepareStatement(q);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getGender());
			ps.setString(5, user.getAbout());
			ps.executeUpdate();
			f=true; 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return f;
	}
	public com.techblogentities.User getUserByEmailandPassword(String email,String password)
	{
		com.techblogentities.User user=null;
		try {
			String q = "select*from users where email=? and password=?";
			PreparedStatement ps = con.prepareStatement(q);
			ps.setString(1, email);
			ps.setString(2,password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user = new com.techblogentities.User();
				String name = rs.getString("name");
				user.setName(name);
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setGender(rs.getString("gender"));
				user.setAbout(rs.getString("about"));
				user.setDateTime(rs.getTimestamp("rdate"));
				user.setProfile(rs.getString("profile"));
		 	 }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return user;
		
	}
	public boolean updateUser(User user)
	{
		boolean f= false;
		try {
			String q= "update users set name=? , email=? , password=? , gender=? , about=? , profile=? where id=?";
			PreparedStatement p = con.prepareStatement(q);
			p.setString(1,user.getName() );
			p.setString(2,user.getEmail());
			p.setString(3, user.getPassword());
			p.setString(4, user.getGender());
			p.setString(5, user.getAbout());
			p.setString(6, user.getProfile());
			p.setInt(7, user.getId());
			p.executeUpdate();
			f=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return f;
	}
	public User getUserByUserId(int userId)
	{
			User user=null;
			try {
			String q = "select*from users where id=?";
			PreparedStatement ps= this.con.prepareStatement(q);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				user = new com.techblogentities.User();
				String name = rs.getString("name");
				user.setName(name);
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setGender(rs.getString("gender"));
				user.setAbout(rs.getString("about"));
				user.setDateTime(rs.getTimestamp("rdate"));
				user.setProfile(rs.getString("profile"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return user;
	}
}
