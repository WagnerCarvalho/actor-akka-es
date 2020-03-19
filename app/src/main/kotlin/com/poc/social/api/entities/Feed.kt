package com.poc.social.api.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.poc.social.api.entities.Actor

@JsonIgnoreProperties(ignoreUnknown = true)
data class Feed(

    @JsonProperty("region")
    val region: String = "",

    @JsonProperty("id")
    val id: Long? = null,

    @JsonProperty("verb")
    val verb: String = "",

    @JsonProperty("actor")
    val actor: Actor = Actor()

)