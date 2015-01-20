package com.example.wwo.search;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.example.wwo.Location;
import com.example.wwo.Request;
import com.example.wwo.Location.LocationType;

public class Search {
	protected static final String HOST_URL = "api.worldweatheronline.com";
	protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private Map<String, String> parameters;
	private PackageType packageType;

	/**
	 * TODO: description
	 * 
	 * Location type: - City or town name Examples: "New York" "New York, NY"
	 * "London, United Kingdom" - IP address Example: "101.25.32.325" - UK or
	 * Canada Postal Code or US Zipcode Examples: "SW1" "90201" - Latitude and
	 * longitude Example: "48.834,2.394"
	 * 
	 * @param packageType
	 *            the package type
	 * @param key
	 *            the API key.
	 * @param locationType
	 *            the location type.
	 * @param location
	 *            the location.
	 * @throws IllegalArgumentException
	 *             if location is not valid. TODO: numberOfDays validation
	 */
	public Search(PackageType packageType, String key,
			LocationType locationType, String location)
			throws IllegalArgumentException {
		Location.validateLocation(locationType, location);
		this.packageType = packageType;
		this.parameters = new HashMap<String, String>();
		parameters.put("key", key);
		parameters.put("query", location);
	}

	/**
	 * TODO
	 * 
	 * @param scheme
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public Request buildRequestAttributes(String scheme) throws IOException,
			URISyntaxException {
		Request ret = new Request(parameters, packageType.getBaseUrl(),
				HOST_URL, scheme);
		return ret;
	}

	/**
	 * The number of results to return. In Free API: Default is 3, Maximum is 3.
	 * In Premium API: Default is 10, Maximum is 50.
	 * 
	 * @param numOfResults
	 *            the number of results to return.
	 * @return a reference to this object.
	 * @throws IllegalArgumentException
	 *             if numOfResults is not correct.
	 */
	public Search setNumOfResults(int numOfResults)
			throws IllegalArgumentException {
		if (packageType.equals(PackageType.FREE)
				&& (numOfResults > 3 || numOfResults < 1)) {
			throw new IllegalArgumentException(
					"NumOfResults must be between 1 and 3 (in Free API");
		} else if (packageType.equals(PackageType.PREMIUM)
				&& (numOfResults > 50 || numOfResults < 1)) {
			throw new IllegalArgumentException(
					"NumOfResults must be between 1 and 50 (in Premium API");
		} else {
			parameters.put("num_of_results", String.valueOf(numOfResults));
		}
		return this;
	}

	/**
	 * Whether to return offset hours from GMT for each location. Default is
	 * false.
	 * 
	 * @param timezone
	 *            If timezone is needed true, else false.
	 * @return a reference to this object.
	 */
	public Search needTimeZone(boolean timezone) {
		parameters.put("timezone", timezone ? "yes" : "no");
		return this;
	}

	/**
	 * Whether to only search for popular locations, such as major cities.
	 * 
	 * @param popular
	 *            If only search for popular locations true, else false.
	 * @return a reference to this object.
	 */
	public Search onlyPopularLocations(boolean popular) {
		parameters.put("popular", popular ? "yes" : "no");
		return this;
	}

	/**
	 * The output format to return. XML, JSON or CSV (comma-separated values).
	 * Default is XML.
	 * 
	 * @param format
	 *            the format to return.
	 * @return a reference to this object.
	 */
	public Search setFormat(Format format) {
		parameters.put("format", format.getValue());
		return this;
	}

	/**
	 * Returns nearest locations for the type of category provided.
	 * 
	 * @param category
	 *            the category.
	 * @return a reference to this object.
	 */
	public Search setTypeOfCategory(Category category) {
		parameters.put("wct", category.getValue());
		return this;
	}

	/**
	 * The API Package type.
	 * 
	 * @author Fanni_Varga
	 * 
	 */
	public static enum PackageType {
		/**
		 * Premium API.
		 */
		PREMIUM("/premium/v1/search.ashx"),
		/**
		 * Free API.
		 */
		FREE("/free/v2/search.ashx");
		private String baseUrl;

		private PackageType(String baseUrl) {
			this.baseUrl = baseUrl;
		}

		protected String getBaseUrl() {
			return baseUrl;
		}
	}

	/**
	 * Extra information that can be included.
	 * 
	 * @author Fanni_Varga
	 * 
	 */
	public static enum Extra {
		/**
		 * Adds yes for day and no for night time period. Note: This parameter
		 * only works with 3 hourly, 6 hourly or 12 hourly intervals.
		 */
		IS_DAY_TIME("isDayTime"),
		/**
		 * Time intervals are displayed in UTC format instead of locate date and
		 * time.
		 */
		UTC_DATE_TIME("utcDateTime"),
		/**
		 * Adds the current weather observation time in UTC as well as local
		 * time of the location requested.
		 */
		LOCAL_OBS_TIME("localObsTime");

		private String value;

		private Extra(String value) {
			this.value = value;
		}

		protected String getValue() {
			return value;
		}
	}

	/**
	 * The output format to return. This program supports only XML and JSON
	 * formats.
	 * 
	 * @author Fanni_Varga
	 * 
	 */
	public static enum Format {
		/**
		 * XML
		 */
		XML("xml"),
		/**
		 * JSON
		 */
		JSON("json"),
		/**
		 * TAB
		 */
		TAB("tab");

		private String value;

		private Format(String value) {
			this.value = value;
		}

		protected String getValue() {
			return value;
		}

	}

	/**
	 * Category
	 * 
	 * @author Fanni_Varga
	 * 
	 */
	public static enum Category {
		/**
		 * Ski
		 */
		SKI("Ski"),
		/**
		 * Cricket
		 */
		CRICKET("Cricket"),
		/**
		 * Football
		 */
		FOOTBALL("Football"),
		/**
		 * Golf
		 */
		GOLF("Golf"),
		/**
		 * Fishing
		 */
		FISHING("Fishing");
		private String value;

		private Category(String value) {
			this.value = value;
		}

		protected String getValue() {
			return value;
		}
	}

}
