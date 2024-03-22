package registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String upwd = request.getParameter("pass"); 
        String Reupwd = request.getParameter("pass"); 
        String umobile = request.getParameter("contact");
        RequestDispatcher dispatcher = null;
        Connection con = null;

        if(uname==null || uname.equals("")) {
			request.setAttribute("status", "invalidName");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
        
        if(uemail==null || uemail.equals("")) {
			request.setAttribute("status", "invalidEmail");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
        
        if(upwd==null || upwd.equals("")) {
			request.setAttribute("status", "invalidupwd");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}else if(Reupwd==null || Reupwd.equals("")) {
			request.setAttribute("status", "invalidReupwd");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
        
        if(umobile==null || umobile.equals("") ) {
			request.setAttribute("status", "invalidumobile");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}else if(umobile.length()>10) {
			request.setAttribute("status", "EnterValidMobileNo");
			dispatcher = request.getRequestDispatcher("registration.jsp");
			dispatcher.forward(request, response);
		}
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup", "root", "swapnil2212");
            String query = "insert into users(uname, upwd, uemail, umobile) values(?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, uname);
            pst.setString(2, upwd); 
            pst.setString(3, uemail);
            pst.setString(4, umobile);

            int rowCount = pst.executeUpdate();
            dispatcher = request.getRequestDispatcher("registration.jsp");
            if (rowCount > 0) {
                request.setAttribute("status", "success");
            } else {
                request.setAttribute("status", "failed");
            }
            dispatcher.forward(request, response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Handle driver class not found exception
            
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database connection errors
            
        } catch (Exception e) {
            e.printStackTrace(); // Catch other unexpected exceptions
            
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
