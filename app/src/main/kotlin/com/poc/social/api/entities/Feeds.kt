package com.poc.social.api.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Feeds(

    @JsonProperty("feeds")
    val feeds: MutableList<Feed?>? = mutableListOf()

)