package com.example.wwo.timezone;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.example.wwo.Location;
import com.example.wwo.Request;
import com.example.wwo.Location.LocationType;

public class TimeZone {
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
	public TimeZone(PackageType packageType, String key,
			LocationType locationType, String location)
			throws IllegalArgumentException {
		Location.validateLocation(locationType, location);
		this.packageType = packageType;
		this.parameters = new HashMap<String, String>();
		parameters.put("key", key);
		parameters.put("q", location);
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
	 * The output format to return. XML, JSON, CSV (comma-separated values), or
	 * tab (tab-separated values). Default is XML.
	 * 
	 * @param format
	 *            the format to return.
	 * @return a reference to this object.
	 */
	public TimeZone setFormat(Format format) {
		parameters.put("format", format.getValue());
		return this;
	}

	/**
	 * The function name for JSON callback.
	 * 
	 * @param function
	 *            the function name.
	 * @return a reference to this object. TODO: function name validation
	 */
	public TimeZone setCallback(String function) {
		parameters.put("callback", function);
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
		PREMIUM("/premium/v1/tz.ashx"),
		/**
		 * Free API.
		 */
		FREE("/free/v2/tz.ashx");
		private String baseUrl;

		private PackageType(String baseUrl) {
			this.baseUrl = baseUrl;
		}

		protected String getBaseUrl() {
			return baseUrl;
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
		JSON("json");

		private String value;

		private Format(String value) {
			this.value = value;
		}

		protected String getValue() {
			return value;
		}

	}

}
