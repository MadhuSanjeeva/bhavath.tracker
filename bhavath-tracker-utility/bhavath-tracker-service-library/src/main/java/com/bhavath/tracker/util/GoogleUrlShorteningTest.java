package com.bhavath.tracker.util;

public class GoogleUrlShorteningTest {

	public static void main(String[] args) {
		
		//http://103.210.74.131:8099/?vehicleNumber=AP31TF1630&citizenMobileNumber=7981937760

		String routeShareUrl = "https://apiabhaya.epragathi.org?";
		String rcNumber = "AP31TF1630";
		String citizenMobileNumber = "7981937760";
		
		String shortnURL = GoogleURLShortening.shortenUrl(routeShareUrl + "vehicleNumber=" + rcNumber + "&citizenMobileNumber=" + citizenMobileNumber + "&isShared=true");
		System.out.println("shortnURL " + shortnURL);

	}

}
