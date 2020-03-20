package com.poc.social.api.routers

object ContactRouter {
    const val BASE_PATH = "/v1/contact"
    const val CREATE_CONTACT = "$BASE_PATH/create/{user_id}"
    const val GET_CONTACT = "$BASE_PATH/get/{user_id}"
}
