import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.text.*;


public class DeleteBooking extends HttpServlet {

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter pw = response.getWriter();
    Utility utility = new Utility(request, pw);
    HttpSession session = request.getSession(true);
    String userName = request.getParameter("username");
    int bookingNum = Integer.parseInt(request.getParameter("ordernum"));
    String checkindate = request.getParameter("checkindate");
    String currentDate = utility.currentDate();
    long diffdays=0;

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try{
      Date date1 = format.parse(checkindate);
      Date date2 = format.parse(currentDate);
      System.out.println("date 1: "+date1);
      System.out.println("date 2: "+date2);

      long difference = date1.getTime() - date2.getTime();
      diffdays = difference/(1000*60*60*24);
      System.out.println("date diff: "+diffdays);
    }

    catch(Exception e){
      e.printStackTrace();
    }
    if(diffdays > 2)
		utility.deleteBooking(userName, bookingNum);
    else
		session.setAttribute("error_msg", "Sorry! you need to Cancel 2 days prior to the Checkin date ! You can't cancel your booking now.");
    response.sendRedirect("Account");

  }


}
