package data.network

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object LlmApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
        defaultRequest {
            url("https://api.openai.com/")
            header("Authorization", "Bearer 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")
        }
    }

    suspend fun chat(): HttpResponse {
        return httpClient.get("v1/chat/completions") {

        }
    }

}
//request
class OpenaiAssistantRequest(
    val engine: String,
    val prompt: String,
    val maxTokens: Int,
    val temperature: Double,
    val topP: Double,
    val n: Int,
    val stream: Boolean,
    val logProbs: Int,
    val stop: List<String>,
    val presencePenalty: Double,
    val frequencyPenalty: Double,
    val bestOf: Int,
    val echo: Boolean,
    val user: String,
    val model: String,
    val messages: List<Message>
)

class Message(
    val role: String,
    val content: String,
)
//"model": "gpt-3.5-turbo",
//"messages": [{"role": "user", "content": "Say this is a test!"}],
//"temperature": 0.7"

//response
