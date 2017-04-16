package my.code;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import my.code.open.weather.OpenWeather;

public class WeatherApp {

	private static final String URL_API = "http://api.openweathermap.org/data/2.5/weather";
	private static final String CITY = "LODZ,pl";
	private static final String API_KEY = "76f6ab49754d6d89b14ed0a2f282cc9a";

	public static void main(String[] args) {
		System.out.println("Weather application");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		URI uri = createURI();

		String json = getJSON(httpclient, uri);

		OpenWeather weather = deserialization(json);

		showWeather(weather);
	}

	private static OpenWeather deserialization(String json) {
		Gson gson = new Gson();
		OpenWeather weather = null;

		if (json == null || json.isEmpty()) {
			System.out.println("Serwer nie przekazał danych! świnia!");
		}

		weather = gson.fromJson(json, OpenWeather.class);
		if (weather == null) {
			System.out.println("Deserializacja się nie udała");
		}
		return weather;
	}

	private static void showWeather(OpenWeather weather) {
		System.out.println("Current weather");

		System.out.println(weather.getName());
		if (weather.getWeather() != null && !weather.getWeather().isEmpty()) {
			System.out.println(weather.getWeather().get(0).getDescription());
		}
		if (weather.getMain() != null) {
			System.out.println(weather.getMain().getTemp());
		}
	}

	private static String getJSON(CloseableHttpClient httpclient, URI uri) {
		HttpGet httpGet = new HttpGet(uri);

		String json = null;
		try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			json = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	private static URI createURI() {
		CityQuery cityQuery = new CityQuery("LODZ", Countries.POLAND.getCountryCode());
		
		URI uri = null;
		try {
			uri = new URIBuilder(URL_API).setParameter("q", CITY).setParameter("appid", API_KEY).setParameter("lang", "PL").addParameter("units", "metric").build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return uri;
	}

}
