import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class WriteReview extends HttpServlet {

      protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            try {
                  String hotelId = request.getParameter("hotelId");
                  String hotelName = request.getParameter("hotelName");
                  String hotelCity = request.getParameter("hotelCity");
                  String hotelZip = request.getParameter("hotelZip");
                  String username = request.getParameter("username");
                  String roomRating = request.getParameter("roomRating");
                  String reviewDate = request.getParameter("reviewDate");
                  String reviewComment = request.getParameter("reviewComment");


                  HotelReviewBean ht = new HotelReviewBean();
                  ht.setHotelId(hotelId);
                  ht.setHotelName(hotelName);
                  ht.setHotelCity(hotelCity);
                  ht.setHotelZip(hotelZip);
                  ht.setUsername(username);
                  ht.setRoomRating(Integer.parseInt(roomRating));
                  ht.setReviewDate(reviewDate);
                  ht.setReviewComment(reviewComment);


                  MongoDBDataStoreUtilities mgUtil = new MongoDBDataStoreUtilities();
                  mgUtil.insertReviews(ht);

                  HttpSession session = request.getSession(true);
                  session.setAttribute("reviewmsg", "Review for "+hotelName+" submitted successfully!");
                  response.sendRedirect("WriteReview");

            }catch(Exception e){
                  e.printStackTrace();
            }
      }

      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                showReviewForm(request, response, out);
      }

      protected void showReviewForm(HttpServletRequest request, HttpServletResponse response, PrintWriter out)throws ServletException, IOException {

                Utility utility = new Utility(request,out);
                LoginUtil loginUtil = new LoginUtil(request);
                HttpSession session = request.getSession(true);

                if(!loginUtil.isLoggedin()){
                    session.setAttribute("missing", "Please Login before writing reviews.");
                    response.sendRedirect("Login");
                    return;
                }

                utility.printHtml("header.jsp");
                out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");

                out.println("<div id='container1'>");
                out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Hotel Review Form </h1>");
                //out.print("<div id='content'><div class='post'>");
                //out.print("<div style='text-align: center;width: 96.5%;margin-left:12px;'>");

                if(session.getAttribute("reviewmsg")!= null){
                    out.print("<hr style='width: 100%'><h2 style='font-size: 35px;'>"+session.getAttribute("reviewmsg")+"</h2><hr style='width: 100%'></article>");
                    session.removeAttribute("reviewmsg");
                }else{
                    String hname = request.getParameter("hname");
                    String hid = request.getParameter("hid");
                    String hcity = request.getParameter("hcity");
                    String hzip = request.getParameter("hzip");

                    String username = "";
                    if(session.getAttribute("user")!= null){
                        User user = (User)session.getAttribute("user");
                        username = user.getUsername();
                    }
                    out.print("<br><br>");
                    out.print("<form method='post' action='WriteReview'>"
                        + "<table style='width:100%'>"
                        + "<tr><td>&nbsp;&nbsp;&nbsp;</td><td style='font-size: 15pt;'>Hotel Name:<br/> &nbsp; </td><td><input type='hidden' name='hotelId' value='"+hid+"'><input type='text' name='hotelName' value='"+hname+"' readonly> <br/> &nbsp; </td></tr>"
                        + "<tr><td>&nbsp;&nbsp;&nbsp;</td><td style='font-size: 15pt;'>Hotel City:<br/> &nbsp; </td><td><input type='text' name='hotelCity' value='"+hcity+"' readonly> <br/> &nbsp; </td></tr>"
                        + "<tr><td>&nbsp;&nbsp;&nbsp;</td><td style='font-size: 15pt;'>Hotel Zip:<br/> &nbsp; </td><td><input type='text' name='hotelZip' value='"+hzip+"' readonly> <br/> &nbsp; </td></tr>"
                        + "<tr><td>&nbsp;&nbsp;&nbsp;</td><td style='font-size: 15pt;'>User Name:<br/> &nbsp; </td><td><input type='text' name='username' value='"+username+"' readonly> <br/> &nbsp; </td></tr>"
                        + "<tr><td>&nbsp;&nbsp;&nbsp;</td><td style='font-size: 15pt;'>Review Rating:<br/> &nbsp; </td><td>"
                        +"<select name='roomRating'><option value='1' selected>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option></select>" +"<img src='img/home/rating.png' height='11%' width='15%'><br/> &nbsp; </td></tr>"
                        + "<tr><td>&nbsp;&nbsp;&nbsp;</td><td style='font-size: 15pt;'>Review Date:<br/> &nbsp; </td><td><input type='date' name='reviewDate' required><br/> &nbsp; </td></tr>"
                        + "<tr><td>&nbsp;&nbsp;&nbsp;</td><td style='font-size: 15pt;'>Review Comment:<br/> &nbsp; </td><td><textarea rows='6' cols='40' name='reviewComment' required></textarea></td></tr>"
                        + "<tr><td>&nbsp;</td><td align='center'><input type='submit' name='subreview' class='tm-yellow-btn' style='height:35px;width:150px;' value='Submit Review'></td><td>&nbsp;</td></tr>"
                        + "</table>"
                        + "</form>");
                }
                out.print("</div>");
                utility.printHtml("leftNavBar.jsp");
                utility.printHtml("footer.jsp");
        }

}
