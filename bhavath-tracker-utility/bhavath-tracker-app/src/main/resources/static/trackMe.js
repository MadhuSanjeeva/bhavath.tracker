TRACK_ME =
	{
		refreshInterval: (1 * 1 * 1000)
		, interval: null
		, autoRefresh: true
		, directionsService: null
		, tripId: null
		, tripRequestTime: null
		, citizenMobileNumber: null
		, vehicleNumber: null
		, isShared: false
		, firstRecord: false
		, shareMyRoute: false
		, appURL: "http://14.98.164.17:8099/v1/"
		, mapConfig:
		{
			initialized: false
			, trackMeId: null
			, map: null
			, trackStatus: null
			, latestGeoLocationId: 0
			, liveStatusRefreshInterval: (1 * 1 * 1000)
			, liveStatusInterval: null
			, infoWindow: null
			, geocoder: null
			, polylineCoordinates: []
			, markerCoordinates: []
			, array_marker: []
			, sourceDestiMarkers: []
			, polyLine: null
			, strokeColor: 'green'
			, strokeOpacity: 2
			, strokeWeight: 4
			, strokeWeight_highlight: 3
			// , mapBounds: null
			, startMarkerColor: 'blue'
			, normalMarkerColor: '#999999'
			, zIndex_marker: 1000
			, zIndex_polyline: 100
			, sourceCoordinates: null
			, destiCoordinates: null
			, destinationMarker: null
			, mapOptions:
			{
				center: null
				, mapTypeId: google.maps.MapTypeId.ROADMAP
				, mapTypeControlOptions: {}
				, zoom: 13
				, mapTypeControl: true
				, navigationControl: true
				, panControl: true
				, scaleControl: true
				, streetViewControl: true
				, overviewMapControl: true
				, draggable: true
				, streetViewControl: true
				, disableDefaultUI: true
				, clickableIcons: false
			}
			, addEvent: function (el, eventType, handler) {
				google.maps.event.addListener(el, eventType, handler);
			}
			, highlightPolyline: function (event, polyLine) {
				polyLine.orgZIndex = polyLine.zIndex;
				polyLine.setOptions(
					{
						strokeWeight: this.strokeWeight_highlight
						, zIndex: 500
					});
			}
			, unHighlightPolyline: function (event, polyLine) {
				polyLine.setOptions(
					{
						strokeWeight: this.strokeWeight
						, zIndex: polyLine.orgZIndex
					});
			}
		}
		, initMap: function () {
			var me = this;
			$('#endbutton').hide();
			$('#imeiNo').hide();
			$('#radius').hide();
			$('#source').hide();
			$('#end1').hide();
			$("#trackbutton").hide();
			$("#startTripButton").hide();
			$("#loader").hide();

			var autocomplete2 = new google.maps.places.Autocomplete(document.getElementById('end1'));
			var autocomplete1 = new google.maps.places.Autocomplete(document.getElementById('source'));
			autocomplete2.setComponentRestrictions({ 'country': ['in'] });
			autocomplete1.setComponentRestrictions({ 'country': ['in'] });

			var url_string = window.location.href;
			var url = new URL(url_string);
			// me.imeiNumber = url.searchParams.get("imeiNumber");
			me.tripId = url.searchParams.get("tripId");
			// me.isGeoFencing = url.searchParams.get("isGeoFencing");

			me.getAllImeiNumbers();

			if (me.tripId !== null) {
				$("#showcontrols").hide();
				me.isTripExist();
			}
			// google.maps.Polyline.prototype.getBounds = function (startBounds) {
			// 	if (startBounds) {
			// 		var bounds = startBounds;
			// 	}
			// 	else {
			// 		var bounds = new google.maps.LatLngBounds();
			// 	}
			// 	this.getPath().forEach(function (item, index) {
			// 		bounds.extend(new google.maps.LatLng(item.lat(), item.lng()));
			// 	});
			// 	return bounds;
			// };
			$('#endbutton').on('click', function () {
				$('#endbutton').hide();
				$("#loader").show();
				// me.imeiNumber = $('#imeiNo :selected').text();
				var data = {
					imeiNumber: me.imeiNumber,
					// tripClosed: true
				}
				console.log(data);
				$.ajax(
					{
						type: "POST",
						url: me.appURL + "endTrip",
						data: JSON.stringify(data),
						contentType: "application/json",
						dataType: 'json',
						success: function (data) {
							window.parent.postMessage(data, 'http://14.98.164.17:9999/')
							document.getElementById('end1').value = "";
							$('#startTripButton').prop("disabled", false).css('background-color', '#6742c0');
							me.detachLiveStatusEvent();
							$('#endbutton').hide();
							// me.isTripExist();
							// var url_string = window.location.href;
							// var url = new URL(url_string);
							// var mobile = url.searchParams.get("mobile");
							// if (mobile) window.location.href = url_string + '&tripEnded=tripEnded';
						},
						error: function (data) {
							window.parent.postMessage(data, 'http://14.98.164.17:9999/')
						}
					});
			});
			var sourceSearchBox = new google.maps.places.SearchBox(document.getElementById('source'));
			var destiSearchBox = new google.maps.places.SearchBox(document.getElementById('end1'));
			$('#startTripButton').on('click', function () {
				$('#startTripButton').hide();
				$("#loader").show();
				if (document.getElementById('end1').value && document.getElementById('end1').value) {
					var place = destiSearchBox.getPlaces();
					var lat = place[0].geometry.location.lat();
					var lng = place[0].geometry.location.lng();
					me.mapConfig.destiCoordinates = lat + "," + lng;
					var imeiNumber = $('#imeiNo :selected').text();
					var data =
					{
						// identifier: me.selectedType,
						sourceLatLang: me.mapConfig.sourceCoordinates,
						destiLatLang: me.mapConfig.destiCoordinates,
						imeiNumber: imeiNumber,
					}
					$.ajax(
						{
							type: "POST",
							url: me.appURL + "initiateTrip",
							data: JSON.stringify(data),
							contentType: "application/json",
							dataType: 'json',
							success: function (data) {
								window.parent.postMessage(data, 'http://14.98.164.17:9999/')
								$('#startTripButton').hide();
								console.log(data);
							},
							error: function (data) {
								window.parent.postMessage(data, 'http://14.98.164.17:9999/')
							}
						});
				}
				else {
					alert("Please enter the source and destination");
				}
			});
			$('#trackbutton').on('click', function () {
				$("#trackbutton").hide();
				$("#loader").show();
				me.radius = $("#radius").val();
				(me.srclatlng);
				me.geofencing(me.srclatlng, me.radius);
			});
			$('#imeiNo').change(function () {
				// var selectedImei = this.value;
				me.imeiNumber = this.value
				if(me.imeiNumber !== 'Select'){
					me.getSourceLatLng(me.imeiNumber);
				}
				else{
					$("#error_msg").text("");
				}
			});
			$('#triptype').change(function () {
				me.selectedType = this.value;
				console.log(me.selectedType);
				$("#imeiNo").show();
				$("#imeiNo").val('Select');
				$("#error_msg").text("");
				if (me.selectedType == '') {
					$("#radius").hide();
					$("#trackbutton").hide();
					$("#source").hide();
					$("#end1").hide();
					$("#startTripButton").hide();
					$("#endbutton").hide();
					$("#imeiNo").hide();
					$(this).css('border', '1px solid #ff0000');
				}
				else if (me.selectedType == 'GEOFENCING') {
					$("#radius").show();
					$("#trackbutton").hide();
					$("#source").hide();
					$("#end1").hide();
					$("#startTripButton").hide();
					$("#endbutton").hide();
				}
				else {
					$("#radius").hide();
					$("#trackbutton").hide();
					$("#source").show();
					$("#end1").show();
					$("#startTripButton").hide();
					$("#endbutton").hide();
				}
			});
		}
		, isTripExist: function () {
			var me = this;
			$.ajax(
				{
					type: "GET",
					url: me.appURL + "tripDetails?tripId=" + me.tripId,
					success: function (data) {
						var data = data.content;
						if (data.length !== 0) {
							$("#imeiNo").show();
							me.imeiNumber = data[0].imeiNumber;
							me.status = data[0].tripClosed;
							me.identifier = data[0].identifier;
							if (data[0].identifier == 'GEOFENCING') {
								me.radius = data[0].radius;
								$("#radius").show();
								$("#radius").val(me.radius);
								me.intilizeMapOnFirstCall(data[0].sourceLatLang.split(',')[0], data[0].sourceLatLang.split(',')[1]);
								me.mapConfig.sourceCoordinates = data[0].sourceLatLang;
								me.reverseGeocode(data[0].sourceLatLang, 1);
								me.attachLiveStatusEvent();
								me.geofencing(me.mapConfig.sourceCoordinates, me.radius);


							}
							else {
								$("#source").show();
								$("#end1").show();
								$("#startTripButton").show();
								me.intilizeMapOnFirstCall(data[0].sourceLatLang.split(',')[0], data[0].sourceLatLang.split(',')[1]);
								me.mapConfig.sourceCoordinates = data[0].sourceLatLang;
								me.mapConfig.destiCoordinates = data[0].destiLatLang;
								me.reverseGeocode(data[0].sourceLatLang, 1);
								me.reverseGeocode(data[0].destiLatLang, 2);
								me.calcRoute(me.mapConfig.sourceCoordinates, me.mapConfig.destiCoordinates);


								// me.mapConfig.map = new google.maps.Map(document.getElementById('map'), me.mapConfig.mapOptions);
								// var bontStart = new google.maps.LatLng(me.mapConfig.sourceCoordinates);
								// var bondEnd = new google.maps.LatLng(me.mapConfig.destiCoordinates);
								// var bounds = new google.maps.LatLngBounds();
								// bounds.extend(bontStart);
								// bounds.extend(bondEnd);
								// me.mapConfig.map.fitBounds(bounds)



							}
							$("#imeiNo").val(me.imeiNumber).attr("selected", true);
							$("#imeiNo").attr('disabled', true);
							$("#triptype").val(me.identifier);
							$("#triptype").attr('disabled', true);
						}
						else {
							alert("No Open Trips available");
						}
					},
					error: function (data) {
					}
				});
		},
		getSourceLatLng: function (imeiNumber) {
			var me = this;
			$.ajax(
				{
					type: "GET",
					url: this.appURL + "deviceCommunication?imeiNumber=" + imeiNumber,
					success: function (data, q) {
						if (data == null || data === "") {
							alert('Current location is not available.');
							$('#startTripButton').prop("disabled", true).css('background-color', '#777575');
							return;
						}
						var data = data.content;
						console.log(data[0].latitude);
						if (data[0].latitude > 0 && data[0].langitude > 0) {
							me.intilizeMapOnFirstCall(data[0].latitude, data[0].langitude);
							var srclatlng = data[0].latitude + ',' + data[0].langitude;
							me.mapConfig.sourceCoordinates = srclatlng;
							me.reverseGeocode(srclatlng, 1);
							me.srclatlng = srclatlng;
							$("#error_msg").text("");
							if (me.selectedType == 'GEOFENCING'){
								$("#trackbutton").show();
							}
							else if (me.selectedType == 'TRIP'){
								$("#startTripButton").show();
							}
							
						}
						else {
							$("#trackbutton").hide();
							$("#startTripButton").hide();
							$("#error_msg").text("No Lat Lng Available for this Device");
						}
					},
					error: function (data) {

					}
				});
		},
		getAllImeiNumbers: function () {
			var me = this;
			$.ajax(
				{
					type: "GET",
					url: this.appURL + "deviceCommunication?status=Communicating",
					success: function (data, q) {
						if (data == null || data === "") {
							alert('Current location is not available.');
							$('#startTripButton').prop("disabled", true).css('background-color', '#777575');
							return;
						}
						var data = data.content;
						$.each(data, function (i, item) {
							$('#imeiNo').append($('<option>', {
								value: item.imeiNumber,
								text: item.imeiNumber
							}));
						});
						// if(me.imeiNumber !== null){
						// 	$("#imeiNo").val(me.imeiNumber).attr("selected","selected");
						// 	$("#imeiNo").attr('disabled', true);
						// 	$("#end1").attr('readonly', true);
						// }
					},
					error: function (data) {

					}
				});
		}
		, initTravel: function (source, destination) {
			var me = this;
			$('#startTripButton').prop("disabled", true).css('background-color', '#777575');
			$('#endbutton').show();
			var data =
			{
				citizenMobileNumber: me.citizenMobileNumber,
				rcNumber: me.vehicleNumber,
				sourceLatLang: source,
				destiLatLang: destination,
			}
			$.ajax(
				{
					type: "POST",
					url: me.appURL + "initiateTrip",
					data: JSON.stringify(data),
					contentType: "application/json",
					dataType: 'json',
					success: function (data) {
						me.tripId = data.code
					},
					error: function (data) {
					}
				});
		}
		, intilizeMapOnFirstCall: function (lat, lng) {
			var me = this;
			me.mapConfig.geocoder = new google.maps.Geocoder();
			me.mapConfig.infoWindow = new google.maps.InfoWindow({ maxWidth: 300 });
			// me.mapConfig.mapBounds = new google.maps.LatLngBounds();
			me.mapConfig.mapOptions.center = new google.maps.LatLng(parseFloat(lat), parseFloat(lng));
			me.mapConfig.mapOptions.mapTypeControlOptions =
				{
					//google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.SATELLITE, google.maps.MapTypeId.HYBRID, google.maps.MapTypeId.TERRAIN
					//style		: google.maps.MapTypeControlStyle.DROPDOWN_MENU
					mapTypeIds: []
				};
			me.mapConfig.map = new google.maps.Map(document.getElementById('map'), me.mapConfig.mapOptions);
			var sourceMarker = me.makeMarker({ lat: parseFloat(lat), lng: parseFloat(lng) });
			me.mapConfig.addEvent(sourceMarker, 'click', function (event) { me.getGeocode(sourceMarker); });
			me.mapConfig.sourceDestiMarkers.push(sourceMarker);






		}
		, calcRoute: function (source, destination) {
			var me = this;
			me.directionsService = new google.maps.DirectionsService();
			var directionsDisplay = new google.maps.DirectionsRenderer({ map: me.mapConfig.map });
			var request =
			{
				origin: source,
				destination: destination,
				provideRouteAlternatives: true,
				unitSystem: google.maps.UnitSystem.METRIC,
				travelMode: google.maps.TravelMode['DRIVING']
			};
			me.directionsService.route(request, function (response, status) {
				for (var j in me.mapConfig.polylineCoordinates) {
					me.mapConfig.polylineCoordinates[j].setMap(null);
				}
				me.mapConfig.polylineCoordinates = [];
				if (status == google.maps.DirectionsStatus.OK) {
					// var bounds = new google.maps.LatLngBounds();
					me.clearMarkers();
					var length = response.routes[0].overview_path.length;

					var slatitude = response.routes[0].overview_path[0].lat();
					var slangitude = response.routes[0].overview_path[0].lng();
					// me.mapConfig.map.setCenter({ lat: slatitude, lng: slangitude });

					var sourceMarker = me.makeMarker({ lat: slatitude, lng: slangitude }, 'images/s.png');
					me.mapConfig.addEvent(sourceMarker, 'click', function (event) { me.getGeocode(sourceMarker); });

					//var dlatitude = response.routes[0].overview_path[length-1].lat();
					//var dlangitude = response.routes[0].overview_path[length-1].lng();

					var dlatitude = parseFloat(me.mapConfig.destiCoordinates.split(",")[0]);
					var dlangitude = parseFloat(me.mapConfig.destiCoordinates.split(",")[1]);

					var destMarker = me.makeMarker({ lat: dlatitude, lng: dlangitude }, 'images/d.png');
					me.mapConfig.addEvent(destMarker, 'click', function (event) { me.getGeocode(destMarker); });

					me.mapConfig.sourceDestiMarkers.push(sourceMarker);
					me.mapConfig.sourceDestiMarkers.push(destMarker);
					var point = response.routes[0].legs[0];
					for (var i = response.routes.length - 1; i >= 0; i--) {
						var color;
						if (i == 0) color = me.mapConfig.startMarkerColor;
						else color = me.mapConfig.normalMarkerColor;
						var line = me.drawPolyline(response.routes[i].overview_path, color);
						me.mapConfig.polylineCoordinates.push(line);
						// bounds = line.getBounds(bounds);
						google.maps.event.addListener(line, 'click', function () {
							var index = me.mapConfig.polylineCoordinates.indexOf(this);
							me.highlightRoute(index);
						});
					}
					// console.log(bounds);
					// me.mapConfig.map.fitBounds(bounds);
				}
			});
			me.attachLiveStatusEvent();
			// var url_string = window.location.href;
			// var url = new URL(url_string);
			// var mobile = url.searchParams.get("mobile");
			// if (mobile)
			// 	window.location.href = url_string + '&tripStarted=tripStarted';
		}
		, detachLiveStatusEvent() {
			var me = this;
			clearInterval(me.mapConfig.liveStatusInterval)
		}
		, attachLiveStatusEvent: function () {
			$("#radius").attr("readonly", true);
			$("#source").attr("readonly", true);
			$("#end1").attr("readonly", true);
			var me = this;
			if (!me.status) {
				$("#startTripButton").hide();
				$("#endbutton").show();
				me.mapConfig.liveStatusInterval = setInterval(function () {
					me.updateLiveStatus();
				}, me.mapConfig.liveStatusRefreshInterval);
			}
			else {
				if (me.identifier !== 'GEOFENCING') {
					me.mapConfig.map.setZoom(15);
				}
				me.updateLiveStatus();
				$("#radius").attr("readonly", true);
				$("#source").attr("readonly", true);
				$("#end1").attr("readonly", true);
				$("#startTripButton").hide();
				$("#endbutton").hide();
				$("#trackbutton").hide();
			}
		}
		, updateLiveStatus: function () {
			var me = this;
			$.ajax(
				{
					type: "GET",
					url: me.appURL + "tripTracks?tripId=" + me.tripId,
					// url	  : me.appURL + "tripTraceDetails?tripId=" + me.tripId + "&traceId=" + me.mapConfig.latestGeoLocationId ,
					success: function (data) {
						/*if (data.content != null &&  data.content.length > 0)
					{
						 var lastRecord = data.content;
						 me.mapConfig.latestGeoLocationId = data.content[0].sourceId;
						 var reversedRecords = data.content.reverse();
				 var latLngArray = [];
				 for (var i=0; i < reversedRecords.length; i++)
			 {
					 latLngArray.push({lat : parseFloat(reversedRecords[i].latitude),lng : parseFloat(reversedRecords[i].langitude)})
			 }
				 me.updatePolyline(latLngArray);
					}*/
						var data = data.content;
						if (data != null && data.length > 0) {
							var lastRecord = data;
							me.mapConfig.latestGeoLocationId = data[0].id;
							var reversedRecords = data.reverse();
							var latLngArray = [];
							for (var i = 0; i < reversedRecords.length; i++) {
								latLngArray.push({ lat: parseFloat(reversedRecords[i].latitude), lng: parseFloat(reversedRecords[i].longitude) })
							}
							// var from = me.mapConfig.sourceCoordinates;
							// var to = parseFloat(latLngArray[0].lat) + ',' + parseFloat(latLngArray[0].lng);


							const to = new google.maps.LatLng(parseFloat(me.mapConfig.sourceCoordinates.split(",")[0]), parseFloat(me.mapConfig.sourceCoordinates.split(",")[1]));
							const from = new google.maps.LatLng(parseFloat(parseFloat(latLngArray[0].lat)), parseFloat(latLngArray[0].lat));
							const distance = google.maps.geometry.spherical.computeDistanceBetween(from, to);
							me.updatePolyline(latLngArray);
							// me.updateMarker(latLngArray);
						}
					},
					error: function (data) {
						console.log(data);
					}
				});
		}
		, updatePolyline: function (latLngArray) {
			var me = this, i, coordinate;
			// me.mapConfig.infoWindow.close();
			me.mapConfig.sourceCoordinates = latLngArray[0].lat + "," + latLngArray[0].lng;
			me.updateMarker(latLngArray);
		}
		, createMarker: function (position, icon) {
			var me = this;
			var marker = new google.maps.Marker({
				position: position,
				icon: icon
			});
			return marker;
		}
		, updateMarker: function (latLngArray) {
			console.log(latLngArray);
			var me = this;
			var me = this, markerOption, marker, len, labelText, icon, source;
			for (var i = 0; i < me.mapConfig.array_marker.length; i++) {
				marker = me.mapConfig.array_marker[i];
				marker.setMap(null);
			}
			// source = new google.maps.LatLng(17.419557,78.400952);
			source = new google.maps.LatLng(parseFloat(me.mapConfig.sourceCoordinates.split(",")[0]), parseFloat(me.mapConfig.sourceCoordinates.split(",")[1]));
			var line = new google.maps.Polyline(
				{
					path: latLngArray,
					strokeColor: "black",
					strokeOpacity: 1.0,
					strokeWeight: 3,
					map: me.mapConfig.map
				});
			icon = 'images/ic_auto.png';
			icon = {
				path: "M17.402,0H5.643C2.526,0,0,3.467,0,6.584v34.804c0,3.116,2.526,5.644,5.643,5.644h11.759c3.116,0,5.644-2.527,5.644-5.644 V6.584C23.044,3.467,20.518,0,17.402,0z M22.057,14.188v11.665l-2.729,0.351v-4.806L22.057,14.188z M20.625,10.773 c-1.016,3.9-2.219,8.51-2.219,8.51H4.638l-2.222-8.51C2.417,10.773,11.3,7.755,20.625,10.773z M3.748,21.713v4.492l-2.73-0.349 V14.502L3.748,21.713z M1.018,37.938V27.579l2.73,0.343v8.196L1.018,37.938z M2.575,40.882l2.218-3.336h13.771l2.219,3.336H2.575z M19.328,35.805v-7.872l2.729-0.355v10.048L19.328,35.805z",
				scale: .7,
				strokeColor: 'yellow',
				strokeWeight: .10,
				fillOpacity: 1,
				fillColor: '#404040',
				offset: '5%',
				anchor: new google.maps.Point(10, 25),
				rotation: google.maps.geometry.spherical.computeHeading(new google.maps.LatLng(latLngArray[latLngArray.length - 2]), new google.maps.LatLng(latLngArray[latLngArray.length - 1]))
			}
			marker = me.createMarker(new google.maps.LatLng(latLngArray[latLngArray.length - 1]), icon);
			marker.setMap(me.mapConfig.map);
			me.mapConfig.array_marker.push(marker);
			// me.mapConfig.map.setCenter(latLngArray[0]);


			var data = {};
			var pointResult;



			for (var i = 0; i < me.mapConfig.polylineCoordinates.length; i++) {
				coordinate = new google.maps.LatLng(latLngArray[0]);
				var line = me.mapConfig.polylineCoordinates[i];
				//var pointResult = me.isLocationOnEdge(coordinate,line,300);
				var pointResult = google.maps.geometry.poly.isLocationOnEdge(coordinate, line, 0.001);
				if (!pointResult) {
					console.log("The location is not on route!");

					data = {};
					data.imeiNumber = me.imeiNumber;
					data.latLang = latLngArray[0].lat + "," + latLngArray[0].lng;

					$.ajax(
						{
							type: "POST",
							url: me.appURL + "routeDeviation",
							contentType: 'application/json',
							data: JSON.stringify(data),
							success: function (data) {

							},
							error: function (data) {
							}
						});
				}

			}
			if (!pointResult) {


			}
		}
		, highlightRoute: function (index) {
			var me = this;
			for (var j in me.mapConfig.polylineCoordinates) {
				if (j == index) color = mapConfig.startMarkerColor;
				else color = mapConfig.normalMarkerColor;
				me.mapConfig.polylineCoordinates[j].setOptions({ strokeColor: color });
			}
		}
		, drawPolyline: function (path, color) {
			var me = this;
			var line = new google.maps.Polyline(
				{
					path: path,
					strokeColor: color,
					strokeOpacity: me.mapConfig.strokeOpacity,
					strokeWeight: me.mapConfig.strokeWeight
				});
			line.setMap(me.mapConfig.map);
			return line;
		}
		, makeMarker: function (position, icon) {
			var me = this;
			var marker = new google.maps.Marker({
				position: position,
				icon: icon
			});
			if (me.identifier !== 'GEOFENCING') {
				marker.setMap(me.mapConfig.map);
			}

			return marker;
		}
		, clearMarkers: function () {
			var me = this;
			for (var i = 0; i < me.mapConfig.sourceDestiMarkers.length; i++) {
				me.mapConfig.sourceDestiMarkers[i].setMap(null);
			}
		}
		, getGeocode: function (marker) {
			var me = this, coordinate, manualAttributes, content = "", location;
			coordinate = marker.getPosition();
			me.mapConfig.geocoder.geocode({ 'latLng': coordinate },
				function (results, status) {
					content += '<center style="font-weight: bold; color: #F75850;">[ ' + coordinate.lat() + ' , ' + coordinate.lng() + ' ]</center><br style="line-height: 0px">';
					if (status == google.maps.GeocoderStatus.OK) {
						if (results[0]) {
							location = results[0].formatted_address;
						}
						else {
							location = 'No Result';
						}
					}
					else {
						location = 'Geocoder failed due to - ' + status;
					}
					content += '<span style="line-height:14px; font-weight: bold;">Location: </span>' + location;
					me.mapConfig.infoWindow.setContent(content);
					me.mapConfig.infoWindow.open(me.mapConfig.map, marker);
				});
		}
		, isLocationOnEdge: function (location, polyline, toleranceInMeters) {
			for (var leg of polyline.getPath().b) {
				if (google.maps.geometry.spherical.computeDistanceBetween(location, leg) >= toleranceInMeters) {
					return false;
				}
			}
			return true;
		}
		, reverseGeocode: function (latLng, type) {
			var me = this, location;
			var type = type;
			latLng = new google.maps.LatLng(parseFloat(latLng.split(",")[0]), parseFloat(latLng.split(",")[1]));
			me.mapConfig.geocoder.geocode({ 'latLng': latLng },
				function (results, status) {
					if (status == google.maps.GeocoderStatus.OK) {
						if (results[0]) {
							location = results[0].formatted_address;
							if (type === 1 || type == '1') {
								document.getElementById('source').value = location;
							}
							else {
								document.getElementById('end1').value = location;
							}
						}
						else {
							location = 'No Result';
						}
					}
					else {
						document.getElementById('source').value = '';
						location = 'Geocoder failed due to - ' + status;
					}
				});
		}
		, geofencing: function (srclatlng, selradius) {
			var me = this;
			var radius = selradius;
			var srclat = parseFloat(srclatlng.split(",")[0]);
			var srclng = parseFloat(srclatlng.split(",")[1]);
			var cityCircle = new google.maps.Circle({
				strokeColor: '#FF0000',
				strokeOpacity: 0.8,
				strokeWeight: 2,
				fillColor: '#FF0000',
				fillOpacity: 0.35,
				map: me.mapConfig.map,
				center: { lat: srclat, lng: srclng },
				radius: parseInt(radius)
			});

			if (me.tripId == null) {
				var data = {
					// identifier: me.selectedType,
					imeiNumber: me.imeiNumber,
					radius: me.radius,
					sourceLatLang: srclatlng
				}
				$.ajax(
					{
						type: "POST",
						url: me.appURL + "initiateTrip",
						data: JSON.stringify(data),
						contentType: "application/json",
						dataType: 'json',
						success: function (data) {
							window.parent.postMessage(data, 'http://14.98.164.17:9999/')
							console.log(data);
						},
						error: function (data) {
							window.parent.postMessage(data, 'http://14.98.164.17:9999/')
						}
					});
			}


		}
	}