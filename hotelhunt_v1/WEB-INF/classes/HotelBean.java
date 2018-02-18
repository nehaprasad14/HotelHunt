import java.util.*;

public class HotelBean implements java.io.Serializable {
	private int id;
	private String name;
	private String hotelimage;
	private String address;
	private String city;
	private String state;
	private int zipcode;
	private float hotelrating;
	private float highrate;
	private float lowrate;
	private String promo;
	private String disabilityCare;
	private String packages;
	private String carRental;
	private String flight;
	private String localTour;
	private String gym;
	private String pool;
	private int discount;
	private ArrayList<String> rooms = new ArrayList<String>();

	public HotelBean() {

	}

	public HotelBean(int id, String name, String hotelimage, String address, String city, String state, int zipcode,
			float hotelrating, float highrate, float lowrate, String promo, String disabilityCare, String packages,
			String carRental, String flight, String localTour, String gym, String pool, int discount,
			ArrayList<String> rooms) {
		super();
		this.id = id;
		this.name = name;
		this.hotelimage = hotelimage;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.hotelrating = hotelrating;
		this.highrate = highrate;
		this.lowrate = lowrate;
		this.promo = promo;
		this.disabilityCare = disabilityCare;
		this.packages = packages;
		this.carRental = carRental;
		this.flight = flight;
		this.localTour = localTour;
		this.gym = gym;
		this.pool = pool;
		this.discount = discount;
		this.rooms = rooms;
	}

	public HotelBean(int id, String name, String hotelimage, String address, String city, String state, int zipcode,
			float hotelrating, float highrate, float lowrate, String promo, String disabilityCare, String packages,
			String carRental, String flight, String localTour, String gym, String pool, int discount) {
		super();
		this.id = id;
		this.name = name;
		this.hotelimage = hotelimage;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.hotelrating = hotelrating;
		this.highrate = highrate;
		this.lowrate = lowrate;
		this.promo = promo;
		this.disabilityCare = disabilityCare;
		this.packages = packages;
		this.carRental = carRental;
		this.flight = flight;
		this.localTour = localTour;
		this.gym = gym;
		this.pool = pool;
		this.discount = discount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHotelimage() {
		return hotelimage;
	}

	public void setHotelimage(String hotelimage) {
		this.hotelimage = hotelimage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public float getHotelrating() {
		return hotelrating;
	}

	public void setHotelrating(float hotelrating) {
		this.hotelrating = hotelrating;
	}

	public float getHighrate() {
		return highrate;
	}

	public void setHighrate(float highrate) {
		this.highrate = highrate;
	}

	public float getLowrate() {
		return lowrate;
	}

	public void setLowrate(float lowrate) {
		this.lowrate = lowrate;
	}

	public String isPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public String isDisabilityCare() {
		return disabilityCare;
	}

	public void setDisabilityCare(String disabilityCare) {
		this.disabilityCare = disabilityCare;
	}

	public String isPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public String isCarRental() {
		return carRental;
	}

	public void setCarRental(String carRental) {
		this.carRental = carRental;
	}

	public String isFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public String isLocalTour() {
		return localTour;
	}

	public void setLocalTour(String localTour) {
		this.localTour = localTour;
	}

	public String isGym() {
		return gym;
	}

	public void setGym(String gym) {
		this.gym = gym;
	}

	public String isPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public ArrayList<String> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<String> rooms) {
		this.rooms = rooms;
	}


}
