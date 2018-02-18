import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;



public class RevenueReport extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    HttpSession session=request.getSession(true);
    Utility utility = new Utility(request, out);
    LoginUtil loginUtil = new LoginUtil(request);

    if (!loginUtil.isLoggedin()) {
      response.sendRedirect("Login");
      return;
    }

    utility.printHtml("header.jsp");
    out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
    out.println("<div id='container1'>");
    out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>Revenue Report</h1>");
    out.println("<br/><br/>");

    out.print("<article><hr style='width: 95%'><h2 style='font-size: 35px;'>Overall Hotel Revenue Report: </h2><hr style='width: 95%'>");


    try{
      int i = 1;
      MySqlDataStoreUtilities mysqldb = new MySqlDataStoreUtilities();
      ArrayList<RevenueReportBean> salesList = mysqldb.getRevenueReport();

      out.print("<table  class='gridtable'>");
      out.print("<th>&nbsp;&nbsp;No.</th><th>&nbsp;&nbsp;Hotel Name</th><th>&nbsp;&nbsp;No. of Bookings</th><th>&nbsp;&nbsp;Total Revenue</th>");
          for(RevenueReportBean rb : salesList){

      			out.print("<tr><td>&nbsp;&nbsp;"+i+"</td>");
            out.print("<td>&nbsp;&nbsp;"+rb.getHotelName()+"</td>");
    //        out.print("<td>"+rb.getItemPrice()+"</td>");
            out.print("<td>&nbsp;&nbsp;"+rb.getHotelCount()+"</td>");
            out.print("<td>&nbsp;&nbsp;"+rb.getTotalSales()+"</td></tr>");
      			i++;
        }
        out.print("</table>");
        out.print("<br><br>");

    }
    catch(Exception e){
      e.printStackTrace();
    }

    out.print("<article><hr style='width: 95%'><h2 style='font-size: 35px;'>BarChart: HotelName Vs TotalRevenue</h2><hr style='width: 95%'>");

    try{

      MySqlDataStoreUtilities mysqldb = new MySqlDataStoreUtilities();
      ArrayList<RevenueReportBean> salesList = mysqldb.getRevenueReport();

      out.println("<script type='text/javascript' src='https://www.gstatic.com/charts/loader.js'></script>");
  		out.println("<script type='text/javascript'>");
  		out.println("google.charts.load('current', {packages: ['corechart', 'bar']});");
  		out.println("google.charts.setOnLoadCallback(drawBasic);");
  		out.println("function drawBasic() {");
  		out.println("var data = google.visualization.arrayToDataTable([");
  		out.println("['Product name', 'Total Revenue',],");

      for(RevenueReportBean rb: salesList){

          String hotelName = rb.getHotelName();
          float hotelSale = rb.getTotalSales();
          out.print("[' " +hotelName+ " ', "+hotelSale+ "],");


        }

      	out.println("]);");

        out.println("var options = {");
    		out.println("title: 'HotelName Vs TotalRevenue',");
    		out.println("chartArea: {width: '60%', height: 300},");
    		out.println("hAxis: {");
    		out.println("title: 'Total Revenue',");
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

        out.println("<div id = 'chart_div' style='width: 900px; height: 400px;'></div>");

        out.print("<br><br>");

    }
    catch(Exception e){
      e.printStackTrace();
    }

    out.print("<article><hr style='width: 95%'><h2 style='font-size: 35px;'>Total daily booking revenue:</h2><hr style='width: 95%'>");

    try{
      int i = 1;
      MySqlDataStoreUtilities mysqldb = new MySqlDataStoreUtilities();
      ArrayList<RevenueReportBean> dailySalesList = mysqldb.getDaywiseRevenueReport();

      out.print("<table  class='gridtable'>");
      out.print("<th>&nbsp;&nbsp;No.</th><th>&nbsp;&nbsp;Booking Date</th><th>&nbsp;&nbsp;Total Revenue</th>");
          for(RevenueReportBean rb : dailySalesList){

      			out.print("<tr><td>&nbsp;&nbsp;"+i+"</td>");
            out.print("<td>&nbsp;&nbsp;"+rb.getBookingDate()+"</td>");
            out.print("<td>&nbsp;&nbsp;"+rb.getDaySales()+"</td></tr>");

      			i++;
        }
        out.print("</table>");
        out.print("<br><br>");

    }
    catch(Exception e){
      e.printStackTrace();
    }
		out.print("</div>");
    utility.printHtml("leftNavBar.jsp");
    utility.printHtml("footer.jsp");


  }

}
