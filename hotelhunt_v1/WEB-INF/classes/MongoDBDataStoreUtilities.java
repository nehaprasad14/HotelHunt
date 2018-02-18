import java.util.*;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.DBCollection;
import com.mongodb.AggregationOutput;
import java.text.DecimalFormat;

public class MongoDBDataStoreUtilities{
   DBCollection myReviews;
   DB db;

/******************************* Create database connection ********************************/
      public void getConnection(){
        MongoClient mongo = new MongoClient("localhost", 27017);
        //System.out.println("Connected to the database successfully");
        db = mongo.getDB("hotelHuntDB");
        myReviews = db.getCollection("myReviews");
      }

/****************************** Insert into database *************************************/
    public void insertReviews(HotelReviewBean hr){
      getConnection();
      BasicDBObject document = new BasicDBObject("hotelId", hr.getHotelId())
        .append("hotelName", hr.getHotelName())
        .append("hotelCity", hr.getHotelCity())
        .append("hotelZip", hr.getHotelZip())
        .append("username", hr.getUsername())
        .append("totalRating", hr.getRoomRating())
        .append("reviewComment", hr.getReviewComment())
        .append("reviewDate", hr.getReviewDate());
        myReviews.insert(document);
        System.out.println("Document inserted successfully");
    }

/******************************* Select reviews *****************************************/
  public HashMap<String,ArrayList<HotelReviewBean>> selectReviews() {
             getConnection();
             myReviews = db.getCollection("myReviews");
             HashMap<String,ArrayList<HotelReviewBean>>  hm = new HashMap<String,ArrayList<HotelReviewBean>>();
             DBCursor cursor = myReviews.find();

             while (cursor.hasNext()) {
                   BasicDBObject obj = (BasicDBObject)cursor.next();
                   ArrayList<HotelReviewBean> arr;
                   if(!hm.containsKey(obj.get("hotelId").toString())){
                       arr = new ArrayList<HotelReviewBean>();
                       hm.put(obj.get("hotelId").toString(), arr);
                   }
                   arr = hm.get(obj.get("hotelId").toString());
                   HotelReviewBean hr = new HotelReviewBean();
                   String reviewId = (String) obj.get("_id").toString();
                   System.out.println(reviewId);
                   hr.setReviewId(reviewId);
                   hr.setHotelId(obj.get("hotelId").toString());
                   hr.setHotelName(obj.get("hotelName").toString());
                   hr.setHotelCity(obj.get("hotelCity").toString());
                   hr.setHotelZip(obj.get("hotelZip").toString());
                   hr.setUsername(obj.get("username").toString());
                   hr.setRoomRating(Integer.parseInt(obj.get("totalRating").toString()));
                   hr.setReviewDate(obj.get("reviewDate").toString());
                   hr.setReviewComment(obj.get("reviewComment").toString());
                   arr.add(hr);
              }
           return hm;
         }
/**************************************** Delete Reviews ************************************************/

public void deleteReview(String hotelId, String username, String reviewComment) {
           getConnection();
           myReviews = db.getCollection("myReviews");

           // BasicDBObject query = new BasicDBObject("_id", reviewId);
           // myReviews.remove(query);

           BasicDBObject query = new BasicDBObject();
           query.append("hotelId", hotelId);
           query.append("username", username);
           query.append("reviewComment", reviewComment);

           myReviews.remove(query);

          // BasicDBObject query = new BasicDBObject();
          // ObjectId id = (ObjectId)reviewId;
          // myReviews.deleteOne("_id", id);

          /*
           HashMap<String,ArrayList<HotelReviewBean>>  hm = new HashMap<String,ArrayList<HotelReviewBean>>();
           DBCursor cursor = myReviews.find();

           while (cursor.hasNext()) {
                 BasicDBObject obj = (BasicDBObject)cursor.next();
                 ArrayList<HotelReviewBean> arr;
                 if(!hm.containsKey(obj.get("hotelId").toString())){
                     arr = new ArrayList<HotelReviewBean>();
                     hm.put(obj.get("hotelId").toString(), arr);
                 }
                 arr = hm.get(obj.get("hotelId").toString());
                 HotelReviewBean hr = new HotelReviewBean();
                 hr.setHotelId(obj.get("hotelId").toString());
                 hr.setHotelName(obj.get("hotelName").toString());
                 hr.setHotelCity(obj.get("hotelCity").toString());
                 hr.setHotelZip(obj.get("hotelZip").toString());
                 hr.setUsername(obj.get("username").toString());
                 hr.setRoomRating(Integer.parseInt(obj.get("totalRating").toString()));
                 hr.setReviewDate(obj.get("reviewDate").toString());
                 hr.setReviewComment(obj.get("reviewComment").toString());
                 arr.add(hr);
            }
         return hm;
         */
       }

/***************************************** Top 5 most liked hotels **************************************/
         public ArrayList<HotelReviewBean> getHotelsByRating(){
                getConnection();
                myReviews = db.getCollection("myReviews");
                DBObject groupFields = new BasicDBObject("_id",0);
                groupFields.put("_id","$hotelName");
                groupFields.put("hotelName",new BasicDBObject("$first", "$hotelName"));
                groupFields.put("totalRating",new BasicDBObject("$avg", "$totalRating"));
                groupFields.put("hotelCity",new BasicDBObject("$first", "$hotelCity"));

                DBObject group = new BasicDBObject("$group",groupFields);

                DBObject sort = new BasicDBObject();
                sort.put("totalRating", -1);
                DBObject orderBy = new BasicDBObject();
                orderBy = new BasicDBObject("$sort", sort);
                DBObject limit = new BasicDBObject();
                limit = new BasicDBObject("$limit", 5);

                AggregationOutput aggregate = myReviews.aggregate(group, orderBy, limit);
                DecimalFormat df = new DecimalFormat("###.##");
                ArrayList<HotelReviewBean> arr = new ArrayList<HotelReviewBean>();
                HotelReviewBean hr = null;
                for(DBObject result: aggregate.results()){
                    BasicDBObject bobj = (BasicDBObject) result;
                    hr = new HotelReviewBean();
                    hr.setHotelName(bobj.getString("hotelName"));
                    hr.setHotelCity(bobj.getString("hotelCity"));
                    Double avg = (Double) bobj.get("totalRating");
                    hr.setServiceRating(df.format(avg)+"");
                    arr.add(hr);
                }
            return arr;
        }

/***************************************** Top 5 hotels by zipcode *****************************************/
        public ArrayList<HotelReviewBean> getHotelsByZipcode(){
                  getConnection();

                  DBObject groupFields = new BasicDBObject("_id",0);
                  groupFields.put("_id","$hotelZip");
                  groupFields.put("Count", new BasicDBObject("$sum", 1));
                  groupFields.put("hotelZip",new BasicDBObject("$first", "$hotelZip"));

                  DBObject group = new BasicDBObject("$group", groupFields);

                  DBObject sort = new BasicDBObject();
                  sort.put("Count", -1);
                  DBObject orderBy = new BasicDBObject();
                  orderBy = new BasicDBObject("$sort", sort);

                  DBObject limit = new BasicDBObject();
                  limit = new BasicDBObject("$limit", 5);

                  AggregationOutput aggregate = myReviews.aggregate(group, orderBy, limit);

                  ArrayList<HotelReviewBean> arr = new ArrayList<HotelReviewBean>();
                  for(DBObject result: aggregate.results()){
                      BasicDBObject bobj = (BasicDBObject) result;
                      HotelReviewBean hr = new HotelReviewBean();
                      hr.setHotelId(bobj.getString("Count"));
                      hr.setHotelZip(bobj.getString("hotelZip"));
                      arr.add(hr);
                  }
              return arr;
        }

/********************************************** Delete Reviews ******************************************/
// public int deleteReviews() {
//   getConnection();
//
//
// }

}
