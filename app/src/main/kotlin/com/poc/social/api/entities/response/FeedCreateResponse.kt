package com.poc.social.api.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeedCreateResponse(

    @JsonProperty("_index")
    val _index: String = "",

    @JsonProperty("_id")
    val _id: String = "",

    @JsonProperty("result")
    val result: String = ""

)