import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun main() = runBlocking(Dispatchers.IO) {
    val API_KEY = "d3e11f23ff604eda9d9100421250109"
    val cities = listOf("Chisinau", "Madrid", "Kyiv", "Amsterdam")


    val retrofit = Retrofit.Builder()
        .baseUrl("http://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(WeatherApiService::class.java)



    println("Forecast for tomorrow:")
    println("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+")
    println("| City            | Min Temp (°C)   | Max Temp (°C)   | Humidity (%)    | Wind Speed (kph)| Wind Direction  |")
    println("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+")


    val results = cities.map { city ->
        async {
            try {
                val response = service.getForecast(API_KEY, city)
                val tomorrowForecast = response.forecast.forecastday[1]
                val dayData = tomorrowForecast.day
                val windDirection = dayData.windDir


                WeatherData(
                    city,
                    dayData.mintempC,
                    dayData.maxtempC,
                    dayData.avghumidity,
                    dayData.maxwindKph,
                    windDirection
                )

            } catch (e: Exception) {
                println("Eroare la obținerea datelor pentru $city: ${e.message}")
                null
            }
        }
    }.mapNotNull { it.await() }




    results.forEach { (city, minTemp, maxTemp, humidity, windSpeed, windDir) ->
        System.out.printf("| %-15s | %-15.1f | %-15.1f | %-15.1f | %-15.1f | %-15s |\n",
            city, minTemp, maxTemp, humidity, windSpeed, windDir ?: "N/A")
    }

    println("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+")
}