package com.example.myapplication

import android.content.Context
import android.net.Uri
import android.os.FileUtils
import okhttp3.MultipartBody

/**
 * Created by Maruf Alam on 18/11/24.
 * marufalam120@gmail.com
 */
// FileUploadRepository.kt
class FileUploadRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun uploadFiles(
        fileUris: List<Uri>,
        contentCategory: String,
        context: Context
    ): Result<UploadResponse> = try {
        val parts = fileUris.map { uri ->
            val file = FileUtils.getFileFromUri(context, uri)
            val requestFile = file.asRequestBody("application/octet-stream".toMediaType())
            MultipartBody.Part.createFormData(
                "files",
                file.name,
                requestFile
            )
        }

        val categoryPart = contentCategory.toRequestBody("text/plain".toMediaType())
        val response = apiService.uploadFiles(parts, categoryPart)

        if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Upload failed: ${response.message()}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}