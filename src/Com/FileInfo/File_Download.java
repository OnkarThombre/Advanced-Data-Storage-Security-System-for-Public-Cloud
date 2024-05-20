package Com.FileInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import Com.Connection.*;
import Com.shamirdemo.*;

/**
 * Servlet implementation class File_Download
 */
@WebServlet("/File_Download")
public class File_Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String origional = "";
	public String fdata;
	public String file;
	byte[] key = null;
	byte[] data = null;
	public static int occurrence = 0;
	public static ArrayList<String> al2 = new ArrayList<String>();
	public static ArrayList<String> al1 = new ArrayList<String>();
	public static ArrayList<String> compare = new ArrayList<String>();

	public File_Download() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream op = response.getOutputStream();
		HttpSession session = request.getSession(false);
		al1.clear();
		al2.clear();
		compare.clear();
		String ContentType = "";
		String fileName = request.getParameter("name");
		String email = (String) session.getAttribute("U_Email_ID");
		String key1;// Name,Position,Department,Branch,Gender;
		key1 = request.getParameter("attribute_1");
		System.out.println("Key=>" + key1);
		System.out.println("file Name===>" + fileName);
		session.setAttribute("fileName", fileName);
		String ownername = request.getParameter("ownername");

		try {
			Connection con = ConnectionFactory.getInstance().getConnection();
			String keydb = null;
			Statement stmt1 = con.createStatement();
			String sha_key = HashGeneratorUtils.generateSHA256(key1);
			ResultSet rs1 = stmt1
					.executeQuery("select * from tblsha where Data_Sha='"
							+ sha_key + "'");
			if (rs1.next()) {
				keydb = "1";
			}
			else{
				keydb="0";
				
			}

			if (keydb.toLowerCase().equals("1")) {
				Statement st = con.createStatement();
				ResultSet rs = st
						.executeQuery("select * from file_info where Filename='"
								+ fileName + "'");
				if (rs.next()) {
					data = rs.getBytes(6);
					// String finalkey=rs.getString(4);
					key = rs.getBytes(7);
					ContentType = rs.getString("ContentType");
				}
				if (ContentType.equals("application/pdf")) {
					byte[] pl = AESCoder.decrypt(key, data);
					fdata = new String(pl);
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition",
							"attachment; filename=\"" + fileName + "\"");
					Document document = new Document();
					try {
						PdfWriter.getInstance(document, op);
						document.open();
						document.add(new Paragraph(fdata));
						document.close();

					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (ContentType.equals("text/plain")) {

					System.out.println("File download successfully");
					response.setHeader("Content-Type",
							"application/octet-stream");
					response.setContentType("text/plain");
					response.setHeader("Content-Disposition",
							"attachment; filename=\"" + fileName + "\"");
					byte[] pl = AESCoder.decrypt(key, data);
					fdata = new String(pl);
					op.println(fdata);
				} else if (ContentType
						.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
					byte[] pl = AESCoder.decrypt(key, data);
					fdata = new String(pl);
					response.setContentType("text/html");
					response.setHeader("Content-Disposition",
							"attachment; filename=\"" + fileName + "\"");
					XWPFDocument docx = new XWPFDocument();
					XWPFParagraph paragraphx = docx.createParagraph();
					XWPFRun runx = paragraphx.createRun();
					runx.setText(fdata);// read line by line file Input to
										// Output
					docx.write(op);// write line by line
				} else {
					try {
						// connects to the database
						byte[] imageData = null;

						// queries the database
						Statement st01 = con.createStatement();
						ResultSet result1 = st01
								.executeQuery("select * from file_info where Filename='"
										+ fileName
										+ "' and emailid='"
										+ ownername + "'");

						if (result1.next()) {
							Blob blob = result1.getBlob("filedata");
							imageData = blob.getBytes(1, (int) blob.length());
							InputStream inputStream = blob.getBinaryStream();

							OutputStream o = response.getOutputStream();
							o.write(imageData);
							inputStream.close();
							// outputStream.close();
						}
						response.setHeader("Content-Type",
								"application/octet-stream");
						// response.setContentType("audio/3gpp");
						response.setHeader("Content-Disposition",
								"attachment; filename=\"" + fileName + "\"");

					} catch (SQLException ex) {
						ex.printStackTrace();

					} catch (IOException ex) {
						ex.printStackTrace();
						response.getWriter().print(
								"IO Error: " + ex.getMessage());
					}
				}

			} else {
				// System.out.println("occurrences=>"+occurrence);
				response.sendRedirect("File_Access.jsp?key=1");
			}

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
