package com.example.wwo.historicalweather;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.wwo.Location;
import com.example.wwo.Request;
import com.example.wwo.Location.LocationType;

/**
 * The Premium Past Weather REST API method allows you to access weather
 * conditions from 1st July 2008 up until the present time. The API returns
 * weather elements such as temperature, precipitation (rainfall), weather
 * description, weather icon and wind speed.
 * 
 * @author Fanni_Varga
 * 
 */
public class HistoricalWeather {
	protected static final String HOST_URL = "api.worldweatheronline.com";
	protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private Map<String, String> parameters;
	private PackageType packageType;

	/**
	 * TODO:description Location type: - City or town name Examples: "New York"
	 * "New York, NY" "London, United Kingdom" - IP address Example:
	 * "101.25.32.325" - UK or Canada Postal Code or US Zipcode Examples: "SW1"
	 * "90201" - Latitude and longitude Example: "48.834,2.394"
	 * 
	 * @param packageType
	 *            the package type
	 * @param key
	 *            the API key.
	 * @param locationType
	 *            the location type
	 * @param location
	 *            the location.
	 * @param date
	 *            the date to return the weather for.
	 * @throws IllegalArgumentException
	 *             if location is not valid.
	 */
	public HistoricalWeather(PackageType packageType, String key,
			LocationType locationType, String location, Date date)
			throws IllegalArgumentException {
		Location.validateLocation(locationType, location);
		this.packageType = packageType;
		this.parameters = new HashMap<String, String>();
		parameters.put("key", key);
		parameters.put("q", location);
		parameters.put("date", DATE_FORMAT.format(date));
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
	 * Include extra information.
	 * 
	 * @param the
	 *            extras extra informations.
	 * @return a reference to this object.
	 */
	public HistoricalWeather includeExtraInformations(Extra... extras) {
		String ret = "";
		if (parameters.containsKey("extra")) {
			String equalsExtras = parameters.get("extra");
			StringBuffer sb = new StringBuffer();
			sb.append(equalsExtras);
			for (Extra e : extras) {
				if (!equalsExtras.contains(e.getValue())) {
					equalsExtras += "," + e.getValue();
				}
			}
			ret = sb.toString();
		} else {
			StringBuffer sb = new StringBuffer();
			for (Extra e : extras) {
				sb.append(e.getValue()).append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
			ret = sb.toString();
		}
		parameters.put("extra", ret);
		return this;
	}

	/**
	 * If you wish to retrieve weather between two dates, use this parameter to
	 * specify the ending date. Important: the enddate parameter must have the
	 * same month and year as the date parameter.
	 * 
	 * @param endDate
	 *            the end date.
	 * @return a reference to this object.
	 * @throws IllegalArgumentException
	 *             when the enddate parameter is not in the same month and year
	 *             as the date parameter, or if the enddate is before date.
	 */
	@SuppressWarnings("deprecation")
	public HistoricalWeather setEndDate(Date endDate) {
		try {
			Date date = DATE_FORMAT.parse(parameters.get("date"));
			if (endDate.before(date)) {
				throw new IllegalArgumentException(
						"The enddate must be after the date.");
			} else {
				if (endDate.getMonth() != date.getMonth()) {
					throw new IllegalArgumentException(
							"The enddate parameter must have the same month as the date parameter.");
				} else if (endDate.getYear() != date.getYear()) {
					throw new IllegalArgumentException(
							"The  enddate parameter must have the same year as the date parameter.");
				}
			}
		} catch (ParseException e) {
			System.err.println(e.getMessage());
		}
		String dateString = DATE_FORMAT.format(endDate);
		parameters.put("enddate", dateString);
		return this;
	}

	/**
	 * Whether to return the nearest weather point for which the weather data is
	 * returned for a given postcode, zipcode and lat/lon values. Default is
	 * false.
	 * 
	 * @param includeLocation
	 *            If include location true, else false.
	 * @return a reference to this object.
	 */
	public HistoricalWeather isIncludeLocation(boolean includeLocation) {
		parameters.put("includeLocation", includeLocation ? "yes" : "no");
		return this;
	}

	/**
	 * Specifies the weather forecast time interval in hours. Default is three
	 * hourly.
	 * 
	 * @param interval
	 *            the interval wich needed.
	 * @return a reference to this object.
	 */
	public HistoricalWeather setForecastTimeInterval(Interval interval) {
		parameters.put("tp", String.valueOf(interval.getValue()));
		return this;
	}

	/**
	 * The output format to return. XML, JSON, CSV (comma-separated values), or
	 * tab (tab-separated values). Default is XML.
	 * 
	 * @param format
	 *            the format to return.
	 * @return a reference to this object.
	 */
	public HistoricalWeather setFormat(Format format) {
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
	public HistoricalWeather setCallback(String function) {
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
		PREMIUM("/premium/v1/past-weather.ashx"),
		/**
		 * Free API.
		 */
		FREE("/free/v2/past-weather.ashx");
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
		UTC_DATE_TIME("utcDateTime");

		private String value;

		private Extra(String value) {
			this.value = value;
		}

		protected String getValue() {
			return value;
		}
	}

	/**
	 * Options for forecast time intervals.
	 * 
	 * @author Fanni_Varga
	 * 
	 */
	public static enum Interval {
		/**
		 * 1 hour.
		 */
		ONE_HOUR(1),
		/**
		 * 3 hourly.
		 */
		THREE_HOURLY(3),
		/**
		 * 6 hourly
		 */
		SIX_HOURLY(6),
		/**
		 * 12 hourly (day/night)
		 */
		TWELVE_HOURLE(12),
		/**
		 * 24 hourly (day avarage)
		 */
		DAY_AVARAGE(24);

		private int value;

		private Interval(int value) {
			this.value = value;
		}

		protected int getValue() {
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
		 * CSV
		 */
		CSV("csv"),
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

}
