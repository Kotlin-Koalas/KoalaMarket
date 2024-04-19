package com.example.smarttrade.volleyRequestClasses

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException

class VolleyMultipartRequest(
    method: Int,
    url: String,
    private val mListener: Response.Listener<NetworkResponse>,
    errorListener: Response.ErrorListener
) : Request<NetworkResponse>(method, url, errorListener) {

    private val mHeaders = mutableMapOf<String, String>()
    private val mParams = mutableMapOf<String, String>()
    private val mByteData = mutableMapOf<String, DataPart>()

    override fun getHeaders(): MutableMap<String, String> = mHeaders

    fun addHeader(key: String, value: String) {
        mHeaders[key] = value
    }

    fun addParam(key: String, value: String) {
        mParams[key] = value
    }
    //la key debe ser "file"
    fun addByteData(key: String, fileName: String, data: ByteArray, mimeType: String) {
        mByteData[key] = DataPart(fileName, data, mimeType)
    }

    override fun getBodyContentType(): String {
        return "multipart/form-data;boundary=$BOUNDARY"
    }

    override fun getBody(): ByteArray {
        val bos = ByteArrayOutputStream()
        val dos = DataOutputStream(bos)

        try {
            // populate text payload
            for ((key, value) in mParams) {
                buildTextPart(dos, key, value)
            }

            // populate data byte payload
            for ((key, dataPart) in mByteData) {
                buildDataPart(dos, key, dataPart)
            }

            dos.writeBytes(TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + LINE_END)
            return bos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return super.getBody()
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<NetworkResponse> {
        return try {
            Response.success(response, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: NetworkResponse) {
        mListener.onResponse(response)
    }

    @Throws(IOException::class)
    private fun buildTextPart(dataOutputStream: DataOutputStream, parameterName: String, parameterValue: String) {
        dataOutputStream.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END)
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"$parameterName\"$LINE_END")
        dataOutputStream.writeBytes(LINE_END)
        dataOutputStream.writeBytes(parameterValue + LINE_END)
    }

    @Throws(IOException::class)
    private fun buildDataPart(dataOutputStream: DataOutputStream, parameterName: String, dataPart: DataPart) {
        dataOutputStream.writeBytes(TWO_HYPHENS + BOUNDARY + LINE_END)
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"$parameterName\"; filename=\"${dataPart.fileName}\"$LINE_END")
        dataOutputStream.writeBytes("Content-Type: ${dataPart.type}$LINE_END")
        dataOutputStream.writeBytes(LINE_END)

        val fileInputStream = ByteArrayInputStream(dataPart.content)
        var bytesAvailable = fileInputStream.available()

        val maxBufferSize = 1024 * 1024
        var bufferSize = Math.min(bytesAvailable, maxBufferSize)
        val buffer = ByteArray(bufferSize)

        // read file and write it into form...
        var bytesRead = fileInputStream.read(buffer, 0, bufferSize)

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize)
            bytesAvailable = fileInputStream.available()
            bufferSize = Math.min(bytesAvailable, maxBufferSize)
            bytesRead = fileInputStream.read(buffer, 0, bufferSize)
        }

        dataOutputStream.writeBytes(LINE_END)
    }

    data class DataPart(val fileName: String, val content: ByteArray, val type: String)

    companion object {
        private const val LINE_END = "\r\n"
        private const val TWO_HYPHENS = "--"
        private val BOUNDARY = "apiclient-" + System.currentTimeMillis()
    }
}