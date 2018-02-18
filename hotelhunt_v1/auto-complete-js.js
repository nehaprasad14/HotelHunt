var req;
var isIE;
var completeField;
var completeTable;
var autoRow;
var searchRating;
var searchZip;
var searchCity;
var hname;
var homeSearch;

function init() {
    homeSearch = document.getElementById("homeSearch");
    completeField = document.getElementById("searchId");
    searchCity = document.getElementById("searchCity");
    searchZip = document.getElementById("searchZip");
    completeTable = document.getElementById("complete-table");
    completeTable1 = document.getElementById("complete-table-1");
    completeTable2 = document.getElementById("complete-table2");
    autoRow = document.getElementById("auto-row");
    minVal = document.getElementById("minVal");
    maxVal = document.getElementById("maxVal");
    disabilityCare = document.getElementById("disabilityCare");
    flight = document.getElementById("flight");
    localTour = document.getElementById("localTour");
    carRental = document.getElementById("carRental");
    pool = document.getElementById("pool");
    gym = document.getElementById("gym");
    hname = document.getElementById("hname");
}

function doAutoCompletion(){
    var url = "";
    if(escape(homeSearch.value)!="")
       url = "HomeSearchAutoComplete?action=complete&id=" + escape(homeSearch.value);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = autocallback;
    req.send(null);
}

function doCompletion() {
  var url = "";
    if(escape(searchZip.value)!="")
       url = "AutoComplete?action=complete&type=zipcode&id=" + escape(searchZip.value);
    else
       url = "AutoComplete?action=complete&type=name&id=" + escape(searchId.value);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}

function amenitiesSearch() {
    //alert(disabilityCare.checked+" ... "+flight.checked+" ... "+localTour.checked+" ... "+carRental.checked+" ... "+pool.checked+" ... "+gym.checked);
    var url = "AutoComplete?action=amenitieslookup&disabilityCare=" + escape(disabilityCare.checked)+"&flight=" + escape(flight.checked)+"&localTour=" + escape(localTour.checked)+"&carRental=" + escape(carRental.checked)+"&pool=" + escape(pool.checked)+"&gym=" + escape(gym.checked);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = loadSearchData;
    req.send(null);
}

function doCitySearch() {
    var url = "AutoComplete?action=citylookup&searchId=" + escape(searchCity.value);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = loadSearchData;
    req.send(null);
}

function doRoomSearch() {
    var url = "RoomSearch?action=roomlookup&searchId=" + escape(hname.value);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = loadRoomData;
    req.send();
}

function doRatingSearch(rating) {
    searchRating = rating;
    var url = "AutoComplete?action=ratinglookup&rating=" + escape(searchRating);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = loadSearchData;
    //document.getElementById(rating).style.backgroundColor = "transparent";
    req.send(null);
}

function minValSearch(){
    var url = "AutoComplete?action=pricelookup&minVal=" + escape(minVal.value)+"&maxVal=" + escape(maxVal.value);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = loadSearchData;
    req.send(null);
}

function doLookupSearch(action , id) {
//  alert(action +".."+id);
    var url = "AutoComplete?action=lookup&searchId=" + id;
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = loadSearchData;
    clearTable();
    req.send(null);

}

function doLookupSearch1(id) {
  document.getElementById("homeSearch").value = id;
  clearTable2();
}

function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function appendHotel(hotelName, hotelID) {
    var row;
    var cell;
    var linkElement;

    if(escape(searchZip.value)!=""){
      if(isIE){
          completeTable1.style.display = 'block';
          row = completeTable1.insertRow(completeTable1.rows.length);
          cell = row.insertCell(0);
      }else{
          completeTable1.style.display = 'table';
          completeTable1.style.position = 'absolute';
          completeTable1.style.width = '100%';
          completeTable1.style.background = '#fff';
          row = document.createElement("tr");
          cell = document.createElement("td");
          row.appendChild(cell);
          completeTable1.appendChild(row);
      }
    }else{
          if(isIE){
              completeTable.style.display = 'block';
              row = completeTable.insertRow(completeTable.rows.length);
              cell = row.insertCell(0);
          }else{
              completeTable.style.display = 'table';
          		completeTable.style.position = 'absolute';
          		completeTable.style.width = '100%';
          		completeTable.style.background = '#fff';
              row = document.createElement("tr");
              cell = document.createElement("td");
              row.appendChild(cell);
              completeTable.appendChild(row);
          }
    }

    cell.className = "popupCell";
    linkElement = document.createElement("a");
    linkElement.className = "popupItem";
    linkElement.style.color = 'black';

    if(escape(searchZip.value)!=""){
      linkElement.setAttribute("onClick","doLookupSearch('lookup','"+hotelName+"');");
    }else{
      linkElement.setAttribute("onClick","doLookupSearch('lookup','"+hotelID+"');");
    }
    linkElement.appendChild(document.createTextNode(hotelName));
    cell.appendChild(linkElement);
}

