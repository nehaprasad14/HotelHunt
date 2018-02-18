import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GetDirection extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
			        HttpSession session=request.getSession(true);
			        Utility utility = new Utility(request, out);
			        LoginUtil loginUtil = new LoginUtil(request);

			    String address = (String)request.getParameter("address");
			    String city = (String)request.getParameter("city");
			    String name = (String)request.getParameter("name");
					utility.printHtml("header.jsp");
					out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
					out.println("<div id='container1'>");

					out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Get Direction</h1>");
					out.println("<br>");
					out.print("<div class='post' style='float: none; width: 100%'>");

			    out.println("<form action='http://maps.google.com/maps' method='get' target='_blank'><label for='saddr'>Enter your location</label><input type='text' name='saddr' /><input type='hidden' name='daddr' value='"+name+","+address+","+city+"' /><input type='submit' value='Get directions'/></form>");

			    out.print("</div></div>");
			    utility.printHtml("leftNavBar.jsp");
			    utility.printHtml("footer.jsp");
  }
}
