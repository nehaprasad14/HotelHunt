import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Home extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Utility utility = new Utility(request,out);
		utility.printHtml("header.jsp");
		utility.printHtml("content.jsp");
		utility.printHtml("footer.jsp");

	}
}
