<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page errorPage="errorPage.jsp" %>
    <%
    	com.techblogentities.User user =(com.techblogentities.User)session.getAttribute("currentUser");
    if(user==null)
    {
    	response.sendRedirect("loginPage.jsp");
    }
    %>
    <%@page import ="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link href="css/mystyle.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
body{
		background: url(img/bg5.jpeg);
		background-size:cover;
		background-attachment:fixed;
	}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark primary-background">
  <a class="navbar-brand" href="index.jsp"><span class="fa fa-asterisk"></span>Tech blog</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Code with Taruna <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Contact</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Dropdown
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Programming</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">DSA</a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link " href="#">Contact</a>
      </li>
     <li class="nav-item">
        <a class="nav-link "  href="#" data-toggle="modal" data-target="#add-post-modal">Do post</a>
      </li>
       
    </ul>
   <ul class="navbar-nav mr-right">
   	<li class="nav-item">
        <a class="nav-link" href="#!" data-toggle="modal" data-target="#profile-modal"><span class ="fa fa-user-circle"></span><%= user.getName() %></a>
      </li> 
   	<li class="nav-item">
        <a class="nav-link" href="LogoutServlet"><span class ="fa fa-user-plus"></span>Logout</a>
      </li>
   </ul>
  </div>
</nav>

<%
							com.techblogentities.Message m = (com.techblogentities.Message)session.getAttribute("msg");
						if(m !=null)
						{
							%>
								<div class="alert <%= m.getCssClass() %> role="alert">
								 <%= m.getContent() %>
								</div>
								<%
								session.removeAttribute("msg");
						}
						%>
						
						<!-- Main body of the page -->
						<main>
							<div class="container">
								<div class="row mt-4">
									<div class="col-md-4">
										<!-- categories -->
											<div class="list-group">
										  <a href="#" onclick = "getPosts(0,this)"  class="c-link list-group-item list-group-item-action active">
										   All Posts
										  </a>
										 <% com.techblogdao.PostDao pd1 = new com.techblogdao.PostDao(com.techbloghelper.ConnectionProvider.getConnection());
						        		ArrayList<com.techblogentities.Category> lis= pd1.getAllCategories();
						        		for(com.techblogentities.Category c2: lis)
						        		{
						        		%>
						        		<a href="#" onclick="getPosts(<%= c2.getCid() %>,this)" class="c-link list-group-item list-group-item-action" ><%= c2.getName() %></a>
						        		
						        		<%} %>
										<!--  <a href="#" class="list-group-item list-group-item-action">Dapibus ac facilisis in</a>
-->  
										</div>
									</div>
									<div class="col-md-8"  >
										<!-- posts -->
									<div class="container text-center" id="loader">
										<i class="fa fa-refresh fa-3x fa-spin"></i>
										<h4 class="mt-2">Loading...</h4>
									</div>
										<div class="container-fluid" id="post-container">
										
											</div>
									</div>
								</div>
							</div>
						</main>
						
						<!-- End of Main body of the page -->
	<!-- Button trigger modal -->


<!-- Modal -->
<div class="modal fade" id="profile-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header primary-background text-white  " >
        <h5 class="modal-title" id="exampleModalLabel"> TechBlog</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="container text-center">
      <img src="pics/<%= user.getProfile() %>" class="img-fluid" style="border-radius:50%;max-width:150px;">
       <h5 class="modal-title mt-3" id="exampleModalLabel"> <%= user.getName() %></h5>
       <!-- Details -->
       <div id="profile-details">
    <table class="table">
  <tbody>
    <tr>
      <th scope="row">ID: </th>
      <td><%= user.getId() %></td>
      
    </tr>
    <tr>
      <th scope="row">Email: </th>
      <td><%=user.getEmail() %></td>
  
    </tr>
    <tr>
      <th scope="row">Gender</th>
      <td><%= user.getGender() %></td>
    
    </tr>
     <tr>
      <th scope="row">About</th>
      <td><%= user.getAbout() %></td>
    
    </tr>
     <tr>
      <th scope="row">Registered on</th>
      <td><%= user.getDateTime()%></td>
    
    </tr>
  </tbody>