function parseMessages(responseXML) {
    if (responseXML == null) {
        return false;
    } else {
        var hotels = responseXML.getElementsByTagName("hotels")[0];
        if (hotels.childNodes.length > 0) {
          if(escape(searchZip.value)!=""){
            completeTable1.setAttribute("bordercolor", "black");
            completeTable1.setAttribute("border", "1");
          }else{
            completeTable.setAttribute("bordercolor", "black");
            completeTable.setAttribute("border", "1");
          }
            for (loop = 0; loop < hotels.childNodes.length; loop++) {
                var hotel = hotels.childNodes[loop];
                var hotelName = hotel.getElementsByTagName("hotelName")[0];
                var hotelID = hotel.getElementsByTagName("hotelID")[0];
                appendHotel(hotelName.childNodes[0].nodeValue,
                    hotelID.childNodes[0].nodeValue);
            }
        }
    }
}

function callback() {
    clearTable();
    if (req.readyState == 4) {
        if (req.status == 200) {
            parseMessages(req.responseXML);
        }
    }
}

function appendHotel1(hotelName, hotelID) {
    var row;
    var cell;
    var linkElement;
    if(isIE){
          completeTable2.style.display = 'block';
          row = completeTable2.insertRow(completeTable2.rows.length);
          cell = row.insertCell(0);
    }else{
          completeTable2.style.display = 'table';
          completeTable2.style.position = 'absolute';
          completeTable2.style.width = '100%';
          completeTable2.style.background = '#fff';
          completeTable2.style.zIndex = "1000";
          row = document.createElement("tr");
          cell = document.createElement("td");
          row.appendChild(cell);
          completeTable2.appendChild(row);
      }
    cell.className = "popupCell";
    linkElement = document.createElement("a");
    linkElement.className = "popupItem";
    linkElement.style.color = 'black';

    linkElement.setAttribute("onClick","doLookupSearch1('"+hotelName+"');");
    linkElement.appendChild(document.createTextNode(hotelName));
    cell.appendChild(linkElement);
}

function parseMessages1(responseXML) {
    if (responseXML == null) {
        return false;
    } else {
        var hotels = responseXML.getElementsByTagName("hotels")[0];
        if (hotels.childNodes.length > 0) {
            completeTable2.setAttribute("bordercolor", "black");
            completeTable2.setAttribute("border", "1");

            for (loop = 0; loop < hotels.childNodes.length; loop++) {
                var hotel = hotels.childNodes[loop];
                var hotelName = hotel.getElementsByTagName("hotelName")[0];
                var hotelID = hotel.getElementsByTagName("hotelID")[0];
                appendHotel1(hotelName.childNodes[0].nodeValue,
                    hotelID.childNodes[0].nodeValue);
            }
        }
    }
}

function autocallback() {
    clearTable2();
    if (req.readyState == 4) {
        if (req.status == 200) {
            parseMessages1(req.responseXML);
        }
    }
}

function loadSearchData() {
    if (req.readyState == 4) {
        if (req.status == 200) {
          document.getElementById("container1").innerHTML=req.responseText;
        }
    }
}

function loadRoomData() {
    if (req.readyState == 4) {
        if (req.status == 200) {
          document.getElementById("room-list").innerHTML=req.responseText;
        }
    }
}

function clearTable2() {
    if (completeTable2.getElementsByTagName("tr").length > 0) {
        completeTable2.style.display = 'none';
        for (loop = completeTable2.childNodes.length -1; loop >= 0 ; loop--) {
            completeTable2.removeChild(completeTable2.childNodes[loop]);
        }
    }
}

function clearTable() {
  if(escape(searchZip.value)!=""){
    if (completeTable1.getElementsByTagName("tr").length > 0) {
        completeTable1.style.display = 'none';
        for (loop = completeTable1.childNodes.length -1; loop >= 0 ; loop--) {
            completeTable1.removeChild(completeTable1.childNodes[loop]);
        }
    }
  }else{
    if (completeTable.getElementsByTagName("tr").length > 0) {
        completeTable.style.display = 'none';
        for (loop = completeTable.childNodes.length -1; loop >= 0 ; loop--) {
            completeTable.removeChild(completeTable.childNodes[loop]);
        }
    }
  }
}
