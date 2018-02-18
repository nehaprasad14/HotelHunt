import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ManagerAccount extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(true);
        Utility utility = new Utility(request, out);
        LoginUtil loginUtil = new LoginUtil(request);

		if (!loginUtil.isLoggedin()) {
			response.sendRedirect("Login");
			return;
		}

		utility.printHtml("header.jsp");
		out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
		out.println("<div id='container1'>");

		out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>My Manager Account</h1>");
    out.println("<br/><br/>");
			out.print("<hr style='width: 100%'><h4 style='font-size: 18px;'> OPERATIONS </h4><hr style='width: 100%'>");
		out.print("<ul style='list-style: none;'>");
		out.print("<li><a href='AddHotel' style='font-size: 20px;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;<b>1. Add/Update/Delete Hotels</b></a></li>");
		out.print("<li><a href='AddRoom' style='font-size: 20px;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;<b>2. Add/Update/Delete Rooms</b></a></li>");
		out.print("</ul>");
out.print("<hr style='width: 100%'><h4 style='font-size: 18px;'> DATA ANALYTICS </h4><hr style='width: 100%'>");
		out.print("<ul style='list-style: none;'>");
		out.print("<li><a href='InventoryReport' style='font-size: 20px;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;<b>1. Inventory Report </b></a></li>");
		out.print("<li><a href='RevenueReport' style='font-size: 20px;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;<b>2. Revenue Report </b></a></li>");
		out.print("</ul>");

    out.print("</div>");
		utility.printHtml("leftNavBar.jsp");
		utility.printHtml("footer.jsp");
  }
}
