import java.sql.*;
import java.util.*;
import java.io.*;

public class DealMatches implements java.io.Serializable
{

  HashMap<Integer, HotelBean> hotelhm;
  HashMap<Integer, HotelBean> selectedhotelhm;

  ArrayList<String> tweets;
  ArrayList<String> selectedTweets;

  public void readTweets() {

    BufferedReader br;
    try {
      String deal=null;
      tweets = new ArrayList<String>();
      //Open DealMatches text file using bufferedReader class
      br = new BufferedReader(new FileReader(
      "C:\\apache-tomcat-7.0.34\\webapps\\hotelhunt_v1\\DealMatch.txt"));

      //Read all deals from the DealMatches text file
      while ((deal = br.readLine()) != null) {
        //add each deal line from text file to tweets ArrayList
        tweets.add(deal);
      }
      br.close();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public HashMap<Integer, HotelBean> getSelectedHotels()
  {
    MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
    hotelhm = sqdb.getHotels();
    selectedhotelhm = new HashMap<Integer, HotelBean>();
    selectedTweets = new ArrayList<String>();
    String deal=null;

    try
    {
      for(Map.Entry<Integer, HotelBean> entry: hotelhm.entrySet())
      {
        if(selectedhotelhm.size()<=2 && !selectedhotelhm.containsKey(entry.getKey()))
        {
        //  System.out.println("in deals match inside if "+selectedProductHm.size());
          for(String deals : tweets){
            HotelBean hotel;
            hotel = hotelhm.get(entry.getKey());
            if(deals.contains(hotel.getName())){
              selectedTweets.add(deals);
              selectedhotelhm.put(entry.getKey(), entry.getValue());
              break;
            }
          }
        }
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

    return selectedhotelhm;
  }

  public ArrayList<String> getSelectedTweets() {
    return selectedTweets;
  }

}
