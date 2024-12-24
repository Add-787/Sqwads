package com.psyluckco.firebase.impl

import android.os.LocaleList
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.psyluckco.firebase.LanguageService
import com.psyluckco.sqwads.core.model.Response
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LanguageServiceImpl @Inject constructor(): LanguageService{
    private val languageIdentifier = LanguageIdentification.getClient()

    private fun getDeviceLanguage() : String{
        return LocaleList.getDefault().get(0).language
    }

    private suspend fun detectLanguage(text: String) : String {
        return try {
            languageIdentifier.identifyLanguage(text).await()
        }catch (e: Exception){
            ""
        }
    }

    override suspend fun convertToDeviceLanguage(text: String): String {
        return try {

            val sourceLanguage = detectLanguage(text)
            val targetLanguage = getDeviceLanguage()
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.fromLanguageTag(sourceLanguage) ?: TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.fromLanguageTag(targetLanguage) ?: TranslateLanguage.ENGLISH)
                .build()
            val translator = Translation.getClient(options)
            val conditions = DownloadConditions.Builder()
                .requireWifi()
                .build()

            translator.downloadModelIfNeeded(conditions).await()

            val translatedText = translator.translate(text).await()
            translator.close()
            translatedText
        }catch (e: Exception){
            e.message ?: "ERROR - Could not translate"
        }
    }

    override fun deleteModel(){
        val modelManager = RemoteModelManager.getInstance()
        modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
            .addOnSuccessListener { models ->
                println(models)
                for (model in models){
                    modelManager.deleteDownloadedModel(model)
                        .addOnSuccessListener {
                            println("Model deleted")
                        }.addOnFailureListener {
                            println("Could not delete models")
                        }
                }
            }
            .addOnFailureListener {

            }
    }
}