import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DisplayPackages extends HttpServlet {

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    		response.setContentType("text/html");
	    		PrintWriter out = response.getWriter();
	        HttpSession session=request.getSession(true);
	        HashMap<Integer, HotelBean> hotelList = new HashMap<Integer, HotelBean>();

	        MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
	        hotelList = sqlUtil.getHotels();

	        if(hotelList != null)
	            displayContent(request, response,out,hotelList);
	        else
	            response.sendRedirect("Home");
		}

    public void displayContent(HttpServletRequest request, HttpServletResponse response, PrintWriter out,HashMap<Integer, HotelBean> hotelList){
        Utility utility = new Utility(request,out);
        HotelBean hotel = null;
        int count = hotelList.size();
        //int i = 1;

				utility.printHtml("header.jsp");
        out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
        out.println("<div id='container1'>");

        out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>OUR PACKAGES</h1>");
        out.println("<br>");
        for(Integer hotelID: hotelList.keySet()){
             hotel = (HotelBean)hotelList.get(hotelID);
            if(hotel.isPackages().equalsIgnoreCase("Yes")){
            out.println(" <div class='col-lg-6'>");
               out.println("<div class='tm-home-box-3'>");
                 out.println("<div class='tm-home-box-3-img-container'>");
                   out.println("<img src='"+hotel.getHotelimage()+"' width='250px' height='225px'>");
                 out.println("</div>");
                 out.println("<div class='tm-home-box-3-info'>");
                   out.println("<p class='tm-home-box-3-description'>");
                      out.println("<b>"+hotel.getName()+" </b><br>");
                      for(int j=0 ; j< hotel.getHotelrating() ; j++)
                          out.println("<i class='fa fa-star' style='color:red'></i>");
                     out.println("<br>");
                     out.println("<img src='img/home/location.jpg' alt='location' width='15%'> "+hotel.getCity()+" - "+hotel.getZipcode()+" <br>");
										 // add changes here
											 if(hotel.isCarRental().equalsIgnoreCase("Yes"))
												 out.println("<i class='fa fa-car fa-lg' style='color:#000080'></i> Free car rental with this hotel");
											 else  if(hotel.isFlight().equalsIgnoreCase("Yes"))
											   out.println("<i class='fa fa-plane fa-lg' style='color:#000080'></i> Flight(one-way)");
											 else  if(hotel.isLocalTour().equalsIgnoreCase("Yes"))
											    out.println("<i class='fa fa-taxi fa-lg' style='color:#000080'></i> Free Local Tour");

                  out.println(" </p>");
                  out.println("<div class='tm-home-box-2-container'>");
                  out.println("<form method='get' action='DisplayRooms'>");
                   out.println("<a href='#' class='tm-home-box-2-link' style='pointer-events: none; cursor: default;'><span class='tm-home-box-2-description border-right'><br>$"+hotel.getLowrate()+"</span></a>");
                   out.println("<input type='hidden' name='hotelID' value='"+hotelID+"'>");
   								out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 19px;width:175px' value='View Package'>");
   								out.println("</form>");
                out.println(" </div>");
               out.println("</div>");
             out.println("</div>");
            out.println(" </div>");
          }/*else{
            out.println("<hr>");
           out.println("<h2 style='text-align: left;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>No packages found!</h2>");
         }*/
        }
        out.println("</div>");
        utility.printHtml("leftNavBar.jsp");
		    utility.printHtml("footer.jsp");
    }

}
