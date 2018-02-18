import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateRoom extends HttpServlet {

  protected void doPost(HttpServletRequest request,
  HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    HttpSession session=request.getSession(true);
    Utility utility = new Utility(request, out);

    String username = (String)session.getAttribute("username");
    int hotelId = Integer.parseInt(request.getParameter("hid"));
    int roomId = Integer.parseInt(request.getParameter("rid"));
    System.out.println("####### update hotel id : "+ hotelId);
    String roomType = request.getParameter("rtype");
    float roomRate = Float.parseFloat(request.getParameter("rprice"));
    int roomQuantity = Integer.parseInt(request.getParameter("rquantity"));
    String ac = request.getParameter("rac");

    utility.updateRoom(hotelId,roomId,roomType,roomRate,roomQuantity,ac);

    response.sendRedirect("AddRoom");

  }

}
