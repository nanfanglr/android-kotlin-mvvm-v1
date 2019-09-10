package com.rui.retrofit2

import android.content.res.AssetManager
import java.io.InputStream
import java.util.*

/**
 *Created by rui on 2019/8/1
 */
class PropertiesManager constructor(assetManager: AssetManager, debug: Boolean) {
    private val PROPERTIES_FILENAME = "project.properties"
    private var properties: Properties
    var isDebug: Boolean = false
    var apiToken: String? = ""

    init {
        isDebug = debug;
        properties = Properties()
        val inputStream: InputStream = assetManager.open(PROPERTIES_FILENAME)
        properties.load(inputStream)
        inputStream.close()
    }

    fun getBaseUrl(): String {
        val baseUrl = if (isDebug) properties.getProperty(Property.BASE_URL_DEV.propertyKey)
        else properties.getProperty(Property.BASE_URL.propertyKey)
        return baseUrl
    }


}

private enum class Property constructor(val propertyKey: String) {
    BASE_URL("dribbleBaseUrl"),
    BASE_URL_DEV("dribbleBaseUrlDev")
}