<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*"%>
<%@page import="Dao.*"%>
<%@ page import="Com.Connection.*"%>
<%@page import="Dao.user"%>
<%@page import="java.sql.ResultSet"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.*,java.text.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Data Storage Security System</title>
<link rel="favicon" href="assets1/images/favicon.png">
<link rel="stylesheet" media="screen"
	href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,700">
<link rel="stylesheet" href="assets1/css/bootstrap.min.css">
<link rel="stylesheet" href="assets1/css/font-awesome.min.css">
<link rel="stylesheet" href="assets1/css/bootstrap-theme.css"
	media="screen">
<link rel="stylesheet" type="text/css" href="assets1/css/da-slider.css" />
<link rel="stylesheet" href="assets1/css/style.css">
<script src="assets1/js/html5shiv.js"></script>
<script src="assets1/js/respond.min.js"></script>
</head>
<body>
	<div class="navbar navbar-inverse" style="background-color: black;">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span><span class="icon-bar"></span><span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="Index.jsp">
					<h4>Data Storage Security System</h4>
				</a>
			</div>

			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav pull-right mainNav">

					<li class="active"><a href="UserHomePage.jsp">Home</a></li>
					<li><a href="File_Access.jsp">File Access</a></li>


					<li><a href="LoginPage"><label style="color: white;"><%=session.getAttribute("U_Name")%></label><b
							class="caret"></b></a></li>

				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
	<!-- /.navbar -->

	<!-- Header -->
	<header id="headO2">
		<div class="container">
			<div class="banner-content">
				<%
					String key = request.getParameter("key");
					if (key == null) {

						String datainfo = request.getParameter("name");
						String[] infodata = datainfo.split(",");
						String O_FileName = infodata[2].toString();

						String ownername = infodata[1].toString();
						String emailid = (String) session.getAttribute("U_Email_ID");
						//String key=request.getParameter("key");
						System.out.println("UserEmail=>" + emailid + "\t Owner Email=>"
								+ ownername + "\t FileName=>" + O_FileName);

						int flag = 0;

						Connection con1 = ConnectionFactory.getInstance()
								.getConnection();
						//
						Statement st1 = con1.createStatement();
						ResultSet rs1 = st1.executeQuery("select * from filerequest");
						if (rs1.next()) {

							if (rs1.getString("U_Status").contains("Pending")) {
								flag = 1;
							} else {
								flag = 2;
							}

						}
						if (flag == 1) {
							///
							Statement st01 = con1.createStatement();
							ResultSet rs001 = st01
									.executeQuery("select * from filerequest  where username='"
											+ emailid
											+ "'and Ownername='"
											+ ownername
											+ "' and Filename='"
											+ O_FileName
											+ "' and U_Status='Pending' ");
							if (rs001.next()) {
								System.out.println("NFile If");
				%>


			</div>
		</div>
	</header>
	<!-- /Header -->

	<div id="courses"></div>
	<!-- container -->
	<section class="container">
		<br>
		<div class="row">
			<div class="col-md-4">
				<img src="./images/login.jpg" alt="" class="img-responsive">
			</div>
			<div class="col-md-8">
				<div>
					<div>
						<div class="panel panel-primary">
							<div class="panel-heading" align="center">File Download
								Page</div>
							<div class="panel-body">
								<form class="form-light mt-20" action="File_Download"
									method="post" autocomplete="off">
									<div class="form-group">
										<b>Owner Email Id</b> <input type="text" class="form-control"
											name="ownername" value="<%=ownername%>" readonly="readonly">
										<br> <b>File Name</b> <input type="text"
											class="form-control" name="name" value="<%=O_FileName%>"
											readonly="readonly"> <br> <b>Enter Key</b> <input
											type="text" class="form-control" name="attribute_1"
											placeholder="Enter Key" required>

										<!-- <select class="form-control" name="attribute_5">
            <option value="a">Select Gender</option>
  <option value="Male">Male</option>
  <option value="Female">Female</option>  
</select>  -->
									</div>
									<input type="submit" value="Download" class="btn btn-success">
									<input type="reset" class="btn btn-danger"><br /> <br />
								</form>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</section>
	<%
		}//if db
			} // flag if end
			else {
				Statement st01 = con1.createStatement();
				ResultSet rs001 = st01
						.executeQuery("select * from filerequest  where username='"
								+ emailid
								+ "'and Ownername='"
								+ ownername
								+ "' and Filename='" + O_FileName + "'");
				if (rs001.next()) {
					System.out.println("NFile If");
	%>

	</div>
	</div>
	</header>
	<!-- /Header -->
	<div id="courses"></div>
	<!-- container -->
	<section class="container">
		<br>
		<div class="row">
			<div class="col-md-4">
				<img src="./images/login.jpg" alt="" class="img-responsive">
			</div>
			<div class="col-md-8">
				<div>
					<div>
						<div class="panel panel-primary">
							<div class="panel-heading" align="center">File Download
								Page</div>
							<div class="panel-body">
								<form class="form-light mt-20" action="File_Download"
									method="post" autocomplete="off">
									<div class="form-group">
										<b>Owner Email Id</b> <input type="text" class="form-control"
											name="ownername" value="<%=ownername%>" readonly="readonly">
										<br> <b>File Name</b> <input type="text"
											class="form-control" name="name" value="<%=O_FileName%>"
											readonly="readonly"> <br> <b>Enter Key</b> <input
											type="text" class="form-control" name="attribute_1"
											placeholder="Enter Key" required>
									</div>
									<input type="submit" value="Download" class="btn btn-success">
									<input type="reset" class="btn btn-danger"><br /> <br />
								</form>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</section>
	<%
		}// if db
			} //flag else end

		} else if (key.equals("3")) {
	%>


	<script type="text/javascript">
		alert("Wrong Key Please try again");
	</script>


	<%
		//response.sendRedirect("File_Downloads.jsp");

		}
	%>
	<!-- JavaScript libs are placed at the end of the document so the pages load faster -->
	<script src="assets1/js/modernizr-latest.js"></script>
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script
		src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
	<script src="assets1/js/jquery.cslider.js"></script>
	<script src="assets1/js/custom.js"></script>
</body>
</html>