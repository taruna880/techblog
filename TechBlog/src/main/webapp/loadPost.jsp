<%@page import="com.techblogdao.LikeDao"%>
<%@page import="com.techblogentities.User"%>
<%@page import="java.util.List"%>
<%@page import="com.techblogentities.Post"%>
<%@page import="com.techblogdao.PostDao"%>
<%@page import="com.techbloghelper.ConnectionProvider"%>
<div class="row">
<%
	User user = (User) session.getAttribute("currentUser");
//Thread.sleep(500);
	PostDao d = new PostDao(ConnectionProvider.getConnection());
int cid= Integer.parseInt(request.getParameter("cid"));
List<Post> posts =null;
if(cid==0)
	{
	posts = d.getAllPost();
	}
else{
	posts= d.getPostByCatID(cid);
}
if(posts.size()==0)
{
	out.println(" <h3 class='display-3 text-center' >No post in this category...</h3> ");
	return;
}
	for(Post p:posts)
	{
		
	
%>
	<div class="col-md-6 mt-2">
		<div class="card">
		 <img class="card-img-top" src="Post_pics/<%= p.getpPic() %>" alt="Card image cap">
		<div class="card-body">
		 <img class="card-img-top my-2" src="Post_pics/<%= p.getpPic() %>>">
			<b><%= p.getpTitle() %></b>
			<p><%= p.getpContent() %></p>
			<div class="card-footer primary-background text-center">
			<%  
				LikeDao ld = new LikeDao(ConnectionProvider.getConnection());
			
			%>
			  <a href="#!" onclick="doLike(<%= p.getPid() %>,<%= user.getId() %>)" class="btn btn-outline-light btn-sm" ><span class="fa fa-thumbs-o-up"></span><span class="like-counter"><%= ld.countLikeOnPost(p.getPid()) %></span></a>
		<!--	<a href="#!" class="btn btn-outline-light btn-sm"  ><i class="fa fa-thumbs-o-up"></i><span>10</span></a> -->
				<a href="showBlogDetails.jsp?post_id=<%= p.getPid() %>" class="btn btn-outline-light btn-sm"  >Read More..</a>
			
		<a href="#!" class="btn btn-outline-light btn-sm"  ><i class="fa fa-commenting-o"></i><span>5</span></a>
			</div>
		</div>	
		</div>
	</div>
<% 
	} 
%>
</div>
<script>
  function doLike(pid, uid) {
    // Add your logic for handling the like operation here
    console.log("Like button clicked for PID: " + pid + ", UID: " + uid);
    const d={
    		uid: uid,
    		pid: pid,
    		operation:'like'
    		
    }
    $.ajax({
    	url:"LikeServlet",
    	data: d,
    	success : function(data,textStatus,jqXHR){
    		console.log(data);
    		if(data.trim()=='true')
    			{
    			let c = $(".like-counter").html();
    			c++;
    			$('.like-counter').html(c);
    			}
    	},
    	error: function(jqXHR,textStatus,errorThrown){
    		console.log(data)
    	}
    })
  }
</script>