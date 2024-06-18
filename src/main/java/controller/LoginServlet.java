package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
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

	        String username = request.getParameter("username");
	        String password = request.getParameter("password");

	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbhost", "username", "password");
	            PreparedStatement ps = con.prepareStatement("SELECT * FROM emp_users WHERE emp_no=? AND password=?");
	            ps.setString(1, username);
	            ps.setString(2, password);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	               out.print("Hello user, " + username + "!" +"\nWelcome back!");
	            } else {
	                out.print("Sorry, username or password error!");
	                request.getRequestDispatcher("login.html").include(request, response);
	            }
	            con.close();
	        } catch (Exception e) {
	            out.println(e);
	        }
	        out.close();
	    
	}

}
