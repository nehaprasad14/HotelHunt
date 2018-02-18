import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.sql.*;

public class Login extends HttpServlet {

  MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
    LoginUtil loginUtil = new LoginUtil(request);

    String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");

        boolean isValid = loginUtil.validation(username, password);
        User user = null;

        if(isValid){
            try{
                user = sqlUtil.getUser(username);
            }catch(NullPointerException e){
				        e.printStackTrace();
            }catch(ClassNotFoundException e){
				        e.printStackTrace();
            }catch (SQLException e){
				        e.printStackTrace();
            }

            if(user!=null){
                String userPassword = user.getPassword();
                String userRole = user.getRole();

                if (password.equals(userPassword) && role.equals(userRole)) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("logged", true);
                    session.setAttribute("user",user);
                    session.setAttribute("username",username);
                    session.setAttribute("userRole",userRole);
                    response.sendRedirect("Home");
                    return;
                }else{
                   showLoginForm(request, response, out, true);
                }
            }else{
                  showLoginForm(request, response, out, true);
            }
        }else{
            response.sendRedirect("Login");
        }
	}

	protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
        LoginUtil loginUtil = new LoginUtil(request);

        if(!loginUtil.isLoggedin())
		      showLoginForm(request, response, out, false);
        else
            response.sendRedirect("Home");
	}

	protected void showLoginForm(HttpServletRequest request,
			HttpServletResponse response, PrintWriter out, boolean error)
			throws ServletException, IOException {

    Utility utility = new Utility(request,out);
    utility.printHtml("header.jsp");
    out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
    out.println("<div id='container1'>");
    out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Sign in</h1>");

    out.println("<br/><br/>");

		if (error)
			out.print("<h4 style='color:red'>Please check your username, password and role </h4>");

		HttpSession session = request.getSession(true);

		if(session.getAttribute("missing")!= null){
			out.print("<h4 style='color:red'>"+session.getAttribute("missing")+"</h4>");
			session.removeAttribute("missing");
		}
        if(session.getAttribute("login_msg")!= null){
			out.print("<h4 style='color:red'>"+session.getAttribute("login_msg")+"</h4>");
			session.removeAttribute("login_msg");
		}
		out.print("<form method='post' action='Login' style='margin: 0 auto;'>"
				+ "<table style='width:50%;align:center;margin: 0 auto;'>"
				+ "<tr><td><h3>Username</h3></td><td><input type='text' name='username' required></td></tr>"
        + "<tr><td><br></td></tr>"
				+ "<tr><td><h3>Password</h3></td><td><input type='password' name='password' required></td></tr>"
        + "<tr><td><br></td></tr>"
				+ "<tr><td><h3>Role</h3></td><td>"
                  +"<select name='role'>"
                  +"<option value='customer' selected>Customer</option>"
                  +"<option value='manager'>Hotel Manager</option>"
                  +"<option value='staff'>Staff</option>"
                  +"</select>"
				+ "</td></tr>"
        + "<tr><td><br></td></tr>"
				+ "<tr><td colspan='2' align='center'><input type='submit' value='Login' style='align:center;width: 150px;height: 40px; margin: 20px; margin-right: 10px;padding: 5px'></td></tr>"

				+ "<tr><td></td><td><strong><a href='SignUp' style='float: right;height: 20px margin: 20px;'>New to HotelHunt? Sign up here!</a></strong></td></tr>"

				+ "</table>"
				+ "</form>");

        out.println("</div>");
        utility.printHtml("leftNavBar.jsp");
        utility.printHtml("footer.jsp");
      }

}
