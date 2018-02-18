<!-- Banner -->
<section class="tm-banner">
  <!-- Flexslider -->
  <div class="flexslider flexslider-banner">
    <ul class="slides">
      <li>
        <div class="tm-banner-inner">
          <h1 class="tm-banner-title">Find <span class="tm-yellow-text">The Best</span> Hotel</h1>
          <p class="tm-banner-subtitle">For Your Holidays</p>
          <a href="DisplayHotels" class="tm-banner-link">Learn More</a>
        </div>
        <img src="img/home/banner-1.jpg" alt="Image" />
      </li>
      <li>
        <div class="tm-banner-inner">
          <h1 class="tm-banner-title">Hotel deals for <span class="tm-yellow-text">$99 or less</span> </h1>
          <p class="tm-banner-subtitle">Book now!</p>
          <a href="AutoComplete?action=pricedealslookup&minVal=10&maxVal=99" class="tm-banner-link">Learn More</a>
        </div>
        <img src="img/home/banner-2.jpg" alt="Image" />
      </li>
      <li>
        <div class="tm-banner-inner">
          <h1 class="tm-banner-title">Most <span class="tm-yellow-text">Popular</span> Hotels</h1>
          <p class="tm-banner-subtitle">5 &bigstar; rating</p>
          <a href="AutoComplete?action=5ratinglookup&rating=5"  class="tm-banner-link">Learn More</a>
        </div>
        <img src="img/home/banner-3.jpg" alt="Image" />
      </li>
    </ul>
  </div>
</section>
<!-- ************************************************************************************************************************ -->

<!-- gray bg -->
<section class="container tm-home-section-1" id="more">
  <div class="row">
    <div class="col-lg-4 col-md-4 col-sm-6">
      <!-- Nav tabs -->
      <div class="tm-home-box-1">
        <div class="nav nav-tabs tm-white-bg">
          <br>
          <h1 style="text-align:center">HOTELS</h1>
            <br>
        </div>

        <!-- Tab panes -->
        <div class="tab-content">
          <div role="tabpanel" class="tab-pane fade in active tm-white-bg" id="hotel">
            <div class="tm-search-box effect2">
              <form action="SearchHotel" method="get" class="hotel-search-form">
                <div class="tm-form-inner">
                  <div class="form-group" name="autofillform" style=" margin: auto; position: relative;">
                      <input type='text' class="form-control" placeholder="Hotel, City, or Zipcode" name="homeSearch" value="" id="homeSearch" onkeyup="doAutoCompletion();" required />
                          <div id="auto-row">
                              <table id="complete-table2" class="gridtable" style="position: absolute; width:100%;"></table>
                          </div>
                  </div>
                  <br>
                  <div class="form-group">
                    <div class='input-group date' id='datetimepicker1'>
                      <input type='text' class="form-control" placeholder="Check-in Date" name="checkin"/>
                      <span class="input-group-addon">
                                    <span class="fa fa-calendar"></span>
                      </span>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class='input-group date' id='datetimepicker2'>
                      <input type='text' class="form-control" placeholder="Check-out Date" name="checkout" />
                      <span class="input-group-addon">
                              <span class="fa fa-calendar"></span>
                      </span>
                    </div>
                  </div>
                  <div class="form-group margin-bottom-0">
                    <select class="form-control" name="roomNum">
                      <option value="0">--  Rooms  -- </option>
                      <option value="1">1</option>
                      <option value="2">2</option>
                      <option value="3">3</option>
                      <option value="4">4</option>
                      <option value="5">5</option>
                      <option value="6">6</option>
                      <option value="7">7</option>
                      <option value="8">8</option>
                      <option value="9">9</option>
                      <option value="10">10</option>

                    </select>
                  </div>
                  <div class="form-group margin-bottom-0">
                    <select class="form-control" name="numOfAdults">
                      <option value="0">--  Adults (18+)  -- </option>
                      <option value="1">1</option>
                      <option value="2">2</option>
                      <option value="3">3</option>
                      <option value="4">4</option>
                      <option value="5">5</option>
                    </select>
                  </div>
                  <div class="form-group margin-bottom-0">
                    <select class="form-control" name="numOfChildren">
                      <option value="0">--  Children -- </option>
                      <option value="0">0</option>
                      <option value="1">1</option>
                      <option value="2">2</option>
                      <option value="3">3</option>
                      <option value="4">4</option>
                      <option value="5">5</option>
                    </select>
                  </div>
                </div>
                <div class="form-group tm-yellow-gradient-bg text-center">
                  <button type="submit" name="submit" class="tm-yellow-btn"><b>Check Now</b></button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  <!-- **************************************************************************************************************-->
    <div>

      <div class="tm-section-header tm-yellow-gradient-bg">
          <h2 class="tm-section-title" style="text-align:center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Top Destinations in United States</h2>
      </div>
