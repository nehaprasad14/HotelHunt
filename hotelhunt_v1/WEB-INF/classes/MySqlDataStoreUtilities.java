import java.sql.*;
import java.util.*;

public class MySqlDataStoreUtilities{
  Connection conn = null;
  HotelBean hotelBean;
  RoomBean roomBean;

  public Connection getConnection() {
    try{
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelhuntdb?useSSL=false","root","root");
    }catch(Exception e){
      e.printStackTrace();
    }
    return conn;
  }

  public void closeConnection(){
    try{
      conn.close();
    }
    catch (SQLException se){
      se.printStackTrace();
    }
  }

  public void insert_hotels_rooms(HashMap<Integer, HotelBean> hotelCatalog, HashMap<Integer, RoomBean> roomCatalog){
    int currHotelId = 0;
    try{
      conn = getConnection();

      Set keys = hotelCatalog.keySet();
      for(Object hotelId : keys){
        HotelBean h = hotelCatalog.get(hotelId);

        String insertHotelQuery = "insert into hotels (hotelName, hotelImage, hotelAddress, hotelCity, hotelState, hotelZipcode, hotelRating, hotelHighrate, hotelLowrate, hotelPromo, hotelDisabilityCare, hotelPackages, hotelCarrental, hotelFlight, hotelLocaltour, hotelGym, hotelPool, hotelDiscount) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(insertHotelQuery);
        ps.setString(1,h.getName());
        ps.setString(2,h.getHotelimage());
        ps.setString(3,h.getAddress());
        ps.setString(4,h.getCity());
        ps.setString(5,h.getState());
        ps.setInt(6,h.getZipcode());
        ps.setFloat(7,h.getHotelrating());
        ps.setFloat(8,h.getHighrate());
        ps.setFloat(9,h.getLowrate());
        ps.setString(10,h.isPromo());
        ps.setString(11,h.isDisabilityCare());
        ps.setString(12,h.isPackages());
        ps.setString(13,h.isCarRental());
        ps.setString(14,h.isFlight());
        ps.setString(15,h.isLocalTour());
        ps.setString(16,h.isGym());
        ps.setString(17,h.isPool());
        ps.setInt(18,h.getDiscount());
        ps.execute();
        ps.close();
        // get hotelId for the last inserted hotel
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select hotelId from hotels where hotelName = '"+h.getName()+"' order by hotelId desc limit 1;");

        while(rs.next()){
          currHotelId = rs.getInt("hotelId");
        }
        rs.close();
        //System.out.println("Hotel ID :: "+currHotelId);
        //Insert rooms for current hotel
        for(String rooms : h.getRooms()){
          RoomBean room = roomCatalog.get(Integer.parseInt(rooms));
          if(rooms.equals(room.getId()+"")){
            String insertRoomsQuery = "insert into rooms (roomId, hotelId, roomType, numberOfAdults, numberOfChildren, roomImage, roomRate, roomQuantity, roomAc) values (?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps1 = conn.prepareStatement(insertRoomsQuery);
            ps1.setInt(1,room.getId());
            ps1.setInt(2,currHotelId);
            ps1.setString(3,room.getRoomType());
            ps1.setInt(4,room.getNumberOfAdults());
            ps1.setInt(5,room.getNumberOfChildren());
            ps1.setString(6,room.getRoomimage());
            ps1.setFloat(7,room.getRoomrate());
            ps1.setInt(8,room.getQuantity());
            ps1.setString(9,room.isAc());
            ps1.execute();
            ps1.close();
          }
        }
      }
      conn.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public void dbCleanup(){
    try{
      conn = getConnection();
      String truncateHotelQuery = "truncate table hotels;";
      String truncateRoomQuery = "truncate table rooms;";

      PreparedStatement ps = conn.prepareStatement(truncateHotelQuery);
      ps.execute();

      PreparedStatement ps1 = conn.prepareStatement(truncateRoomQuery);
      ps1.execute();

      conn.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public HashMap<Integer, HotelBean> getHotels(){
    HashMap<Integer, HotelBean> hotelhm = new HashMap<Integer, HotelBean>();
    try{
      conn = getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from hotels;");

      while(rs.next()){
        hotelBean = new HotelBean();
        hotelBean.setId(rs.getInt("hotelId"));
        hotelBean.setName(rs.getString("hotelName"));
        hotelBean.setHotelimage(rs.getString("hotelImage"));
        hotelBean.setAddress(rs.getString("hotelAddress"));
        hotelBean.setCity(rs.getString("hotelCity"));
        hotelBean.setState(rs.getString("hotelState"));
        hotelBean.setZipcode(rs.getInt("hotelZipcode"));
        hotelBean.setHotelrating(rs.getFloat("hotelRating"));
        hotelBean.setHighrate(rs.getFloat("hotelHighrate"));
        hotelBean.setLowrate(rs.getFloat("hotelLowrate"));
        hotelBean.setPromo(rs.getString("hotelPromo"));
        hotelBean.setDisabilityCare(rs.getString("hotelDisabilityCare"));
        hotelBean.setPackages(rs.getString("hotelPackages"));
        hotelBean.setCarRental(rs.getString("hotelCarrental"));
        hotelBean.setFlight(rs.getString("hotelFlight"));
        hotelBean.setLocalTour(rs.getString("hotelLocaltour"));
        hotelBean.setGym(rs.getString("hotelGym"));
        hotelBean.setPool(rs.getString("hotelPool"));
        hotelBean.setDiscount(rs.getInt("hotelDiscount"));

        // Retrieve rooms for each hotel.
        Statement stmt1 = conn.createStatement();
        ResultSet rs1 = stmt1.executeQuery("select * from rooms where rooms.hotelId = '"+ hotelBean.getId()+"'");
        ArrayList<String> roomIdList = new ArrayList<String>();
        while(rs1.next()){
          roomIdList.add(rs1.getInt("roomId")+"");
        }
        stmt1.close();
        rs1.close();

        hotelBean.setRooms(roomIdList);
        hotelhm.put(hotelBean.getId(), hotelBean);
      }
      rs.close();
      conn.close();
      return hotelhm;
    }catch(Exception e){
      e.printStackTrace();
    }
    return hotelhm;
  }


  public HashMap<Integer, RoomBean> getRooms(){
    HashMap<Integer, RoomBean> roomhm = new HashMap<Integer, RoomBean>();
    try{
      conn = getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select roomId, hotelId, roomType, numberOfAdults, numberOfChildren, roomImage, roomRate, roomQuantity, roomAc from rooms group by roomId");

      while(rs.next()){
        RoomBean roomBean = new RoomBean();
        roomBean.setId(rs.getInt("roomId"));
        roomBean.setHotelId(rs.getInt("hotelId"));
        roomBean.setRoomType(rs.getString("roomType"));
        roomBean.setNumberOfAdults(rs.getInt("numberOfAdults"));
        roomBean.setNumberOfChildren(rs.getInt("numberOfChildren"));
        roomBean.setRoomimage(rs.getString("roomImage"));
        roomBean.setRoomrate(rs.getFloat("roomRate"));
        roomBean.setQuantity(rs.getInt("roomQuantity"));
        roomBean.setAc(rs.getString("roomAc"));
        roomhm.put(roomBean.getId(), roomBean);
      }
      rs.close();
      conn.close();
      return roomhm;
    }catch(Exception e){
      e.printStackTrace();
    }
    return roomhm;
  }

  public User getUser(final String username) throws ClassNotFoundException,SQLException{
    User user = null;
    try{
      conn = getConnection();
      conn.setAutoCommit(true);
      Statement stmt=conn.createStatement();
      ResultSet rs=stmt.executeQuery("Select * from registration where username='"+username+"'");
      while(rs.next()){
        user=new User();
        user.setUid(rs.getInt("userid"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
      }
      rs.close();
      stmt.close();
    }catch(SQLException exception){
      exception.printStackTrace();
    }
    finally{
      closeConnection();
    }
    return user;
  }

  public int addUser(User user) throws ClassNotFoundException,SQLException{
    conn = getConnection();
    PreparedStatement ps1=null;
    String command1=null;
    int result1=0;

    if(ps1==null){
      try{
        command1 = "insert into registration(username,email,password,role) values(?,?,?,?)";

        ps1=conn.prepareStatement(command1);

        ps1.setString(1, user.getUsername());
        ps1.setString(2, user.getEmail());
        ps1.setString(3, user.getPassword());
        ps1.setString(4, user.getRole());

        result1=ps1.executeUpdate();
        ps1.close();
      }
      catch (NullPointerException e){
        e.printStackTrace();
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
      finally{
        closeConnection();
      }
    }
    return result1;
  }

  public HashMap<String, ArrayList<BillingOrderBean>> getBilledOrders(String username){

    BillingOrderBean bo = new BillingOrderBean();
    HashMap<String, ArrayList<BillingOrderBean>> billedOrderhm = new HashMap<String, ArrayList<BillingOrderBean>>();

    try{
      conn = getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from reservation res where res.userName = '"+ username+"'");
      ArrayList<BillingOrderBean> billedOrderList = new ArrayList<BillingOrderBean>();

      while(rs.next()){
        bo = new BillingOrderBean();
        bo.setOrderNum(rs.getInt("bookingNum"));
        bo.setUsername(rs.getString("userName"));
        bo.setFullname(rs.getString("fullName"));
        bo.setEmail(rs.getString("email"));
        bo.setTotal(rs.getDouble("totalBill"));
        bo.setCreditcardNo(rs.getString("creditCardNo"));
        bo.setCvv(rs.getInt("cvv"));
        bo.setAddress1(rs.getString("address1"));
        bo.setAddress2(rs.getString("address2"));
        bo.setZipcode(rs.getString("zipcode"));
        bo.setBookingDate(rs.getString("bookingDate"));


        // Retrieve orderItems for each billed order.
        Statement stmt1 = conn.createStatement();
        ResultSet rs1 = stmt1.executeQuery("select * from bookingdetails oi where oi.bookingNum = '"+ bo.getOrderNum()+"'");
        ArrayList<OrderItemBean> itemList = new ArrayList<OrderItemBean>();
        while(rs1.next()){
          OrderItemBean oib = new OrderItemBean();
          oib.setHotelId(rs1.getInt("hotelId"));
          oib.setHotelName(rs1.getString("hotelName"));
          oib.setHotelImage(rs1.getString("hotelImage"));
          oib.setRoomId(rs1.getInt("roomId"));
          oib.setRoomType(rs1.getString("roomType"));
          oib.setRoomImage(rs1.getString("roomImage"));
          oib.setTotalPrice(rs1.getDouble("totalPrice"));
          oib.setRoomQuantity(rs1.getInt("roomQuantity"));
          oib.setCheckInDate(rs1.getString("checkInDate"));
          oib.setCheckOutDate(rs1.getString("checkOutDate"));
          itemList.add(oib);
        }
        stmt1.close();
        rs1.close();
        bo.setOrderItems(itemList);
        billedOrderList.add(bo);
      }
      billedOrderhm.put(username,billedOrderList);
      rs.close();
      conn.close();
      return billedOrderhm;
    }catch(Exception e){
      e.printStackTrace();
    }
    return billedOrderhm;
  }

  public HashMap<String, ArrayList<BillingOrderBean>> getBilledOrders(){

      BillingOrderBean bo = new BillingOrderBean();
      HashMap<String, ArrayList<BillingOrderBean>> billedOrderhm = new HashMap<String, ArrayList<BillingOrderBean>>();

      try{
        conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from reservation ");
        ArrayList<BillingOrderBean> billedOrderList = new ArrayList<BillingOrderBean>();
        String username = "";

        while(rs.next()){
          bo = new BillingOrderBean();
          bo.setOrderNum(rs.getInt("bookingNum"));
          bo.setUsername(rs.getString("userName"));
          username = bo.getUsername();
          bo.setFullname(rs.getString("fullName"));
          bo.setEmail(rs.getString("email"));
          bo.setTotal(rs.getDouble("totalBill"));
          bo.setCreditcardNo(rs.getString("creditCardNo"));
          bo.setCvv(rs.getInt("cvv"));
          bo.setAddress1(rs.getString("address1"));
          bo.setAddress2(rs.getString("address2"));
          bo.setZipcode(rs.getString("zipcode"));
          bo.setBookingDate(rs.getString("bookingDate"));


          // Retrieve orderItems for each billed order.
          Statement stmt1 = conn.createStatement();
          ResultSet rs1 = stmt1.executeQuery("select * from bookingdetails oi where oi.bookingNum = '"+ bo.getOrderNum()+"'");
          ArrayList<OrderItemBean> itemList = new ArrayList<OrderItemBean>();
          while(rs1.next()){
            OrderItemBean oib = new OrderItemBean();
            oib.setHotelId(rs1.getInt("hotelId"));
            oib.setHotelName(rs1.getString("hotelName"));
            oib.setHotelImage(rs1.getString("hotelImage"));
            oib.setRoomId(rs1.getInt("roomId"));
            oib.setRoomType(rs1.getString("roomType"));
            oib.setRoomImage(rs1.getString("roomImage"));
            oib.setTotalPrice(rs1.getDouble("totalPrice"));
            oib.setRoomQuantity(rs1.getInt("roomQuantity"));
            oib.setCheckInDate(rs1.getString("checkInDate"));
            oib.setCheckOutDate(rs1.getString("checkOutDate"));
            itemList.add(oib);
          }
          stmt1.close();
          rs1.close();
          bo.setOrderItems(itemList);
          billedOrderList.add(bo);
        }
        billedOrderhm.put(username,billedOrderList);
        rs.close();
        conn.close();
        return billedOrderhm;
      }catch(Exception e){
        e.printStackTrace();
      }
      return billedOrderhm;
    }

  public int insertBilledOrder(BillingOrderBean bo){
    int bookingNum =0;
    try{
      conn = getConnection();
      String insertCustomerOrderQuery = "insert into reservation (userName, fullname, email, totalBill, creditCardNo, cvv, address1, address2, zipcode, bookingDate) values (?,?,?,?,?,?,?,?,?,?);";
      PreparedStatement ps = conn.prepareStatement(insertCustomerOrderQuery);
      ps.setString(1,bo.getUsername());
      ps.setString(2,bo.getFullname());
      ps.setString(3,bo.getEmail());
      ps.setDouble(4,bo.getTotal());
      ps.setString(5,bo.getCreditcardNo());
      ps.setInt(6,bo.getCvv());
      ps.setString(7,bo.getAddress1());
      ps.setString(8,bo.getAddress2());
      ps.setString(9,bo.getZipcode());
      ps.setString(10,bo.getBookingDate());
      ps.execute();

      // get orderNum for the last inserted order
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select bookingNum from reservation where userName = '"+bo.getUsername()+"' order by bookingNum desc limit 1;");

      while(rs.next()){
        bookingNum = rs.getInt("bookingNum");
      }
      rs.close();
      //insert order items
      for(OrderItemBean oi : bo.getOrderItems()){
        String insertOrderItemQuery = "insert into bookingdetails (bookingNum, hotelId, hotelName, hotelImage, roomId, roomType, roomImage, totalPrice, roomQuantity, checkInDate, checkOutDate) values (?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement ps1 = conn.prepareStatement(insertOrderItemQuery);
        ps1.setInt(1,bookingNum);
        ps1.setInt(2,oi.getHotelId());
        ps1.setString(3,oi.getHotelName());
        ps1.setString(4,oi.getHotelImage());
        ps1.setInt(5,oi.getRoomId());
        ps1.setString(6,oi.getRoomType());
        ps1.setString(7,oi.getRoomImage());
        ps1.setDouble(8,oi.getTotalPrice());
        ps1.setInt(9,oi.getRoomQuantity());
        ps1.setString(10,oi.getCheckInDate());
        ps1.setString(11,oi.getCheckOutDate());
        ps1.execute();

        //Decrement quantity of each room in the orderitem

        //get the quantity of the orderItem from Product table
        Statement stmt1 = conn.createStatement();
        ResultSet rs1 = stmt1.executeQuery("select roomQuantity from rooms where roomId = '"+oi.getRoomId()+"';");
        int quantity = 0;
        while(rs1.next()){
          quantity = rs1.getInt("roomQuantity");
        }

        System.out.print("the quantity of product is"+ quantity);
        //Update the product quantity in the product table
        String updateOrderQuery = "update rooms set roomQuantity = ? where roomId = ?;";
        PreparedStatement ps3 = conn.prepareStatement(updateOrderQuery);
        ps3.setInt(1,(quantity-oi.getRoomQuantity()));
        ps3.setInt(2,oi.getRoomId());
        ps3.execute();

      }
      conn.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return bookingNum;
  }

  public void deleteBilledBooking(String userName, int bookingNum){

    try{
      conn = getConnection();
      String deleteReservationQuery = "delete from reservation where userName = ? and bookingNum = ?;";
      //check order items in deleted customer order.
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select roomId,roomQuantity from bookingdetails where bookingNum = '"+bookingNum+"';");
      int[] rooms = new int[50];
      int[] roomQuantity = new int[50];
      int i=0;

      while(rs.next()){
        rooms[i] = rs.getInt("roomId");
        roomQuantity[i] = rs.getInt("roomQuantity");
        i++;
      }
      String deleteBookingDetailsQuery = "delete from bookingdetails where bookingNum = ?;";
      PreparedStatement ps = conn.prepareStatement(deleteReservationQuery);
      ps.setString(1,userName);
      ps.setInt(2,bookingNum);
      ps.execute();

      PreparedStatement ps1 = conn.prepareStatement(deleteBookingDetailsQuery);
      ps1.setInt(1,bookingNum);
      ps1.execute();

      //increase the count of deleted rooms in the rooms table

      for(int j=0; j<rooms.length; j++){

        //get the quantity of the room from rooms table
        Statement stmt1 = conn.createStatement();
        ResultSet rs1 = stmt1.executeQuery("select roomQuantity from rooms where roomId = '"+rooms[j]+"';");
        int quantity = 0;
        while(rs1.next()){
          quantity = rs1.getInt("roomQuantity");
        }

        //Update the room quantity in the rooms table
        String updateRoomQuery = "update rooms set roomQuantity = ? where roomId = ?;";
        PreparedStatement ps3 = conn.prepareStatement(updateRoomQuery);
        ps3.setInt(1,(quantity+roomQuantity[j]));
        ps3.setInt(2,rooms[j]);
        ps3.execute();

      }

      conn.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }

  }

  public void updateBookingDetails(int orderNum, int hotelid,int roomid,int roomquantity,String checkindate,String checkoutdate){
      try{
        conn = getConnection();

        String updateBookingQuery = "update bookingdetails set roomQuantity = ?,  checkInDate = ?, checkOutDate = ? where bookingNum = ? and hotelId = ? and roomId = ?;";
        PreparedStatement ps = conn.prepareStatement(updateBookingQuery);
        ps.setInt(1,roomquantity);
        ps.setString(2,checkindate);
        ps.setString(3,checkoutdate);
        ps.setInt(4,orderNum);
        ps.setInt(5,hotelid);
        ps.setInt(6,roomid);
        ps.execute();
        ps.close();

        conn.close();
      }
      catch(Exception e){
        e.printStackTrace();
      }
    }

  //ManagerAccount
  public void addHotel(String hotelname,String himage,String address,String city,String state,int zipcode,float rating,float rate,String disability,String gym,String pool,int discount){

    try{
      conn = getConnection();

      String insertHotelQuery = "insert into hotels (hotelName, hotelImage, hotelAddress, hotelCity, hotelState, hotelZipcode, hotelRating, hotelHighrate, hotelLowrate, hotelPromo, hotelDisabilityCare, hotelPackages, hotelCarrental, hotelFlight, hotelLocaltour, hotelGym, hotelPool, hotelDiscount) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
      PreparedStatement ps = conn.prepareStatement(insertHotelQuery);
      ps.setString(1,hotelname);
      ps.setString(2,himage);
      ps.setString(3,address);
      ps.setString(4,city);
      ps.setString(5,state);
      ps.setInt(6,zipcode);
      ps.setFloat(7,rating);
      ps.setFloat(8,rate);
      ps.setFloat(9,0);
      ps.setString(10,"no");
      ps.setString(11,disability);
      ps.setString(12,"no");
      ps.setString(13,"no");
      ps.setString(14,"no");
      ps.setString(15,"no");
      ps.setString(16,gym);
      ps.setString(17,pool);
      ps.setInt(18,discount);
      ps.execute();
      ps.close();

      conn.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public void updateHotel(int hotelId,String name,String address,String city,int zipcode,String disability,int discount){
    try{
      conn = getConnection();

      String updateHotelQuery = "update hotels set hotelName = ?, hotelAddress = ?, hotelCity = ?,  hotelZipcode = ?, hotelDisabilityCare = ?, hotelDiscount = ? where hotelId = ?;";
      PreparedStatement ps = conn.prepareStatement(updateHotelQuery);
      ps.setString(1,name);
      ps.setString(2,address);
      ps.setString(3,city);
      ps.setInt(4,zipcode);
      ps.setString(5,disability);
      ps.setInt(6,discount);
      ps.setInt(7,hotelId);
      ps.execute();
      ps.close();

      conn.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public void deleteHotel(int hotelId){

    try{
      conn = getConnection();

      String deleteHotelQuery = "delete from hotels where hotelId = ?;";
      PreparedStatement ps = conn.prepareStatement(deleteHotelQuery);
      ps.setInt(1,hotelId);
      ps.execute();

      conn.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }

  }

  public void addRoom(int roomId, int hotelId, String rimage, String roomType, int noofadults, int noofchilds, float roomRate, int roomQuantity, String ac){

    try{
      conn = getConnection();

      String insertRoomsQuery = "insert into rooms (roomId, hotelId, roomType, numberOfAdults, numberOfChildren, roomImage, roomRate, roomQuantity, roomAc) values (?,?,?,?,?,?,?,?,?);";
      PreparedStatement ps = conn.prepareStatement(insertRoomsQuery);
      ps.setInt(1,roomId);
      ps.setInt(2,hotelId);
      ps.setString(3,roomType);
      ps.setInt(4,noofadults);
      ps.setInt(5,noofchilds);
      ps.setString(6,rimage);
      ps.setFloat(7,roomRate);
      ps.setInt(8,roomQuantity);
      ps.setString(9,ac);

      ps.execute();
      ps.close();

      conn.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public void updateRoom(int hotelId,int roomId,String roomType,float roomRate,int roomQuantity,String ac){
    try{
      conn = getConnection();

      String updateRoomQuery = "update rooms set roomType = ?, roomRate = ?, roomQuantity = ?,  roomAc = ? where hotelId = ? and roomId = ?;";
      PreparedStatement ps = conn.prepareStatement(updateRoomQuery);
      ps.setString(1,roomType);
      ps.setFloat(2,roomRate);
      ps.setInt(3,roomQuantity);
      ps.setString(4,ac);
      ps.setInt(5,hotelId);
      ps.setInt(6,roomId);
      ps.execute();
      ps.close();

      conn.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

  public void deleteRoom(int roomId){
        try{
            conn = getConnection();
            String deleteRoomQuery = "delete from rooms where roomId = ?;";
            PreparedStatement ps = conn.prepareStatement(deleteRoomQuery);
            ps.setInt(1,roomId);
            ps.execute();
            conn.close();
        } catch(Exception e){
          e.printStackTrace();
        }
  }

  public int addBilledBooking(String username1,String fullname,String email,double total,String creditcardNo,int cvv,String address1,String address2,String zipcode,String bookingDate,int hotelid,String roomtype,int roomquantity,String checkindate,String checkoutdate){
      int bookingNum =0;
      String hotelName="";
      String hotelImage = "";
      int roomId =0;
      String roomImage = "";
      double roomRate=0;

      try{
        conn = getConnection();
        String insertCustomerBookingQuery = "insert into reservation (userName, fullname, email, totalBill, creditCardNo, cvv, address1, address2, zipcode, bookingDate) values (?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(insertCustomerBookingQuery);
        ps.setString(1,username1);
        ps.setString(2,fullname);
        ps.setString(3,email);
        ps.setDouble(4,total);
        ps.setString(5,creditcardNo);
        ps.setInt(6,cvv);
        ps.setString(7,address1);
        ps.setString(8,address2);
        ps.setString(9,zipcode);
        ps.setString(10,bookingDate);
        ps.execute();

        // get orderNum for the last inserted order
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select bookingNum from reservation where userName = '"+username1+"' order by bookingNum desc limit 1;");

        while(rs.next()){
          bookingNum = rs.getInt("bookingNum");
        }
        rs.close();

        // get hotel name, img  for the last inserted booking
        Statement stmt3 = conn.createStatement();
        ResultSet rs3 = stmt3.executeQuery("select hotelName, hotelImage from hotels where hotelId = '"+hotelid+"';");

        while(rs3.next()){
          hotelName = rs3.getString("hotelName");
          hotelImage = rs3.getString("hotelImage");
        }
        rs3.close();

        // get room id, img, price  for the last inserted booking
        Statement stmt4 = conn.createStatement();
        ResultSet rs4 = stmt4.executeQuery("select roomId, roomImage, roomRate from rooms where hotelId = "
        +hotelid+" and roomType= '"+roomtype+"';");

        while(rs4.next()){
          roomId = rs4.getInt("roomId");
          roomImage = rs4.getString("roomImage");
          roomRate = rs4.getDouble("roomRate");
        }
        rs4.close();

        //insert order items

          String insertOrderItemQuery = "insert into bookingdetails (bookingNum, hotelId, hotelName, hotelImage, roomId, roomType, roomImage, totalPrice, roomQuantity, checkInDate, checkOutDate) values (?,?,?,?,?,?,?,?,?,?,?);";
          PreparedStatement ps1 = conn.prepareStatement(insertOrderItemQuery);
          ps1.setInt(1,bookingNum);
          ps1.setInt(2,hotelid);
          ps1.setString(3,hotelName);
          ps1.setString(4,hotelImage);
          ps1.setInt(5,roomId);
          ps1.setString(6,roomtype);
          ps1.setString(7,roomImage);
          ps1.setDouble(8,roomRate);
          ps1.setInt(9,roomquantity);
          ps1.setString(10,checkindate);
          ps1.setString(11,checkoutdate);
          ps1.execute();

          //Decrement quantity of each room in the orderitem

          //get the quantity of the orderItem from Product table
          Statement stmt1 = conn.createStatement();
          ResultSet rs1 = stmt1.executeQuery("select roomQuantity from rooms where roomId = '"+roomId+"';");
          int quantity = 0;
          while(rs1.next()){
            quantity = rs1.getInt("roomQuantity");
          }

          System.out.print("the quantity of product is"+ quantity);
          //Update the product quantity in the product table
          String updateOrderQuery = "update rooms set roomQuantity = ? where roomId = ?;";
          PreparedStatement ps5 = conn.prepareStatement(updateOrderQuery);
          ps5.setInt(1,(quantity-roomquantity));
          ps5.setInt(2,roomId);
          ps5.execute();


        conn.close();
      }
      catch(Exception e){
        e.printStackTrace();
      }
      return bookingNum;
    }

    public ArrayList<RevenueReportBean> getRevenueReport(){
      ArrayList<RevenueReportBean> sales = new ArrayList<RevenueReportBean>();

      try{
        conn = getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT hotelName,count(hotelName) as noOfItems,sum(totalPrice) as totalSales FROM bookingdetails group by hotelId;");

        while(rs.next()){
          RevenueReportBean rb = new RevenueReportBean();
          rb.setHotelName(rs.getString("hotelName"));
        //  rb.setItemPrice(rs.getFloat("itemPrice"));
          rb.setHotelCount(rs.getInt("noOfItems"));
          rb.setTotalSales(rs.getFloat("totalSales"));
          sales.add(rb);
        }
        rs.close();
        conn.close();
        return sales;

      }catch(Exception e){
        e.printStackTrace();
      }
      return sales;

    }

    public ArrayList<RevenueReportBean> getDaywiseRevenueReport(){
    ArrayList<RevenueReportBean> sales = new ArrayList<RevenueReportBean>();

    try{
      conn = getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT bookingDate,sum(totalBill) as totalSales FROM reservation group by bookingDate;");

      while(rs.next()){
        RevenueReportBean rb = new RevenueReportBean();
        rb.setBookingDate(rs.getString("bookingDate"));
        rb.setDaySales(rs.getFloat("totalSales"));
        sales.add(rb);
      }
      rs.close();
      conn.close();
      return sales;

    }catch(Exception e){
      e.printStackTrace();
    }
    return sales;

  }

  public String getHotelName(int hotelId){

  String hotelName="";
  try{
    conn = getConnection();

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT hotelName FROM hotels where hotelId ='"+hotelId+"';");

    while(rs.next()){
    hotelName = rs.getString("hotelName");
    }

    rs.close();
    conn.close();
    return hotelName;

  }catch(Exception e){
    e.printStackTrace();
  }
  return hotelName;

}

  public ArrayList<Integer> selectHotelsByTopRatings() {
      ArrayList<Integer> hotelIDs = new ArrayList<Integer>();
      try {
        conn = getConnection();
        String sql = "SELECT hotelId, hotelRating FROM hotelhuntdb.hotels "+
                      " WHERE hotelRating = 5 LIMIT 4";
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
          int hid = rs.getInt("hotelId");
          hotelIDs.add(hid);
        }
        conn.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      return hotelIDs;
  }

  public HashMap<String, Integer> selectHotelsByTopCity() {
      HashMap<String, Integer> cityList = new HashMap<String, Integer>();
      try {
        conn = getConnection();
        String sql = "SELECT h.hotelCity, COUNT(r.bookingNum) as 'count' " +
                      "FROM hotels h, bookingdetails r " +
                      "WHERE h.hotelId = r.hotelId " +
                      "GROUP BY h.hotelCity " +
                      "ORDER BY COUNT(r.bookingNum) DESC " +
                      "LIMIT 2";
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while(rs.next()) {
          String city = rs.getString("h.hotelCity");
          int count = rs.getInt("count");
          cityList.put(city,count);
        }
        conn.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      return cityList;
  }

  /***************************** Get top 5 cities where maximum number of hotels are booked *******************************/
     public ArrayList<HotelReviewBean> selectHotelsByCity() {
       ArrayList<HotelReviewBean> cityList = new ArrayList<HotelReviewBean>();
       try {
         conn = getConnection();
         String sql = "SELECT h.hotelCity, COUNT(r.bookingNum) as 'count' " +
                       "FROM hotels h, bookingdetails r " +
                       "WHERE h.hotelId = r.hotelId " +
                       "GROUP BY h.hotelCity " +
                       "ORDER BY count DESC " +
                       "LIMIT 5";
         PreparedStatement st = conn.prepareStatement(sql);
         ResultSet rs = st.executeQuery();
         while(rs.next()) {
           String city = rs.getString("h.hotelCity");
           int count = rs.getInt("count");
           HotelReviewBean hr = new HotelReviewBean();
           hr.setHotelId(count+"");
           hr.setHotelCity(city);
           cityList.add(hr);
         }
         conn.close();
       }
       catch (Exception e) {
         e.printStackTrace();
       }
       return cityList;
     }

     /************************************************* Availability of rooms ***********************************************/

      public ArrayList<Availability> checkAvailableRooms() {
       ArrayList<Availability> arr = new ArrayList<Availability>();
       try {
         conn = getConnection();
         String sql = "SELECT h.hotelName,sum(r.roomQuantity) "+
         "FROM hotelhuntdb.rooms r,hotelhuntdb.hotels h "+
        "where r.hotelId = h.hotelId group by r.hotelid;";
         PreparedStatement st = conn.prepareStatement(sql);
         ResultSet rs = st.executeQuery();
         while(rs.next()) {
             String hotel_name = rs.getString(1);
             int count = rs.getInt(2);
             Availability av = new Availability();
             av.setHotelName(hotel_name);
             av.setCount(count);
             arr.add(av);
         }
         conn.close();
       }
       catch (Exception e) {
         e.printStackTrace();
       }
       return arr;
      }
}
