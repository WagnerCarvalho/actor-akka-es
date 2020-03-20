package com.poc.social.api.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.poc.social.api.entities.Actor

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeedResponse(

    @JsonProperty("_source")
    val source: Source = Source()

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Source(

    @JsonProperty("region")
    val region: String = "",

    @JsonProperty("verb")
    val verb: String = "",

    @JsonProperty("actor")
    val actor: Actor = Actor()

)
