<style>
/***********************  Left navigation css ***********************/

hr {
  border-color: black;
  background-color: yellow;
  height: 1px;
}
.sidebar {
  float: left;
  width: 18%;
  margin-top: 5px;
  margin-left: 15px;
}

.sidebar h3 {
  padding-bottom: 0;
  font-size: 18px;
  color: #FCDD44;
  text-transform: uppercase;
  font-weight: bold;
  padding: 7px 7px;
  border-bottom: 1px solid #000000;
  background-color: #000000;
  font-family: 'Open Sans', sans-serif;
}

.sidebar ul {
  margin: 0;
  padding: 0;
  list-style: none;
}
/*E6E7E9*/
.sidebar ul li {
  background-color: #fff;
  margin-bottom: 20px;
  line-height: 1.9em;
}

.sidebar li ul {
  list-style: none;
  margin: 0px;
}

.sidebar li ul li {
  display: block;
  border-top: none;
  padding: 2px 7px 2px 7px;
  margin: 0;
  line-height: 1.5em;
  font-size: 15px;
}

.sidebar li ul li.text {
  display: block;
  border-bottom: none;
}

.sidebar li ul li a {
  display: block;
  font-weight: normal;
  text-decoration: none;
  font-weight: bold;
  color: black;
}

.sidebar li ul li a:hover {
  color: #000000;
  background-color: #E6E7E9;;
  text-decoration: none;
}

.clear {
  clear: both;
}

.fa-star-o:hover {
  color: black;
}

/***********************  Left navigation css ***********************/

</style>


