package com.poc.social.api.module.es

import com.poc.social.api.entities.Feed
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
        @Path("id") id: Long
    ): Single<FeedResponse>

    @GET("/social_contact/_doc/{id}")
    fun getContact(
        @Path("id") id: Long
    ): Single<ContactResponse>
}