</table>
</div> 
	
	<div id="profile-edit" style="display:none;">
		<h3 class= "mt-2">Please edit carefully..</h3>
		<form action="EditServlet" method="post" enctype="multipart/form-data">
			<table class="table">
				<tr>
					<td>ID: </td>
					<td><%= user.getId() %>	</td>
				</tr>
				
				<tr>
					<td>Email: </td>
					<td><input type="email" class="form-control"  name ="user_email" value="<%= user.getEmail()%>"></td>
				</tr>
				
				<tr>
					<td>Name: </td>
					<td><input type="text" class="form-control"  name ="user_name" value="<%= user.getName()%>"></td>
				</tr>
				
				<tr>
					<td>Password : </td>
					<td><input type="password" class="form-control"  name ="user_password" value="<%= user.getPassword()%>"></td>
				</tr>
				
				<tr>
					<td>Gender: </td>
					<td><%= user.getGender().toUpperCase() %>					</td>
				</tr>
				
				<tr>
					<td>About : </td>
					<td><textarea rows="3" class="form-control"  name ="user_about" ><%= user.getPassword()%>"></textarea></td>
				</tr>
				  <tr>
					<td>New Profile: </td>
					<td><input type="file" name="image" ></td>
				</tr>
			
			</table>
			<div class="container">
				<button type="submit" class="btn btn-outline-primary">Save</button>
			</div>
		</form>
	</div>
	
       </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button id="edit-profile-button" type="button" class="btn btn-primary">Edit</button>
      </div>
    </div>
  </div>
</div>
		<!-- Add post  modal -->
		

<!-- Modal -->
<div class="modal fade" id="add-post-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Provide the post details</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="add-post-form" action="AddPostServlet" method="POST" >
        	<div class="form-group">
        	<select class="form-control" name ="cid">
        		<option selected disabled>---Select Category---</option>
        		<% com.techblogdao.PostDao pd = new com.techblogdao.PostDao(com.techbloghelper.ConnectionProvider.getConnection());
        		ArrayList<com.techblogentities.Category> list= pd.getAllCategories();
        		for(com.techblogentities.Category c: list)
        		{
        		%>
        		<option value="<%=c.getCid() %>"><%= c.getName() %></option>
        		
        		<%} %>
        	</select>
        	</div>
        	<div class="form-group" >
        		<input name="pTitle" type="text" placeholder="Enter post title" class="form-control">
        	</div>
        	<div class="form-group" >
        		<textarea name ="pContent" class="form-control" placeholder="Enter your content" ></textarea>
        	</div>
        	<div class="form-group" >
        		<textarea name="pCode" class="form-control" placeholder="Enter your program (if any) " ></textarea>
        	</div>
        	<div class="from-group">
        	<label>Select your pic</label>
        	<br>
        	<input name="pic" type="file">
        	</div>
        	 <div class="container text-center">
      	<button type="submit" class="btn btn-outline-primary">Post</button>
      </div>
        </form>
      </div>
     
    </div>
  </div>
</div>
		<!-- End post  modal -->
	<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<script src="js/myjs.js" type="text/javascript"></script>
<script>
	$(document).ready(function(){
		
		let editStatus = false;
		
		$('#edit-profile-button').click(function(){
			if(editStatus==false){
			$('#profile-details').hide();
			$('#profile-edit').show();
			editStatus=true;
			$(this).text("Back");
			}
			else
				{
				$('#profile-details').show();
				$('#profile-edit').hide();
				editStatus=false;
				$(this).text("Edit");
				}
		})
	});
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
<!-- adding post js -->

	<script>
		 $(document).ready(function(e){
			 console.log("hello");
			$("#add-post-form").on('submit',function(event){
				console.log("hello");
				event.preventDefault();
				console.log("submitted");
				let form = new FormData(this);
				$.ajax({
					url:'AddPostServlet',
					type: 'POST',
					data: form,
					success: function(data,textStatus,jqXHR){
						console.log(data);
						if(data.trim()=='DONE')
							{
							swal("Good job!", "Saved Successfully", "success");
							}
						else
							{
							swal("Error!", "Something went wrong...try again!", "error");
							}
					},
					error: function(jqXHR,textStatus,errorThrown){
						//console.log("errror");
						swal("Error!", "Something went wrong...try again!", "error");
					},
					processData: false,
					contentType: false
				})
			}) 
			 
		 })
	</script>
	<script>
		function getPosts(catId,temp){
				$("#loader").show();
				$("#post-conatiner").hide();
				$(".c-link").removeClass('active')
				$.ajax({
					url: "loadPost.jsp",
					data: {cid:catId},
					success: function(data,textStatus,jqXHR){
						console.log(data);
						$("#loader").hide  ();
						$("#post-conatiner").show();
						$("#post-container").html(data)
						$(temp).addClass('active')
					}
				
			})
		}
		$(document).ready(function(e){
			let allPostRef =$('.c-link')[0]
			getPosts(0,allPostRef)
		})
	</script>
</body>
</html>