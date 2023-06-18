package com.techblogdao;

import java.sql.*;

public class LikeDao {
	Connection con;
	public LikeDao(Connection con) {
		this.con=con;
		// TODO Auto-generated constructor stub
	}
	public boolean insertLike(int post_id , int user_id) {
		boolean f = false;
		try {
			String q = "insert into likes(pid,uid) values(?,?)";
			PreparedStatement ps = this.con.prepareStatement(q);
			ps.setInt(1, post_id);
			ps.setInt(2, user_id);
			ps.executeUpdate();
			f=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return f;
	}
	public int countLikeOnPost(int post_id) {
		int count=0;
		try {
		 String q = "select count(*) from likes where pid=?";
		 PreparedStatement ps = this.con.prepareStatement(q);
		 ps.setInt(1, post_id);
		 ResultSet set = ps.executeQuery();
		 if(set.next())
		 {
			 count = set.getInt("count(*)"); 
		 }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}
	public boolean isLikedByUser(int pid,int uid) throws SQLException
	{
		boolean f=false;
		PreparedStatement p = this.con.prepareStatement("select *from likes where pid=? and uid=?");
		p.setInt(1, pid);
		p.setInt(2, uid);
		ResultSet rs = p.executeQuery();
		if(rs.next())
		{
			f=true;
		}
		return f;
	}
	public boolean deleteLike(int pid,int uid) throws SQLException
	{
		boolean f=false;
		PreparedStatement p = this.con.prepareStatement("delete from likes where pid=? and uid=?");
		p.setInt(1, pid);
		p.setInt(2, uid);
	 p.executeUpdate();
		f=true;
		return f;
	}
}
