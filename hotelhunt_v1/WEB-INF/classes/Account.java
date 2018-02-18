import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Account extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
			    HttpSession session=request.getSession(true);
			    LoginUtil loginUtil = new LoginUtil(request);

					if (!loginUtil.isLoggedin()) {
						response.sendRedirect("Login");
						return;
					}

					if(session.getAttribute("user")!= null){
               User user = (User)session.getAttribute("user");
               String userRole = user.getRole();
               userRole.trim();

               if (userRole.equals("customer")) {
			         displayCustomer(request, response);
		       }else if (userRole.equals("manager")) {
			         response.sendRedirect("ManagerAccount");
		       }else if (userRole.equals("staff")) {
                     displayStaff(request, response);
               }
         }
      }

      protected void displayCustomer(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Utility utility = new Utility(request, out);
		boolean empty = true;
		utility.printHtml("header.jsp");
		out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
		out.println("<div id='container1'>");
		out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>MY BOOKINGS</h1>");
		out.print("<div class='post' style='float: none; width: 100%'>");
		out.print("<br/>");
		HttpSession session = request.getSession(true);
		if(session.getAttribute("error_msg")!=null){
			out.print("<h4 style='color:red'>"+session.getAttribute("error_msg")+"</h4>");
			session.removeAttribute("error_msg");
		}
		out.print("<table style='width:100%' >");
		out.println("<tr><th>&nbsp;&nbsp;&nbsp;</th><th>User Name</th><th>Booking #</th><th>Booking Date</th><th>Hotel Name</th><th>Room Type</th><th>Price</th><th>No. of Rooms</th><th>Check-in Date</th><th>Check-out Date</th><th>Action</th></tr>");

		for (BillingOrderBean bo : utility.getBilledOrders()) {
			ArrayList<OrderItemBean> orderItems = bo.getOrderItems();
      for(OrderItemBean oi : orderItems){
			out.println("<form action='DeleteBooking' method='post'>");
			out.println("<input type='hidden' name='username' value='"+bo.getUsername()+"'>");
			out.println("<input type='hidden' name='ordernum' value='"+bo.getOrderNum()+"'>");
			out.println("<input type='hidden' name='checkindate' value='"+oi.getCheckInDate()+"'>");
			out.println("<tr><td>&nbsp;&nbsp;&nbsp;</td><td>"+bo.getUsername()+"</td>");
			out.println("<td>"+bo.getOrderNum()+"</td>");
			out.println("<td>"+bo.getBookingDate()+"</td>");
			out.println("<td>"+oi.getHotelName()+"</td>");
			out.println("<td>"+oi.getRoomType()+"</td>");
			out.println("<td>"+oi.getTotalPrice()+"</td>");
			out.println("<td>"+oi.getRoomQuantity()+"</td>");
			out.println("<td>"+oi.getCheckInDate()+"</td>");
			out.println("<td>"+oi.getCheckOutDate()+"</td>");
			out.println("<td><input type='submit' class='btnbuy' value='Delete Booking'></td></tr>");
			out.println("</form>");
			empty = false;
    }
		}

		out.println("</table>");
		if (empty) {
			out.print("<h4 style='color:red'>No Bookings found</h4>");
		}
		out.print("</div></div>");
		utility.printHtml("leftNavBar.jsp");
		utility.printHtml("footer.jsp");
	}

	protected void displayStaff(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
			PrintWriter out = response.getWriter();
			Utility utility = new Utility(request,out);
			boolean empty = true;
			HashMap<Integer, HotelBean> hotelList = new HashMap<Integer, HotelBean>();

	    MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
	    hotelList = sqlUtil.getHotels();

			utility.printHtml("header.jsp");
			out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
			out.println("<div id='container1'>");
			out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Create Account</h1>");

			out.println("<br/><br/>");
			HttpSession session = request.getSession(true);

			if(session.getAttribute("login_msg")!= null){
					out.print("<h4 style='color:red'>"+session.getAttribute("login_msg")+"</h4>");
					session.removeAttribute("login_msg");
			}

					out.print("<form method='post' action='SignUp' style='margin: 0 auto;' >"
							+ "<table style='width:50%;align:center;margin: 0 auto;'>"
							+ "<tr><td><h3>Username</h3></td><td><input type='text' name='username' required></td></tr>"
							+ "<tr><td><br></td></tr>"
							+ "<tr><td><h3>Email address</h3></td><td><input type='text' name='email' required></td></tr>"
							+ "<tr><td><br></td></tr>"
							+ "<tr><td><h3>Password</h3></td><td><input type='password' name='password' required></td></tr>"
							+ "<tr><td><br></td></tr>"
							+ "<tr><td><h3>Role</h3></td><td>"
								+"<select name='role'>"
								+"<option value='customer' selected>Customer</option>"
								+"<option value='manager'>Hotel Manager</option>"
								+"<option value='staff'>Staff</option>"
								+"</select>"
							+ "</td></tr>"
							+ "<tr><td><br></td></tr>"
							+ "<tr><td colspan='2' align='center'><input type='submit' name='signUp' value='Create User Account' style='align:center;height: 20px margin: 20px; margin-right: 10px;padding: 5px'></td></tr>"

							+ "</table>"

							+ "</form>");


			//Customer Bookings

			out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Customer Bookings</h1>");

			if(session.getAttribute("error_msg")!=null){
				out.print("<h4 style='color:red'>"+session.getAttribute("error_msg")+"</h4>");
				session.removeAttribute("error_msg");
			}
			//out.print("<table class='gridtable'>");
			if (session.getAttribute("update_msg") != null){
				out.print("<h4 style='color:red'>"+ session.getAttribute("update_msg") + "</h4>");
				session.removeAttribute("update_msg");
			}
					out.println("<br>");
			out.print("<table style='width: 50%;'>");
			out.println("<tr><th>User Name</th><th>Booking No.</th><th>Booking Date</th><th>Hotel Name</th><th>No. of Rooms</th><th>Check-in Date</th><th>Check-out Date</th><th>Action</th></tr>");

			for (BillingOrderBean bo : utility.getAllCustomersBookings()) {
				ArrayList<OrderItemBean> orderItems = bo.getOrderItems();
	      for(OrderItemBean oi : orderItems){
				out.println("<tr><form method='post'>");
				out.println("<input type='hidden' name='hotelid' value='"+oi.getHotelId()+"'>");
				out.println("<input type='hidden' name='roomid' value='"+oi.getRoomId()+"'>");
				out.println("<td><input type='text' name='username' value='"+bo.getUsername()+"'></td>");
				out.println("<td><input type='text' name='ordernum' value='"+bo.getOrderNum()+"' readonly></td>");
				out.println("<td><input type='text' name='bookingdate' value='"+bo.getBookingDate()+"' readonly></td>");
				out.println("<td><input type='text' name='hotelname' value='"+oi.getHotelName()+"' readonly></td>");
				out.println("<td><input type='text' name='roomquantity' value='"+oi.getRoomQuantity()+"'></td>");
				out.println("<td><input type='text' name='checkindate' value='"+oi.getCheckInDate()+"'></td>");
				out.println("<td><input type='text' name='checkoutdate' value='"+oi.getCheckOutDate()+"'></td>");
				out.println("<td><input type='submit' name='dbutton' value='Delete Booking' onclick='form.action=\"DeleteBooking\";'><input type='submit' name='ubutton' value='Update Booking' onclick='form.action=\"UpdateBooking\";'></td>");
				out.println("</form></tr>");
				empty = false;
	    }
			}

			out.println("</table>");
			if (empty) {
				out.print("<h4 style='color:red'>No Bookings found</h4>");
			}

			// Add Bookings

			out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Add Customer Bookings</h1>");
			out.println("<br>");
			if(session.getAttribute("error_msg")!=null){
				out.print("<h4 style='color:red'>"+session.getAttribute("error_msg")+"</h4>");
				session.removeAttribute("error_msg");
			}
			out.print("<table style='width: 60%;'>");
			if (session.getAttribute("add_msg") != null){
				out.print("<h4 style='color:red'>"+ session.getAttribute("add_msg") + "</h4>");
				session.removeAttribute("add_msg");
			}
			//out.println("<tr><th>User Name</th><th>Full Name</th><th>Email Id</th><th>Credit Card No.</th><th>CVV</th><th>Zip Code</th><th>Shipping Address1</th><th>Shipping Address2</th><th>Price</th><th>Action</th></tr>");

			out.println("<form action='CheckOut' method='post'>");

			out.println("<tr><td>Username</td><td><input type='text' name='username' required></td>");
			out.println("<tr><td>Select Hotel Name </td><td>"
	    +"<select id='hid' name='hid' >");

	    out.print("<option value='0'>Select Hotel</option>");
	    for(Integer hotelId: hotelList.keySet()){
	      HotelBean hotel = (HotelBean)hotelList.get(hotelId);
	      out.print("<option value='"+hotel.getId()+"'>"+hotel.getName()+"</option>");
	    //  out.println("<input type='hidden' name='hotelId' value='"+hotel.getId()+"'>");
	    }
	    out.print("</select>"
	    +" </td></tr>");

			out.print("<tr><td>Room type</td><td>"
	    +"<select name='rtype'>"
	    +"<option value='single' selected>single</option>"
	    +"<option value='double'>double</option>"
	    +"<option value='queen'>queen</option>"
	    +"<option value='delux'>delux</option>"
	    +"</select>"
	    +" </td></tr>");
			out.println("<tr><td>Room Quantity</td><td><input type='text' name='roomquantity' required></td></tr>");
			out.println("<tr><td>Check-in Date</td><td><input type='date' name='checkindate' required></td></tr>");
			out.println("<tr><td>Check-out Date</td><td><input type='date' name='checkoutdate' required></td></tr>");
			out.println("<td><input type='submit' class='btnbuy' name='add' value='Add Booking'</td>");
			out.println("</form></table>");

			out.println("</div>");
			utility.printHtml("leftNavBar.jsp");
			utility.printHtml("footer.jsp");
		}

}
