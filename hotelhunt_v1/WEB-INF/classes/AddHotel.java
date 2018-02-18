import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@MultipartConfig(fileSizeThreshold=1024*1024*2,
maxFileSize=1024*1024*10,
maxRequestSize=1024*1024*50)

public class AddHotel extends HttpServlet {

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

    out.print("<article><hr style='width: 95%'><h2 style='font-size: 35px;'>Add Hotel </h2><hr style='width: 95%'>");

    // Add hotel
    out.print("<div><table id='bestseller'><form method='post' action='AddHotel' enctype='multipart/form-data' >");

    out.print("<tr><td>&nbsp;</td><td>Hotel Name </td><td><input type='text' name='hname' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Upload Image </td><td><input type='file' name='himage' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Address</td><td><input type='text' name='haddress' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>City</td><td><input type='text' name='hcity' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>State</td><td><input type='text' name='hstate' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Zipcode</td><td><input type='text' name='hzip' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Hotel Rating</td><td><input type='text' name='hrating' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Rate</td><td><input type='text' name='hrate' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Disability Care</td><td>"
    +"<select name='hdisability'>"
    +"<option value='yes' selected>Yes</option>"
    +"<option value='no'>No</option>"
    +"</select>"
    +" </td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Gym</td><td>"
    +"<select name='hgym'>"
    +"<option value='yes' selected>Yes</option>"
    +"<option value='no'>No</option>"
    +"</select>"
    +" </td></tr>");
    out.print("<tr><td>&nbsp;</td><td>Pool</td><td>"
    +"<select name='hpool'>"
    +"<option value='yes' selected>Yes</option>"
    +"<option value='no'>No</option>"
    +"</select>"
    +" </td></tr>");

    out.print("<tr><td>&nbsp;</td><td>Discount</td><td><input type='text' name='hdiscount' value='' required></td></tr>");
    out.print("<tr><td>&nbsp;</td><td><input type='hidden' name='option' value='add'></td><td><input type='submit' class='btn1' value='Add hotel'></td><td>&nbsp;</td></tr>");

    out.print("</form></table></div>");

    // update hotel

    HashMap<Integer, HotelBean> hotelList = new HashMap<Integer, HotelBean>();

    MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
    hotelList = sqlUtil.getHotels();


    out.print("<hr style='width: 95%'><h2 style='font-size: 35px;'>Hotel List </h2><hr style='width: 95%'>");

    out.print("<div><table id='bestseller' width='80%'>");

    out.print("<tr><td><b>Hotel Name</b></td><td><b>Address</b> </td><td> <b>City </b></td><td><b>Zipcode</b></td><td><b>Disability Care</b></td><td><b> Discount % </b></td><td><b>Action</b></td></tr>");

    for(Integer hotelId: hotelList.keySet()){
      HotelBean hotel = (HotelBean)hotelList.get(hotelId);

      out.print("<form method='post'><tr><td><input type='hidden' name='hid' value='"+hotel.getId()+"'><input type='text' name='hname' value='"+hotel.getName()+"' required></td><td><input type='text' name='haddress' value='"+hotel.getAddress()+"' required></td><td><input type='text' name='hcity' value='"+hotel.getCity()+"' required></td><td><input type='text' name='hzip' value='"+hotel.getZipcode()+"' required></td><td><input type='text' name='hdisability' value='"+hotel.isDisabilityCare()+"' required></td><td><input type='text' name='hdiscount' value='"+hotel.getDiscount()+"' required></td>"+
      "<td><input type='submit' class='btn1' name='update' value='Update' onclick='form.action=\"UpdateHotel\";'><td>"
      +"<td><input type='submit' class='btn1' name='delete' value='Delete' onclick='form.action=\"DeleteHotel\";'></td></tr></form>");
    }

    out.print("</table></div>");

    out.println("</div>");
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

      String hotelname = request.getParameter("hname");
      String address = request.getParameter("haddress");
      String city = request.getParameter("hcity");
      String state = request.getParameter("hstate");
      int zipcode = Integer.parseInt(request.getParameter("hzip"));
      float rating = Float.parseFloat(request.getParameter("hrating"));
      float rate = Float.parseFloat(request.getParameter("hrate"));
      String disability = request.getParameter("hdisability");
      String gym = request.getParameter("hgym");
      String pool = request.getParameter("hpool");
      int discount = Integer.parseInt(request.getParameter("hdiscount"));
      String himage = "";

      Part part = request.getPart("himage");
      String fileName = extractFileName(part);

      if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
        // part.write(savePath + fileName);
        part.write("C:\\apache-tomcat-7.0.34\\webapps\\hotelhunt_v1\\img\\hotel\\"+fileName);

      }

      himage = "img/hotel/"+fileName;
      utility.addHotel(hotelname,himage,address,city,state,zipcode,rating,rate,disability,gym,pool,discount);
      session.setAttribute("msg", "hotel added successfully.");

    }

    displayManagerAcc(request, response, out, utility);
  }else{
    session.setAttribute("missing", "Please login before adding any item to cart.");
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
