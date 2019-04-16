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

/**
 * Servlet implementation class AddUserServlet
 */
@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userid = "";
		String email = "";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
        	userid = (String) session.getAttribute("userid");
        	email = (String) session.getAttribute("email");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Audioface?user=root&password=<PASSWORD>");
            ps = conn.prepareStatement("INSERT INTO users(userid,email) VALUES(?,?)");
            ps.setString(1, userid); // set first variable in prepared statement
            ps.setString(2, email);
            ps.executeUpdate();
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
                System.out.print("hello");
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
