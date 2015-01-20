package com.example.wwo.skiweather;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.wwo.Location;
import com.example.wwo.Request;
import com.example.wwo.Location.LocationType;

public class SkiWeather {
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
	 * @param numberOfDays
	 *            number of days of forecast.
	 * @throws IllegalArgumentException
	 *             if location is not valid. TODO: numberOfDays validation
	 */
	public SkiWeather(PackageType packageType, String key,
			LocationType locationType, String location, int numberOfDays)
			throws IllegalArgumentException {
		Location.validateLocation(locationType, location);
		this.packageType = packageType;
		this.parameters = new HashMap<String, String>();
		parameters.put("key", key);
		parameters.put("q", location);
		parameters.put("num_of_days", String.valueOf(numberOfDays));
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
	 * Include extra informations. You can also specify multiple values.
	 * 
	 * @param extras
	 *            the extra informations.
	 * @return a reference to this object.
	 */
	public SkiWeather includeExtraInformations(Extra... extras) {
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
	 * Specifies weather for a date.
	 * 
	 * @param date
	 *            The date which the weather wanted.
	 * @return a reference to this object.
	 */
	public SkiWeather date(Date date) {
		String dateString = DATE_FORMAT.format(date);
		parameters.put("date", dateString);
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
	public SkiWeather isIncludeLocation(boolean includeLocation) {
		parameters.put("includeLocation", includeLocation ? "yes" : "no");
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
	public SkiWeather setFormat(Format format) {
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
	public SkiWeather setCallback(String function) {
		parameters.put("callback", function);
		return this;
	}

	/**
	 * Returns weather description in other languages.
	 * 
	 * @param language
	 *            the language needed.
	 * @return a reference to this object.
	 */
	public SkiWeather setOtherLanguage(Language language) {
		parameters.put("lang", language.getValue());
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
		PREMIUM("/premium/v1/ski.ashx"),
		/**
		 * Free API.
		 */
		FREE("/free/v2/ski.ashx");
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
		IS_DAY_TIME("isDayTime");

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
		JSON("json");

		private String value;

		private Format(String value) {
			this.value = value;
		}

		protected String getValue() {
			return value;
		}

	}

	/**
	 * Languages
	 * 
	 * @author Fanni_Varga
	 * 
	 */
	public static enum Language {
		ARABIC("ar"), BENGALI("bn"), BULGARIAN("bg"), CHINESE_SIMPLIFIED("zh"), CHINESE_TRADITIONAL(
				"zh_tw"), CZECH("cs"), DUTCH("nl"), FINNISH("fi"), FRENCH("fr"), GERMAN(
				"de"), GREEK("el"), HINDI("hi"), HUNGARIAN("hu"), ITALIAN("it"), JAPANESE(
				"ja"), JAVANESE("jv"), KOREAN("ko"), MANDARIN("zh_cmn"), MARATHI(
				"mr"), POLISH("pl"), PORTUGUESE("pt"), PUNJABI("pa"), ROMANIAN(
				"ro"), RUSSIAN("ru"), SERBIAN("sr"), SINHALESE("si"), SLOVAK(
				"sk"), SPANISH("es"), SWEDISH("sv"), TAMIL("ta"), TELUGU("te"), TURKISH(
				"tr"), UKRAINIAN("uk"), URDU("ur"), Vietnamese("vi"), WU_SHANGHAINESE(
				"zh_wuu"), XIANG("zh_hsn"), YUE_CANTONESE("zh_yue"), ZULU("zu");
		private String value;

		private Language(String value) {
			this.value = value;
		}

		protected String getValue() {
			return value;
		}
	}

}
