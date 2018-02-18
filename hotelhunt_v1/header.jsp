<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>HotelHunt</title>
  <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,400italic,600,700' rel='stylesheet' type='text/css'>
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css' >
  <link href="css/font-awesome.min.css" rel="stylesheet">
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet">
  <link href="css/flexslider.css" rel="stylesheet">
  <link href="css/templatemo-style.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>      		<!-- jQuery -->
    <script type="text/javascript" src="js/moment.js"></script>							<!-- moment.js -->
  	<script type="text/javascript" src="js/bootstrap.min.js"></script>					<!-- bootstrap js -->
  	<script type="text/javascript" src="js/bootstrap-datetimepicker.min.js"></script>	<!-- bootstrap date time picker js, http://eonasdan.github.io/bootstrap-datetimepicker/ -->
  	<script type="text/javascript" src="js/jquery.flexslider-min.js"></script>
  <!--
  	<script src="js/froogaloop.js"></script>
  	<script src="js/jquery.fitvid.js"></script>
  -->
     <script type="text/javascript" src="js/templatemo-script.js"></script>      		<!-- Templatemo Script -->
  	<script>
  		// HTML document is loaded. DOM is ready.
  		$(function() {

  			$('#hotelCarTabs a').click(function (e) {
  			  e.preventDefault()
  			  $(this).tab('show')
  			})

          	$('.date').datetimepicker({
              	format: 'MM/DD/YYYY'
              });
              $('.date-time').datetimepicker();

  			// https://css-tricks.com/snippets/jquery/smooth-scrolling/
  		  	$('a[href*=#]:not([href=#])').click(function() {
  			    if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
  			      var target = $(this.hash);
  			      target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
  			      if (target.length) {
  			        $('html,body').animate({
  			          scrollTop: target.offset().top
  			        }, 1000);
  			        return false;
  			      }
  			    }
  		  	});
  		});

  		// Load Flexslider when everything is loaded.
  		$(window).load(function() {

      //	For images only
  		    $('.flexslider').flexslider({
  			    controlNav: false
  		    });


  	  	});
  	</script>
      <script type="text/javascript" src="auto-complete-js.js"></script>
<!--    <script type="text/javascript">
    !function(t,e){"use strict";var r=function(t){try{var r=e.head||e.getElementsByTagName("head")[0],a=e.createElement("script");a.setAttribute("type","text/javascript"),a.setAttribute("src",t),r.appendChild(a)}catch(t){}};
      t.CollectId = "5a0b25fd65ab29daf260b6e0",r("https://s3.amazonaws.com/collectchat/launcher.js")}(window,document);
    </script>
-->

<script type="text/javascript">!function(t,e){"use strict";var r=function(t){try{var r=e.head||e.getElementsByTagName("head")[0],a=e.createElement("script");a.setAttribute("type","text/javascript"),a.setAttribute("src",t),r.appendChild(a)}catch(t){}};t.CollectId = "5a0b25fd65ab29daf260b6e0",r("https://s3.amazonaws.com/collectchat/launcher.js")}(window,document);</script>
  </head>
  <body class="tm-gray-bg" onload="init()">


  	<!-- Header -->
  	<div class="tm-header">
  		<div class="container">
  			<div class="row">
  				<div class="col-lg-3 col-md-3 col-sm-3 tm-site-name-container">
  					<a href="Home" class="tm-site-name">Hotel Hunt</a>
  				</div>
	  			<div class="col-lg-9 col-md-9 col-sm-9">

	  				<nav class="tm-nav">
						<ul>
							<li><a href="DisplayHotels">Hotels</a></li>
							<li><a href="DisplayDeals">Deals</a></li>
              <li><a href="DisplayPackages">Packages </a></li>
            <!--  <li><a href="#">Contact Us</a></li>-->
