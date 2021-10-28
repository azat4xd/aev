package com.fingerprintjs.android.pro.playgroundpro.demo.api


import com.fingerprintjs.android.pro.fingerprint.transport.Request
import com.fingerprintjs.android.pro.fingerprint.transport.RequestResultType
import com.fingerprintjs.android.pro.fingerprint.transport.RequestType
import com.fingerprintjs.android.pro.fingerprint.transport.TypedRequestResult
import org.json.JSONObject
import java.util.*


class Verdict(
    val name: String,
    val value: Boolean
)

class VerificationResult(
    val deviceId: String,
    val verdicts: List<Verdict>
)

class VerificationResultResponse(
    type: RequestResultType,
    rawResponse: ByteArray?
) : TypedRequestResult<VerificationResult>(type, rawResponse) {
    override fun typedResult(): VerificationResult? {
        val errorResponse = VerificationResult("", emptyList())
        val body = rawResponse?.toString(Charsets.UTF_8) ?: return errorResponse
        return try {
            val jsonBody = JSONObject(body)
            val deviceId = jsonBody.getString(DEVICE_ID_KEY)
            val results = jsonBody.getJSONObject(RESULTS_KEY)
            val verdictList = LinkedList<Verdict>()
            results.keys().forEach {
                val value = results.getJSONObject(it).getBoolean(VALUE_KEY)
                verdictList.add(Verdict(splitCamelCaseString(it), value))
            }
            VerificationResult(deviceId, verdictList)
        } catch (exception: Exception) {
            errorResponse
        }
    }

    private fun splitCamelCaseString(camelCaseString: String): String {
        return camelCaseString.map {
            if (it.isUpperCase()) {
                " ${it.toLowerCase()}"
            } else it
        }.joinToString("").capitalize()
    }
}

class GetResultsRequest(
    endpointUrl: String,
    autorizationToken: String,
    requestId: String
) : Request {
    override val url = "$endpointUrl/api/v1/results?id=${requestId}"
    override val type = RequestType.GET
    override val headers = mapOf(
        "Content-Type" to "application/json",
        "Auth-Token" to autorizationToken
    )

    override fun bodyAsMap() = emptyMap<String, Any>()
}

private const val DEVICE_ID_KEY = "deviceId"
private const val RESULTS_KEY = "results"
private const val VALUE_KEY = "v"
