package mod3.case2.api

import mod3.case2.models.RequestModel
import mod3.case2.models.ResponseModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("compare")
    suspend fun compareModels(@Body request: RequestModel): ResponseModel
}

object RetrofitClient {
    // Use 10.0.2.2 for Android Emulator to connect to localhost
    private const val BASE_URL = "http://10.0.2.2:8000/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}