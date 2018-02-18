import java.util.*;

public class RevenueReportBean implements java.io.Serializable{

	private String hotelName;
	//private float itemPrice;
	private int hotelCount;
	private float totalSales;
	private String bookingDate;
	private float daySales;

  public RevenueReportBean(){

  }

  public RevenueReportBean(String hotelName, int hotelCount,
	float totalSales, String bookingDate, float daySales) {
		this.hotelName = hotelName;
	//	this.itemPrice = itemPrice;
		this.hotelCount = hotelCount;
		this.totalSales = totalSales;
		this.bookingDate = bookingDate;
		this.daySales = daySales;
	}

  public String getHotelName() {
    return hotelName;
  }
  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public int getHotelCount() {
    return hotelCount;
  }
  public void setHotelCount(int hotelCount) {
    this.hotelCount = hotelCount;
  }

  public float getTotalSales() {
    return totalSales;
  }
  public void setTotalSales(float totalSales) {
    this.totalSales = totalSales;
  }

  public String getBookingDate() {
    return bookingDate;
  }
  public void setBookingDate(String bookingDate) {
    this.bookingDate = bookingDate;
  }

  public float getDaySales() {
      return daySales;
  }
  public void setDaySales(float daySales) {
      this.daySales = daySales;
  }

  }
