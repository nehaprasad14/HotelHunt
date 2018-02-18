import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DisplayRooms extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		response.setContentType("text/html");
    		PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(true);
        MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
        HashMap<Integer, HotelBean> hotelList = new HashMap<Integer, HotelBean>();
        hotelList = sqlUtil.getHotels();
        String hotelID = request.getParameter("hotelID");
        HotelBean hotel = (HotelBean)hotelList.get(Integer.parseInt(hotelID));

        if(hotelList != null)
            displayContent(request, response,out,hotel);
        else
            response.sendRedirect("Home");
	}

  public void displayContent(HttpServletRequest request, HttpServletResponse response, PrintWriter out,HotelBean hotel){
      Utility utility = new Utility(request,out);
      ArrayList<String> roomIDList = new ArrayList<String>();
      roomIDList = hotel.getRooms();

      HashMap<Integer, RoomBean> roomsList = new HashMap<Integer, RoomBean>();
      MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
      roomsList = sqlUtil.getRooms();

      ArrayList<String> imageList = new ArrayList<String>();
      imageList.add(hotel.getHotelimage());
      for(String roomID : roomIDList){
          RoomBean room = (RoomBean)roomsList.get(Integer.parseInt(roomID));
          imageList.add(room.getRoomimage());
      }


      int count = roomIDList.size();
      int i = 1;

      utility.printHtml("header.jsp");
      out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
      out.println("<div id='container1' style='position: relative;'>");

      out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px; '>"+hotel.getName()+"</h1>");
      out.println("<br>");

    //  <!-- Image carousal -->
      out.println("<div id='myCarousel' class='carousel slide' data-ride='carousel' style='width: 96.5%; height:20%;margin-left:12px;'>");
          //  <!-- Indicators -->
            out.println("<ol class='carousel-indicators'>");
              out.println("<li data-target='#myCarousel' data-slide-to='0' class='active'></li>");
              out.println("<li data-target='#myCarousel' data-slide-to='1'></li>");
              out.println("<li data-target='#myCarousel' data-slide-to='2'></li>");
            out.println("</ol>");

        //    <!-- Wrapper for slides -->
            int k = 0;

            out.println("<div class='carousel-inner' >");
            for(String imgsrc : imageList){
                if(k == 0)
                    out.print("<div class='item active'>");
                else
                    out.print("<div class='item'>");
                    out.print("<img src='"+imgsrc+"'  style='height:400px;width:1200px;'>");
                  out.println("</div>");
                  k++;
            }
            out.println("</div>");

        //    <!-- Left and right controls -->
            out.println("<a class='left carousel-control' href='#myCarousel' data-slide='prev'>");
              out.println("<span class='glyphicon glyphicon-chevron-left' style='color:yellow;'></span>");
              out.println("<span class='sr-only'>Previous</span>");
            out.println("</a>");
            out.println("<a class='right carousel-control' href='#myCarousel' data-slide='next'>");
              out.println("<span class='glyphicon glyphicon-chevron-right' style='color:yellow;'></span>");
              out.println("<span class='sr-only'>Next</span>");
            out.println("</a>");
          out.println("</div>");

    //  <!-- end -->

          out.println("<hr>");
            out.println("<h3 style='text-align: center;width: 96.5%;margin-left:12px;'>Hotel Details</h3>");
          out.println("<hr>");

          out.println("<div style='width: 96.5%;margin-left:12px;padding:10px;'>");
            out.println("<table>");
              out.println("<tr>");
                out.println("<td><h4> <b>Address: </b></h4></td><td>  <img src='img/home/location.jpg' width='40px'>"+hotel.getAddress()+" - "+hotel.getCity()+","+hotel.getState()+"</td>");

											out.println("<td>");
							out.print("<form method='post' action='GetDirection'>" +
							"<input type='hidden' name='address' value='"+hotel.getAddress()+"'>"+
							"<input type='hidden' name='city' value='"+hotel.getCity()+"'>"+
							"<input type='hidden' name='name' value='"+hotel.getName()+"'>");
							out.println("&nbsp;&nbsp;&nbsp; <input type='submit' name='submit' class='tm-yellow-btn' style='height:35px;width:150px;' value='Get Direction'></form>");
							out.println("</td>");
							out.println("</tr>");

              out.println("<tr>");
                out.println("<td><h4> <b>Rating: </b></h4></td>");
                out.println("<td>");
                for(int j=0 ; j< hotel.getHotelrating() ; j++)
                    out.println("<i class='fa fa-star' style='color:red'></i>");
                out.println("</td>");
              out.println("</tr>");
             out.println("<tr>");
                out.println("<td><h4> <b>Amenities: </b></h4></td>");
              out.println("<td>");
                if(hotel.isDisabilityCare().equalsIgnoreCase("Yes"))
                  out.println("<br>  &nbsp;&nbsp;&nbsp;<i class='fa fa-wheelchair' style='color:#000080'></i> Disability Care : <i class='fa fa-check-circle'  style='color:green' ></i>");
                else
                   out.println("<br>  &nbsp;&nbsp;&nbsp;<i class='fa fa-wheelchair' style='color:#000080'></i> Disability Care : <i class='fa fa-times-circle'  style='color:red' ></i>");


                if(hotel.isPool().equalsIgnoreCase("Yes"))
                  out.println("<br>  &nbsp;&nbsp;&nbsp;<i class='fa fa-life-ring' style='color:#000080'></i> Pool : <i class='fa fa-check-circle'  style='color:green' ></i>");
                else
                  out.println("<br>  &nbsp;&nbsp;&nbsp;<i class='fa fa-life-ring' style='color:#000080'></i> Pool : <i class='fa fa-times-circle'  style='color:red' ></i>");

                if(hotel.isGym().equalsIgnoreCase("Yes"))
                  out.println("<br>  &nbsp;&nbsp;&nbsp;<i class='fa fa-heartbeat' style='color:#000080'></i> Gym : <i class='fa fa-check-circle'  style='color:green' ></i>");
                else
                   out.println("<br>  &nbsp;&nbsp;&nbsp;<i class='fa fa-heartbeat' style='color:#000080'></i> Gym : <i class='fa fa-times-circle' style='color:red' ></i>");
                out.println("</td>");
              out.println("</tr>");

							 if(hotel.isPackages().equalsIgnoreCase("Yes")){
								 out.println("<tr>");
								 		 out.println("<td><h4> <b>Provide Pakage:  </b></h4></td><td>Yes</td>");
										 out.println("</tr>");
										 out.println("<tr>");
										 out.println("<td></td>");
										 if(hotel.isCarRental().equalsIgnoreCase("Yes"))
		 									out.println("<td>  &nbsp;&nbsp;&nbsp;<i class='fa fa-car fa-lg' style='color:#000080'></i> Free car rental : <i class='fa fa-check-circle' style='color:green' ></i>");
		 								else
		 									out.println("<td>  &nbsp;&nbsp;&nbsp;<i class='fa fa-car fa-lg' style='color:#000080'></i> Free car rental : <i class='fa fa-times-circle' style='color:red' ></i>");

											if(hotel.isFlight().equalsIgnoreCase("Yes"))
			                  out.println("<br>  &nbsp;&nbsp;&nbsp;<i class='fa fa-plane fa-lg' style='color:#000080'></i> Airport Transfers : <i class='fa fa-check-circle'  style='color:green' ></i>");
			                else
			                   out.println("<br>  &nbsp;&nbsp;&nbsp;<i class='fa fa-plane fa-lg' style='color:#000080'></i> Airport Transfers : <i class='fa fa-times-circle'  style='color:red' ></i>");

										if(hotel.isLocalTour().equalsIgnoreCase("Yes"))
	 			                  out.println("<br>  &nbsp;&nbsp;&nbsp;<i class='fa fa-taxi fa-lg' style='color:#000080'></i> Free Local Tour : <i class='fa fa-check-circle'  style='color:green' ></i>");
	 			                else
	 			                   out.println("<br>  &nbsp;&nbsp;&nbsp;<i class='fa fa-taxi fa-lg' style='color:#000080'></i> Free Local Tour : <i class='fa fa-times-circle'  style='color:red' ></i>");

									out.println("</td>");
									out.println("</tr>");
							 }else{
								 out.println("<tr>");
								 out.println("<td><h4> <b>Provide Pakage:  </b></h4></td><td>No</td>");
								 out.println("</tr>");
							 }

              out.println("<tr>");
                out.println("<td>");

								//added for reviews
								out.println("<form method='get' action='WriteReview'>"
                        +"<input type='hidden' name='hname' value='"+hotel.getName()+"'>"
                        +"<input type='hidden' name='hid' value='"+hotel.getId()+"'>"
                        +"<input type='hidden' name='hcity' value='"+hotel.getCity()+"'>"
                        +"<input type='hidden' name='hzip' value='"+hotel.getZipcode()+"'>"
                        +"<input type='submit' name='submit' class='tm-yellow-btn' style='height:35px;width:150px;' value='Write Review'>"
                        +"</form>");
									out.println("</td>");
								out.println("<td>");
											out.println("<form method='get' action='ViewReview'>"
                        +"<input type='hidden' name='hname' value='"+hotel.getName()+"'>"
												+"<input type='hidden' name='hid' value='"+hotel.getId()+"'>"
                        +"&nbsp;&nbsp;&nbsp; <input type='submit' name='submit' class='tm-yellow-btn' style='height:35px;width:150px;' value='View Review'>"
                        +"</form>");
												out.println("</td>");


              out.println("</tr>");
            out.println("</table>");
          out.println("</div>");
					//********************************************Rooms***************************************************//
          out.println("<hr>");
            out.println("<h3 style='text-align: center;width: 96.5%;margin-left:12px;'>Available Rooms</h3>");
          out.println("<hr>");

					for(String roomID : roomIDList){
							RoomBean room = (RoomBean)roomsList.get(Integer.parseInt(roomID));
							out.println("<div style='width: 96.5%;margin-left:12px;padding:10px;'>");
							out.println("<form method='post' action='Cart'>");
							out.println("<input type='hidden' name='hid' value='"+hotel.getId()+"'>");
							out.println("<input type='hidden' name='hname' value='"+hotel.getName()+"'>");
							out.println("<input type='hidden' name='himage' value='"+hotel.getHotelimage()+"'>");
							out.println("<input type='hidden' name='rid' value='"+room.getId()+"'>");
							out.println("<input type='hidden' name='roomtype' value='"+room.getRoomType()+"'>");
							out.println("<input type='hidden' name='rimage' value='"+room.getRoomimage()+"'>");
							out.println("<input type='hidden' name='rprice' value='"+room.getRoomrate()+"'>");

								out.println("<table>");
									out.println("<tr>");
										out.println("<td rowspan='4'><img src='"+room.getRoomimage()+"' style='height:150px;width:150px;'></td>");
										out.println("<td><br><h3>&nbsp;&nbsp; "+room.getRoomType()+"</h3></td>");
									out.println("</tr>");
									out.println("<tr>");
										out.println("<td><br>&nbsp;&nbsp;&nbsp;<b>Price:  $"+room.getRoomrate()+"  </b> per night </td>");
										out.println("<td><br>&nbsp;&nbsp;&nbsp;<b>Adults: </b>");
										out.println("<select>");
											out.println("<option value='0'>--  Adults (18+) -- </option>");
											out.println("<option value='1'>1</option>");
											out.println("<option value='2'>2</option>");
											out.println("<option value='3'>3</option>");
											out.println("<option value='4'>4</option>");
											out.println("<option value='5'>5</option>");
										out.println("</select>");
										out.println("</td>");
										out.println("<td><br>&nbsp;&nbsp;&nbsp;<b>Children: </b>");
											out.println("<select>");
												out.println("<option value='0'>--  Children --</option>");
												out.println("<option value='0'>0</option>");
												out.println("<option value='1'>1</option>");
												out.println("<option value='2'>2</option>");
												out.println("<option value='3'>3</option>");
												out.println("<option value='4'>4</option>");
												out.println("<option value='5'>5</option>");
											out.println("</select>");
										out.println("</td>");
									out.println("</tr>");
									out.println("<tr>");
											out.println("<td>&nbsp;&nbsp;&nbsp;<b>Rooms Available: </b>"+room.getQuantity()+"</td>");
											out.println("<td>&nbsp;&nbsp;&nbsp;<b>Check In: </b>");
		                      out.println("<input type='date' name='checkindate' placeholder='Check-in Date' />");
											out.println("</td>");
											out.println("<td>&nbsp;&nbsp;&nbsp;<b>Check Out: </b>");
		                      out.println("<input type='date' name='checkoutdate' placeholder='Check-in Date' />");
											out.println("</td>");
									out.println("</tr>");
									out.println("<tr>");
									  out.println("<td>&nbsp;&nbsp;&nbsp;<b>Select Room Quantity: </b>");
										out.println("<select name='rquantity'>");
										 for(int j = 0; j < room.getQuantity();j++)
											out.println("<option value='"+(j+1)+"'>"+(j+1)+"</option>");
										out.println("</select>");
										out.println("</td>");
										out.println("<td>&nbsp;&nbsp;&nbsp;<input type='submit' name='submit' class='tm-yellow-btn' style='height:35px;width:90px;' value='Book Now'></td>");
									out.println("</tr>");
								out.println("</table>");
								out.println("</form>");
							out.println("</div>");
					}

			//<!-- displayRooms -->
      out.println("</div>");
      utility.printHtml("leftNavBar.jsp");
      utility.printHtml("footer.jsp");
  }

}
