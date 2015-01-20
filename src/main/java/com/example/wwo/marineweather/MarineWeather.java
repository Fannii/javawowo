package com.example.wwo.marineweather;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.example.wwo.Location;
import com.example.wwo.Request;
import com.example.wwo.Location.LocationType;

public class MarineWeather {
	protected static final String HOST_URL = "api.worldweatheronline.com";
	protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private Map<String, String> parameters;
	private PackageType packageType;

	/**
	 * TODO:description
	 * 
	 * @param packageType
	 *            the package type
	 * @param key
	 *            the API key.
	 * @param location
	 *            Comma separated longitude and latitude in degrees.
	 * @throws IllegalArgumentException
	 *             if location is not valid.
	 */
	public MarineWeather(PackageType packageType, String key, String location)
			throws IllegalArgumentException {
		Location.validateLocation(LocationType.LAT_AND_LONG, location);
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
	 * Whether to return weather forecast output. Default is true.
	 * 
	 * @param fx
	 *            If needed true, else false.
	 * @return a reference to this object.
	 */
	public MarineWeather needForecastOutput(boolean fx) {
		parameters.put("fx", fx ? "yes" : "no");
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
	public MarineWeather setFormat(Format format) {

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
	public MarineWeather setCallback(String function) {
		parameters.put("callback", function);
		return this;
	}

	/**
	 * Specifies the weather forecast time interval in hours. Default is three
	 * hourly.
	 * 
	 * @param interval
	 *            the interval which needed.
	 * @return a reference to this object.
	 */
	public MarineWeather setForecastTimeInterval(Interval interval) {
		parameters.put("tp", String.valueOf(interval.getValue()));
		return this;
	}

	/**
	 * Set true to return tide data information if available. Default is false.
	 * 
	 * @param tide
	 *            If tide data is needed true, else false.
	 * @return a reference to this object.
	 */
	public MarineWeather needTideData(boolean tide) {
		parameters.put("tide", tide ? "yes" : "no");
		return this;
	}

	/**
	 * Returns weather description in other languages.
	 * 
	 * @param language
	 *            the language needed.
	 * @return a reference to this object.
	 */
	public MarineWeather setOtherLanguage(Language language) {
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
		PREMIUM("/premium/v1/marine.ashx"),
		/**
		 * Free API.
		 */
		FREE("/free/v2/marine.ashx");
		private String baseUrl;

		private PackageType(String baseUrl) {
			this.baseUrl = baseUrl;
		}

		protected String getBaseUrl() {
			return baseUrl;
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
