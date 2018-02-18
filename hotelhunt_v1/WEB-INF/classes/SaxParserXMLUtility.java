import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParserXMLUtility extends DefaultHandler {
	HotelBean hotel;
	RoomBean room;
	static HashMap<Integer, HotelBean> hotelCatalog;
	static HashMap<Integer, RoomBean> roomCatalog;
	MySqlDataStoreUtilities sqdb = new MySqlDataStoreUtilities();
	String hotelsXmlFileName;
	String elementValueRead;

	public SaxParserXMLUtility() {

	}

	public SaxParserXMLUtility(String hotelsXmlFileName) {
		this.hotelsXmlFileName = hotelsXmlFileName;
		hotelCatalog = new HashMap<Integer, HotelBean>();
		roomCatalog = new HashMap<Integer, RoomBean>();
		parseDocument();
		prettyPrint();
		try {
			sqdb.dbCleanup();
			sqdb.insert_hotels_rooms(hotelCatalog, roomCatalog);
			System.out.println("*****************************************");
			System.out.println("Hotel details loaded in database from xml ");
			System.out.println("*****************************************");
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	private void parseDocument() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(hotelsXmlFileName, this);
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfig error");
		} catch (SAXException e) {
			System.out.println("SAXException : xml not well formed");
		} catch (IOException e) {
			System.out.println("IO error");
		}
	}

	private void prettyPrint() {
		System.out.println("prettyPrint ");
		for (Integer hotelId : hotelCatalog.keySet()) {
			hotel = hotelCatalog.get(hotelId);
			 //System.out.println("Keys are: "+hotel.getId()+" Value: "+ hotel.getName());
		}

		for (Integer roomId : roomCatalog.keySet()) {
			room = roomCatalog.get(roomId);
			//System.out.println("Keys are: "+room.getId()+" Value: "+room.getRoomType());
		}

	}

	public HashMap<Integer, HotelBean> getHotelMap() {
		return hotelCatalog = sqdb.getHotels();
	}

	public HashMap<Integer, RoomBean> getRoomMap() {
		return roomCatalog = sqdb.getRooms();
	}

	@Override
	public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {
		// System.out.println("In startElement ");
		if (elementName.equalsIgnoreCase("hotel")) {
			hotel = new HotelBean();
			hotel.setId(Integer.parseInt(attributes.getValue("id")));
		}

		if (elementName.equalsIgnoreCase("Room")) {
			room = new RoomBean();
			room.setId(Integer.parseInt(attributes.getValue("id")));
		}

	}

	@Override
	public void endElement(String str1, String str2, String element) throws SAXException {
		// System.out.println("In endElement ");
		if (element.equals("hotel")) {
			hotelCatalog.put(hotel.getId(), hotel);
			return;
		}

		if (element.equals("Room")) {
			roomCatalog.put(room.getId(), room);
			return;
		}

		// Parsing Hotels
		if (element.equalsIgnoreCase("name")) {
			hotel.setName(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("image")) {
			hotel.setHotelimage(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("address1")) {
			hotel.setAddress(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("city")) {
			hotel.setCity(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("state")) {
			hotel.setState(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("zipcode")) {
			hotel.setZipcode(Integer.parseInt(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("hotelRating")) {
			hotel.setHotelrating(Float.parseFloat(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("highRate")) {
			hotel.setHighrate(Float.parseFloat(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("lowRate")) {
			hotel.setLowrate(Float.parseFloat(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("promo")) {
			hotel.setPromo(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("package")) {
			hotel.setPackages(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("carRental")) {
			hotel.setCarRental(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("disabilityCare")) {
			hotel.setDisabilityCare(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("flight")) {
			hotel.setFlight(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("localTour")) {
			hotel.setLocalTour(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("gym")) {
			hotel.setGym(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("pool")) {
			hotel.setPool(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("discount")) {
			hotel.setDiscount(Integer.parseInt(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("roomNo")) {
			hotel.getRooms().add(elementValueRead);
			return;
		}

		// Parsing Rooms

		if (element.equalsIgnoreCase("numberOfAdults")) {
			room.setNumberOfAdults(Integer.parseInt(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("numberOfChildren")) {
			room.setNumberOfChildren(Integer.parseInt(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("rimage")) {
			room.setRoomimage(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("rate")) {
			room.setRoomrate(Integer.parseInt(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("quantity")) {
			room.setQuantity(Integer.parseInt(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("ac")) {
			room.setAc(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("roomType")) {
			room.setRoomType(elementValueRead);
			return;
		}

	}

	@Override
	public void characters(char[] content, int begin, int end) throws SAXException {
		elementValueRead = new String(content, begin, end);
	}

	public static void addHotelHashmap() {
		String TOMCAT_HOME = System.getProperty("catalina.home");
		new SaxParserXMLUtility(TOMCAT_HOME + "\\webapps\\hotelhunt_v1\\WEB-INF\\xml\\hotelCatalog.xml");

	}
}
