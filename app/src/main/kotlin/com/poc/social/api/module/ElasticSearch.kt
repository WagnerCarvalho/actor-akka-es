package com.poc.social.api.module

import com.poc.social.api.entities.request.FeedCreateRequest
import com.poc.social.api.entities.response.FeedCreateResponse
import com.poc.social.api.entities.response.FeedResponse
import io.reactivex.Single
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.GET

interface ElasticSearch {

    @PUT("/social_feed/_doc/{id}")
    fun createCard(
        @Path("id") id: String,
        @Body request: FeedCreateRequest
    ): Single<FeedCreateResponse>

    @GET("/social_feed/_doc/{id}")
    fun get(
        @Path("id") id: String
    ): Single<FeedResponse>
}