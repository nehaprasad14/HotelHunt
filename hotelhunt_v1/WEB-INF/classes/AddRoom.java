import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@MultipartConfig(fileSizeThreshold=1024*1024*2,
maxFileSize=1024*1024*10,
maxRequestSize=1024*1024*50)

public class AddRoom extends HttpServlet {

  protected void doGet(HttpServletRequest request,
  HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    HttpSession session=request.getSession(true);
    Utility utility = new Utility(request, out);
    LoginUtil loginUtil = new LoginUtil(request);

    if (!loginUtil.isLoggedin()) {
      response.sendRedirect("Login");
      return;
    }
    displayManagerAcc(request, response, out, utility);
  }

  protected void displayManagerAcc(HttpServletRequest request,
  HttpServletResponse response, PrintWriter out,Utility utility) throws ServletException, IOException {

    HashMap<Integer, HotelBean> hotelList = new HashMap<Integer, HotelBean>();
    HashMap<Integer, RoomBean> roomList = new HashMap<Integer, RoomBean>();

    MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
    HttpSession session = request.getSession(true);

    utility.printHtml("header.jsp");
    out.println("<style> #container1 {width: 80%;margin: 5px 10px auto auto;padding:10px;box-shadow: 5px 5px 5px 5px #CCCCCC;background-color: #fff;float:right;}</style>");
    out.println("<div id='container1'>");
    out.println("<h1 style='text-align: center;background-color:#dbd9d9;width: 96.5%;margin-left:12px;padding:10px;'>My Manager Account</h1>");
    out.println("<br/><br/>");

    if(session.getAttribute("msg")!= null){
      out.print("<h4 style='color:red'>"+session.getAttribute("msg")+"</h4>");
      session.removeAttribute("msg");
    }

    out.print("<article><hr style='width: 95%'><h2 style='font-size: 35px;'>&nbsp;&nbsp;Add Room </h2><hr style='width: 95%'>");

    // Add Room
    hotelList = sqlUtil.getHotels();
    out.print("<div><table><form method='post' action='AddRoom' enctype='multipart/form-data' >");

    out.print("<tr><td>&nbsp;</td><td>Hotel Name </td><td>"
    +"<select name='hid'>");
    for(Integer hotelId: hotelList.keySet()){
      HotelBean hotel = (HotelBean)hotelList.get(hotelId);
      out.print("<option value='"+hotel.getId()+"'>"+hotel.getName()+"</option>");
    //  out.println("<input type='hidden' name='hotelId' value='"+hotel.getId()+"'>");
    }
    out.print("</select>"
    +" </td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Room Type</td><td><input type='text' name='rtype' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Upload Image </td><td><input type='file' name='rimage' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>No. of Adults Allowed</td><td>"
    +"<select name='noofadults'>"
    +"<option value='1' selected>1</option>"
    +"<option value='2'>2</option>"
    +"<option value='3'>3</option>"
    +"<option value='4'>4</option>"
    +"<option value='5'>5</option>"
    +"</select>"
    +" </td></tr>");
    out.print("<tr><td>&nbsp;</td><td>No. of Childrens Allowed</td><td>"
    +"<select name='noofchilds'>"
    +"<option value='0' selected>0</option>"
    +"<option value='1'>1</option>"
    +"<option value='2'>2</option>"
    +"<option value='3'>3</option>"
    +"<option value='4'>4</option>"
    +"<option value='5'>5</option>"
    +"</select>"
    +" </td></tr>");

    out.print("<tr><td>&nbsp;</td><td>Rate</td><td><input type='text' name='rrate' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Quantity</td><td><input type='text' name='rquantity' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Air Condition</td><td>"
    +"<select name='ac'>"
    +"<option value='yes' selected>Yes</option>"
    +"<option value='no'>No</option>"
    +"</select>"
    +" </td></tr>");
    out.print("<tr><td>&nbsp;</td><td><input type='hidden' name='option' value='add'></td><td><input type='submit' class='btn1' value='Add Room'></td><td>&nbsp;</td></tr>");

    out.print("</form></table></div>");

    // update Room

    out.print("<hr style='width: 95%'><h2 style='font-size: 35px;'>&nbsp;&nbsp;Room List </h2><hr style='width: 95%'>");

    hotelList = sqlUtil.getHotels();
    roomList = sqlUtil.getRooms();
/*
    out.print("<table><tr><td>&nbsp;</td><td>Select Hotel Name </td><td>"
    +"&nbsp;<select id='hname' name='hname'  onchange='doRoomSearch()'>");
    out.print("<option value='0'>Select Hotel</option>");
    for(Integer hotelId: hotelList.keySet()){
      HotelBean hotel = (HotelBean)hotelList.get(hotelId);
      out.print("<option value='"+hotel.getId()+"'>"+hotel.getName()+"</option>");
    //  out.println("<input type='hidden' name='hotelId' value='"+hotel.getId()+"'>");
    }
    out.println("</select>"+" </td></tr></table>");
    out.println("<br><br>");

    out.println("<div id='room-list'>");
    */
//***********************************************************

out.print("<table width='80%'>");
out.println("<tr><td><b>Hotel Name</b></td><td><b>Room Type</b></td><td> <b>Room Price </b></td><td><b>Room Quantity</b></td><td><b>AC.</b></td><td><b>Action</b></td></tr>");

for(Integer roomId: roomList.keySet()){
    RoomBean room = (RoomBean)roomList.get(roomId);
  //  if(room.getHotelId() == hID){
            String hotelName = sqlUtil.getHotelName(room.getHotelId());
            out.println("<form method='post'>");
            out.println("<tr>");
            out.print("<td>");
            out.print("<input type='hidden' name='hid' value='"+room.getHotelId()+"'>");
            out.print("<input type='hidden' name='rid' value='"+room.getId()+"'>");
            out.print("<input type='text' name='hname' value='"+hotelName+"' readonly></td>");
            out.print("<td><input type='text' name='rtype' value='"+room.getRoomType()+"' required></td>");
            out.print("<td><input type='text' name='rprice' value='"+room.getRoomrate()+"' required></td>");
            out.print("<td><input type='text' name='rquantity' value='"+room.getQuantity()+"' required></td>");
            out.print("<td><input type='text' name='rac' value='"+room.isAc()+"'required></td>");
            out.print("<td><input type='submit' class='btn1' name='update' value='Update' onclick='form.action=\"UpdateRoom\";'></td>");
            out.print("<td><input type='submit' class='btn1' name='delete' value='Delete' onclick='form.action=\"DeleteRoom\";'>");
            out.print("</td>");
            out.println("</tr>");
            out.println("</form>");
  //  }
}
out.print("</table>");

//************************************************************

    out.println("</div></div>");
    utility.printHtml("leftNavBar.jsp");
    utility.printHtml("footer.jsp");
  }


protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  response.setContentType("text/html");
  PrintWriter out = response.getWriter();
  HttpSession session=request.getSession(true);
  LoginUtil loginUtil = new LoginUtil(request);
  Utility utility = new Utility(request, out);

