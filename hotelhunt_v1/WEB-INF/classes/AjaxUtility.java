import java.sql.*;
import java.util.*;

public class AjaxUtility{
       MySqlDataStoreUtilities sqlUtil = new MySqlDataStoreUtilities();

       public void AjaxUtility(){}

       public StringBuffer readAnyData(String searchId){
                 //System.out.println("readData .. searchId: "+searchId);
                 StringBuffer sb = new StringBuffer();
                 HashMap<Integer, HotelBean> hotelList = sqlUtil.getHotels();
                 ArrayList<String> zipcodeList = new ArrayList<String>();
                 ArrayList<String> cityList = new ArrayList<String>();
                 Iterator it = hotelList.entrySet().iterator();

                 while(it.hasNext()){
                      Map.Entry hotelEntry = (Map.Entry)it.next();
                      HotelBean hotel = (HotelBean)hotelEntry.getValue();
                      String zipcode = hotel.getZipcode()+"";
                      String city = hotel.getCity();

                      if(hotel.getName().toLowerCase().startsWith(searchId.toLowerCase())){
                          //System.out.println("hotel.getName(): "+hotel.getName());
                          sb.append("<hotel>");
                          sb.append("<hotelID>" + hotel.getId() + "</hotelID>");
                          sb.append("<hotelName>" + hotel.getName() + "</hotelName>");
                          sb.append("</hotel>");
                      }
                      if(!zipcodeList.contains(zipcode)){
                              zipcodeList.add(zipcode);
                              if(zipcode.toLowerCase().startsWith(searchId.toLowerCase())){
                            //  System.out.println("hotel.getZipcode(): "+hotel.getZipcode());
                              sb.append("<hotel>");
                              sb.append("<hotelID>" + hotel.getId() + "</hotelID>");
                              sb.append("<hotelName>" + hotel.getZipcode() + "</hotelName>");
                              sb.append("</hotel>");
                          }
                      }
                      if(!cityList.contains(city)){
                              cityList.add(city);
                              if(city.toLowerCase().startsWith(searchId.toLowerCase())){
                              System.out.println("searchId.toLowerCase(): "+searchId.toLowerCase());
                              System.out.println("city.toLowerCase(): "+city.toLowerCase());
                              sb.append("<hotel>");
                              sb.append("<hotelID>" + hotel.getId() + "</hotelID>");
                              sb.append("<hotelName>" + hotel.getCity() + "</hotelName>");
                              sb.append("</hotel>");
                          }
                      }

                  }
              return sb;
         }

       //readData() get the hotels starting with letter typed from HashMap into StringBuffer
       public StringBuffer readData(String searchId){
               //System.out.println("readData .. searchId: "+searchId);
               StringBuffer sb = new StringBuffer();
               HashMap<Integer, HotelBean> hotelList = sqlUtil.getHotels();
               Iterator it = hotelList.entrySet().iterator();

               while(it.hasNext()){
                    Map.Entry hotelEntry = (Map.Entry)it.next();
                    HotelBean hotel = (HotelBean)hotelEntry.getValue();
                    if(hotel.getName().toLowerCase().startsWith(searchId.toLowerCase())){
                        //System.out.println("hotel.getName(): "+hotel.getName());
                        sb.append("<hotel>");
                        sb.append("<hotelID>" + hotel.getId() + "</hotelID>");
                        sb.append("<hotelName>" + hotel.getName() + "</hotelName>");
                        sb.append("</hotel>");
                    }
               }
            return sb;
       }

       public StringBuffer readZipData(String searchId){
               System.out.println("readData .. searchId: "+searchId);
               StringBuffer sb = new StringBuffer();
               HashMap<Integer, HotelBean> hotelList = sqlUtil.getHotels();
               Iterator it = hotelList.entrySet().iterator();
               ArrayList<String> zipcodeList = new ArrayList<String>();
               while(it.hasNext()){
                    Map.Entry hotelEntry = (Map.Entry)it.next();
                    HotelBean hotel = (HotelBean)hotelEntry.getValue();
                    String zipcode = hotel.getZipcode()+"";

                    if(!zipcodeList.contains(zipcode)){
                        zipcodeList.add(zipcode);
                        if(zipcode.toLowerCase().startsWith(searchId.toLowerCase())){
                          //  System.out.println("hotel.getZipcode(): "+hotel.getZipcode());
                            sb.append("<hotel>");
                            sb.append("<hotelID>" + hotel.getId() + "</hotelID>");
                            sb.append("<hotelName>" + hotel.getZipcode() + "</hotelName>");
                            sb.append("</hotel>");
                        }
                    }
               }
            return sb;
       }

}
