package com.psyluckco.firebase

interface LanguageService {
    suspend fun convertToDeviceLanguage(text: String) : String
    fun deleteModel()
}