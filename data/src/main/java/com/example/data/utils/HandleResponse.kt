package com.example.data.utils

import com.example.data.extensions.ContextExtensions
import com.example.domain.common.ApiError
import com.example.domain.common.ApiException
import com.example.domain.common.ApiNoConnectivityError
import com.example.domain.common.ApiSuccess
import com.example.domain.common.NetworkResult
import com.example.domain.generic.GenericApiErrorModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.koin.java.KoinJavaComponent
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import retrofit2.HttpException
import retrofit2.Response
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

/**
 * @param runOnlyIfTokenExists When set to true, gets the token from [getUserTokenBlocking] and if empty returns [ApiError]
 */
suspend fun <T> handleResponse(
    execute: suspend () -> Response<T>,
): NetworkResult<T> =
    try {
        val contextExtensions = KoinJavaComponent.get<ContextExtensions>(ContextExtensions::class.java)
        if (!contextExtensions.isInternetAvailable())
            throw NoConnectivityException()

            val response = execute()
            val body = response.body()

            if (response.isSuccessful && body != null) {
                ApiSuccess(body)
            } else {
                var errorBody = response.errorBody()?.string()
                if (isXmlString(errorBody ?: "")) {
                    errorBody = convertXmlToJson(errorBody ?: "")
                }

                val errorModel: GenericApiErrorModel? = Gson().fromJson(errorBody, GenericApiErrorModel::class.java)
                ApiError(
                    networkCode = response.code(),
                    networkMessage = response.message(),
                    errorModel = errorModel ?: GenericApiErrorModel(message = errorBody)
                )
            }
    } catch (e: NoConnectivityException) {
        ApiNoConnectivityError(message = e.message)
    } catch (e: HttpException) {
        ApiError(networkCode = e.code(), networkMessage = e.message())
    } catch (e: Exception) {
        ApiException(e)
    }

fun convertXmlToJson(xmlString: String): String {
    val documentBuilderFactory = DocumentBuilderFactory.newInstance()
    val documentBuilder = documentBuilderFactory.newDocumentBuilder()
    val document = documentBuilder.parse(InputSource(StringReader(xmlString)))

    val rootNode = document.documentElement
    val jsonObject = buildJsonObject(rootNode)

    return jsonObject.toString()
}

private fun buildJsonObject(node: Node): JsonObject {
    val jsonObject = JsonObject()

    if (node is Element) {
        val childNodes = node.childNodes

        for (i in 0 until childNodes.length) {
            val childNode = childNodes.item(i)

            if (childNode is Element) {
                if (childNode.hasChildNodes() && childNode.childNodes.length == 1 && childNode.firstChild.nodeType == Node.TEXT_NODE) {
                    // Leaf node with text content
                    val jsonValue = childNode.firstChild.textContent.trim()
                    jsonObject.addProperty(childNode.nodeName, jsonValue)
                } else {
                    // Non-leaf node, recursively build a nested JSON object
                    jsonObject.add(childNode.nodeName, buildJsonObject(childNode))
                }
            }
        }
    }

    return jsonObject
}

private fun isXmlString(input: String): Boolean =
    try {
        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        val documentBuilder = documentBuilderFactory.newDocumentBuilder()
        documentBuilder.parse(InputSource(StringReader(input)))
        true
    } catch (e: Exception) {
        // Parsing failed, so it's not XML
        false
    }