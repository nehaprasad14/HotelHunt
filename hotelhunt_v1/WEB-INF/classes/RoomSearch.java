import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RoomSearch extends HttpServlet {
      AjaxUtility aUtil = new AjaxUtility();

      public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
          String action = request.getParameter("action");

          if (action.equals("roomlookup")) {
            String sId = request.getParameter("searchId");
          //  System.out.println("citylookup:: "+ sId);
              if (sId != null) {
  				         request.setAttribute("hotelId", sId);

                   doPost(request,response);
              }
          }
       }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            Utility utility = new Utility(request,out);
            MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
            HashMap<Integer, RoomBean> roomList = sqlUtil.getRooms();
            int hID = Integer.parseInt(request.getAttribute("hotelId")+"");

            out.print("<table width='80%'>");
            out.println("<tr><td><b>Room Type</b></td><td> <b>Room Price </b></td><td><b>Room Quantity</b></td><td><b>AC.</b></td><td><b>Action</b></td></tr>");

            for(Integer roomId: roomList.keySet()){
                RoomBean room = (RoomBean)roomList.get(roomId);
                if(room.getHotelId() == hID){
                        out.println("<form method='post'>");
                        out.println("<tr>");
                        out.print("<td>");
                        out.print("<input type='hidden' name='hid' value='"+room.getHotelId()+"'>");
                        out.print("<input type='hidden' name='rid' value='"+room.getId()+"'>");
                        out.print("<input type='text' name='rtype' value='"+room.getRoomType()+"' required></td>");
                        out.print("<td><input type='text' name='rprice' value='"+room.getRoomrate()+"' required></td>");
                        out.print("<td><input type='text' name='rquantity' value='"+room.getQuantity()+"' required></td>");
                        out.print("<td><input type='text' name='rac' value='"+room.isAc()+"'required></td>");
                        out.print("<td><input type='submit' class='btn1' name='update' value='Update' onclick='form.action=\"UpdateRoom\";'>");
                        out.print("<input type='submit' class='btn1' name='delete' value='Delete' onclick='form.action=\"DeleteRoom\";'>");
                        out.print("</td>");
                        out.println("</tr>");
                        out.println("</form>");
                }
            }
            out.print("</table>");
        }
}
