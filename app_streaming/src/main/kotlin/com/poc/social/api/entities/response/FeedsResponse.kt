package com.poc.social.api.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.poc.social.api.entities.request.FeedRequest

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeedsResponse(

    @JsonProperty("feeds")
    val feedRequests: MutableList<FeedRequest?>? = mutableListOf()

)
