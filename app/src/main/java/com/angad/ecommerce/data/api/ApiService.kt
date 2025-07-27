package com.angad.ecommerce.data.api

import com.angad.ecommerce.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("productdetails/{productId}/{variantId}")
    suspend fun getProductDetails(
        @Path("productId") productId: String,
        @Path("variantId") variantId: String,
        @Query("lang") lang: String = "en",
        @Query("store") store: String = "KWD"
    ): Response<ProductResponse>
}