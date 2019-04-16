package Friendship;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/AddFriendServlet")
public class AddFriendServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	protected static final String DB_URL = "jdbc:mysql://localhost:3306/Audioface";
	protected static final String USER = "root";
	protected static final String PASS = "Rp1m34man*4308";
       
    public AddFriendServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String friend1 = "";
		String friend2 = "";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
        	System.out.println("Trying to add friendship...");
        	friend1 = (String) request.getParameter("friend1Userid");
        	friend2 = (String) request.getParameter("friend2Userid");
        	System.out.println(friend1 + " " + friend2);
        	
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL + "?user=" + USER + "&password=" + PASS + "&serverTimezone=UTC");
            
            String sql = "SELECT * FROM friendship WHERE friend1=? AND friend2=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, friend1); // set first variable in prepared statement
            ps.setString(2, friend2);
            rs = ps.executeQuery();
            
            if(!rs.next()) {
            	sql = "INSERT INTO friendship (friend1, friend2) VALUES (?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, friend1); // set first variable in prepared statement
                ps.setString(2, friend2);
                ps.executeUpdate();
                System.out.println("Successfully added friendship!");
            } else {
            	System.out.println("Friendship already exists!");
            }
        }
        catch (SQLException sqle) {
            System.out.println ("before sqle: "+ sqle.getMessage());
        }
        catch(ClassNotFoundException cnfe) {
            System.out.println("cnfe: "+ cnfe.getMessage());
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if(rs != null) rs.close();
            } 
            catch (SQLException sqle) {
                System.out.println("sqle: " + sqle.getMessage());
            }
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}