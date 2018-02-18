import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateBooking extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utility utility = new Utility(request, pw);
		HttpSession session = request.getSession(true);
		String username = request.getParameter("username");
    int hotelid = Integer.parseInt(request.getParameter("hotelid"));
    int roomid = Integer.parseInt(request.getParameter("roomid"));
		int orderNum = Integer.parseInt(request.getParameter("ordernum"));
		int roomquantity = Integer.parseInt(request.getParameter("roomquantity"));
		String checkindate = request.getParameter("checkindate");
		String checkoutdate = request.getParameter("checkoutdate");


    MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
		//utility.updateReservation(orderNum,userName,email,zipcode,address1,address2);
    sqlUtil.updateBookingDetails(orderNum,hotelid,roomid,roomquantity,checkindate,checkoutdate);

  session.setAttribute("update_msg", "Order "+orderNum+" has been Updated !");
  if(utility.checkLoggedIn() == true){
    response.sendRedirect("Account"); return;
  }

    /*
		for (BillingOrderBean bo : utility.getBilledOrders(userName)){

      ArrayList<OrderItemBean> orderItems = bo.getOrderItems();
      for(OrderItemBean oi : orderItems){

  			if(bo.getOrderNum() == orderNum){
  				bo.setEmail(email);
  				bo.setZipcode(zipcode);
  				bo.setAddress1(address1);
  				bo.setAddress2(address2);



  				session.setAttribute("update_msg", "Order "+orderNum+" has been Updated !");
  				if(utility.checkLoggedIn() == true){
  					response.sendRedirect("Account"); return;
  				}

  			}
		   }
     }*/
	}

}
