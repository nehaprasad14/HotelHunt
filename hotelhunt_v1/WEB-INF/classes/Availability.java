import java.io.*;
public class Availability{
	private String hotel_name;
  private int count;

	public Availability(String hotel_name, int count) {
		this.hotel_name=hotel_name;
		this.count=count;
	}

  public Availability(){

  }

	public String getHotelName() {
		return hotel_name;
	}

	public void setHotelName(String hotel_name) {
		this.hotel_name = hotel_name;
	}

  public int getCount(){
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