  String username = "";
  if(loginUtil.isLoggedin() && session.getAttribute("user")!= null){
    User user = (User)session.getAttribute("user");
    username = user.getUsername();


    String opt = request.getParameter("option");
    opt.trim();

    if(opt!=null && opt.equals("add")){

      int hotelId = Integer.parseInt(request.getParameter("hid"));
      String roomType = request.getParameter("rtype");
      int noofadults = Integer.parseInt(request.getParameter("noofadults"));
      int noofchilds = Integer.parseInt(request.getParameter("noofchilds"));
      float roomRate = Float.parseFloat(request.getParameter("rrate"));
      int roomQuantity = Integer.parseInt(request.getParameter("rquantity"));
      String ac = request.getParameter("ac");
      String rimage = "";

      Part part = request.getPart("rimage");
      String fileName = extractFileName(part);

      if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
        // part.write(savePath + fileName);
        part.write("C:\\apache-tomcat-7.0.34\\webapps\\hotelhunt_v1\\img\\hotel\\rooms\\"+fileName);

      }

      rimage = "img/hotel/rooms/"+fileName;
      utility.addRoom(hotelId,rimage,roomType,noofadults,noofchilds,roomRate,roomQuantity,ac);
      session.setAttribute("msg", "Room added successfully.");

    }

    displayManagerAcc(request, response, out, utility);
  }else{
    session.setAttribute("missing", "Please login before adding any Room.");
    response.sendRedirect("Login");
  }
}

private String extractFileName(Part part) {
  String contentDisp = part.getHeader("content-disposition");
  String[] items = contentDisp.split(";");
  for (String s : items) {
    if (s.trim().startsWith("filename"))
    return s.substring(s.indexOf("=") + 2, s.length() - 1);
  }
  return "";
}

}
