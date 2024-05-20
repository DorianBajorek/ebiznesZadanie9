package com.example

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@CrossOrigin(origins = ["http://localhost:3000"], allowCredentials = "true")
@RestController
class ChatController {
    var communicator: GptCommunicator = GptCommunicator()
    @PostMapping("/api/getMessage")
    fun getMessage(@RequestBody message: String): String{
        println(message)
        return communicator.sendMessage(message)
    }
}