<aside class="sidebar">
  <ul>
    <li>
      <h3><i class="fa fa-line-chart" aria-hidden="true"></i>&nbsp;&nbsp;Top Trends</h3>
      <ul>
        <li><a href="Trending"><h4><b>&nbsp;&nbsp;Trending</b></h4></a></li>
        <!--
          <li><a href="InventoryReport"><h4><b>&nbsp;&nbsp;Inventory Report</b></h4></a></li>
          <li><a href="RevenueReport"><h4><b>&nbsp;&nbsp;Revenue Report</b></h4></a></li>
        -->
      </ul>
    </li>
    <li>
      <h3><i class="fa fa-filter"></i> &nbsp;&nbsp;Search Filters</h3>
      <h4> &nbsp;&nbsp;&nbsp;<b>Hotel Name</b></h4>
      <ul>
      <li>
          <div name="autofillform" style=" margin: auto; position: relative;">
                <input type="text" name="searchId" value="" id="searchId" onkeyup="doCompletion()"
                 placeholder="Enter Hotel Name" style="width:100%;" />
                    <div id="auto-row">
                        <table id="complete-table" class="gridtable" style="position: absolute; width:100%;"></table>
                    </div>
          </div>
      </li>
      </ul>
      <hr>
      <h4>&nbsp;&nbsp;&nbsp;<b>City</b></h4>
      <ul>
        <li>
          <select style="width:100%; height:30px;" id="searchCity" name="searchCity"  onchange="doCitySearch()">
            <option value="0" style="width:100%; height:30px">Select City</option>
            <option value="Chicago" style="width:100%; height:30px">Chicago</option>
            <option value="New York" style="width:100%; height:30px">New York</option>
            <option value="Seattle" style="width:100%; height:30px">Seattle</option>
            <option value="Los Angeles" style="width:100%; height:30px">Los Angeles</option>
            <option value="Pittsburgh" style="width:100%; height:30px">Pittsburgh</option>
          </select>
        </li>
      </ul>
  <hr>
      <h4> &nbsp;&nbsp;&nbsp;<b>Zip Code</b></h4>
      <ul>
        <li>
            <div name="autofillform" style=" margin: auto; position: relative;">
                  <input type="text" name="searchZip" value="" id="searchZip" onkeyup="doCompletion()"
                   placeholder="Enter Zip code" style="width:100%;" />
                      <div id="auto-row">
                          <table id="complete-table-1" class="gridtable" style="position: absolute; width:100%;"></table>
                      </div>
            </div>
        </li>
      </ul>
  <hr>
      <h4> &nbsp;&nbsp;&nbsp;<b>Nightly Price</b></h4>
      <ul>
        <li>
          <fieldset class="" data-filter-name="price" id="filter-price">
            <div id="filter-price-contents" class="filter-contents">
              <input name="f-price-currency-code" type="hidden" value="USD"><input name="f-price-multiplier" type="hidden" value="1">
                <div class="input-wrapper">
                  <label id="f-price-min-label"> &nbsp;Min ($)</label>
                    <select id="minVal" name="minVal"  onchange="minValSearch()">
                      <option value="50" selected>50</option>
                      <option value="100">100</option>
                      <option value="150">150</option>
                      <option value="200">200</option>
                      <option value="250">250</option>
                      <option value="300">300</option>
                      <option value="350">350</option>
                      <option value="400">400</option>
                    </select>&nbsp;&nbsp;&nbsp;
                  <label id="f-price-max-label">Max ($)</label>
                  <select id="maxVal" name="maxVal"  onchange="minValSearch()">
                    <option value="300">300</option>
                    <option value="350">350</option>
                    <option value="400">400</option>
                    <option value="450">450</option>
                    <option value="500">500</option>
                    <option value="550">550</option>
                    <option value="600">600</option>
                    <option value="650">650</option>
                    <option value="700">700</option>
                    <option value="750">750</option>
                    <option value="800">800</option>
                    <option value="850">850</option>
                    <option value="900">900</option>
                    <option value="950">950</option>
                    <option value="1000" selected>1000</option>
                  </select>
          </div>
        </div>
      </fieldset>

    </li>
  <hr>
    <h4> &nbsp;&nbsp;&nbsp;<b>Star Rating</b></h4>
    <ul>
      <li>&nbsp;
        <button name="searchRating"  onclick="doRatingSearch(this.value)" value="1">1<i id="1" class="fa fa-star" style="color:red"></i></button>
        <button name="searchRating"  onclick="doRatingSearch(this.value)" value="2">2<i id="2" class="fa fa-star" style="color:red"></i></button>
        <button name="searchRating"  onclick="doRatingSearch(this.value)" value="3">3<i id="3" class="fa fa-star" style="color:red"></i></button>
        <button name="searchRating"  onclick="doRatingSearch(this.value)" value="4">4<i id="4" class="fa fa-star" style="color:red"></i></button>
        <button name="searchRating"  onclick="doRatingSearch(this.value)" value="5">5<i id="5" class="fa fa-star" style="color:red"></i></button>
      </li>
    </ul>

    <hr>
      <h4>&nbsp;&nbsp;&nbsp;<b>Amenities</b></h4>
      <ul>
        <li>
          &nbsp;&nbsp;<input type="checkbox" name="disabilityCare" id="disabilityCare" onchange="amenitiesSearch()">&nbsp;&nbsp; Disability Care </input><br>
          &nbsp;&nbsp;<input type="checkbox" name="flight" id="flight" onchange="amenitiesSearch()" >&nbsp;&nbsp; Airport Transfers</input><br>
          &nbsp;&nbsp;<input type="checkbox" name="localTour" id="localTour" onchange="amenitiesSearch()">&nbsp;&nbsp; Local Tour </input><br>
          &nbsp;&nbsp;<input type="checkbox" name="carRental" id="carRental" onchange="amenitiesSearch()">&nbsp;&nbsp; Car Rental </input><br>
          &nbsp;&nbsp;<input type="checkbox" name="pool" id="pool" onchange="amenitiesSearch()">&nbsp;&nbsp; Pool </input><br>
          &nbsp;&nbsp;<input type="checkbox" name="gym" id="gym" onchange="amenitiesSearch()">&nbsp;&nbsp; Gym </input><br>
        </li>
      </ul>
    <hr>

  </ul>
</li>

</ul>
</aside>
<div class="clear"></div>
