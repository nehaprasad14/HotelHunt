import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class RemoveItem extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utility utility = new Utility(request, pw);

		String hotelName = request.getParameter("hotelname");
		String roomType = request.getParameter("roomtype");

		utility.removeBooking(hotelName,roomType);
		response.sendRedirect("Cart");

	}


}
