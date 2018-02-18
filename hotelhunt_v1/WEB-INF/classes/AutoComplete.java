import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AutoComplete extends HttpServlet {
      AjaxUtility aUtil = new AjaxUtility();

      public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
          String action = request.getParameter("action");
          String searchId = request.getParameter("id");
          String type = request.getParameter("type");
          try{
                StringBuffer sb = new StringBuffer();
                boolean namesAdded = false;
                if(action.equals("complete") && type.equals("name") ){
                  if (!searchId.equals("")){
                      sb = aUtil.readData(searchId);
                      if(sb!=null || !sb.equals("")){
                        System.out.println("sb :"+sb.toString()+".");
                        namesAdded=true;
                      }
                      if(namesAdded){
                        System.out.println("namesAdded : true");
                        response.setContentType("text/xml");
                        response.setHeader("Cache-Control", "no-cache");
                        response.getWriter().write("<hotels>" + sb.toString() + "</hotels>");
                      }
                }
              }else if(action.equals("complete") && type.equals("zipcode") ){
                if (!searchId.equals("")){
                    sb = aUtil.readZipData(searchId);
                    if(sb!=null || !sb.equals("")){
                      System.out.println("sb :"+sb.toString()+".");
                      namesAdded=true;
                    }
                    if(namesAdded){
                      System.out.println("namesAdded : true");
                      response.setContentType("text/xml");
                      response.setHeader("Cache-Control", "no-cache");
                      response.getWriter().write("<hotels>" + sb.toString() + "</hotels>");
                    }
              }
            }
          }catch(Exception exception){
              exception.printStackTrace();
          }

          if (action.equals("lookup")) {
            String sId = request.getParameter("searchId");
          //  System.out.println("lookup:: "+ sId);
              if (sId != null) {
  				        request.setAttribute("hotelID", sId);
                  request.setAttribute("hotelCity", "");
                  request.setAttribute("hotelRating", "0");
                  request.setAttribute("minVal", "0");
                  request.setAttribute("maxVal", "0");

                  request.setAttribute("disabilityCare", "false");
                  request.setAttribute("flight", "false");
                  request.setAttribute("localTour", "false");
                  request.setAttribute("carRental", "false");
                  request.setAttribute("pool", "false");
                  request.setAttribute("gym", "false");
                   request.setAttribute("amenities", "false");
                  doPost(request,response);
              }
          }

          if (action.equals("citylookup")) {
            String sId = request.getParameter("searchId");
          //  System.out.println("citylookup:: "+ sId);
              if (sId != null) {
  				         request.setAttribute("hotelCity", sId);
                   request.setAttribute("hotelRating", "0");
                   request.setAttribute("hotelID", "0");
                   request.setAttribute("minVal", "0");
                   request.setAttribute("maxVal", "0");

                   request.setAttribute("disabilityCare", "false");
                   request.setAttribute("flight", "false");
                   request.setAttribute("localTour", "false");
                   request.setAttribute("carRental", "false");
                   request.setAttribute("pool", "false");
                   request.setAttribute("gym", "false");
                   request.setAttribute("amenities", "false");
                   doPost(request,response);
              }
          }

          if (action.equals("roomlookup")) {
            String sId = request.getParameter("searchId");
          //  System.out.println("citylookup:: "+ sId);
              if (sId != null) {
  				         request.setAttribute("hotelId", sId);
                   doPost(request,response);
              }
          }

          if (action.equals("ratinglookup")) {
            String sId = request.getParameter("rating");
          //  System.out.println("ratinglookup:: "+ sId);
              if (sId != null) {
  				         request.setAttribute("hotelCity", "");
                   request.setAttribute("hotelID", "0");
                   request.setAttribute("hotelRating", sId);
                   request.setAttribute("minVal", "0");
                   request.setAttribute("maxVal", "0");

                   request.setAttribute("disabilityCare", "false");
                   request.setAttribute("flight", "false");
                   request.setAttribute("localTour", "false");
                   request.setAttribute("carRental", "false");
                   request.setAttribute("pool", "false");
                   request.setAttribute("gym", "false");
                    request.setAttribute("amenities", "false");
                  doPost(request,response);
              }
          }

          if (action.equals("pricelookup")) {
            String minVal = request.getParameter("minVal");
            String maxVal = request.getParameter("maxVal");
        //    System.out.println("pricelookup:: "+ minVal +".."+maxVal);
              if (minVal != null && maxVal != null) {
  				         request.setAttribute("hotelCity", "");
                   request.setAttribute("hotelID", "0");
                   request.setAttribute("hotelRating", "0");
                   request.setAttribute("minVal", minVal);
                   request.setAttribute("maxVal", maxVal);

                   request.setAttribute("disabilityCare", "false");
                   request.setAttribute("flight", "false");
                   request.setAttribute("localTour", "false");
                   request.setAttribute("carRental", "false");
                   request.setAttribute("pool", "false");
                   request.setAttribute("gym", "false");
                    request.setAttribute("amenities", "false");
                  doPost(request,response);
              }
          }

          if (action.equals("amenitieslookup")) {

            String disabilityCare = request.getParameter("disabilityCare");
            String flight = request.getParameter("flight");
            String localTour = request.getParameter("localTour");
            String carRental = request.getParameter("carRental");
            String pool = request.getParameter("pool");
            String gym = request.getParameter("gym");

              if (disabilityCare != null && flight != null && localTour != null && carRental != null && pool != null && gym != null) {
              //   System.out.println( disabilityCare +" .. "+ flight +" .. "+ localTour +" .. "+carRental+" .. "+pool+" .. "+gym);
  				         request.setAttribute("hotelCity", "");
                   request.setAttribute("hotelID", "0");
                   request.setAttribute("hotelRating", "0");
                   request.setAttribute("minVal", "0");
                   request.setAttribute("maxVal", "0");

                   request.setAttribute("disabilityCare", disabilityCare);
                   request.setAttribute("flight", flight);
                   request.setAttribute("localTour", localTour);
                   request.setAttribute("carRental", carRental);
                   request.setAttribute("pool", pool);
                   request.setAttribute("gym", gym);

                   request.setAttribute("amenities", "true");
                   doPost(request,response);
              }
          }

          if (action.equals("topcitylookup")) {
            String sId = request.getParameter("searchId");
              if (sId != null) {
  				         request.setAttribute("hCity", sId);
                   doHotelsByCity(request,response);
              }
          }

          if (action.equals("5ratinglookup")) {
             String sId = request.getParameter("rating");
              if (sId != null) {
  				         request.setAttribute("rating", sId);
                   doHotelsBy5Rating(request,response);
              }
          }

          if (action.equals("pricedealslookup")) {
            String minVal = request.getParameter("minVal");
            String maxVal = request.getParameter("maxVal");
              if (minVal != null && maxVal != null) {
  				         request.setAttribute("minVal", minVal);
                   request.setAttribute("maxVal", maxVal);
                   doHotelsBypricedeals(request,response);
              }
          }
       }

       public void doHotelsBypricedeals(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
           response.setContentType("text/html");
           PrintWriter out = response.getWriter();
           Utility utility = new Utility(request,out);
           MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
           HashMap<Integer, HotelBean> hotelList = sqlUtil.getHotels();

           int minVal = Integer.parseInt(request.getAttribute("minVal")+"");
           int maxVal = Integer.parseInt(request.getAttribute("maxVal")+"");

           HotelBean hotel = null;
           int count = hotelList.size();
           int i = 1;

           utility.printHtml("header.jsp");
           out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
           out.println("<div id='container1'>");

           out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>HOTELS</h1>");

           int count1 = 0;
           boolean noMatch = false;

           for(Integer hotelID: hotelList.keySet()){
                hotel = (HotelBean)hotelList.get(hotelID);

                if((minVal<= hotel.getLowrate()) && (hotel.getHighrate() <= maxVal)){
                       count1++;
                       noMatch = false;
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
                       out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='View Reviews'>");
                       out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='Write Review'>");

                       out.println("</div>");
                       out.println("</div>");
                       out.println("</div>");

                       if(i%4 == 0 || i == count)
                            out.println("<br>");

                       i++;
                   }else if(count1 == 0){
                      noMatch = true;
                   }
           }
           if(noMatch){
              noMatch = false;
               out.println("<hr>");
              out.println("<h2 style='text-align: left;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>No hotels found!</h2>");
           }

          out.println("</div>");
           utility.printHtml("leftNavBar.jsp");
           utility.printHtml("footer.jsp");

       }


       public void doHotelsBy5Rating(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
           response.setContentType("text/html");
           PrintWriter out = response.getWriter();
           Utility utility = new Utility(request,out);
           MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
           HashMap<Integer, HotelBean> hotelList = sqlUtil.getHotels();

           int rating = Integer.parseInt(request.getAttribute("rating")+"");

           HotelBean hotel = null;
           int count = hotelList.size();
           int i = 1;

           utility.printHtml("header.jsp");
           out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
           out.println("<div id='container1'>");

           out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>HOTELS</h1>");

           int count1 = 0;
           boolean noMatch = false;

           for(Integer hotelID: hotelList.keySet()){
                hotel = (HotelBean)hotelList.get(hotelID);

                if(hotel.getHotelrating() == rating){
                       count1++;
                       noMatch = false;
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
                       out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='View Reviews'>");
                       out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='Write Review'>");

                       out.println("</div>");
                       out.println("</div>");
                       out.println("</div>");

                       if(i%4 == 0 || i == count)
                            out.println("<br>");

                       i++;
                   }else if(count1 == 0){
                      noMatch = true;
                   }
           }
           if(noMatch){
              noMatch = false;
               out.println("<hr>");
              out.println("<h2 style='text-align: left;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>No hotels found!</h2>");
           }

          out.println("</div>");
           utility.printHtml("leftNavBar.jsp");
           utility.printHtml("footer.jsp");

       }


       public void doHotelsByCity(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
           response.setContentType("text/html");
           PrintWriter out = response.getWriter();
           Utility utility = new Utility(request,out);
           MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
           HashMap<Integer, HotelBean> hotelList = sqlUtil.getHotels();

           String hotelCity = (String) request.getAttribute("hCity");

           HotelBean hotel = null;
           int count = hotelList.size();
           int i = 1;

           utility.printHtml("header.jsp");
           out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
           out.println("<div id='container1'>");

           out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>HOTELS</h1>");

           int count1 = 0;
           boolean noMatch = false;

           for(Integer hotelID: hotelList.keySet()){
                hotel = (HotelBean)hotelList.get(hotelID);

                if(hotel.getCity().equalsIgnoreCase(hotelCity)){
                       count1++;
                       noMatch = false;
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
                       out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='View Reviews'>");
                       out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='Write Review'>");

                       out.println("</div>");
                       out.println("</div>");
                       out.println("</div>");

                       if(i%4 == 0 || i == count)
                            out.println("<br>");

                       i++;
                   }else if(count1 == 0){
                      noMatch = true;
                   }
           }
           if(noMatch){
              noMatch = false;
               out.println("<hr>");
              out.println("<h2 style='text-align: left;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>No hotels found!</h2>");
           }

          out.println("</div>");
           utility.printHtml("leftNavBar.jsp");
           utility.printHtml("footer.jsp");

       }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Utility utility = new Utility(request,out);
            MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
            HashMap<Integer, HotelBean> hotelList = sqlUtil.getHotels();
            String hID = (String) request.getAttribute("hotelID");
            String hotelCity = (String) request.getAttribute("hotelCity");
            String hotelRating = (String) request.getAttribute("hotelRating");
            String minVal = (String) request.getAttribute("minVal");
            String maxVal = (String) request.getAttribute("maxVal");

            String disabilityCare = (String)request.getAttribute("disabilityCare");
            String flight =(String) request.getAttribute("flight");
            String localTour =(String) request.getAttribute("localTour");
            String carRental =(String) request.getAttribute("carRental");
            String pool = (String)request.getAttribute("pool");
            String gym = (String)request.getAttribute("gym");

            String amenities = (String)request.getAttribute("amenities");

            if(disabilityCare.equals("true"))
                disabilityCare = "Yes";
            /*else
                disabilityCare = "false";*/

            if(flight.equals("true"))
                flight = "Yes";
          /*  else
                flight = "No";*/

            if(localTour.equals("true"))
                localTour = "Yes";
          /*  else
                localTour = "No";*/

            if(carRental.equals("true"))
                carRental = "Yes";
          /*  else
                carRental = "No";*/

            if(pool.equals("true"))
                pool = "Yes";
          /*  else
                pool = "No";*/

            if(gym.equals("true"))
                gym = "Yes";
          /*  else
                gym = "No";*/

            HotelBean hotel = null;
            int count = hotelList.size();
            int i = 1;

    			/*	utility.printHtml("header.jsp");
            out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
            out.println("<div id='container1'>");
*/
            out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>HOTELS</h1>");

            int count1 = 0;
            boolean noMatch = false;

            for(Integer hotelID: hotelList.keySet()){
                 hotel = (HotelBean)hotelList.get(hotelID);

                 if((((Integer.parseInt(minVal)<= hotel.getLowrate()) && (hotel.getHighrate() <= Integer.parseInt(maxVal)))||(hotel.getHotelrating() == Integer.parseInt(hotelRating))||(hotel.getCity().equalsIgnoreCase(hotelCity))||(hotel.getId() == Integer.parseInt(hID))||
                 (hotel.getZipcode() == Integer.parseInt(hID)) ) &&  amenities.equals("false") ){
                        count1++;
                        noMatch = false;
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
        								out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='View Reviews'>");
        								out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='Write Review'>");

                        out.println("</div>");
                        out.println("</div>");
                        out.println("</div>");

        		            if(i%4 == 0 || i == count)
        								     out.println("<br>");

        		            i++;
                    }else if((hotel.isDisabilityCare().equalsIgnoreCase(disabilityCare) || hotel.isFlight().equalsIgnoreCase(flight) || hotel.isLocalTour().equalsIgnoreCase(localTour) || hotel.isCarRental().equalsIgnoreCase(carRental) || hotel.isPool().equalsIgnoreCase(pool) || hotel.isGym().equalsIgnoreCase(gym)) &&  amenities.equals("true")){
                      count1++;
                      noMatch = false;
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
                      out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='View Reviews'>");
                      out.println("<input type='submit' class='tm-home-box-2-description ' style='padding-top: 10px;' value='Write Review'>");

                      out.println("</div>");
                      out.println("</div>");
                      out.println("</div>");

                      if(i%4 == 0 || i == count)
                           out.println("<br>");

                      i++;
                    }else if(count1 == 0){
                       noMatch = true;
                    }
            }
            if(noMatch){
               noMatch = false;
                out.println("<hr>");
               out.println("<h2 style='text-align: left;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>No hotels found!</h2>");
            }
          //  request.getRequestDispatcher("DisplayHotels").forward(request, response);
        /*    out.println("</div>");
            utility.printHtml("leftNavBar.jsp");
    		    utility.printHtml("footer.jsp");
            */
        }
}
