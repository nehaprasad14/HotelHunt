import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.*;
import java.util.*;
import javax.servlet.http.*;

public class Utility {
	HttpServletRequest req;
	PrintWriter out;
	String url;
	HttpSession session;
	HashMap<Integer, HotelBean> hotelhm;
	HashMap<Integer, RoomBean> roomhm;

	public static HashMap<String, ArrayList<OrderItemBean>> orders = new HashMap<String, ArrayList<OrderItemBean>>();
	public static HashMap<String, ArrayList<BillingOrderBean>> billedOrders = new HashMap<String, ArrayList<BillingOrderBean>>();

	public Utility(HttpServletRequest req, PrintWriter out) {
		this.req = req;
		this.out = out;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}

	public void printHtml(String file) {
		String result = HtmlToString(file);
		if (file == "header.jsp") {
			if(session.getAttribute("user")!= null){
				 User user = (User)session.getAttribute("user");
					String username = user.getUsername();
					String role = user.getRole();
					result = result
									//+ "<li ><a>Hello, &nbsp;"+username+" </a></li>"
									//+ "<li><a href='Account'>My Account</a></li>"
									+ "<li><a href='Cart'><i class='fa fa-shopping-cart fa-lg' ></i> Cart("+CartCount()+")</a></li>"
									+"<style>li.dropdown11 {display: inline-block !important;}.dropdown11-content {display: none !important;background-color: black;position: absolute !important;min-width: 160px;box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);z-index: 2000 !important;}.dropdown11-content a {padding: 12px 16px;text-decoration: none;display: block !important;text-align: left;}.dropdown11:hover .dropdown11-content {display: block !important;}</style>"
									+ "<li class='dropdown11'>"
									+ " <a href='javascript:void(0)' class='dropbtn'>Hello, &nbsp;"+username+"</a>"
									+ "<div class='dropdown11-content'>"
									    + "<a href='Account'>My Account</a>"
									    + "<a href='Logout'>Logout</a>"
									 + "  </div>"
									+ "  </li>"

									+ "</ul></nav></div></div></div></div>";
			}else{
								result = result +"<li><a href='Login'>Login</a></li>"
													+ "</ul></nav></div></div></div></div>";
			 }
        out.print(result);
		} else if (file == "content.jsp"){
			    out.print(result);
					displayTopCity();
					displayTopHotels();
					displayTopPackages();
    } else {
		     	out.print(result);
    }
	}


	public void displayTopCity(){
					String hotelStr="";
					MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
					HashMap<String, Integer> cityList = sqlUtil.selectHotelsByTopCity();
					String imgsrc = "";
					boolean alt = false;
					if(cityList.size() != 0){
							for(String city: cityList.keySet()){
								if(city.equalsIgnoreCase("Chicago")){
										imgsrc = "img/home/chicago.jpg";
								}else if(city.equalsIgnoreCase("New York")){
										imgsrc = "img/home/newyork.jpg";
								}else if(city.equalsIgnoreCase("Seattle")){
										imgsrc = "img/home/seattle.jpg";
								}else if(city.equalsIgnoreCase("Los Angeles")){
									  imgsrc = "img/home/losAngeles.jpg";
								}else if(city.equalsIgnoreCase("Pittsburgh")){
										imgsrc = "img/home/pittsburgh.jpg";
								}

								int count = cityList.get(city);
								hotelStr = hotelStr + "<div class='col-lg-4 col-md-4 col-sm-6'>"
						        +" <div class='tm-home-box-1 tm-home-box-1-2 tm-home-box-1-center'>"
						         +"  <img src='"+imgsrc+"' alt='image' class='img-responsive' style='max-width: 100%; max-height: 80%;'>"
						          +" <a href='AutoComplete?action=topcitylookup&searchId=" + city +"'>";
											if(!alt){
						            hotelStr = hotelStr +" <div class='tm-green-gradient-bg tm-city-price-container'>";
												alt = true;
											}else{
												hotelStr = hotelStr +" <div class='tm-red-gradient-bg tm-city-price-container'>";
											}

						          hotelStr = hotelStr +"   <span>"+city+"</span>"
						            +"   <span>"+count+" Bookings</span>"
						            +" </div>"
						           +"</a>"
						         +"</div>"
						       +"</div>";
							}
					}

					hotelStr = hotelStr + "</div></div>";
					//result = result + hotelStr;
					out.print(hotelStr);
			}

