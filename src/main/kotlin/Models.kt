import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val forecast: Forecast
)


data class Forecast(
    val forecastday: List<Forecastday>
)


data class Forecastday(
    val date: String,
    val day: Day
)


data class Day(
    @SerializedName("maxtemp_c")
    val maxtempC: Double,
    @SerializedName("mintemp_c")
    val mintempC: Double,
    @SerializedName("avghumidity")
    val avghumidity: Double,
    @SerializedName("maxwind_kph")
    val maxwindKph: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    val condition: Condition
)


data class Condition(
    val text: String
)


data class Wind(
    @SerializedName("wind_dir")
    val windDir: String
)


data class WeatherData(
    val city: String,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Double,
    val windSpeed: Double,
    val windDir: String?
)