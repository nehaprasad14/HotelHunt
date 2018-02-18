import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteHotel extends HttpServlet {

  protected void doPost(HttpServletRequest request,
  HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    HttpSession session=request.getSession(true);
    Utility utility = new Utility(request, out);

    String username = (String)session.getAttribute("username");
    int hotelId = Integer.parseInt(request.getParameter("hid"));
    utility.deleteHotel(hotelId);
    response.sendRedirect("AddHotel");

  }

}