			public void displayTopHotels(){
							String hotelStr="";
							MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
							ArrayList<Integer> hotelIDs = sqlUtil.selectHotelsByTopRatings();
							 HashMap<Integer, HotelBean> hotelList = new HashMap<Integer, HotelBean>();
							 hotelList = sqlUtil.getHotels();
							   HotelBean hotel = null;
							hotelStr = hotelStr + "<div class='section-margin-top'>"
								+"<div class='row'>"
									+"<div class='tm-section-header'>"
										+"<div class='col-lg-3 col-md-3 col-sm-3'>"
											+"<hr>"
										+"</div>"
										+"<div class='col-lg-6 col-md-6 col-sm-6'>"
											+"<h2 class='tm-section-title'>Popular Hotels in US</h2></div>"
										+"<div class='col-lg-3 col-md-3 col-sm-3'>"
											+"<hr>"
										+"</div>"
									+"</div>"
								+"</div>";

								for(Integer hotelID: hotelIDs){
				             hotel = (HotelBean)hotelList.get(hotelID);

				                hotelStr = hotelStr + "<div class='col-lg-3 col-md-3 col-sm-6 col-xs-6 col-xxs-12' style='padding-top: 15px;'>"
				                +"<div class='tm-home-box-2' style='background-color: #f7f7f7;'>"
				                +"  <img src='"+hotel.getHotelimage()+"' width='100%' height='200px'>"
				                +"    <h3 ><b>"+hotel.getName()+"</b> <br><br>";

				                for(int j=0 ; j< hotel.getHotelrating() ; j++)
				                    hotelStr = hotelStr +"<i class='fa fa-star' style='color:red'></i>";

				                hotelStr = hotelStr +"    </h3>"
				                +"  <span class='tm-home-box-3-description'><img src='img/home/location.jpg' alt='image' width='15%'>"+hotel.getCity()+" - "+hotel.getZipcode()+"</span>"
				                +"<div class='tm-home-box-3-container'>"
												+"<form method='get' action='DisplayRooms'>"
				                +"<a href='#' class='tm-home-box-2-link' style='pointer-events: none; cursor: default;'><span class='tm-home-box-2-description border-right border-bottom border-top border-left'><br><b>$"+hotel.getLowrate()+"</b></span></a>"
												+"<input type='hidden' name='hotelID' value='"+hotelID+"'>"
												+"<input type='submit' class='tm-home-box-2-link' style='text-transform: uppercase;background-color: #f7f7f7;padding: 19px 16px;border: 1px solid #7F7F7F;;font-weight: 700;transition: all 0.3s ease;' value='View Rooms'>"
												+"</form>"

												+"<a href='WriteReview?hname="+hotel.getName()+"&hid="+hotel.getId()+"&hcity="+hotel.getCity()+"&hzip="+hotel.getZipcode()+"' class='tm-home-box-2-link'><span class='tm-home-box-2-description border-right border-bottom border-top border-left'><br><b>Write Review</b></span></a>"
				                +"<a href='ViewReview?hname="+hotel.getName()+"&hid="+hotel.getId()+"' class='tm-home-box-2-link' ><span class='tm-home-box-2-description border-right border-bottom border-top border-left'><br><b>View Review</b></span></a>"

												+"</div>"
				                +"</div>"
				                +"</div>";
				        }

								hotelStr = hotelStr +"</div>"+"</div>"+"</section>";
						//result = result + hotelStr;
						out.print(hotelStr);

					}

			public void displayTopPackages() {
					out.println("<section class='tm-white-bg section-padding-bottom'>");
					out.println("<div class='container'>");
					out.println("<div class='row'>");
					out.println("<div class='tm-section-header section-margin-top'>");
						out.println("<div class='col-lg-4 col-md-3 col-sm-3'>");
							out.println("<hr>");
						out.println("</div>");
						out.println("<div class='col-lg-4 col-md-6 col-sm-6'>");
							out.println("<h2 class='tm-section-title'>Our Popular Packages</h2></div>");
						out.println("<div class='col-lg-4 col-md-3 col-sm-3'>");
							out.println("<hr>");
						out.println("</div>");
					out.println("</div>");

					MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();
					 HashMap<Integer, HotelBean> hotelList = new HashMap<Integer, HotelBean>();
					 hotelList = sqlUtil.getHotels();
					 HotelBean hotel = null;
					 int i=1;
					for(Integer hotelID: hotelList.keySet()){
					 hotel = (HotelBean)hotelList.get(hotelID);
							if(hotel.isPackages().equalsIgnoreCase("Yes") && i<=4){
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
							i++;
				  }
			  }
					out.println("</div>");
				out.println("</div>");
			out.println("</section>");
			}

