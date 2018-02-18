public class OrderItemBean implements java.io.Serializable {

	private int hotelId;
	private String hotelName;
	private String hotelImage;
	private int roomId;
	private String roomType;
	private String roomImage;
	private double totalPrice;
	private int roomQuantity;
	private String checkInDate;
	private String checkOutDate;

	
	public OrderItemBean(){
		
	}
	public OrderItemBean(int hotelId, String hotelName, String hotelImage, int roomId, String roomType,
			String roomImage, double totalPrice, int roomQuantity, String checkInDate, String checkOutDate) {
		super();
		this.hotelId = hotelId;
		this.hotelName = hotelName;
		this.hotelImage = hotelImage;
		this.roomId = roomId;
		this.roomType = roomType;
		this.roomImage = roomImage;
		this.totalPrice = totalPrice;
		this.roomQuantity = roomQuantity;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelImage() {
		return hotelImage;
	}

	public void setHotelImage(String hotelImage) {
		this.hotelImage = hotelImage;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomImage() {
		return roomImage;
	}

	public void setRoomImage(String roomImage) {
		this.roomImage = roomImage;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getRoomQuantity() {
		return roomQuantity;
	}

	public void setRoomQuantity(int roomQuantity) {
		this.roomQuantity = roomQuantity;
	}

	public String getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	

}
