import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class DeleteReview extends HttpServlet {

      protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          Utility utility = new Utility(request,out);

  		    String hotelId =(String) request.getParameter("hotelid");
          String hotelname =(String) request.getParameter("hotelname");
          String username =(String) request.getParameter("username");
          String reviewcomment =(String) request.getParameter("reviewcomment");

        //  System.out.println("reviewId ::" +reviewId);

          MongoDBDataStoreUtilities mgUtil = new MongoDBDataStoreUtilities();
          mgUtil.deleteReview(hotelId, username, reviewcomment);

          response.sendRedirect("ViewReview?hname="+hotelname+"&hid="+hotelId);


        }
      }
