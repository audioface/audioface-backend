package Friendship;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/RemoveFriendServlet")
public class RemoveFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public RemoveFriendServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String friend1 = "";
		String friend2 = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
        	System.out.println("Trying to remove friends...");
        	friend1 = (String) request.getParameter("friend1Userid");
        	friend2 = (String) request.getParameter("friend2Userid");
        	System.out.println(friend1 + " " + friend2);
        	
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(AddFriendServlet.DB_URL + "?user=" + AddFriendServlet.USER + "&password=" + AddFriendServlet.PASS + "&serverTimezone=UTC");
            ps = conn.prepareStatement("DELETE FROM friendship WHERE friend1=? AND friend2=?");
            ps.setString(1, friend1); // set first variable in prepared statement
            ps.setString(2, friend2);
            ps.executeUpdate();
            System.out.println("Successfully removed friends!");
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
            } 
            catch (SQLException sqle) {
                System.out.println("sqle: "+sqle.getMessage());
            }
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}