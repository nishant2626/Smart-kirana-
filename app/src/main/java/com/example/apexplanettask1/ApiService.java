package com.example.apexplanettask1;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    // Firebase REST API requires .json at the end of the path
    
    @GET("products.json")
    Call<Map<String, Product>> getProducts();

    @POST("products.json")
    Call<Map<String, String>> addProduct(@Body Product product);
}