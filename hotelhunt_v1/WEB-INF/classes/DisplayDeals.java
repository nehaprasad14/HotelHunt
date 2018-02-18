import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DisplayDeals extends HttpServlet {

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
        DealMatches dealMatches = new DealMatches();
        HotelBean hotel = null;
        int count = hotelList.size();
        int i = 1;

				utility.printHtml("header.jsp");
        out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
        out.println("<div id='container1'>");
        out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Deals Match</h1>");

        // added Deal Match Section.

        out.println("<h2 style='font-size:25px; color: #238ec6'>We beat our competitors in all aspects.</h2><br><h2 style='font-size:25px; color: #238ec6'>Best Deals Match Guaranteed on below deals!</h2>");

			dealMatches.readTweets();
			HashMap<Integer, HotelBean> selectedhotelhm = dealMatches.getSelectedHotels();
			ArrayList<String> selectedTweets = dealMatches.getSelectedTweets();
			if(selectedTweets.isEmpty())
			{
				out.println("<p><h3>No Offers Found !</h3></p>");
			}
			else
			{
				out.println("<ul style='list-style: none;'>");
				for(String tweet: selectedTweets)
				{
					out.println("<li>&nbsp;<h4>"+tweet+"</h4></li>");
				}
				out.println("<ul>");
			}
			out.println("<h2 class='title meta'><a style='font-size: 24px;'>Deal Matches</a>"
			+ "</h2>");

			if(selectedhotelhm.isEmpty())
			{
				out.println("<p><h3>No Offers Found !</h3></p>");
			}
			else
			{
        for(Integer hotelID: selectedhotelhm.keySet()){
             hotel = (HotelBean)selectedhotelhm.get(hotelID);

             out.println("<div class='col-lg-3 col-md-3 col-sm-6 col-xs-6 col-xxs-12' style='padding-top: 15px;'>");
             out.println("<div class='tm-home-box-2' style='background-color: #f7f7f7;'>");
             out.println("  <img src='"+hotel.getHotelimage()+"' width='100%' height='200px'>");
             out.println("    <h3 ><b>"+hotel.getName()+"</b> <br><br>");
             for(int j=0 ; j< hotel.getHotelrating() ; j++)
                 out.println("<i class='fa fa-star' style='color:red'></i>");
             out.println("    </h3>");
             out.println("  <span class='tm-home-box-3-description'><img src='img/home/location.jpg' alt='image' width='15%'>"+hotel.getCity()+" - "+hotel.getZipcode()+"</span>");
             out.println("<div class='tm-home-box-3-container'>");
             out.println("<form method='get' action='DisplayRooms'>");
             out.println("<a href='#' class='tm-home-box-2-link'><span class='tm-home-box-2-description border-right border-bottom border-top border-left'><br><b>$"+hotel.getLowrate()+"</b></span></a>");
             out.println("<input type='hidden' name='hotelID' value='"+hotelID+"'>");
             out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 19px;' value='View Rooms'>");
             out.println("</form>");
            // out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='View Reviews'>");
             //out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='Write Review'>");
						 out.println("<a href='WriteReview?hname="+hotel.getName()+"&hid="+hotel.getId()+"&hcity="+hotel.getCity()+"&hzip="+hotel.getZipcode()+"' class='tm-home-box-2-link'><span class='tm-home-box-2-description border-right border-bottom border-top border-left'><br><b>Write Review</b></span></a>");
						out.println("<a href='ViewReview?hname="+hotel.getName()+"&hid="+hotel.getId()+"' class='tm-home-box-2-link' ><span class='tm-home-box-2-description border-right border-bottom border-top border-left'><br><b>View Review</b></span></a>");

             out.println("</div>");
             out.println("</div>");
             out.println("</div>");

             if(i%4 == 0 || i == count)
                  out.println("<br>");

             i++;
				}
			}

      out.println("</div>");
      utility.printHtml("leftNavBar.jsp");
      utility.printHtml("footer.jsp");


    }

}
