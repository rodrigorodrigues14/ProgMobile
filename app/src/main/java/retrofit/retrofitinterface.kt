package retrofit
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface retrofitInterface {
    @GET("/api/symbols")
    suspend fun getSimbolos(): Response<List<String>>

    @GET("/api/symbol/summary/{symbol}")
    suspend fun getSimboloSumario(@Path("symbol") symbol: String) : Response<Sumario>

    @GET("/api/symbol/details/{symbol}")
    suspend fun getSimboloDetalhes(@Path("symbol") symbol: String) : Response<Detalhes>

    @GET("/api/news")
    suspend fun getNews() : Response<List<News>>
}
