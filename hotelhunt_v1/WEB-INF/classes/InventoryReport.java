import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InventoryReport extends HttpServlet{
	private String error_msg;

	ArrayList<Availability> arr = new ArrayList<Availability>();

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

    MySqlDataStoreUtilities md = new MySqlDataStoreUtilities();

    arr = md.checkAvailableRooms();

    Utility utility = new Utility(request,out);
    utility.printHtml("header.jsp");
    out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
    out.println("<div id='container1'>");

    out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>INVENTORY REPORT</h1>");

    out.print("<ul style='list-style: none;'>");
    out.print("<li><a href='#report1' style='font-size: 20px;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;<b>1. Hotel List with available rooms</b></a></li>");
    out.print("<li><a href='#report2' style='font-size: 20px;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;<b>2. Bar Chart that shows the hotel names and the total number of rooms available</b></a></li>");
    out.print("<li><a href='#report3' style='font-size: 20px;color:black'>&nbsp;&nbsp;&nbsp;&nbsp;<b>3. Hotel List which is offering discount </b></a></li>");
    out.print("</ul>");

    out.print("<article id='report1'><hr style='width: 100%'><h2 style='font-size: 20px;'>&nbsp;&nbsp;&nbsp;&nbsp;<b> Hotel List with available rooms </b></h2><hr style='width: 100%'>");
                out.print("<table style='width:60%' >");
                out.print("<tr><td>&nbsp;</td><td><b>Hotel Name </b></td><td><b>Rooms Count</b></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
                for(Availability av : arr) {
                      out.print("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>"
                          + "<td>"+av.getHotelName()+"</td>"
                          + "<td>"+av.getCount()+"</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
                  }
    out.print("</table><article>");

    out.print("<article id='report1'><hr style='width: 100%'><h2 style='font-size: 20px;'>&nbsp;&nbsp;&nbsp;&nbsp;<b> Bar Chart with the hotel names and the total number of rooms available</b> </h2><hr style='width: 100%'>");
    out.println("<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>");
		out.println("<script type='text/javascript'>");
		out.println("google.charts.load('current', {packages: ['corechart', 'bar']});");
		out.println("google.charts.setOnLoadCallback(drawBasic);");
		out.println("function drawBasic() {");
		out.println("var data = google.visualization.arrayToDataTable([");
		out.println("['Hotel name', 'Available rooms',],");

    for(Availability av : arr) {
      String hotel_name = av.getHotelName();
      int count = av.getCount();
      out.println("[' " +hotel_name+ " ', "+count+ "],");
    }

    out.println("]);");
		out.println("var options = {");
		out.println("title: 'Availability of rooms',");
		out.println("chartArea: {width: '65%', height: 950},");
		out.println("hAxis: {");
		out.println("title: 'Number of available rooms in hotels',");
		out.println("minValue: 0");
		out.println("},");
		out.println("vAxis: {");
		out.println("title: 'Hotel Name'");
		out.println("}");
		out.println("};");
		out.println("var chart = new google.visualization.BarChart(document.getElementById('chart_div'));");
		out.println("chart.draw(data, options);");
		out.println("}");
		out.println("</script>");

    out.println("<div id = 'chart_div' style='width:900px; height:1000px'></div>");
    out.print("<article>");

    out.print("<article id='report3'><hr style='width: 100%'><h2 style='font-size: 20px;'>&nbsp;&nbsp;&nbsp;&nbsp;<b> Hotel List which is offering discount </b></h2><hr style='width: 100%'>");
    out.print("<table style='width:60%' >");
    MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
     HashMap<Integer, HotelBean> hotelList = new HashMap<Integer, HotelBean>();
    hotelList = sqlUtil.getHotels();
    HotelBean hotel = null;
    out.print("<tr><td>&nbsp;</td><td><b>Hotel Name </b></td><td><b>Discount %</b></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
    for(Integer hotelID: hotelList.keySet()){
         hotel = (HotelBean)hotelList.get(hotelID);
        if(hotel.getDiscount() != 0){
          out.print("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>"
            + "<td>"+hotel.getName()+"</td>"
            + "<td>"+hotel.getDiscount() +"</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
        }
    }
    out.print("</table><article>");
    out.println("</div>");
    utility.printHtml("leftNavBar.jsp");
    utility.printHtml("footer.jsp");

  }
}
