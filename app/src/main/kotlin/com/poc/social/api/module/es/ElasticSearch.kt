package com.poc.social.api.module.es

import com.poc.social.api.entities.Feed
import com.poc.social.api.entities.request.ContactRequest
import com.poc.social.api.entities.response.ContactResponse
import com.poc.social.api.entities.response.FeedCreateResponse
import com.poc.social.api.entities.response.FeedResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ElasticSearch {

    @PUT("/social_feed/_doc/{id}")
    fun createFeed(
        @Path("id") id: String,
        @Body request: Feed
    ): Single<FeedCreateResponse>

    @GET("/social_feed/_doc/{id}")
    fun getFeed(
        @Path("id") id: String
    ): Single<FeedResponse>

    @PUT("/social_contact/_doc/{user_id}")
    fun createContact(
        @Path("user_id") userId: Long,
        @Body request: ContactRequest
    ): Single<ContactResponse>

    @GET("/social_contact/_doc/{user_id}")
    fun getContact(
        @Path("user_id") user_id: Long
    ): Single<ContactResponse>
}
