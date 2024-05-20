package com.example

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class GptCommunicator {
    val apiKey = "key:)"

    val client = OkHttpClient()

    val mediaType = "application/json; charset=utf-8".toMediaType()

    constructor(){
        prepareGptHistory()
    }

    fun displayContentAfterPhrase(jsonString: String): String {
        val contentPhrase = "\"content\": \""
        var result = ""

        var startIndex = jsonString.indexOf(contentPhrase)

        while (startIndex != -1) {
            val contentStartIndex = startIndex + contentPhrase.length
            val endIndex = jsonString.indexOf("\"", contentStartIndex)
            val content = jsonString.substring(contentStartIndex, endIndex)
            result += "$content"
            startIndex = jsonString.indexOf(contentPhrase, endIndex)
        }
        return result
    }

    fun isSpecialStart(prompt: String): Boolean {
        if(prompt == "Hello" || prompt == "Hi" || prompt == "What can i buy here?" || prompt == "What is it?") {
            return true
        }
        return false
    }

    fun isSpecialEnd(prompt: String): Boolean {
        if(prompt == "Bye" || prompt == "thanks for help" || prompt == "Thank you, goodbye" || prompt == "Have a good one" || prompt == "no, thanks its all"){
            return true
        }
        return false;
    }

    fun removeLastLetter(input: String): String{
        return input.substring(0, input.length - 1)
    }

    fun sendMessage(prompt: String): String{
        var promptToSend = removeLastLetter(prompt)
        println(promptToSend)
        if(isSpecialStart(promptToSend)){
            promptToSend = "The question is: " + promptToSend + ", get answer like that: We are a shop with the best clothes and here you can buy shoes, t-shirts, and many others special clother!"
        } else if(isSpecialEnd(prompt)){
            promptToSend = "The question is: " + promptToSend + ", get answer like that: Thanks for visiting our shop and see you in the future!"
        } else {
            promptToSend = promptToSend + ". Remember you are a shop assistant and you offer T-shirt (10$ price), shoes (40$ price), jeans (30$ price) and no more"
        }
        val requestBody = """
            {
                "model": "gpt-4-turbo",
                "messages": [
                    {
                        "role": "user",
                        "content": "$promptToSend"
                    }
                ],
                "max_tokens": 50
            }
        """.trimIndent().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) println("JEST JAKAS LIPKA")

            val responseBody = response.body?.string()
            return displayContentAfterPhrase(responseBody ?: "")
        }
    }

    fun prepareGptHistory(){
        var configureBotPrompt: String = "Image you are a assistant of shop and in your shop you can sell: T-shirt (10$ price), shoes (40$ price), jeans (30$ price) and no more. Each your answer should be about this shop"
        println(sendMessage(configureBotPrompt))
    }
}