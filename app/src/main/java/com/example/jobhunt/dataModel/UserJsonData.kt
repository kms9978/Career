package com.example.jobhunt.DataModel

import com.google.gson.JsonObject

class UserJsonData {
    companion object {
        // JSON 객체를 생성하는 메소드
        fun createJsonObject(username: String, nickname: String, password: String): JsonObject {
            return JsonObject().apply {

                // JsonObject에 username, nickname, password를 추가
                addProperty("username", username)
                addProperty("nickname", nickname)
                addProperty("password", password)
            }
        }
    }
}