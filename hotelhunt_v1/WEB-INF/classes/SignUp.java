import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.sql.*;

public class SignUp extends HttpServlet {

      private String error_msg;
      MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();

      protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        LoginUtil loginUtil = new LoginUtil(request);
        HttpSession session = request.getSession(true);

        String username = null;
        String email = null;
        String password = null;
        String role = null;
        String error_msg = null;

        User user=null;
        int result = 0;
        try{
                username = request.getParameter("username");
                email = request.getParameter("email");
                password = request.getParameter("password");
                role = request.getParameter("role");

                username.trim();
                email.trim();
                password.trim();
                role.trim();

                user = sqlUtil.getUser(username);

                if(user != null){
                    error_msg = " Username already exist.";
                    System.out.println(error_msg);
                    session.setAttribute("login_msg",error_msg);
                    response.sendRedirect("SignUp");
                }else{
                    user = new User(username,email,password,role);
                    result = sqlUtil.addUser(user);

                    if(result != 0){
                        session.setAttribute("login_msg", "Username '"+username+"' account is created. Please login");

                        if(!loginUtil.isLoggedin()){
                            response.sendRedirect("Login");
                            return;
                        }else {
                            response.sendRedirect("Home");
                            return;
                        }
                    }
                }
            }catch(NullPointerException e){
        				 e.printStackTrace();
            }catch(ClassNotFoundException e){
				          e.printStackTrace();
            }catch (SQLException e){
				          e.printStackTrace();
            }
      }

      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            showSignUpForm(request, response, out, false);
        }

      protected void showSignUpForm(HttpServletRequest request, HttpServletResponse response, PrintWriter out, boolean error)
                throws ServletException, IOException {

            Utility utility = new Utility(request,out);
            utility.printHtml("header.jsp");
            out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
            out.println("<div id='container1'>");
            out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Create Account</h1>");

            out.println("<br/><br/>");
            HttpSession session = request.getSession(true);

            if(session.getAttribute("login_msg")!= null){
                out.print("<h4 style='color:red'>"+session.getAttribute("login_msg")+"</h4>");
                session.removeAttribute("login_msg");
            }

            if (error)
                out.print("<h4 style='color:red'>"+error_msg+"</h4>");
                out.print("<form method='post' action='SignUp' style='margin: 0 auto;' >"
                    + "<table style='width:50%;align:center;margin: 0 auto;'>"
                    + "<tr><td><h3>Username</h3></td><td><input type='text' name='username' required></td></tr>"
                    + "<tr><td><br></td></tr>"
                    + "<tr><td><h3>Email address</h3></td><td><input type='text' name='email' required></td></tr>"
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
                    + "<tr><td colspan='2' align='center'><input type='submit' name='signUp' value='Create User Account' style='align:center;height: 20px margin: 20px; margin-right: 10px;padding: 5px'></td></tr>"

                    + "</table>"

                    + "</form>");
                out.println("</div>");
                utility.printHtml("leftNavBar.jsp");
            		utility.printHtml("footer.jsp");
        }

}
