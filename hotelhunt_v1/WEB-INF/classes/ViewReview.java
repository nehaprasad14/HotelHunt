import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ViewReview extends HttpServlet {

      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          Utility utility = new Utility(request,out);
          HttpSession session=request.getSession(true);

  		    String hotelId =(String) request.getParameter("hid");
          String hotelName =(String) request.getParameter("hname");

          System.out.println("hid ::" +hotelId);
          HashMap<String,ArrayList<HotelReviewBean>> htHashMap = new HashMap<String,ArrayList<HotelReviewBean>>();
          ArrayList<HotelReviewBean> htList = null;
          MongoDBDataStoreUtilities mgUtil = new MongoDBDataStoreUtilities();
          htHashMap = mgUtil.selectReviews();

          if(!htHashMap.containsKey(hotelId)){
              htList = null;
          }else{
              htList = htHashMap.get(hotelId);
          }

          utility.printHtml("header.jsp");
          out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
          out.println("<div id='container1'>");
          out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Hotel Reviews </h1>");

          //out.print("<div style='text-align: center;width: 96.5%;margin-left:12px;'>");
          if(htList == null){
              out.print("<hr style='width: 100%'><h3 style='font-size: 35px;'> No reviews for hotel '"+hotelName+"' yet...</h3><hr style='width: 100%'>");
          }else{

              out.print("<table style='width:90%'>");
              out.print("<tr><td>&nbsp;</td><td><b>Hotel Name </b></td><td><b>Hotel City </b></td><td><b>User Name </b></td><td><b>Review Rating </b></td><td><b>Review Date </b></td><td><b>Review Comment </b></td></tr>");
              for (HotelReviewBean ht : htList) {
                    out.print("<tr><td>&nbsp;</td><td>"+ht.getHotelName()+"</td>"
                      //  + "<td>"+ht.getReviewId()+"</td>"
                        + "<td>"+ht.getHotelCity()+"</td>"
                        + "<td>"+ht.getUsername()+"</td>"
                        + "<td>"+ht.getRoomRating()+"</td>"
                        + "<td>"+ht.getReviewDate()+"</td>"
                        + "<td>"+ht.getReviewComment()+"</td>");

                        //delete reviews
                        if(session.getAttribute("user")!= null){
                             User user = (User)session.getAttribute("user");
                             String userRole = user.getRole();
                             userRole.trim();

                             if (userRole.equals("staff") || userRole.equals("manager")) {
                                out.print("<td><form method='post' action='DeleteReview'>");
                                out.print("<input type='hidden' name='hotelid' value='"+ht.getHotelId()+"'>");
                                out.print("<input type='hidden' name='hotelname' value='"+ht.getHotelName()+"'>");
                                out.print("<input type='hidden' name='username' value='"+ht.getUsername()+"'>");
                                out.print("<input type='hidden' name='reviewcomment' value='"+ht.getReviewComment()+"'>");
                                out.print("<input type='submit' class='btn1' value='Delete Review'></form></td>");
                              }
                            }
                      out.print("</tr>");
                }
              out.print("</table>");
        }
        out.print("</div>");
        utility.printHtml("leftNavBar.jsp");
        utility.printHtml("footer.jsp");
      }

}
