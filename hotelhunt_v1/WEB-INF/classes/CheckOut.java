import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class CheckOut extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utility utility = new Utility(request,pw);
		int i = 1;
		utility.printHtml("header.jsp");
		pw.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
		pw.println("<div id='container1'>");
		pw.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>CHECK OUT</h1>");

		//pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<hr style='width: 100%'><h4 style='font-size: 18px;'> BOOKING DETAILS </h4><hr style='width: 100%'>");
		//pw.print("<a style='font-size: 24px;'>Booking Details</a>");
		//pw.print("</h2><div class='entry'>");
		pw.print("<table  style='width: 96.5%;margin-left:12px;'>");
		pw.print("<th>No.</th><th>Hotel Name</th><th>Room Type</th><th>Total Cost</th><th>Room Quantity</th><th>Check-In Date</th><th>Check-Out Date</th>");
		for (OrderItemBean oi : utility.getCustomerOrders()) {
			pw.print("<tr>");
			pw.print("<td>"+i+".</td><td>"+oi.getHotelName()+"</td><td>"+oi.getRoomType()+"</td><td>$  "+oi.getTotalPrice()+"</td><td>"+oi.getRoomQuantity()+"</td><td>"+oi.getCheckInDate()+"</td><td>"+oi.getCheckOutDate()+"</td>");
			pw.print("</tr>");
			i++;
		}
		pw.print("</table>");

	//	pw.print("<div id='content'>");
		pw.print("<hr style='width: 100%'><h4 style='font-size: 18px;'> BILLING DETAILS </h4><hr style='width: 100%'>");
		//pw.print("<a style='font-size: 24px;'>Billing Details</a></h2>");
		pw.print("<form action='CheckOut' method='post'><table style='width: 40%;margin-left:12px;'>"
		+ "<tr><td>Total Amount</td><td><input type='number' name='total' readonly='readonly' value='"+utility.getCartTotal()+"'/></td></tr>"
		+ "<tr><td>Full Name</td><td><input type='text' name='fullname' required='required'/></td></tr>"
		+ "<tr><td>Email Id</td><td><input type='email' name='email' required='required'/></td></tr>"
		+ "<tr><td>Credit Card Number</td><td><input type='text' name='creditcard' required=required'/></td></tr>"
		+ "<tr><td>CVV</td><td><input type='text' name='cvv' required='required'/></td></tr>"
		+ "<tr><td>Address Line 1</td><td><input type='text' name='address1' required='required'/></td></tr>"
		+ "<tr><td>Address Line 2</td><td><input type='text' name='address2' required='required'/></td></tr>"
		+ "<tr><td>Zipcode</td><td><input type='text' name='zipcode' required='required'/></td></tr>"
		+ "<tr><td></td><td><input type='submit' name='login' class='buybtn' value='Place Order'></td></tr>"
		+ "</table></form>");

		pw.print("</div>");
		utility.printHtml("leftNavBar.jsp");
		utility.printHtml("footer.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(true);
		Utility utility = new Utility(request, pw);
		MySqlDataStoreUtilities msqldb = new MySqlDataStoreUtilities();

		String username = "";
		String username1 = "";
		String fullname = "";
		double total = 0;
		String email = "";
		String creditcardNo = "";
		int cvv = 0;
		String address1 = "";
		String address2 = "";
		String zipcode = "";
		int hotelid = 0;
		String roomtype = "";
		int roomquantity = 0;
		String checkindate = "";
		String checkoutdate = "";


		String button = request.getParameter("add");
		//System.out.println("###########Checkout button is "+ button);
		if(button==null){
		button = "checkout";
		System.out.println("im here "+ button);
		 fullname = request.getParameter("fullname");
		 total = Double.parseDouble(request.getParameter("total"));
		 email = request.getParameter("email");
		 creditcardNo = request.getParameter("creditcard");
		 cvv = Integer.parseInt(request.getParameter("cvv"));
		 address1 = request.getParameter("address1");
		 address2 = request.getParameter("address2");
		 zipcode = request.getParameter("zipcode");
		 username = (String)session.getAttribute("username");
	}else{
		 username1 = request.getParameter("username");
		 fullname = request.getParameter("username");
		 total = 0;
		 email = "Not provided";
		 creditcardNo = "COD";
		 cvv = 0;
		 address1 = "Not provided";
		 address2 = "Not provided";
		 zipcode = "Not provided";
		 hotelid = Integer.parseInt(request.getParameter("hid"));
		 roomtype = request.getParameter("rtype");
		 roomquantity = Integer.parseInt(request.getParameter("roomquantity"));
		 checkindate = request.getParameter("checkindate");
		 checkoutdate = request.getParameter("checkoutdate");

	}

		String bookingDate = utility.currentDate();
		int orderNum = 0;
		ArrayList<OrderItemBean> orderItems = utility.getCustomerOrders();

	//	String username1 = request.getParameter("username");


		if(button.equalsIgnoreCase("Add Booking"))
			 orderNum = msqldb.addBilledBooking(username1, fullname, email, total, creditcardNo, cvv, address1, address2, zipcode, bookingDate, hotelid,roomtype, roomquantity, checkindate, checkoutdate);
		else
			 orderNum = utility.storeBilledBooking(username, fullname, email, total, creditcardNo, cvv, address1, address2, zipcode, bookingDate, orderItems);

		if(button.equalsIgnoreCase("Add Booking")){
			session.setAttribute("add_msg", "Booking "+orderNum+" has been Created Successfully !");
			if(utility.checkLoggedIn() == true){
				response.sendRedirect("Account"); return;
			}
		}

		utility.clearCart(username);
		utility.printHtml("header.jsp");
		pw.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
		pw.println("<div id='container1'>");
		pw.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Booking Confiramtion</h1>");
		//utility.printHtml("LeftNavigationBar.html");

		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Booking Placed Successfully</a></h2>");
		pw.print("<div class='entry'>");
		pw.print("<p style='font-size:20px;color:green;'>Confirmation number: <b>#"+orderNum+"</b><p>");
		pw.print("</div></div></div></div>");
		utility.printHtml("leftNavBar.jsp");
		utility.printHtml("footer.jsp");
	}

}