			public String HtmlToString(String file) {
				String result = null;
				try {
					String webPage = url + file;
					URL url = new URL(webPage);
					URLConnection urlConnection = url.openConnection();
					InputStream is = urlConnection.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);

					int numCharsRead;
					char[] charArray = new char[1024];
					StringBuffer sb = new StringBuffer();
					while ((numCharsRead = isr.read(charArray)) > 0) {
						sb.append(charArray, 0, numCharsRead);
					}
					result = sb.toString();
				} catch (Exception e) {
				}
				return result;
			}

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	public void storeBooking(int hotelId, String hotelName, String  hotelImage, int roomId, String roomType, String roomImage, int roomQuantity, double totalPrice,  String checkInDate, String checkOutDate){

		OrderItemBean OrderItem = new OrderItemBean(hotelId, hotelName, hotelImage, roomId, roomType, roomImage, totalPrice, roomQuantity, checkInDate, checkOutDate);
		if(!orders.containsKey((String)session.getAttribute("username"))){
			ArrayList<OrderItemBean> arr = new ArrayList<OrderItemBean>();
			orders.put((String)session.getAttribute("username"), arr);
		}

		ArrayList<OrderItemBean> orderItems = orders.get((String)session.getAttribute("username"));
		orderItems.add(OrderItem);
	}

	public ArrayList<OrderItemBean> getCustomerOrders(){
		ArrayList<OrderItemBean> order = new ArrayList<OrderItemBean>();
		if(orders.containsKey((String)session.getAttribute("username")))
		order= orders.get((String)session.getAttribute("username"));
		return order;
	}

	public boolean checkLoggedIn(){
		if (session.getAttribute("username")==null)
		return false;
		return true;
	}

	public int CartCount(){
		if(checkLoggedIn())
		return getCustomerOrders().size();
		return 0;
	}

		public void removeBooking(String hotelName, String roomType){

		ArrayList<OrderItemBean> orderItems = orders.get((String)session.getAttribute("username"));
		if(!orderItems.isEmpty()){
			Iterator<OrderItemBean> itr = orderItems.iterator();
			while(itr.hasNext()){
				OrderItemBean oi = itr.next();
				if(hotelName.equals(oi.getHotelName()) && roomType.equals(oi.getRoomType()))
				itr.remove();
			}
		}
	}

		public int storeBilledBooking(String username, String fullname, String email, double total, String creditcardNo, int cvv, String address1, String address2, String zipcode, String bookingDate, ArrayList<OrderItemBean> orderItems){

		BillingOrderBean  SalesOrder = new BillingOrderBean(username, fullname, email, total, creditcardNo, cvv, address1, address2, zipcode, bookingDate, orderItems);
		int orderNum = 0;
		MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
		//retrieve billed orders from db
		try{
			billedOrders = sqdb.getBilledOrders(username);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		//check if that user has any existing orders
		if(!billedOrders.containsKey(username)){
			ArrayList<BillingOrderBean> arr = new ArrayList<BillingOrderBean>();
			billedOrders.put(username, arr);
		}

		//Insert into db
		try{
			orderNum = sqdb.insertBilledOrder(SalesOrder);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		// Add orders in HashMap
		SalesOrder.setOrderNum(orderNum);
		ArrayList<BillingOrderBean> salesOrders = billedOrders.get(username);
		salesOrders.add(SalesOrder);

		return orderNum;

	}

	public ArrayList<BillingOrderBean> getBilledOrders(){

	MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
	ArrayList<BillingOrderBean> salesBookings = new ArrayList<BillingOrderBean>();
	try{
		billedOrders = sqdb.getBilledOrders((String)session.getAttribute("username"));
	}
	catch(Exception e){
		e.printStackTrace();
	}
	if(billedOrders.containsKey((String)session.getAttribute("username")))
	salesBookings = billedOrders.get((String)session.getAttribute("username"));
	return salesBookings;

}

public ArrayList<BillingOrderBean> getAllCustomersBookings(){
		MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
		try{
			billedOrders = sqdb.getBilledOrders();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		ArrayList<BillingOrderBean> salesOrders = new ArrayList<BillingOrderBean>();
		ArrayList<BillingOrderBean> allSalesOrders = new ArrayList<BillingOrderBean>();
		//	System.out.println("key before ###### "+billedOrders.size());
		for(String key : billedOrders.keySet()){
			//		System.out.println("key ###### "+ key);
			if(billedOrders.containsKey(key)){
				salesOrders = billedOrders.get(key);
				for(BillingOrderBean bo : salesOrders)
				allSalesOrders.add(bo);
			}
		}
		return allSalesOrders;
	}

public void deleteBooking(String userName, int bookingNum){
		MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
		//	System.out.println("#########Delete order helper username : " + userName);
		//	System.out.println("#########Delete order helper orderno : " + orderNum);
		try{
			sqdb.deleteBilledBooking(userName, bookingNum);
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	public double getCartTotal(){
		double total = 0;
		for (OrderItemBean oi : getCustomerOrders()) {
			total = total + oi.getTotalPrice();
		}
		return total;
	}

	public void clearCart(String username){

		ArrayList<OrderItemBean> orderItems = orders.get((String)session.getAttribute("username"));
		if(!orderItems.isEmpty()){
			orderItems.clear();
		}
	}

	public String currentDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date).toString();
	}

	public void addHotel(String hotelname,String himage,String address,String city,String state,int zipcode,float rating,float rate,String disability,String gym,String pool,int discount){
		SaxParserXMLUtility sp = new SaxParserXMLUtility();
		MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
		hotelhm = sp.getHotelMap();
		Integer id = hotelhm.size()+1;

		try{
			sqdb.addHotel(hotelname,himage,address,city,state,zipcode,rating,rate,disability,gym,pool,discount);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		HotelBean hotel = new HotelBean(id, hotelname,himage,address,city,state,zipcode,rating,rate,0,"no",disability,"no","no","no","no",gym,pool,discount);
		hotelhm.put(id, hotel);
	}

	public void updateHotel(int hotelId,String name,String address,String city,int zipcode,String disability,int discount){
		MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
		System.out.println("####### helper hotel id : "+ hotelId);
		try{
			sqdb.updateHotel(hotelId,name,address,city,zipcode,disability,discount);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void deleteHotel(int hotelId){
		SaxParserXMLUtility sp = new SaxParserXMLUtility();
		MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
		hotelhm = sp.getHotelMap();
		if(hotelhm.containsKey(hotelId)){
			HotelBean hotel = hotelhm.get(hotelId);
			sqdb.deleteHotel(hotelId);
			if(hotel!=null){
				hotelhm.remove(hotelId);
			}
		}

	}

	//rooms

	public void addRoom(int hotelId,String rimage,String roomType,int noofadults,int noofchilds,float roomRate,int roomQuantity,String ac){
		SaxParserXMLUtility sp = new SaxParserXMLUtility();
		MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
		roomhm = sp.getRoomMap();
		Integer roomId = roomhm.size()+1001;

		try{
			sqdb.addRoom(roomId,hotelId,rimage,roomType,noofadults,noofchilds,roomRate,roomQuantity,ac);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		RoomBean room = new RoomBean(roomId,hotelId,noofadults,noofchilds,rimage,roomRate,roomQuantity,ac,roomType);
		roomhm.put(roomId, room);
	}

	public void updateRoom(int hotelId,int roomId,String roomType,float roomRate,int roomQuantity,String ac){
		MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
		//System.out.println("####### helper hotel id : "+ hotelId);
		//System.out.println("####### helper room id : "+ roomId);
		try{
			sqdb.updateRoom(hotelId,roomId,roomType,roomRate,roomQuantity,ac);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void deleteRoom(int roomId){
			SaxParserXMLUtility sp = new SaxParserXMLUtility();
			MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
			roomhm = sp.getRoomMap();
			if(roomhm.containsKey(roomId)){
				RoomBean room = roomhm.get(roomId);
				sqdb.deleteRoom(roomId);
				if(room!=null){
					roomhm.remove(roomId);
				}
			}
	}

	public HashMap<Integer, HotelBean> searchHotels(String homeSearch,String checkin, String checkout, int roomNum, int numOfAdults,int numOfChildren){
					HashMap<Integer, HotelBean> hotelList = new HashMap<Integer, HotelBean>();
					HashMap<Integer, HotelBean> newhotelList = new HashMap<Integer, HotelBean>();
					MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();

					hotelList = sqlUtil.getHotels();
					HashMap<Integer, RoomBean> roomList = sqlUtil.getRooms();
					Iterator it = hotelList.entrySet().iterator();

					while(it.hasNext()){
							 Map.Entry hotelEntry = (Map.Entry)it.next();
							 HotelBean hotel = (HotelBean)hotelEntry.getValue();
							 String zipcode = hotel.getZipcode()+"";
							 String city = hotel.getCity();

							 if((hotel.getName().toLowerCase().startsWith(homeSearch.toLowerCase())) || (zipcode.toLowerCase().startsWith(homeSearch.toLowerCase())) || (city.toLowerCase().startsWith(homeSearch.toLowerCase()))){
								 for(Integer roomId: roomList.keySet()){
										 RoomBean room = (RoomBean)roomList.get(roomId);
										 if(room.getHotelId() == hotel.getId() && room.getQuantity() >= roomNum){
											 				newhotelList.put(hotel.getId(),hotel);
										 }
									 }
							 }

					 }
					 return newhotelList;
	  }

}
