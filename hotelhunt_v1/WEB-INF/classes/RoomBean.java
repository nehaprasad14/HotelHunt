public class RoomBean implements java.io.Serializable{
	private int id;
	private int hotelId;
	private int numberOfAdults;
	private int numberOfChildren;
	private String roomimage;
	private float roomrate;
	private int quantity;
	private String ac;
	private String roomType;

	public RoomBean(){

	}

	public RoomBean(int id, int numberOfAdults, int numberOfChildren, String roomimage, float roomrate,
			int quantity, String ac, String roomType) {
		super();
		this.id = id;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
		this.roomimage = roomimage;
		this.roomrate = roomrate;
		this.quantity = quantity;
		this.ac = ac;
		this.roomType = roomType;
	}

	public RoomBean(int id,int hotelId, int numberOfAdults, int numberOfChildren, String roomimage, float roomrate,
			int quantity, String ac, String roomType) {
		super();
		this.id = id;
		this.hotelId = hotelId;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
		this.roomimage = roomimage;
		this.roomrate = roomrate;
		this.quantity = quantity;
		this.ac = ac;
		this.roomType = roomType;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}


	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public String getRoomimage() {
		return roomimage;
	}

	public void setRoomimage(String roomimage) {
		this.roomimage = roomimage;
	}

	public float getRoomrate() {
		return roomrate;
	}

	public void setRoomrate(float roomrate) {
		this.roomrate = roomrate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String isAc() {
		return ac;
	}

	public void setAc(String ac) {
		this.ac = ac;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}


}
