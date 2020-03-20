package com.poc.social.api.routers

object FeedRouter {
    const val BASE_PATH = "/v1/feed"
    const val CREATE_FEED = "$BASE_PATH/create"
    const val CREATE_SOCIAL_FEED = "$BASE_PATH/create-social"
    const val GET_FEED = "$BASE_PATH/get/{id}"
}
