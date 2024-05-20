package Com.FileInfo;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Com.Connection.ConnectionFactory;

/**
 * Servlet implementation class ShareServlet
 */
@WebServlet("/ShareServlet")
public class ShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static long starttime = 0, endtime = 0, total = 0;
	public String fromEmail;
	public String password;
	public Session mailSession;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShareServlet() {
		super();  
		String fromEmail = "encloud27@gmail.com";
		String password = "jszo aeuq nnqo tbvu";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		this.fromEmail = fromEmail;
		this.password = password;

		Session mailSession = Session.getInstance(props,
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								fromEmail, password);
					}
				});
		this.mailSession = mailSession;
		System.out.println("Email Session connected");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String[] username = request.getParameterValues("Checkbox");
		String filename = request.getParameter("filename");
		String O_Name = "";
		String name = (String) session.getAttribute("O_Name");
		String Owner_Email = (String) session.getAttribute("O_Email_ID");
		// String email="";
		starttime = System.currentTimeMillis();

		try {
			for (String s : username) {
				Connection con = ConnectionFactory.getInstance()
						.getConnection();
				Statement st = con.createStatement();

				try {
					String query1 = "select * from file_info where Filename='"
							+ filename + "' and emailid='" + Owner_Email + "'";
					ResultSet rs1 = st.executeQuery(query1);

					while (rs1.next()) {
						System.out.println("Selected User Name=>" + s);
						O_Name = rs1.getString("Username");
					}

					// System.out.println(" From "+ Owner_Email+" To "+s);

					// SendMail.mailSend(name,filename, email);

					System.out.println("");

				} catch (Exception e) {
				}

				String ownername = (Owner_Email);
				String touser = (s);
				String data2 = ownername + "#" + filename + "#" + touser;
				// share file

				String Status = "Pending", RequestAuthorName = "0";
				Statement st00 = (Statement) con.createStatement();
				st00.executeUpdate("insert into filerequest (username, Filename, Ownername,U_Status) values('"
						+ s
						+ "','"
						+ filename
						+ "','"
						+ Owner_Email
						+ "','"
						+ Status + "')");

				try {
					String q = "select P_Key from file_info where Filename='"
							+ filename + "' and emailid='" + Owner_Email + "'";
					ResultSet rs = st.executeQuery(q);
					while (rs.next()) {
						String P_Key = rs.getString("P_Key");

						Message message = new MimeMessage(this.mailSession);
						message.setFrom(new InternetAddress());
						message.setRecipients(Message.RecipientType.TO,
								InternetAddress.parse(s));
						message.setSubject("Public key sharing for ENCloud...");
						message.setText("Dear "
								+ s
								+ ",\n\nThank You for using our ENCloud storage."
								+ "\n\n" + Owner_Email
								+ "has shared a public key with access of the file \t:" + P_Key
								+ "\nPlease access file using above provided public key");
						Transport.send(message);

						System.out
								.println("Registration confirmation email sent to "
										+ s);
					}
				} catch (Exception e) {
					throw e;
				}
			}
		} catch (Exception e) {
			// System.out.println(e);
			e.printStackTrace();
		}
		endtime = System.currentTimeMillis();
		total = endtime - starttime;
		System.out.println("Current Time=>" + total);
		pw.println("<html><script>alert('File Share Success');</script><body>");
		pw.println("");
		pw.println("</body></html>");
		RequestDispatcher rd = request.getRequestDispatcher("/File_Share.jsp");
		rd.include(request, response);
	}

}
