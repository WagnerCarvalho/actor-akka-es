package com.poc.social.api.entities.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.poc.social.api.entities.Actor

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeedCreateRequest(

    @JsonProperty("region")
    val region: String = "",

    @JsonProperty("verb")
    val verb: String = "",

    @JsonProperty("actor")
    val actor: Actor = Actor()

)