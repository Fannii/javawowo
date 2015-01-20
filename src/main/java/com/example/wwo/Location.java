package com.example.wwo;

public class Location {

	public static void validateLocation(LocationType type, String location)
			throws IllegalArgumentException {
		switch (type) {
		case CITY:
			if (!location.matches("^[a-zA-Z0-9, ]*$")) {
				throw new IllegalArgumentException(
						"With CITY type the location should contains only letters, numbers, comma and space.");
			}
			break;
		case IP_ADDRESS:
			if (!location
					.matches("^(([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])[.]([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])[.]([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5])[.]([01]?[0-9][0-9]?|2[0-4][0-9]|25[0-5]))$")) {
				throw new IllegalArgumentException(
						"IP address need to match XXX.XXX.XXX.XXX where all XXX are between 0 and 255");
			}
			break;
		case UK_POSTAL_CODE:
			if (!location
					.matches("[A-Z]{1,2}[0-9R][0-9A-Z]? ?[0-9][A-Z-[CIKMOV]]{2}")) {
				throw new IllegalArgumentException("Invalid UK postal code");
			}
			break;
		case CANADA_POSTAL_CODE:
			if (!location
					.matches("[ABCEFGHJKLMNPRSTVXY][0-9][ABCEFGHJKLMNPRSTVWXYZ] ?[0-9][ABCEFGHJKLMNPRSTVWXYZ][0-9]")) {
				throw new IllegalArgumentException("Invalid Canada postal code");
			}
			break;
		case US_ZIPCODE:
			if (!location.matches("^[0-9]{5}(?:-[0-9]{4})?$")) {
				throw new IllegalArgumentException("Invalid US zip code");
			}
			break;
		case LAT_AND_LONG:
			if (!location
					.matches("[-+]?([1-8]?[0-9](.[0-9]{3})?|90(.0+)?),[ \t\n\b\r\f]*[-+]?(180(.0+)?|((1[0-7][0-9])|([1-9]?[0-9]))(.[0-9]{3})?)")) {
				throw new IllegalArgumentException("Invalid Canada postal code");
			}
			break;
		}
	}

	/**
	 * UK: [A-Z]{1,2}[0-9R][0-9A-Z]? ?[0-9][A-Z-[CIKMOV]]{2} Canada:
	 * [ABCEFGHJKLMNPRSTVXY][0-9][ABCEFGHJKLMNPRSTVWXYZ]
	 * ?[0-9][ABCEFGHJKLMNPRSTVWXYZ][0-9] US: ^[0-9]{5}(?:-[0-9]{4})?$ lat and
	 * long:
	 * [-+]?([1-8]?\d(\.\d{3})?|90(\.0+)?),\s*[-+]?(180(\.0+)?|((1[0-7]\d)|
	 * ([1-9]?\d))(\.\d{3})?)
	 */

	/**
	 * Location type
	 * 
	 * @author Fanni_Varga
	 * 
	 */
	public static enum LocationType {
		/**
		 * City Name City Name, State (US only) City Name, State, Country City
		 * Name, Country
		 */
		CITY,
		/**
		 * IP Address XXX.XXX.XXX.XXX
		 */
		IP_ADDRESS,
		/**
		 * UK postal code
		 */
		UK_POSTAL_CODE,
		/**
		 * Canada postal code
		 */
		CANADA_POSTAL_CODE,
		/**
		 * US zipcode
		 */
		US_ZIPCODE,
		/**
		 * Latitude and longitude
		 */
		LAT_AND_LONG
	}
}
