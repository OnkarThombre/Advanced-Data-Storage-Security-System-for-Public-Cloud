package Com.AdminInfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Com.Connection.ConnectionFactory;

/**
 * Servlet implementation class U_History_Reject
 */
@WebServlet("/U_History_Reject")
public class U_History_Reject extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public U_History_Reject() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String U_Email=request.getParameter("uemail");
		System.out.println("UEmail=>"+U_Email);
		 Connection con = null;
		try {	

			con=ConnectionFactory.getInstance().getConnection();
			String U_Name = null,U_Password= null,U_Gender= null,U_Role= null,U_Mobile= null,Birth_Date= null,U_Status= null;
			Statement sts=con.createStatement();
			ResultSet rs=sts.executeQuery("select * FROM tbluser WHERE U_Email_ID='"+U_Email+"'");
			if(rs.next())
			{
				U_Name=rs.getString("U_Name");
				U_Password=rs.getString("U_Password");
				U_Gender=rs.getString("U_Gender");
				U_Role=rs.getString("U_Role");
				U_Mobile=rs.getString("U_Mobile");
				Birth_Date=rs.getString("Birth_Date");
				
			}
			//String U_ID=userid(U_Name);
			
			Statement st=(Statement) con.createStatement();
		String msg="Reject";
		st.executeUpdate("update tbluser set U_Status='"+msg+"' where U_Email_ID='"+U_Email+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			request.getRequestDispatcher("/U_History_Request.jsp").include(request, response);
			


		//AcceptReq.acceptreq(U_Name,U_Password,U_Gender,U_Role, U_Mobile,Birth_Date,msg,U_Email,U_ID);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
