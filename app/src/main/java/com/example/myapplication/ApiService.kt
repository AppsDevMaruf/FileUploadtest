package com.example.myapplication

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * Created by Maruf Alam on 18/11/24.
 * marufalam120@gmail.com
 */
// ApiService.kt
interface ApiService {
    @Multipart
    @POST("services/contentservice/api/content/uploads")
    suspend fun uploadFiles(
        @Part files: List<MultipartBody.Part>,
        @Part("contentCategory") contentCategory: RequestBody
    ): Response<UploadResponse>
}