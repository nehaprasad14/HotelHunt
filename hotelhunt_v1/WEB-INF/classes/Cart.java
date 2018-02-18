import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Cart extends HttpServlet {


  public static HashMap<String, ArrayList<OrderItemBean>> orders = new HashMap<String, ArrayList<OrderItemBean>>();
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter pw = response.getWriter();
    Utility utility = new Utility(request,pw);
    int hotelId = Integer.parseInt(request.getParameter("hid"));
    String hotelName = request.getParameter("hname");
    String hotelImage = request.getParameter("himage");
    int roomId = Integer.parseInt(request.getParameter("rid"));
    int roomQuantity = Integer.parseInt(request.getParameter("rquantity"));
    String roomType = request.getParameter("roomtype");
    String roomImage = request.getParameter("rimage");
    float roomPrice = Float.parseFloat(request.getParameter("rprice"));
    double totalPrice = roomPrice * roomQuantity;
    String checkInDate = request.getParameter("checkindate");
    String checkOutDate = request.getParameter("checkoutdate");

    utility.storeBooking(hotelId, hotelName, hotelImage, roomId, roomType, roomImage, roomQuantity, totalPrice, checkInDate, checkOutDate);
    displayCart(request, response);
  }
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    displayCart(request, response);
  }

  protected void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter pw = response.getWriter();
    Utility utility = new Utility(request,pw);
    HotelBean hotel;
    RoomBean room;
    HashMap<Integer, HotelBean> hotelCatalog ;
    HashMap<Integer, RoomBean> roomCatalog ;
    SaxParserXMLUtility sp = new SaxParserXMLUtility();

    if(!utility.checkLoggedIn()){
      HttpSession session = request.getSession(true);
      session.setAttribute("login_msg", "Please Login to add items to cart");
      response.sendRedirect("Login");
      return;
    }

    utility.printHtml("header.jsp");
    pw.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
    pw.println("<div id='container1'>");
    pw.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>MY CART </h1>");
    pw.print("<br>");
    pw.print("<div style='text-align: center;width: 96.5%;margin-left:12px;'>");
    if(utility.CartCount()>0){
      pw.print("<table style='text-align: center;width: 96.5%;margin-left:12px;'>");
      int i = 1;
      double total = 0;
      pw.print("<th>No.</th><th>&nbsp;&nbsp; Hotel Name</th><th>&nbsp;&nbsp;Hotel Image</th><th>&nbsp;&nbsp;Room Type</th><th>&nbsp;&nbsp;Room Image</th><th>&nbsp;&nbsp; Room Quantity</th><th>&nbsp;&nbsp; Check-In Date</th><th>&nbsp;&nbsp; Check-Out Date</th><th>&nbsp;&nbsp; Price</th><th>&nbsp;&nbsp;Action <br></th>");
      for (OrderItemBean oi : utility.getCustomerOrders()) {
        pw.print("<tr>");
        pw.print("<td>"+i+".</td><td>"+oi.getHotelName()+"</td><td>"+oi.getHotelImage()+"</td><td>"+oi.getRoomType()+"</td><td>"+oi.getRoomImage()+"</td><td>"+oi.getRoomQuantity()+"</td><td>"+oi.getCheckInDate()+"</td><td>"+oi.getCheckOutDate()+"</td><td>$  "+oi.getTotalPrice()+"</td><td><a href='RemoveItem?hotelname="+oi.getHotelName()+"&roomtype=" +oi.getRoomType()+"' class='tm-yellow-btn' style='height:35px;width:150px;padding:5px'>Remove Booking</a><br>&nbsp;</td>");
        pw.print("</tr>");
        total = total +oi.getTotalPrice();
        i++;
      }
      pw.print("<tr><th><br> &nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th>Total</th><th>"+total+"</th>");
      pw.print("<td><a href='CheckOut' class='tm-yellow-btn' style='height:35px;width:150px;padding:7px'>Check Out</a></td><th>&nbsp;</th><th>&nbsp;</th></tr>");
      pw.print("</table>");
    }else{
      pw.print("<h4 style='color:red'>Your Cart is empty</h4>");
    }
    pw.print("</div></div></div></div>");
    utility.printHtml("leftNavBar.jsp");
    utility.printHtml("footer.jsp");

  }
}
