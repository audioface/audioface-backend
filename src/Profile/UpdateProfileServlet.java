import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateProfileServlet
 */
@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Audioface?user=root&password=<PASSWORD>");
            String sql = "SELECT username, password, des, dob FROM Users WHERE id = userID";
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            String userid = (String) session.getAttribute("userid");
            String updatedEmail = (String) session.getAttribute("email");
            String updatedDes = (String) session.getAttribute("des");
            String updatedDob = (String) session.getAttribute("dob");
            String oldEmail = "";
            String oldDes = "";
            String oldDob = "";
            
            while (rs.next()) {
            	oldEmail = rs.getString("email");
            	oldDes = rs.getString("des");
            	oldDob = rs.getString("dob");
            	
            	if (!oldEmail.equalsIgnoreCase(updatedEmail)) {
            		sql = "UPDATE User SET email = ? WHERE id = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, updatedEmail);
                    ps.setString(2, userid);
                    ps.executeUpdate();
            	}
            	
            	
            	if (!oldDes.equalsIgnoreCase(updatedDes)) {
            		sql = "UPDATE User SET des = ? WHERE id = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, updatedDes);
                    ps.setString(2, userid);
                    ps.executeUpdate();
            	}
            	
            	if (!oldDob.equalsIgnoreCase(updatedDob)) {
            		sql = "UPDATE User SET dob = ? WHERE id = ?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, updatedDob);
                    ps.setString(2, userid);
                    ps.executeUpdate();
            	}
            }
            session.setAttribute("userid", userid);
			session.setAttribute("email", updatedEmail);
			session.setAttribute("des", updatedDes);
			session.setAttribute("dob", updatedDob);
	    	RequestDispatcher rd = request.getRequestDispatcher("/DisplayProfile");
	    	rd.forward(request, response);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
