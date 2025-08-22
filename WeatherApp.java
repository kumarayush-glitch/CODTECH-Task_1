import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import org.json.JSONObject;

public class WeatherApp {

    private static final String API_KEY = "667ae1bccf441d9f599c9aa1722ec2ea";
    private static final String CITY_NAME = "Delhi";

    public static void main(String[] args) {
        String apiUrl = String.format(
                "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                CITY_NAME, API_KEY
        );

        try {
            String jsonResponse = fetchWeatherData(apiUrl);
            if (jsonResponse != null) {
                parseAndDisplay(jsonResponse);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String fetchWeatherData(String url) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        }
        else {
            System.err.println("Error fetching data. Status code: " + response.statusCode());
            return null;
        }
    }

    public static void parseAndDisplay(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String cityName = jsonObject.getString("name");
        JSONObject main = jsonObject.getJSONObject("main");
        double temp = main.getDouble("temp");
        double feelsLike = main.getDouble("feels_like");
        int humidity = main.getInt("humidity");
        String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

        System.out.println("--- Weather Report for " + cityName + " ---");
        System.out.println("Description: " + description);
        System.out.println("Temperature: " + temp + " °C");
        System.out.println("Feels Like: " + feelsLike + " °C");
        System.out.println("Humidity: " + humidity + " %");
        System.out.println("-------------------------------------");
    }
}