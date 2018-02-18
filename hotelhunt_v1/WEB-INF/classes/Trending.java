import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class Trending extends HttpServlet {

      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          Utility utility = new Utility(request,out);

          ArrayList<HotelReviewBean> cityList = new ArrayList<HotelReviewBean>();
          ArrayList<HotelReviewBean> htlist = null;

          MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
          cityList = sqlUtil.selectHotelsByCity();

          MongoDBDataStoreUtilities mgUtil = new MongoDBDataStoreUtilities();
          htlist = mgUtil.getHotelsByRating();

          utility.printHtml("header.jsp");
          out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
          out.println("<div id='container1'>");
          out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 100%;margin-right:8px;padding:10px;'>Trending Hotels</h1>");


          if(htlist == null){
               out.print("<hr style='width: 100%'><h4 style='font-size: 20px;'> No trending hotels yet...</h4><hr style='width: 100%'>");
           }else{
               out.print("<hr style='width: 100%'><h4 style='font-size: 20px;'> Top 5 most liked hotels based on ratings</h4><hr style='width: 100%'>");
               out.print("<table style='width:50%'>");
               out.print("<tr><td>&nbsp;</td><td>&nbsp;</td><td><b># </b></td><td><b>Hotel Name </b></td><td><b>City </b></td><td><b>Rating </b></td></tr>");
               int i=1;
               for (HotelReviewBean rv : htlist) {
                     out.print("<tr><td>&nbsp;</td><td>&nbsp;</td><td>"+ i +". </td>"
                         + "<td>"+rv.getHotelName()+"</td>"
                         + "<td>"+rv.getHotelCity()+"</td>"
                         + "<td>"+rv.getServiceRating()+"</td></tr>");
                   i++;
               }
               out.print("</table>");
           }

          if(cityList == null){
              out.println("<hr style='width: 100%'><h4 style='font-size: 20px;'> No trending hotels yet...</h4><hr style='width: 100%'>");
          }else{
              out.println("<hr style='width: 100%'><h4 style='font-size: 20px;'> Top 5 most booked hotels by city </h4><hr style='width: 100%'>");
              out.println("<table style='width:50%'>");
              out.println("<tr><td>&nbsp;</td><td>&nbsp;</td><td><b># </b></td><td><b>City </b></td><td><b>Total Bookings </b></td></tr>");
              int i = 1;
              for (HotelReviewBean hr : cityList){
                out.println("<tr><td>&nbsp;</td><td>&nbsp;</td><td>"+ i +". </td>"
                        + "<td>"+hr.getHotelCity()+"</td><td>"+hr.getHotelId()+"</td></tr>");
                i++;
              }
              out.println("</table>");
          }

          ArrayList<HotelReviewBean> zipList = new ArrayList<HotelReviewBean>();
          zipList = mgUtil.getHotelsByZipcode();
          if(zipList == null){
               out.print("<hr style='width: 100%'><h4 style='font-size: 20px;'> No trending hotels yet...</h4><hr style='width: 100%'>");
          }else{
               out.print("<hr style='width: 100%'><h4 style='font-size: 20px;'> Top 5 zip-codes (where maximum hotels booked)</h4><hr style='width: 100%'>");
               out.print("<table style='width:50%'>");
               out.print("<tr><td>&nbsp;</td><td>&nbsp;</td><td><b># </b></td><td><b>Zip-codes </b></td><td><b>Count </b></td></tr>");
               int i = 1;
               for (HotelReviewBean rv : zipList) {
                     out.print("<tr><td>&nbsp;</td><td>&nbsp;</td><td>"+ i +". </td>"
                             + "<td>"+ rv.getHotelZip() +"</td>"
                         + "<td>"+rv.getHotelId()+"</td></tr>");
                     i++;
               }
               out.print("</table>");
           }
        out.println("</div>");
        utility.printHtml("leftNavBar.jsp");
        utility.printHtml("footer.jsp");
      }

}
