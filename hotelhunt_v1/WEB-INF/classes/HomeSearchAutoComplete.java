import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HomeSearchAutoComplete extends HttpServlet {
      AjaxUtility aUtil = new AjaxUtility();

      public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
          String action = request.getParameter("action");
          String searchId = request.getParameter("id");

          try{
                StringBuffer sb = new StringBuffer();
                boolean namesAdded = false;
                if(action.equals("complete")){
                  if (!searchId.equals("")){
                      sb = aUtil.readAnyData(searchId);
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
        }  
}
