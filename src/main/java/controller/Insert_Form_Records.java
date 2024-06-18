package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Insert_Form_Records
 */
@WebServlet("/Insert_Form_Records")
public class Insert_Form_Records extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Insert_Form_Records() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String emp_name = request.getParameter("emp_name");
		String emp_no = request.getParameter("emp_no");
		String pass = request.getParameter("password");
		String gender = request.getParameter("gender");
		String emp_job = request.getParameter("emp_job");
		String department = request.getParameter("department");
		
		if (emp_name == null || emp_name.isEmpty() ||
	            emp_no == null || emp_no.isEmpty() ||
	            pass == null || pass.isEmpty() ||
	            gender == null || gender.isEmpty() ||
	            emp_job == null || emp_job.isEmpty() ||
	            department == null || department.isEmpty()) {

	            response.getWriter().write("Error: Please fill all the fields!");
	            return;
	        }

	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (Exception e) {
	            e.printStackTrace();
	            out.print("Error loading database: " + e.getMessage());
	            return;
	        }

	        try {
	           
	            String dbURL = "jdbc:mysql://localhost:3306/dbhost";
	            String username = "username";
	            String password = "password";

	            Connection connection = DriverManager.getConnection(dbURL, username, password);

	            
	            String insertSQL = "INSERT INTO emp_users (emp_no, password, emp_name, department, gender, emp_job) VALUES (?, ?, ?, ?, ?, ?)";
	            PreparedStatement statement = connection.prepareStatement(insertSQL);

	           
	            statement.setString(1, emp_no);
	            statement.setString(2, pass);
	            statement.setString(3, emp_name);
	            statement.setString(4, department);
	            statement.setString(5, gender);
	            statement.setString(6, emp_job);

	            try {
	                statement.executeUpdate();
	                out.print("<h2>Record inserted successfully</h2>");
	                out.print("<p>You will be redirected to the login page in 5 seconds...</p>");
	                out.print("<meta http-equiv='refresh' content='5;URL=login.html'>");
	                out.print("</body></html>");
	            } catch (SQLException e) {
	                if (e.getSQLState().equals("23000")) { 
	                    out.print("Error: Duplicate Employee Number insert another Employee Number!");
	                } else {
	                    out.print("Error inserting record: " + e.getMessage());
	                }
	            } finally {
	                statement.close();
	                connection.close();
	            }

	        } catch (SQLException e) {
	            for (Throwable t : e) {
	                t.printStackTrace();
	            }
	            out.print("Error inserting record: " + e.getMessage());
	        }
	    }

}

