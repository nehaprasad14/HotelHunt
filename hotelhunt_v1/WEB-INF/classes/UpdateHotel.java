import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateHotel extends HttpServlet {

  protected void doPost(HttpServletRequest request,
  HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    HttpSession session=request.getSession(true);
    Utility utility = new Utility(request, out);

    String username = (String)session.getAttribute("username");
    int hotelId = Integer.parseInt(request.getParameter("hid"));
    System.out.println("####### update hotel id : "+ hotelId);
    String name = request.getParameter("hname");
    String address = request.getParameter("haddress");
    String city = request.getParameter("hcity");
    int zipcode = Integer.parseInt(request.getParameter("hzip"));
    String disability = request.getParameter("hdisability");
    int discount = Integer.parseInt(request.getParameter("hdiscount"));

    utility.updateHotel(hotelId,name,address,city,zipcode,disability,discount);

    response.sendRedirect("AddHotel");

  }

}
