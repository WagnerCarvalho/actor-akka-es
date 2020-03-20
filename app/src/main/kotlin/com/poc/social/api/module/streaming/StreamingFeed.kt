package com.poc.social.api.module.streaming

import com.poc.social.api.entities.response.FeedContact
import com.poc.social.api.entities.response.FeedCreateResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface StreamingFeed {

    @POST("/feeds")
    fun sendFeedSocial(
        @Body request: FeedContact
    ): Single<FeedCreateResponse>

}