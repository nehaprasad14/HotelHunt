import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DeleteRoom extends HttpServlet {

  protected void doPost(HttpServletRequest request,
  HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    HttpSession session=request.getSession(true);
    Utility utility = new Utility(request, out);

    String username = (String)session.getAttribute("username");
    int roomId = Integer.parseInt(request.getParameter("rid"));
    utility.deleteRoom(roomId);
    response.sendRedirect("AddRoom");

  }

}
