import java.io.*;

public class HotelReviewBean{
  private String reviewId;
  private String hotelId;
	private String hotelName;
  private String hotelState;
	private String hotelCity;
  private String hotelZip;
	private String username;
	private String reviewTitle;
  private String travelType;
  private String travelMonthYear;
  private String serviceRating;
  private int roomRating;
  private int totalRating;
  private String reviewComment;
  private String reviewDate;
  private Double avgRating;

  public HotelReviewBean() {

  }

	public HotelReviewBean(String hotelId, String hotelName, String hotelState, String hotelCity, String hotelZip, String username, String reviewTitle, String travelType, String travelMonthYear, String serviceRating, int roomRating, int totalRating, String reviewComment, String reviewDate) {
		this.hotelId = hotelId;
		this.hotelName = hotelName;
    this.hotelState = hotelState;
		this.hotelCity = hotelCity;
    this.hotelZip = hotelZip;
    this.username = username;
    this.reviewTitle = reviewTitle;
    this.travelType = travelType;
    this.travelMonthYear = travelMonthYear;
    this.serviceRating = serviceRating;
    this.roomRating = roomRating;
    this.totalRating = totalRating;
    this.reviewComment = reviewComment;
    this.reviewDate = reviewDate;
	}

  public HotelReviewBean(String hotelId, String hotelName, String hotelState, String hotelCity, String hotelZip, Double avgRating) {
    this.hotelId = hotelId;
    this.hotelName = hotelName;
    this.hotelState = hotelState;
    this.hotelCity = hotelCity;
    this.hotelZip = hotelZip;
    this.avgRating = avgRating;
  }

  // public HotelReviewBean(String hotelId, String hotelName, String hotelState, String hotelCity, String hotelZip) {
  //   this.hotelId = hotelId;
  //   this.hotelName = hotelName;
  //   this.hotelState = hotelState;
  //   this.hotelCity = hotelCity;
  //   this.hotelZip = hotelZip;
  // }


  public String getReviewId() {
    return reviewId;
  }

  public void setReviewId(String reviewId) {
    this.reviewId = reviewId;
  }

  public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

  public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

  public String getHotelState() {
		return hotelState;
	}

	public void setHotelState(String hotelState) {
		this.hotelState = hotelState;
	}

  public String getHotelCity() {
		return hotelCity;
	}

	public void setHotelCity(String hotelCity) {
		this.hotelCity = hotelCity;
	}

  public String getHotelZip() {
		return hotelZip;
	}

	public void setHotelZip(String hotelZip) {
		this.hotelZip = hotelZip;
	}

  public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

  public String getReviewTitle() {
    return reviewTitle;
  }

  public void setReviewTitle(String reviewTitle) {
    this.reviewTitle = reviewTitle;
  }

  public String getTravelType() {
    return travelType;
  }

  public void setTravelType(String travelType) {
    this.travelType = travelType;
  }

  public String getTravelMonthYear() {
    return travelMonthYear;
  }

  public void setTravelMonthYear(String travelMonthYear) {
    this.travelMonthYear = travelMonthYear;
  }

  public String getServiceRating() {
    return serviceRating;
  }

  public void setServiceRating(String serviceRating) {
    this.serviceRating= serviceRating;
  }

  public int getRoomRating() {
    return roomRating;
  }

  public void setRoomRating(int roomRating) {
    this.roomRating= roomRating;
  }

  public int getTotalRating() {
    return totalRating;
  }

  public void setTotalRating(int totalRating) {
    this.totalRating = totalRating;
  }

  public String getReviewComment() {
    return reviewComment;
  }

  public void setReviewComment(String reviewComment) {
    this.reviewComment = reviewComment;
  }

  public String getReviewDate() {
    return reviewDate;
  }

  public void setReviewDate(String reviewDate) {
    this.reviewDate = reviewDate;
  }

  public Double getAvgRating() {
    return avgRating;
  }

  public void setAvgRating(Double avgRating) {
    this.avgRating = avgRating;
  }
}
